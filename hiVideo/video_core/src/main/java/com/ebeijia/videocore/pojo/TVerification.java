package com.ebeijia.videocore.pojo;

import java.io.Serializable;
import java.util.Date;

public class TVerification implements Serializable {
    private String mobile;

    private String verfCode;

    private String verfType;

    private String isActive;

    private Date sendTime;

    private Date lastTime;

    private static final long serialVersionUID = 1L;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerfCode() {
        return verfCode;
    }

    public void setVerfCode(String verfCode) {
        this.verfCode = verfCode;
    }

    public String getVerfType() {
        return verfType;
    }

    public void setVerfType(String verfType) {
        this.verfType = verfType;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
}