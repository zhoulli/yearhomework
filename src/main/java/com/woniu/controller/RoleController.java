package com.woniu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniu.domain.Role;
import com.woniu.dto.Result;
import com.woniu.dto.StatusCode;
import com.woniu.service.RoleService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhouli
 * @since 2021-02-02
 */
@RestController
@RequestMapping("/role")
@CrossOrigin
public class RoleController {

    @Resource
    private RoleService roleService;

    private Integer uid;


    @GetMapping("getAllRoles")
    public Result getAllRoles(){
        ArrayList<String> rolenames=new ArrayList<String>();
        List<Role> roles= roleService.list(null);
        roles.forEach(role -> {
            rolenames.add(role.getRolename());
        });
        return new Result(true, StatusCode.OK,"获取所有的角色名",rolenames);
    }

    @GetMapping("getRolesByUid/{uid}")
    public Result getRolesByUid(@PathVariable Integer uid){
        System.out.println("uid===>>>"+uid);
        ArrayList<String> rolenames=new ArrayList<String>();
        List<Role> roles = roleService.findRolesByUId(uid);
        roles.forEach(role -> {
            rolenames.add(role.getRolename());
        });
        return new Result(true, StatusCode.OK,"根据用户ID获取其角色",rolenames);

    }


    @GetMapping("getRoles")
    public Result getRoles(){
        List<Role> roles= roleService.list(null);
        return new Result(true, StatusCode.OK,"获取所有的角色",roles);
    }

    @DeleteMapping("deleteUserOldRoles/{uid}")
    public Result deleteUserOldRoles(@PathVariable Integer uid){
        System.out.println("delete====>>>\n\n"+uid);
        this.uid=uid;
        boolean b = roleService.deleteRoleAndUserByUid(uid);
        if(b){
            return new Result(true, StatusCode.OK,"删除用户以前的角色成功");
        }
        return new Result(false, StatusCode.Error,"删除用户以前的角色失败");
    }

    @PostMapping("insertUserRoles")
    public Result insertUserRoles(@RequestBody ArrayList<String> defaultRole){
        System.out.println("defaultRole====>>>"+defaultRole);

        ArrayList<Integer> rids=new ArrayList<Integer>();
        defaultRole.forEach(role->{
            QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("rolename",role);
            Role one = roleService.getOne(queryWrapper);
            rids.add(one.getRoleId());
        });
        if(!ObjectUtils.isEmpty(uid)){
            rids.forEach(rid->{
                roleService.insertRoleAndUser(uid,rid);
            });
            return new Result(true, StatusCode.OK,"新增用户与角色成功",defaultRole);
        }
        return new Result(false, StatusCode.Error,"新增用户与角色失败");

    }


}

