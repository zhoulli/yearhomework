package com.woniu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.domain.Permission;
import com.woniu.domain.User;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhouli
 * @since 2021-02-02
 */
public interface UserService extends IService<User> {

    public Set<Permission> findAllPermission(Integer uid);

    public List<Permission> getPermissions(String username, Integer pid);


}
