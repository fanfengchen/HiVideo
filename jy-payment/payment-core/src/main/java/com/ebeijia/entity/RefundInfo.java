package com.ebeijia.entity;

import java.io.Serializable;

public class RefundInfo implements Serializable {
    private Integer id;

    private String refundNo;

    private Integer refundAmt;

    private String refundReason;

    private String refundSts;

    private String res;

    private Long orderId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo == null ? null : refundNo.trim();
    }

    public Integer getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(Integer refundAmt) {
        this.refundAmt = refundAmt;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason == null ? null : refundReason.trim();
    }

    public String getRefundSts() {
        return refundSts;
    }

    public void setRefundSts(String refundSts) {
        this.refundSts = refundSts == null ? null : refundSts.trim();
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res == null ? null : res.trim();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}