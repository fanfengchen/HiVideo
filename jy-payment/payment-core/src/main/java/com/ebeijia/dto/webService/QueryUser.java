package com.ebeijia.dto.webService;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Created by YJ on 2016/9/26.
 */
public class QueryUser {
    @XStreamAlias("WarrantNo")
    private String warrantNo;

    @XStreamAlias("UserNo")
    private String userNo;

    @XStreamAlias("Mon")
    private String mon;

    @XStreamAlias("StartNum")
    private String startNum;

    @XStreamAlias("TotalPower")
    private String totalPower;

    @XStreamAlias("EndNum")
    private String endNum;

    @XStreamAlias("ShouldMoney")
    private String shouldMoney;

    @XStreamAlias("PunishMoney")
    private String punishMoney;

    @XStreamAlias("Money")
    private String money;

    public String getWarrantNo() {
        return warrantNo;
    }

    public void setWarrantNo(String warrantNo) {
        this.warrantNo = warrantNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getStartNum() {
        return startNum;
    }

    public void setStartNum(String startNum) {
        this.startNum = startNum;
    }

    public String getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(String totalPower) {
        this.totalPower = totalPower;
    }

    public String getEndNum() {
        return endNum;
    }

    public void setEndNum(String endNum) {
        this.endNum = endNum;
    }

    public String getShouldMoney() {
        return shouldMoney;
    }

    public void setShouldMoney(String shouldMoney) {
        this.shouldMoney = shouldMoney;
    }

    public String getPunishMoney() {
        return punishMoney;
    }

    public void setPunishMoney(String punishMoney) {
        this.punishMoney = punishMoney;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
