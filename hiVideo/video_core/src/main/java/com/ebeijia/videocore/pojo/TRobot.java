package com.ebeijia.videocore.pojo;

import java.io.Serializable;
import java.util.Date;

public class TRobot implements Serializable {
    private Integer rId;

    private String userId;

    private String deviceId;

    private String dtype;

    private String deviceName;

    private String about;

    private String res1;

    private String res2;

    private String res3;

    private String res4;

    private String res5;

    private Date lastTime;
    private String state;
    private String sname;
    
    public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	private static final long serialVersionUID = 1L;

    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getRes1() {
        return res1;
    }

    public void setRes1(String res1) {
        this.res1 = res1;
    }

    public String getRes2() {
        return res2;
    }

    public void setRes2(String res2) {
        this.res2 = res2;
    }

    public String getRes3() {
        return res3;
    }

    public void setRes3(String res3) {
        this.res3 = res3;
    }

    public String getRes4() {
        return res4;
    }

    public void setRes4(String res4) {
        this.res4 = res4;
    }

    public String getRes5() {
        return res5;
    }

    public void setRes5(String res5) {
        this.res5 = res5;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
    
}