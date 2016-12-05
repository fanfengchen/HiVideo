package com.ebeijia.videocore.pojo;

import java.io.Serializable;
import java.util.Date;

public class TVideoResource implements Serializable {
    private Integer vid;

    private String vname;

    private String vtype;

    private String vage;

    private String vu;

    private String uu;

    private String vUrl;

    private String vState;

    private String fileId;

    private String about;

    private String res1;

    private String res2;

    private String res3;

    private String res4;

    private String res5;

    private Date lastTime;
    
    private String imgpath;

    private static final long serialVersionUID = 1L;

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getVtype() {
        return vtype;
    }

    public void setVtype(String vtype) {
        this.vtype = vtype;
    }

    public String getVage() {
        return vage;
    }

    public void setVage(String vage) {
        this.vage = vage;
    }

    public String getVu() {
        return vu;
    }

    public void setVu(String vu) {
        this.vu = vu;
    }

    public String getUu() {
        return uu;
    }

    public void setUu(String uu) {
        this.uu = uu;
    }

    public String getvUrl() {
        return vUrl;
    }

    public void setvUrl(String vUrl) {
        this.vUrl = vUrl;
    }

    public String getvState() {
        return vState;
    }

    public void setvState(String vState) {
        this.vState = vState;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
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

	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}
    
    
}