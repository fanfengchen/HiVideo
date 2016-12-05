package com.ebeijia.entity;

import java.io.Serializable;

public class PayLimitTime implements Serializable {
    private Integer id;

    private String feeType;

    private String limitType;

    private String limitDateStart;

    private String limitDateEnd;

    private String limitTimeStart;

    private String limitTimeEnd;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType == null ? null : feeType.trim();
    }

    public String getLimitType() {
        return limitType;
    }

    public void setLimitType(String limitType) {
        this.limitType = limitType == null ? null : limitType.trim();
    }

    public String getLimitDateStart() {
        return limitDateStart;
    }

    public void setLimitDateStart(String limitDateStart) {
        this.limitDateStart = limitDateStart == null ? null : limitDateStart.trim();
    }

    public String getLimitDateEnd() {
        return limitDateEnd;
    }

    public void setLimitDateEnd(String limitDateEnd) {
        this.limitDateEnd = limitDateEnd == null ? null : limitDateEnd.trim();
    }

    public String getLimitTimeStart() {
        return limitTimeStart;
    }

    public void setLimitTimeStart(String limitTimeStart) {
        this.limitTimeStart = limitTimeStart == null ? null : limitTimeStart.trim();
    }

    public String getLimitTimeEnd() {
        return limitTimeEnd;
    }

    public void setLimitTimeEnd(String limitTimeEnd) {
        this.limitTimeEnd = limitTimeEnd == null ? null : limitTimeEnd.trim();
    }
}