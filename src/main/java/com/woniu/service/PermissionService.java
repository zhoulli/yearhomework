package com.woniu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.domain.Permission;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhouli
 * @since 2021-02-02
 */
public interface PermissionService extends IService<Permission> {


    public Set<Permission> findAllPermissions();

    public Set<Permission> queryPermissionByRid(Integer rid);

    public void deletePermissionAndRoleByRid(Integer rid);

    public void insertRoleAndPermission(Integer rid, Integer pid);


}
