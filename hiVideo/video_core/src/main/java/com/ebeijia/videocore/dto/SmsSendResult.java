package com.ebeijia.videocore.dto;

import java.util.Map;

public class SmsSendResult {
	private String errorCode;
	private String msg;
	private String subCode;
	private String subMsg;
	private String body;
	private Map<String, String> params;
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public String getSubMsg() {
		return subMsg;
	}
	public void setSubMsg(String subMsg) {
		this.subMsg = subMsg;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	
}
