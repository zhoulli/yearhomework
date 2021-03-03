package com.woniu.controller;


import com.woniu.domain.Permission;
import com.woniu.dto.Result;
import com.woniu.dto.StatusCode;
import com.woniu.service.PermissionService;
import com.woniu.vo.AllPermissions;
import com.woniu.vo.Ids;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedHashSet;
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
@RequestMapping("/permission")
@CrossOrigin
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @GetMapping("getPermissions/{rid}")
    public Result getPermissions(@PathVariable Integer rid){
        AllPermissions allPermissions=new AllPermissions();
        Set<Permission> needPermissions = permissionService.findAllPermissions();
        System.out.println("rid=====>>>>\n\n"+rid);
        Set<Integer> rids3=new LinkedHashSet<Integer>();
        Set<Permission> permissions = permissionService.queryPermissionByRid(rid);
        permissions.forEach(permission -> {
            rids3.add(permission.getId());
        });
        System.out.println("rids3=====>>>>\n\n"+rids3);
        allPermissions.setPermissions(needPermissions);
        allPermissions.setRids3(rids3);
        return new Result(true, StatusCode.OK,"查询全部所需权限成功",allPermissions);
    }

    @DeleteMapping("deletePermissionByRid/{rid}")
    public Result deletePermissionByRid(@PathVariable Integer rid){
        System.out.println("rid====>>>"+rid);
        permissionService.deletePermissionAndRoleByRid(rid);
        return new Result(true, StatusCode.OK,"根据角色id删除权限角色中间表成功");
    }

    @PostMapping("insertPermissionByRidAndPid")
    public Result insertPermissionByRidAndPid(@RequestBody Ids ids){
        System.out.println("ids====>>>"+ids);
        ids.getPids().forEach(pid->{
            permissionService.insertRoleAndPermission(ids.getRid(),pid);
        });
        return new Result(true, StatusCode.OK,"根据角色id删除权限角色中间表成功");
    }
}

