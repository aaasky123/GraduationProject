package com.wxj.entity;

public class Work {
    private Long workId;

    private String workName;

    private Long createUserId;

    private String createTime;

    private String endTime;

    private Integer companyId;

    private Integer state;

    private String startDescription;

    private String endDescription;

    private Integer confidential;

    public Work(Long workId,String workName, Long createUserId,String createTime,String endTime,Integer companyId,Integer state,String startDescription,String endDescription,Integer confidential) {
        this.workId=workId;
        this.workName=workName;
        this.createUserId=createUserId;
        this.createTime=createTime;
        this.endTime=endTime;
        this.companyId=companyId;
        this.state=state;
        this.startDescription=startDescription;
        this.endDescription=endDescription;
        this.confidential=confidential;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName == null ? null : workName.trim();
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStartDescription() {
        return startDescription;
    }

    public void setStartDescription(String startDescription) {
        this.startDescription = startDescription == null ? null : startDescription.trim();
    }

    public String getEndDescription() {
        return endDescription;
    }

    public void setEndDescription(String endDescription) {
        this.endDescription = endDescription == null ? null : endDescription.trim();
    }

    public Integer getConfidential() {
        return confidential;
    }

    public void setConfidential(Integer confidential) {
        this.confidential = confidential;
    }
}
