package com.ebeijia.robot.core.exception;

public class WebInfoException extends RuntimeException{
	private String exceptionCode;
	private String exceptionMesssage;
	public WebInfoException(String exceptionCode,String exceptionMessage) {
		this.exceptionCode=exceptionCode;
		this.exceptionMesssage=exceptionMessage;
	}
	public WebInfoException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	public WebInfoException(String message, Throwable cause) {
		super(message, cause);
	}
	public WebInfoException(String message) {
		super(message);
	}
	public WebInfoException(Throwable cause) {
		super(cause);
	}
	public String getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	public String getExceptionMesssage() {
		return exceptionMesssage;
	}
	public void setExceptionMesssage(String exceptionMesssage) {
		this.exceptionMesssage = exceptionMesssage;
	}
	
	
}
