package com.woniu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.domain.Role;
import com.woniu.mapper.RoleMapper;
import com.woniu.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhouli
 * @since 2021-02-02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Role> findRolesByUId(Integer id) {
        return roleMapper.findRolesByUId(id);
    }

    @Override
    public boolean deleteRoleAndUserByUid(Integer uid) {
        roleMapper.deleteRoleAndUserByUid(uid);
        return true;

    }

    @Override
    public boolean insertRoleAndUser(Integer uid, Integer rid) {
         roleMapper.insertRoleAndUser(uid, rid);
        return true;
    }

}
