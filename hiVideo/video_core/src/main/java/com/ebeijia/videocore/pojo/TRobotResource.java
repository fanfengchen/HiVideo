package com.ebeijia.videocore.pojo;

import java.io.Serializable;
import java.util.Date;

public class TRobotResource implements Serializable {
    private Integer rrId;

    private Integer vid;

    private Integer rId;

    private Date vlTime;

    private String about;

    private String res1;

    private String res2;

    private String res3;

    private String res4;

    private String res5;

    private Date lastTime;
    private String deviceId;
    private String deviceName;
    private String vtype;
    private String vname;
    private String vage;
    private String uu;
    private String vu;
    private String imgpath;
    private String vurl;
    private String state;
	

    public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getVtype() {
		return vtype;
	}

	public void setVtype(String vtype) {
		this.vtype = vtype;
	}

	public String getVname() {
		return vname;
	}

	public void setVname(String vname) {
		this.vname = vname;
	}

	public String getVage() {
		return vage;
	}

	public void setVage(String vage) {
		this.vage = vage;
	}

	public String getUu() {
		return uu;
	}

	public void setUu(String uu) {
		this.uu = uu;
	}

	public String getVu() {
		return vu;
	}

	public void setVu(String vu) {
		this.vu = vu;
	}

	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

	public String getVurl() {
		return vurl;
	}

	public void setVurl(String vurl) {
		this.vurl = vurl;
	}

	private static final long serialVersionUID = 1L;

    public Integer getRrId() {
        return rrId;
    }

    public void setRrId(Integer rrId) {
        this.rrId = rrId;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }

    public Date getVlTime() {
        return vlTime;
    }

    public void setVlTime(Date vlTime) {
        this.vlTime = vlTime;
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