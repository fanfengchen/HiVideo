package com.ebeijia.entity.coffee;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zc on 2016/9/19.
 */
@Entity
@Table(name = "tbl_coupon_order")
public class TblCouponOrder {

    private int id;
    private String couponNo;
    private String orderNo;
    private Date orderSubTime;
    private String state;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID",unique = true,nullable = false,length = 11)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "COUPON_NO",length = 20)
    public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo;
    }

    @Column(name = "ORDER_NO",length = 20)
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Column(name = "ORDER_SUB_TIME" ,columnDefinition = "TIMESTAMP")
    public Date getOrderSubTime() {
        return orderSubTime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:ss:mm")
    public void setOrderSubTime(Date orderSubTime) {
        this.orderSubTime = orderSubTime;
    }

    @Column(name = "STATE",length = 2)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
