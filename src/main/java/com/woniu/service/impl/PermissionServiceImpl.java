package com.woniu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.domain.Permission;
import com.woniu.mapper.PermissionMapper;
import com.woniu.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhouli
 * @since 2021-02-02
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public Set<Permission> findAllPermissions() {
        List<Permission> permissions = permissionMapper.selectList(null);
        Set<Permission> needPermissions=new LinkedHashSet<>();

        permissions.forEach(first -> {
            if(first.getLevel()==1){
                first.setChildren(new LinkedHashSet<Permission>());
                needPermissions.add(first);
            }
        });

        permissions.forEach(second->{
            needPermissions.forEach(first->{
                if(second.getPid()==first.getId()){
                    second.setChildren(new LinkedHashSet<Permission>());
                    first.getChildren().add(second);
                }
            });
        });

        permissions.forEach(third->{
            if(third.getLevel()==3) {
                needPermissions.forEach(second -> {
                    second.getChildren().forEach(root -> {
                        if (third.getPid() == root.getId()) {
                            root.getChildren().add(third);
                        }
                    });
                });
            }
        });
        return needPermissions;
    }

    @Override
    public Set<Permission> queryPermissionByRid(Integer rid) {
        Set<Permission> needPermissions=new LinkedHashSet<>();
        Set<Permission> permissions = permissionMapper.queryPermissionByRid(rid);
        permissions.forEach(permission -> {
            if (permission.getLevel()==3){
                needPermissions.add(permission);
            }
        });

        return needPermissions;
    }

    @Override
    public void deletePermissionAndRoleByRid(Integer rid) {
        permissionMapper.deletePermissionAndRoleByRid(rid);
    }

    @Override
    public void insertRoleAndPermission(Integer rid, Integer pid) {
        permissionMapper.insertRoleAndPermission(rid,pid);
    }
}
