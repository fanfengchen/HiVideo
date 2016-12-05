package com.ebeijia.video.modle.api;

public class DominateLogRes {

	private String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String dtime;
	private String state;

	private String deviceName;
	private String dContext;
	
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getdContext() {
		return dContext;
	}

	public void setdContext(String dContext) {
		this.dContext = dContext;
	}

	public String getDtime() {
		return dtime;
	}

	public void setDtime(String dtime) {
		this.dtime = dtime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	

}
