package com.ebeijia.robot.core.util;

import org.slf4j.LoggerFactory;

/**
 * Created by chyulin on 2015/10/16.
 */
public class LoggerUtil {
	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(LoggerUtil.class);

	public static void info(String message, Throwable e) {
		if (logger.isInfoEnabled()) {
			logger.info(message, e);
		}
	}

	public static void info(Throwable e) {
		info(null, e);
	}

	public static void info(String message) {
		info(message, null);
	}

	public static void error(String message) {
		error(message, null);
	}

	public static void error(String message, Throwable t) {
		if (logger.isErrorEnabled()) {
			logger.error(message, t);
		}
	}

	public static void error(Throwable t) {
		error(null, t);
	}

}
