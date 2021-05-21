package com.wxj.work.Token;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

public class AdminLoginToken implements HostAuthenticationToken, RememberMeAuthenticationToken{
    private String number;
    private String password;
    private Long companyId;

    public AdminLoginToken(String number, String password, Long companyId) {
        this.number = number;
        this.password = password;
        this.companyId = companyId;
    }

    public String getUsername() {
        return number;
    }

    public void setUsername(String username) {
        this.number = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public boolean isRememberMe() {
        return false;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}