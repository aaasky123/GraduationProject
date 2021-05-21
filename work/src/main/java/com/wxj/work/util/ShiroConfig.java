package com.wxj.work.util;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
   //ShiroFilterBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro的内置过滤器
        /*
        anon: 无需认证就可以进行访问
        authc：必须认证
        user：必须拥有 记住我 功能才能用
        perms：拥有对某个资源的权限才能访问
        role：拥有对某个角色权限才能访问
         */
        Map<String, String> fliterMap=new LinkedMap();
        fliterMap.put("/login","anon");
        fliterMap.put("/login/**","anon");
        //fliterMap.put("/index","authc");
        //fliterMap.put("/index/*","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(fliterMap);
        //设置登录的请求
        shiroFilterFactoryBean.setLoginUrl("/login");
        return shiroFilterFactoryBean;
    }
    //DefaultWebSecurityManager;2
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建 realm 对象,需要自定义;1
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
}
