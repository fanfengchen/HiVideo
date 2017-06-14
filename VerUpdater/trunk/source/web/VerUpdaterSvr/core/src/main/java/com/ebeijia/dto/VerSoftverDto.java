package com.ebeijia.dto;

import java.io.Serializable;

public class VerSoftverDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String softId;
	private String typeId;
	private String typeName;
	private String name;
	private String packName;
	private String verNo;
	private String packUrl;
	private String imgUrl;
	private String isuTime;
	private String isForce;
	private String updateLog;
	private String about;

	public String getSoftId() {
		return softId;
	}

	public void setSoftId(String softId) {
		this.softId = softId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getIsuTime() {
		return isuTime;
	}

	public void setIsuTime(String isuTime) {
		this.isuTime = isuTime;
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

	@Override
	public String toString() {
		return "VerSoftverDto [softId=" + softId + ", typeId=" + typeId
				+ ", typeName=" + typeName + ", name=" + name + ", packName="
				+ packName + ", verNo=" + verNo + ", packUrl=" + packUrl
				+ ", imgUrl=" + imgUrl + ", isuTime=" + isuTime + ", isForce="
				+ isForce + ", updateLog=" + updateLog + ", about=" + about
				+ "]";
	}
	

}
