package com.woniu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.domain.Permission;
import com.woniu.domain.User;
import com.woniu.mapper.UserMapper;
import com.woniu.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Set<Permission> findAllPermission(Integer uid) {

        List<Permission> allPermissions = userMapper.findAllPermission(uid);

        Set<Permission> userSinglePermission=new LinkedHashSet<>();

        allPermissions.forEach(first->{
            if(first.getLevel()==1){
                first.setChildren(new LinkedHashSet<Permission>());
                userSinglePermission.add(first);
            }
        });

        allPermissions.forEach(second->{
            userSinglePermission.forEach(first->{
                if(second.getPid()==first.getId()){
                    first.getChildren().add(second);
                }
            });
        });
        System.out.println("userSinglePermission====>>>>/n"+userSinglePermission);

        return userSinglePermission;
    }

    @Override
    public List<Permission> getPermissions(String username,Integer pid) {
        List<Permission> permissions = userMapper.getPermissions(username,pid);
        ArrayList<Permission> needPermission=new ArrayList<Permission>();
        permissions.forEach(permission -> {
            if(permission.getLevel()==3){
                needPermission.add(permission);
            }
        });
        return needPermission;
    }
}
