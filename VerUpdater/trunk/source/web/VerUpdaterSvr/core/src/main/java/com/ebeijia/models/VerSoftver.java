package com.ebeijia.models;

import java.util.Date;

public class VerSoftver {
	private Integer softId;
	private String userId;
	private String chnlId;
	private Integer typeId;
	private String name;
	private String packName;
	private String verNo;
	private String packUrl;
	private Integer imgId;
	private Date isuTime;
	private String isForce;
	private String updateLog;
	private String about;
	private String res2;
	private String res3;
	private String res4;
	private Date lastTime;
	private VerSofttype verSoftype;
	private MdiFileinfo mdiFileinfo;
	


	public Integer getSoftId() {
		return softId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ver_softver.SOFT_ID
	 *
	 * @param softId
	 *            the value for ver_softver.SOFT_ID
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public void setSoftId(Integer softId) {
		this.softId = softId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ver_softver.USER_ID
	 *
	 * @return the value of ver_softver.USER_ID
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ver_softver.USER_ID
	 *
	 * @param userId
	 *            the value for ver_softver.USER_ID
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ver_softver.CHNL_ID
	 *
	 * @return the value of ver_softver.CHNL_ID
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public String getChnlId() {
		return chnlId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ver_softver.CHNL_ID
	 *
	 * @param chnlId
	 *            the value for ver_softver.CHNL_ID
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public void setChnlId(String chnlId) {
		this.chnlId = chnlId == null ? null : chnlId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ver_softver.TYPE_ID
	 *
	 * @return the value of ver_softver.TYPE_ID
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public Integer getTypeId() {
		return typeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ver_softver.TYPE_ID
	 *
	 * @param typeId
	 *            the value for ver_softver.TYPE_ID
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ver_softver.NAME
	 *
	 * @return the value of ver_softver.NAME
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ver_softver.NAME
	 *
	 * @param name
	 *            the value for ver_softver.NAME
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ver_softver.PACK_NAME
	 *
	 * @return the value of ver_softver.PACK_NAME
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public String getPackName() {
		return packName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ver_softver.PACK_NAME
	 *
	 * @param packName
	 *            the value for ver_softver.PACK_NAME
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public void setPackName(String packName) {
		this.packName = packName == null ? null : packName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ver_softver.VER_NO
	 *
	 * @return the value of ver_softver.VER_NO
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public String getVerNo() {
		return verNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ver_softver.VER_NO
	 *
	 * @param verNo
	 *            the value for ver_softver.VER_NO
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public void setVerNo(String verNo) {
		this.verNo = verNo == null ? null : verNo.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ver_softver.PACK_URL
	 *
	 * @return the value of ver_softver.PACK_URL
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public String getPackUrl() {
		return packUrl;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ver_softver.PACK_URL
	 *
	 * @param packUrl
	 *            the value for ver_softver.PACK_URL
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public void setPackUrl(String packUrl) {
		this.packUrl = packUrl == null ? null : packUrl.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ver_softver.IMG_ID
	 *
	 * @return the value of ver_softver.IMG_ID
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public Integer getImgId() {
		return imgId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ver_softver.IMG_ID
	 *
	 * @param imgId
	 *            the value for ver_softver.IMG_ID
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public void setImgId(Integer imgId) {
		this.imgId = imgId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ver_softver.ISU_TIME
	 *
	 * @return the value of ver_softver.ISU_TIME
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public Date getIsuTime() {
		return isuTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ver_softver.ISU_TIME
	 *
	 * @param isuTime
	 *            the value for ver_softver.ISU_TIME
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public void setIsuTime(Date isuTime) {
		this.isuTime = isuTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ver_softver.IS_FORCE
	 *
	 * @return the value of ver_softver.IS_FORCE
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public String getIsForce() {
		return isForce;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ver_softver.IS_FORCE
	 *
	 * @param isForce
	 *            the value for ver_softver.IS_FORCE
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public void setIsForce(String isForce) {
		this.isForce = isForce == null ? null : isForce.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ver_softver.RES2
	 *
	 * @return the value of ver_softver.RES2
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public String getRes2() {
		return res2;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ver_softver.RES2
	 *
	 * @param res2
	 *            the value for ver_softver.RES2
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public void setRes2(String res2) {
		this.res2 = res2 == null ? null : res2.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ver_softver.RES3
	 *
	 * @return the value of ver_softver.RES3
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public String getRes3() {
		return res3;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ver_softver.RES3
	 *
	 * @param res3
	 *            the value for ver_softver.RES3
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public void setRes3(String res3) {
		this.res3 = res3 == null ? null : res3.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ver_softver.RES4
	 *
	 * @return the value of ver_softver.RES4
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public String getRes4() {
		return res4;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ver_softver.RES4
	 *
	 * @param res4
	 *            the value for ver_softver.RES4
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public void setRes4(String res4) {
		this.res4 = res4 == null ? null : res4.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ver_softver.LAST_TIME
	 *
	 * @return the value of ver_softver.LAST_TIME
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public Date getLastTime() {
		return lastTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ver_softver.LAST_TIME
	 *
	 * @param lastTime
	 *            the value for ver_softver.LAST_TIME
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ver_softver.UPDATE_LOG
	 *
	 * @return the value of ver_softver.UPDATE_LOG
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public String getUpdateLog() {
		return updateLog;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ver_softver.UPDATE_LOG
	 *
	 * @param updateLog
	 *            the value for ver_softver.UPDATE_LOG
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog == null ? null : updateLog.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column ver_softver.ABOUT
	 *
	 * @return the value of ver_softver.ABOUT
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public String getAbout() {
		return about;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column ver_softver.ABOUT
	 *
	 * @param about
	 *            the value for ver_softver.ABOUT
	 *
	 * @mbggenerated Fri Oct 14 17:02:13 CST 2016
	 */
	public void setAbout(String about) {
		this.about = about == null ? null : about.trim();
	}
	public VerSofttype getVerSoftype() {
		return verSoftype;
	}

	public void setVerSoftype(VerSofttype verSoftype) {
		this.verSoftype = verSoftype;
	}
	
	public MdiFileinfo getMdiFileinfo() {
		return mdiFileinfo;
	}

	public void setMdiFileinfo(MdiFileinfo mdiFileinfo) {
		this.mdiFileinfo = mdiFileinfo;
	}

	
}