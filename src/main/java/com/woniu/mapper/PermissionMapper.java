package com.woniu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.domain.Permission;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhouli
 * @since 2021-02-02
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    @Select("select p.*\n" +
            "FROM t_role_permission rp\n" +
            "join t_permission p\n" +
            "on p.id=rp.pid\n" +
            "where rp.rid=#{rid}")
    public Set<Permission> queryPermissionByRid(Integer rid);

    @Select("DELETE from t_role_permission\n" +
            " where rid=#{rid}")
    public Integer deletePermissionAndRoleByRid(Integer rid);

    @Select("insert into t_role_permission(rid,pid) VALUES(#{rid},#{pid})")
    public Integer insertRoleAndPermission(@RequestParam Integer rid, @RequestParam Integer pid);


    @Select("SELECT p.*\n" +
            "from t_user u\n" +
            "join t_user_role ur\n" +
            "on u.id=ur.uid\n" +
            "join t_role r\n" +
            "on r.id=ur.rid\n" +
            "join t_role_permission rp\n" +
            "on rp.rid=r.id\n" +
            "join t_permission p\n" +
            "on p.id=rp.pid\n" +
            "where u.username=#{username}")
    public List<Permission> getPermissions(String username);


}
