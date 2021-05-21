package com.wxj.entity;

public class CommonTurnUser {
    private Long id;

    private Long userId;

    private Long turnUserId;

    private Integer companyId;

    private Integer employeeId;

    private String picture;

    private String realname;

    public CommonTurnUser(Long id, Long userId,Long turnUserId,Integer companyId,Integer employeeId,String realname) {
        this.id=id;
        this.userId=userId;
        this.turnUserId=turnUserId;
        this.companyId=companyId;
        this.employeeId=employeeId;
        this.realname=realname;
        //this.picture=picture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTurnUserId() {
        return turnUserId;
    }

    public void setTurnUserId(Long turnUserId) {
        this.turnUserId = turnUserId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
}
