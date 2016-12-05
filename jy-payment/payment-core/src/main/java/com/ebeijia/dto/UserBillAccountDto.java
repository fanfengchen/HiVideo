package com.ebeijia.dto;

import java.io.Serializable;

/**
 * Created with com.ebeijia.dto
 * User : zc
 * Date : 2016/9/28
 * 前台展示数据
 */
public class UserBillAccountDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userNo;//户号
    private String username;//户名
    private String startNum;//起码
    private String endNum;//止码
    private String totalPowe;//用量
    private String shouldMoney;//欠缴金额
    private String punishMoney;//违约金
    private String balance;//上次余额
    private String money;//合计欠费金额
    private String statementMonth;//结束月份


    private String address;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserBillAccountDto() {
    }

    public String getUserNo() {
        return this.userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStartNum() {
        return this.startNum;
    }

    public void setStartNum(String startNum) {
        this.startNum = startNum;
    }

    public String getEndNum() {
        return this.endNum;
    }

    public void setEndNum(String endNum) {
        this.endNum = endNum;
    }

    public String getTotalPowe() {
        return this.totalPowe;
    }

    public void setTotalPowe(String totalPowe) {
        this.totalPowe = totalPowe;
    }

    public String getShouldMoney() {
        return this.shouldMoney;
    }

    public void setShouldMoney(String shouldMoney) {
        this.shouldMoney = shouldMoney;
    }

    public String getPunishMoney() {
        return this.punishMoney;
    }

    public void setPunishMoney(String punishMoney) {
        this.punishMoney = punishMoney;
    }

    public String getBalance() {
        return this.balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getMoney() {
        return this.money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStatementMonth() {
        return this.statementMonth;
    }

    public void setStatementMonth(String statementMonth) {
        this.statementMonth = statementMonth;
    }
}

