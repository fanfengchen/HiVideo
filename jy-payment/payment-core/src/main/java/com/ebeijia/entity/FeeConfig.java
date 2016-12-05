package com.ebeijia.entity;

import java.io.Serializable;

public class FeeConfig implements Serializable {
    private Integer id;

    private String oprType;

    private String feeType;

    private String amt;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOprType() {
        return oprType;
    }

    public void setOprType(String oprType) {
        this.oprType = oprType == null ? null : oprType.trim();
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType == null ? null : feeType.trim();
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt == null ? null : amt.trim();
    }
}