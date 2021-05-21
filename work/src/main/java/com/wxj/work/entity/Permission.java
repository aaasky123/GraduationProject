package com.wxj.work.entity;

public class Permission {
    private Integer id;

    private Long userId;

    private Long createAdminId;

    private String createPermissionTime;
    /**
     * 0,无权限
     * 1，只有查看权限
     * 2，只有批注权限
     * 3，有查看和批注权限
     */
    private Integer permissionValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCreateAdminId() {
        return createAdminId;
    }

    public void setCreateAdminId(Long createAdminId) {
        this.createAdminId = createAdminId;
    }

    public String getCreatePermissionTime() {
        return createPermissionTime;
    }

    public void setCreatePermissionTime(String createPermissionTime) {
        this.createPermissionTime = createPermissionTime == null ? null : createPermissionTime.trim();
    }

    public Integer getPermissionValue() {
        return permissionValue;
    }

    public void setPermissionValue(Integer permissionValue) {
        this.permissionValue = permissionValue;
    }
}