package com.ebeijia.dto;

/**
 * Created by YPJ on 2016/6/22.
 */
public class RefundDto {

    private String openId;

    private String paySeqNo;

    private String orderNo;

    private Long payAmt;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    private Long orderId;

    private String refundSeqNo;

    private Long totalAmt;

    public Long getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Long totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getRefundSeqNo() {
        return refundSeqNo;
    }

    public void setRefundSeqNo(String refundSeqNo) {
        this.refundSeqNo = refundSeqNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPaySeqNo() {
        return paySeqNo;
    }

    public void setPaySeqNo(String paySeqNo) {
        this.paySeqNo = paySeqNo;
    }

    public Long getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(Long payAmt) {
        this.payAmt = payAmt;
    }

    public String getOrderNo() {

        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
