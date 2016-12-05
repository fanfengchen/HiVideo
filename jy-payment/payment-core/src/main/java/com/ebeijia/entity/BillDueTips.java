package com.ebeijia.entity;

import java.io.Serializable;
import java.util.Date;

public class BillDueTips implements Serializable {
    private Integer id;

    private String accountNo;

    private String accName;

    private String accAddress;

    private String accMobile;

    private String settleMonth;

    private String useAmount;

    private String endReads;

    private String startReads;

    private String startMonth;

    private String dueAmt;

    private Date pushDate;

    private String pushSts;

    private String pushContents;

    private String openId;


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName == null ? null : accName.trim();
    }

    public String getAccAddress() {
        return accAddress;
    }

    public void setAccAddress(String accAddress) {
        this.accAddress = accAddress == null ? null : accAddress.trim();
    }

    public String getAccMobile() {
        return accMobile;
    }

    public void setAccMobile(String accMobile) {
        this.accMobile = accMobile == null ? null : accMobile.trim();
    }

    public String getSettleMonth() {
        return settleMonth;
    }

    public void setSettleMonth(String settleMonth) {
        this.settleMonth = settleMonth == null ? null : settleMonth.trim();
    }

    public String getUseAmount() {
        return useAmount;
    }

    public void setUseAmount(String useAmount) {
        this.useAmount = useAmount == null ? null : useAmount.trim();
    }

    public String getEndReads() {
        return endReads;
    }

    public void setEndReads(String endReads) {
        this.endReads = endReads == null ? null : endReads.trim();
    }

    public String getStartReads() {
        return startReads;
    }

    public void setStartReads(String startReads) {
        this.startReads = startReads == null ? null : startReads.trim();
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth == null ? null : startMonth.trim();
    }

    public String getDueAmt() {
        return dueAmt;
    }

    public void setDueAmt(String dueAmt) {
        this.dueAmt = dueAmt == null ? null : dueAmt.trim();
    }

    public Date getPushDate() {
        return pushDate;
    }

    public void setPushDate(Date pushDate) {
        this.pushDate = pushDate;
    }

    public String getPushSts() {
        return pushSts;
    }

    public void setPushSts(String pushSts) {
        this.pushSts = pushSts == null ? null : pushSts.trim();
    }

    public String getPushContents() {
        return pushContents;
    }

    public void setPushContents(String pushContents) {
        this.pushContents = pushContents == null ? null : pushContents.trim();
    }
}