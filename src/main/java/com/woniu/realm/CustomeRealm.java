package com.woniu.realm;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniu.domain.Permission;
import com.woniu.domain.Role;
import com.woniu.domain.User;
import com.woniu.mapper.PermissionMapper;
import com.woniu.mapper.RoleMapper;
import com.woniu.mapper.UserMapper;
import com.woniu.token.JwtToken;
import com.woniu.utils.JwtUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class CustomeRealm extends AuthorizingRealm {


    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("开始授权------------------------");
        //需要查询数据库根据username去查询该用户的角色

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String token = (String) principalCollection.getPrimaryPrincipal();
        DecodedJWT decodeToken = JwtUtils.getDecodeToken(token);
        String username = decodeToken.getClaim("username").asString();
        List<Role> roles = roleMapper.getRole(username);
        List<Permission> permissions = permissionMapper.getPermissions(username);

        List<String> rolenames=new ArrayList<>();
        List<String> permissionNames=new ArrayList<>();
        if(!CollectionUtils.isEmpty(roles)){
            roles.forEach(role -> {
                rolenames.add(role.getRolename());
            });
        }

        if(!CollectionUtils.isEmpty(permissions)){
            permissions.forEach(permission -> {
                permissionNames.add(permission.getElement());
            });
        }


        simpleAuthorizationInfo.addRoles(rolenames);
        simpleAuthorizationInfo.addStringPermissions(permissionNames);

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("开始认证-----------------------");
        String token = (String) authenticationToken.getPrincipal();
        DecodedJWT decodeToken = JwtUtils.getDecodeToken(token);
        String username = decodeToken.getClaim("username").asString();
        if(ObjectUtils.isEmpty(username)){
            throw new AuthenticationException("token认证失败！");
        }
        return new SimpleAuthenticationInfo(token,token,this.getName());

    }
}
