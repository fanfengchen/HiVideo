package com.ebeijia.dto;

/**
 * Created by YJ on 2016/9/20.
 */
public class OrderResultDto {

    private String openId;

    private String orderAmt;

    private String tokeId;

    private String sentStatus;

    private String attachv;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(String orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getTokeId() {
        return tokeId;
    }

    public void setTokeId(String tokeId) {
        this.tokeId = tokeId;
    }

    public String getSentStatus() {
        return sentStatus;
    }

    public void setSentStatus(String sentStatus) {
        this.sentStatus = sentStatus;
    }

    public String getAttachv() {
        return attachv;
    }

    public void setAttachv(String attachv) {
        this.attachv = attachv;
    }
}
