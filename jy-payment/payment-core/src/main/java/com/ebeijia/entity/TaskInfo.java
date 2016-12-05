package com.ebeijia.entity;

import java.io.Serializable;

public class TaskInfo implements Serializable {
    private Long id;

    private String taskNo;

    private String taskServerNo;

    private Integer taskRetryCount;

    private String taskResult;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo == null ? null : taskNo.trim();
    }

    public String getTaskServerNo() {
        return taskServerNo;
    }

    public void setTaskServerNo(String taskServerNo) {
        this.taskServerNo = taskServerNo == null ? null : taskServerNo.trim();
    }

    public Integer getTaskRetryCount() {
        return taskRetryCount;
    }

    public void setTaskRetryCount(Integer taskRetryCount) {
        this.taskRetryCount = taskRetryCount;
    }

    public String getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(String taskResult) {
        this.taskResult = taskResult == null ? null : taskResult.trim();
    }
}