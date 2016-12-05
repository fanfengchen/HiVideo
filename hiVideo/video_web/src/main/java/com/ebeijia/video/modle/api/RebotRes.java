package com.ebeijia.video.modle.api;

/**
 * 机器人列表返回参数
 * @author lijm
 * @date 2016-11-09
 */
public class RebotRes {

	
	private String rid;
	private String deviceId;
	private String deviceName;
	private String dType;
	private String state;
	private String sname;
	
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getdType() {
		return dType;
	}
	public void setdType(String dType) {
		this.dType = dType;
	}
	
	
}
