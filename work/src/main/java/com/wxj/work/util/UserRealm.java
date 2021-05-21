package com.wxj.work.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wxj.work.Token.AdminLoginToken;
import com.wxj.work.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//自定义的UserRealm
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        AdminLoginToken adminLoginToken=(AdminLoginToken) authenticationToken;
        String number=adminLoginToken.getUsername();
        String password=adminLoginToken.getPassword().toString();
        Map<String,Object> loginMap=new HashMap<>();
        loginMap.put("number",number);
        loginMap.put("password",password);
        JSONObject jsonObject=new JSONObject(loginMap);
        String loginData=jsonObject.toString();
        Result result=userService.loginByAdmin(loginData);
        if("管理员登录失败".equals(result.getMsg())){
            return null;
        }else {
            return new SimpleAuthenticationInfo("",loginData,"");
        }
    }
}
