package com.ebeijia.videocore.exception;

public class ServiceException extends Exception {

	private String exceptionCode;
	private String exceptionMesssage;
	
	

	public ServiceException(String exceptionCode, String exceptionMesssage) {
		this.exceptionCode = exceptionCode;
		this.exceptionMesssage = exceptionMesssage;
	}

	public ServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ServiceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
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
