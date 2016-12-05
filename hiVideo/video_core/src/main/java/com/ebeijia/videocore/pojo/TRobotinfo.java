package com.ebeijia.videocore.pojo;

import java.io.Serializable;
import java.util.Date;

public class TRobotinfo implements Serializable {
    private Integer rrId;

    private Integer rId;

    private String state;

    private String about;

    private String sname;

    private String res2;

    private String res3;

    private String res4;

    private String res5;

    private Date lastTime;

    private static final long serialVersionUID = 1L;

    public Integer getRrId() {
        return rrId;
    }

    public void setRrId(Integer rrId) {
        this.rrId = rrId;
    }

    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

   

    public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
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
}