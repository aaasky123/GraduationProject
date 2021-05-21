package com.wxj.work.entity;

public class Work {
    /**
     * 工作主键
     */
    private Long workId;

    /**
     * 工作名称
     */
    private String workName;

    /**
     * 工作创建者id
     */
    private Long createUserId;

    /**
     *工作创建时间
     */
    private String createTime;

    /**
     *工作完结时间
     */
    private String endTime;

    /**
     * 公司id
     */
    private Integer companyId;

    /**
     * 状态
     * 0，进行中
     * 1，完成
     */
    private Integer state;

    /**
     * 创建工作说明
     */
    private String startDescription;

    /**
     * 工作完成说明
     */
    private String endDescription;

    /**
     * 工作保密性
     * 0，公开
     * 1，保密
  */
    private Integer confidential;

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