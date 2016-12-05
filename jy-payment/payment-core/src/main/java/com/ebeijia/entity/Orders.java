package com.ebeijia.entity;

import java.io.Serializable;
import java.util.Date;

public class Orders implements Serializable {
    private Long id;

    private String orderNo;

    private String orderType;

    private Date orderTime;

    private Long orderAmt;

    private String payType;

    private String orderDesc;

    private String paySuccTime;

    private String orderStatus;

    private String userId;

    private String res;

    private String payAccountNo;

    private String sentNo;

    private String free;



    private  OrdersDetail ordersDetail;

    private String payMonth;

    private static final long serialVersionUID = 1L;
    private String orderAmtString;

    public String getOrderAmtString() {
        return orderAmtString;
    }

    public void setOrderAmtString(String orderAmtString) {
        this.orderAmtString = orderAmtString;
    }


    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getPayMonth() {
        return payMonth;
    }

    public void setPayMonth(String payMonth) {
        this.payMonth = payMonth;
    }

    public String getPayAccountNo() {
        return payAccountNo;
    }

    public void setPayAccountNo(String payAccountNo) {
        this.payAccountNo = payAccountNo;
    }

    public String getSentNo() {
        return sentNo;
    }

    public void setSentNo(String sentNo) {
        this.sentNo = sentNo;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Long getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(Long orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc == null ? null : orderDesc.trim();
    }

    public String getPaySuccTime() {
        return paySuccTime;
    }

    public void setPaySuccTime(String paySuccTime) {
        this.paySuccTime = paySuccTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res == null ? null : res.trim();
    }

    public OrdersDetail getOrdersDetail() {
        return ordersDetail;
    }

    public void setOrdersDetail(OrdersDetail ordersDetail) {
        this.ordersDetail = ordersDetail;

    }
}