package com.woniu.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.woniu.filter.JwtFilter;
import com.woniu.realm.CustomeRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class shiroConfig {

    @Bean
    public Realm realm(){
        CustomeRealm customeRealm = new CustomeRealm();
        //设置凭证
//        HashedCredentialsMatcher matcher=new HashedCredentialsMatcher();
//        matcher.setHashAlgorithmName("md5");
//        matcher.setHashIterations(1024);
//        customeRealm.setCredentialsMatcher(matcher);
        //设置缓存
//        customeRealm.setCachingEnabled(true);
//        customeRealm.setAuthenticationCachingEnabled(true);
//        customeRealm.setAuthorizationCachingEnabled(true);
//        customeRealm.setCacheManager(new EhCacheManager());
        return customeRealm;
    }

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm());
        manager.setRememberMeManager(cookieRememberMeManager());
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(defaultWebSecurityManager());
        Map<String, Filter> filters = factoryBean.getFilters();
        filters.put("jwt",new JwtFilter());
        LinkedHashMap<String,String> hashMap = new LinkedHashMap<>();
        hashMap.put("/user/login","anon");
        hashMap.put("/**","jwt");
        factoryBean.setFilterChainDefinitionMap(hashMap);
        return factoryBean;
    }

    @Bean
    public CookieRememberMeManager cookieRememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        SimpleCookie rememberMe = new SimpleCookie("RememberMe");
        rememberMe.setMaxAge(7*24*60*60);
        cookieRememberMeManager.setCookie(rememberMe);
        cookieRememberMeManager.setCipherKey(Base64.decode("qwertyuiopasdfghjklzxc=="));
        return cookieRememberMeManager;
    }


}
