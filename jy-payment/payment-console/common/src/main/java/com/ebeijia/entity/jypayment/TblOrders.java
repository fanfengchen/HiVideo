package com.ebeijia.entity.jypayment;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with com.ebeijia.entity.jypayment
 * User : zc
 * Date : 2016/10/24
 */
@Entity
@Table(name = "tbl_orders")
public class TblOrders {

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",unique = true,nullable = false,length = 20)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "order_no", length = 2)
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Column(name = "order_type", length = 2)
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Column(name = "order_time" , columnDefinition = "TIMESTAMP")
    public Date getOrderTime() {
        return orderTime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    @Column(name = "order_amt", length = 20)
    public Long getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(Long orderAmt) {
        this.orderAmt = orderAmt;
    }

    @Column(name = "pay_type", length = 2)
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Column(name = "order_desc", length = 100)
    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    @Column(name = "pay_succ_time", length = 14)
    public String getPaySuccTime() {
        return paySuccTime;
    }

    public void setPaySuccTime(String paySuccTime) {
        this.paySuccTime = paySuccTime;
    }

    @Column(name = "order_status", length = 2)
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Column(name = "user_id", length = 50)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "res", length = 100)
    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

}
