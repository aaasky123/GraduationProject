package com.wxj.work.entity;

public class Notifications {
    private Long id;

    private Long createUserId;

    private String notificationsContent;

    private Long createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getNotificationsContent() {
        return notificationsContent;
    }

    public void setNotificationsContent(String notificationsContent) {
        this.notificationsContent = notificationsContent == null ? null : notificationsContent.trim();
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}