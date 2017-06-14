package com.ebeijia.robot.modle.api;
/**
 * 机器人端返回设备信息
 * @author lijm
 * @date 2016-12-16
 *
 */
public class RobotAppRes {
	
	private String ip;
	private String port;
	private String deviceId;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}	
}
