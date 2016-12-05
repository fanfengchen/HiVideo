package com.ebeijia.entity;

import java.io.Serializable;

public class InterInvokeInfo implements Serializable {
    private Long id;

    private String interNo;

    private String interType;

    private String interResult;

    private String refServiceNo;

    private Integer retryCount;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInterNo() {
        return interNo;
    }

    public void setInterNo(String interNo) {
        this.interNo = interNo == null ? null : interNo.trim();
    }

    public String getInterType() {
        return interType;
    }

    public void setInterType(String interType) {
        this.interType = interType == null ? null : interType.trim();
    }

    public String getInterResult() {
        return interResult;
    }

    public void setInterResult(String interResult) {
        this.interResult = interResult == null ? null : interResult.trim();
    }

    public String getRefServiceNo() {
        return refServiceNo;
    }

    public void setRefServiceNo(String refServiceNo) {
        this.refServiceNo = refServiceNo == null ? null : refServiceNo.trim();
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }
}