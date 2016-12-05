package com.ebeijia.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ff on 2016/9/28.
 */
public class UserOrderListDto {

    private String orderNo;//订单编号
    private Date payTime;//缴费时间
    private String payType;//缴费类型
    private String payAccountNo;//缴费账号
    private String orderStatus;//订单状态
    private BigDecimal orderAmt;//缴费金额
    private String sentResult;//缴费结果
    private String payMonth;

    public String getPayMonth() {
        return payMonth;
    }

    public void setPayMonth(String payMonth) {
        this.payMonth = payMonth;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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

    public BigDecimal getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(BigDecimal orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getSentResult() {
        return sentResult;
    }

    public void setSentResult(String sentResult) {
        this.sentResult = sentResult;
    }
}
