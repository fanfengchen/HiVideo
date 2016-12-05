package com.ebeijia.entity;

import java.io.Serializable;

public class OrdersDetail implements Serializable {
    private Long id;

    private String payAccountNo;

    private String sentResult;

    private Long needPay;

    private String sentNo;

    private Long prestoreAmt;

    private Long orderId;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    private String serialNumber;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayAccountNo() {
        return payAccountNo;
    }

    public void setPayAccountNo(String payAccountNo) {
        this.payAccountNo = payAccountNo == null ? null : payAccountNo.trim();
    }

    public String getSentResult() {
        return sentResult;
    }

    public void setSentResult(String sentResult) {
        this.sentResult = sentResult == null ? null : sentResult.trim();
    }

    public Long getNeedPay() {
        return needPay;
    }

    public void setNeedPay(Long needPay) {
        this.needPay = needPay;
    }

    public String getSentNo() {
        return sentNo;
    }

    public void setSentNo(String sentNo) {
        this.sentNo = sentNo == null ? null : sentNo.trim();
    }

    public Long getPrestoreAmt() {
        return prestoreAmt;
    }

    public void setPrestoreAmt(Long prestoreAmt) {
        this.prestoreAmt = prestoreAmt;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}