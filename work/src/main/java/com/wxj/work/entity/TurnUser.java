package com.wxj.work.entity;

public class TurnUser {
    private Long id;

    private Long userId;

    private Long turnUserId;

    private Integer companyId;

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
}