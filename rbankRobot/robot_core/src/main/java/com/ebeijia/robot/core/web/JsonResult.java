package com.ebeijia.robot.core.web;

import java.io.Serializable;

public class JsonResult implements Serializable {
	private String resultCode;
	private String errorMessage;
	private Object data;

	public JsonResult(String resultCode, String errorMessage, Object data) {
		this.resultCode = resultCode;
		this.errorMessage = errorMessage;
		this.data = data;
	}

	public static JsonResult success() {
		return new JsonResult("1", "", null);
	}

	public static JsonResult success(Object obj) {
		return new JsonResult("1", "", obj);
	}

	public static JsonResult error() {
		return new JsonResult("0", "", null);
	}

	public static JsonResult error(String errorMessage) {
		return new JsonResult("0", errorMessage, null);
	}

	public static JsonResult errorWithCode(String resultCode,
			String errorMessage) {
		return new JsonResult(resultCode, errorMessage, null);
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
