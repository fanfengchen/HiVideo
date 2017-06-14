package com.ebeijia.dto;

import java.util.Date;
import java.util.List;

public class VerSoftDto{
	
	private String name;
	private String packName;
	private String verNo;
	private String packUrl;
	private String imgUrl;
	private String isuTime;
	private String isForce;
	private String updateLog;
	private String about;
	private List<PreviewImgUrl> list;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackName() {
		return packName;
	}

	public void setPackName(String packName) {
		this.packName = packName;
	}

	public String getVerNo() {
		return verNo;
	}

	public void setVerNo(String verNo) {
		this.verNo = verNo;
	}

	public String getPackUrl() {
		return packUrl;
	}

	public void setPackUrl(String packUrl) {
		this.packUrl = packUrl;
	}

	public String getIsuTime() {
		return isuTime;
	}

	public void setIsuTime(String string) {
		this.isuTime = string;
	}

	public String getIsForce() {
		return isForce;
	}

	public void setIsForce(String isForce) {
		this.isForce = isForce;
	}

	public String getUpdateLog() {
		return updateLog;
	}

	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public List<PreviewImgUrl> getList() {
		return list;
	}

	public void setList(List<PreviewImgUrl> list) {
		this.list = list;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
}
