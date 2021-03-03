package com.woniu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.woniu.domain.Permission;
import com.woniu.domain.Role;
import com.woniu.domain.User;
import com.woniu.dto.Result;
import com.woniu.dto.StatusCode;
import com.woniu.service.RoleService;
import com.woniu.service.UserService;
import com.woniu.utils.JwtUtils;
import com.woniu.utils.Mysalt;
import com.woniu.vo.Tadd;
import com.woniu.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhouli
 * @since 2021-02-02
 */
@RestController
@RequestMapping("/user")
//@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;

  @DeleteMapping("deleteOneUser/{uid}")
  public Result deleteUser(@PathVariable Integer uid){
      boolean b = userService.removeById(uid);
      if(b)
      return new Result(true,StatusCode.OK,"删除用户成功");
      return new Result(false,StatusCode.Error,"删除用户失败");
  }

    @PutMapping("updateOneUser")
    public Result updateUser(@RequestBody User user){
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",user.getId());
        boolean b = userService.update(user, wrapper);
        if(b)
            return new Result(true,StatusCode.OK,"修改用户成功");
        return new Result(false,StatusCode.Error,"修改用户失败");
    }




    @PostMapping("register")
    public Result register(@RequestBody UserVo userVo){
        System.out.println("register==="+userVo);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userVo.getUsername());
        User userDB = userService.getOne(queryWrapper);
        if(ObjectUtils.isEmpty(userDB)){
            User user=new User();
            String salt = Mysalt.getSalt(8);
            Md5Hash md5Hash=new Md5Hash(userVo.getPassword(),salt,1024);
            user.setUsername(userVo.getUsername());
            user.setPassword(md5Hash.toHex());
            user.setSalt(salt);
            boolean save = userService.save(user);
            if(save){
                return new Result(true, StatusCode.OK,"注册成功");
            }
            return new Result(false, StatusCode.ERRORREGISTER,"注册失败");
        }
        return new Result(false, StatusCode.MUCHNAME,"注册名重复");
    }


    @PostMapping("login")
    public Result login(@RequestBody UserVo userVo){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userVo.getUsername());
        User userDB = userService.getOne(queryWrapper);

        if(!ObjectUtils.isEmpty(userDB)){
            Md5Hash md5Hash = new Md5Hash(userVo.getPassword(), userDB.getSalt(), 2048);
            String md5Password = md5Hash.toHex();
            if(userDB.getPassword().equals(md5Password)){
                //登陆成功
                HashMap<String, String> map = new HashMap<>();
                map.put("username",userVo.getUsername());
//                map.put("id",userDB.getId().toString());
                String jwtToken = JwtUtils.getCodeToken(map);
                Tadd  tadd=new Tadd();
                tadd.setJwtToken(jwtToken);
                tadd.setUser(userDB);
                return new Result(true, StatusCode.OK,"登陆成功",tadd);
            }
            return new Result(false, StatusCode.IncorrectCredentials,"凭证错误");
        }
        return new Result(false, StatusCode.UnknownAccount,"该用户不存在");
    }

    //得到所有的权限菜单
    @GetMapping("getMenus/{uid}")
    public Result getMenus(@PathVariable Integer uid){
        System.out.println("userID=====>>>>>"+uid);
        Set<Permission> allPermission = userService.findAllPermission(uid);
        return new Result(true,StatusCode.OK,"查询权限菜单成功",allPermission);
    }

    //获取所有的用户
    @GetMapping("getUsers")
    @RequiresRoles(value = {"董事长","人事总监"},logical = Logical.OR)
    public Result getUsers(){
        List<User> users = userService.list(null);
        return new Result(true,StatusCode.OK,"查询用户信息成功",users);
    }

    //根据用户名和权限id获取对应的三级权限目录
    @GetMapping("getThirdPermissionByUsername/{username}/{pid}")
    public Result getThirdPermissionByUsername(@PathVariable String username,@PathVariable Integer pid){
        System.out.println("username===>>>"+username+":"+pid);
        List<Permission> permissions = userService.getPermissions(username,pid);
        return new Result(true,StatusCode.OK,"查询第三级权限名",permissions);
    }

}

