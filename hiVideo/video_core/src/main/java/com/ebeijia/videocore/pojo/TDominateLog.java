package com.ebeijia.videocore.pojo;

import java.io.Serializable;
import java.util.Date;

public class TDominateLog implements Serializable {
	
	private Integer id;
    private String deviceid;

    private String dtype;

    private Date dtime;

    private String dstate;

    private String dcontext;

    private String res1;

    private String res2;

    private String res3;

    private Date lastTime;
    private String deviceName;
    
    public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	private static final long serialVersionUID = 1L;

    public String getDeviceid() {
        return deviceid;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public Date getDtime() {
        return dtime;
    }

    public void setDtime(Date dtime) {
        this.dtime = dtime;
    }

    public String getDstate() {
        return dstate;
    }

    public void setDstate(String dstate) {
        this.dstate = dstate;
    }

    public String getDcontext() {
        return dcontext;
    }

    public void setDcontext(String dcontext) {
        this.dcontext = dcontext;
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

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
}