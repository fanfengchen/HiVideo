package com.ebeijia.robot.core.pojo;

import java.io.Serializable;
import java.util.Date;

public class TDictateRobot implements Serializable{
	
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer dId;

    private String deviceId;

    private String dictateCode;

    private String dictateName;

    private String dictateContext;
    
    private String dType;

    private String fileId;
    
    private String res1;

    private String res2;//存放指令描述

    private String res3;

    private String res4;

    private String res5;

	private String dName;
	
	private String dContext;
	
	private String urlPath;
	
    private Date lastTime;

	public Integer getdId() {
		return dId;
	}

	public void setdId(Integer dId) {
		this.dId = dId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDictateCode() {
		return dictateCode;
	}

	public void setDictateCode(String dictateCode) {
		this.dictateCode = dictateCode;
	}

	public String getDictateName() {
		return dictateName;
	}

	public void setDictateName(String dictateName) {
		this.dictateName = dictateName;
	}

	public String getDictateContext() {
		return dictateContext;
	}

	public void setDictateContext(String dictateContext) {
		this.dictateContext = dictateContext;
	}

	public String getdType() {
		return dType;
	}

	public void setdType(String dType) {
		this.dType = dType;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
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

	public String getdName() {
		return dName;
	}

	public void setdName(String dName) {
		this.dName = dName;
	}

	public String getdContext() {
		return dContext;
	}

	public void setdContext(String dContext) {
		this.dContext = dContext;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
    
   

    
}