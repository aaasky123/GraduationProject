package com.wxj.entity;

public class File {
    private Integer id;

    private String fileName;

    private String newFileName;

    private String path;

    private Long workId;

    private Integer workFlowId;

    public File(Integer id, String fileName, String newFileName, String path, Long workId, Integer workFlowId){
        this.id=id;
        this.fileName=fileName;
        this.newFileName=fileName;
        this.newFileName=newFileName;
        this.path=path;
        this.workId=workId;
        this.workFlowId=workFlowId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName == null ? null : newFileName.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public Integer getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(Integer workFlowId) {
        this.workFlowId = workFlowId;
    }
}
