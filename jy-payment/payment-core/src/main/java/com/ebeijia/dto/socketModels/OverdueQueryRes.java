package com.ebeijia.dto.socketModels;

import java.math.BigDecimal;

/**
 * Created by LiYan on 2016/9/21.
 * 欠费查询结果
 */
public class OverdueQueryRes {
    private String resCode;//返回值
    private String userNo;//户号
    private String username;//户名
    private String userStatus;//用户状态
    private BigDecimal balance;//上次余额
    private String address;//用户地址
    private String phoneNo;//手机号
    private String statementMonth;//结算月份yyyy-MM
    private String power;//用量
    private BigDecimal punishMoney;//违约金
    private BigDecimal shouldMoney;//欠费金额
    private Integer startNum;//起始表数
    private Integer endNum;//终止表数
    private String startMonth;//起始月份
    private BigDecimal unitPrice;//单价

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getStatementMonth() {
        return statementMonth;
    }

    public void setStatementMonth(String statementMonth) {
        this.statementMonth = statementMonth;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public BigDecimal getPunishMoney() {
        return punishMoney;
    }

    public void setPunishMoney(BigDecimal punishMoney) {
        this.punishMoney = punishMoney;
    }

    public BigDecimal getShouldMoney() {
        return shouldMoney;
    }

    public void setShouldMoney(BigDecimal shouldMoney) {
        this.shouldMoney = shouldMoney;
    }

    public Integer getStartNum() {
        return startNum;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    public Integer getEndNum() {
        return endNum;
    }

    public void setEndNum(Integer endNum) {
        this.endNum = endNum;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
