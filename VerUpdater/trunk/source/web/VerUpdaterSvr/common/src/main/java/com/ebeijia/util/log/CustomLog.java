package com.ebeijia.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomLog {
	private Logger logger = LoggerFactory.getLogger(CustomLog.class);
	private static CustomLog mInstances = null;

	private CustomLog() {
	}

	// 单实例模式
	public static CustomLog getInstances() {
		if (mInstances == null)
			mInstances = new CustomLog();
		return mInstances;
	}

	public void logInfo(String message, Throwable t) {
		if (logger.isErrorEnabled()) {
			logger.error(message, t);
		}
	}

	public void logInfo(String message) {
		logInfo(message, null);
	}

	public void logError(String message) {
		logger.error(message);
	}
}
