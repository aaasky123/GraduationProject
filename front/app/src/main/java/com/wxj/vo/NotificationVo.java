package com.wxj.vo;

public class NotificationVo {
    private Long id;

    private Long createUserId;

    private String notificationsContent;

    private Long createTime;

    private String realname;

    public NotificationVo(Long id, Long createUserId, String notificationsContent, Long createTime, String realname){
        this.id=id;
        this.createUserId=createUserId;
        this.notificationsContent=notificationsContent;
        this.createTime=createTime;
        this.realname=realname;
    }

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

    public String getRealname(){
        return realname;
    }

    public void setRealname(String realName) {
        this.realname = realName;
    }
}
