package com.ebeijia.entity.jypayment;

/**
 * Created with com.ebeijia.entity.jypayment
 * User : zc
 * Date : 2016/10/25
 */
public class OrderDto {
    private String orderTimeStart;
    private String orderTimeEnd;
    private String orderType;
    private String orderNo;
    private String payAccountNo;
    private String orderStatus;
    private String sentResult;

    public String getOrderTimeStart() {
        return orderTimeStart;
    }

    public void setOrderTimeStart(String orderTimeStart) {
        this.orderTimeStart = orderTimeStart;
    }

    public String getOrderTimeEnd() {
        return orderTimeEnd;
    }

    public void setOrderTimeEnd(String orderTimeEnd) {
        this.orderTimeEnd = orderTimeEnd;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayAccountNo() {
        return payAccountNo;
    }

    public void setPayAccountNo(String payAccountNo) {
        this.payAccountNo = payAccountNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSentResult() {
        return sentResult;
    }

    public void setSentResult(String sentResult) {
        this.sentResult = sentResult;
    }
}
