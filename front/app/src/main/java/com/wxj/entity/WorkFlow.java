package com.wxj.entity;

public class WorkFlow {
    private Long workId;

    private Long workFlowId;

    private Integer companyId;

    private Long hostId;

    private String turnReason;

    private String startTime;

    private String endTime;

    private Integer state;

    private String workFlowName;

    public WorkFlow(Long workId,Long workFlowId, Integer companyId,Long hostId,String turnReason,String startTime,String endTime,Integer state,String workFlowName) {
        this.workId=workId;
        this.workFlowId=workFlowId;
        this.companyId=companyId;
        this.hostId=hostId;
        this.turnReason=turnReason;
        this.startTime=startTime;
        this.endTime=endTime;
        this.state=state;
        this.workFlowName=workFlowName;
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

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public String getTurnReason() {
        return turnReason;
    }

    public void setTurnReason(String turnReason) {
        this.turnReason = turnReason == null ? null : turnReason.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getWorkFlowName() {
        return workFlowName;
    }

    public void setWorkFlowName(String workFlowName) {
        this.workFlowName = workFlowName == null ? null : workFlowName.trim();
    }
}
