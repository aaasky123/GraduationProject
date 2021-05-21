package com.wxj.entity;

public class Helper {
    private Integer id;

    private Long helperUserId;

    private Long workId;

    private Long workFlowId;

    private Integer companyId;

    public Helper(Integer id, Long helperUserId,Long workId,Integer companyId,Long workFlowId) {
        this.id=id;
        this.helperUserId=helperUserId;
        this.workId=workId;
        this.companyId=companyId;
        this.workFlowId=workFlowId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getHelperUserId() {
        return helperUserId;
    }

    public void setHelperUserId(Long helperUserId) {
        this.helperUserId = helperUserId;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public Long getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(Long workFlowId) {
        this.workFlowId = workFlowId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
