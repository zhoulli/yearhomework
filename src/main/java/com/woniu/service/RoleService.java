package com.woniu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.domain.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhouli
 * @since 2021-02-02
 */
public interface RoleService extends IService<Role> {

    public List<Role> findRolesByUId(Integer id);

    public boolean deleteRoleAndUserByUid(Integer uid);

    public boolean insertRoleAndUser(Integer uid, Integer rid);


}
