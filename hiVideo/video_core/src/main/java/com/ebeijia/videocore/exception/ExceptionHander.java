package com.ebeijia.videocore.exception;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebeijia.videocore.web.JsonResult;
import com.ebeijia.videocore.web.ResponseMessage;

/**
 * controller异常处理 公用类
 */
@ControllerAdvice(annotations = Controller.class)
public class ExceptionHander {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@ResponseBody
	@ExceptionHandler(WebInfoException.class)
	public ResponseMessage webInfoExcpetionHandle(WebInfoException ex) {
		logger.error(ex.getMessage(), ex);
		return ResponseMessage.error(ex.getExceptionCode(),
				ex.getExceptionMesssage());
	}

	@ResponseBody
	@ExceptionHandler(ServiceException.class)
	public ResponseMessage serviceExceptionHandle(ServiceException ex) {
		logger.error(ex.getMessage(), ex);
		if (StringUtils.isEmpty(ex.getExceptionCode())) {
			return ResponseMessage.error();
		} else {
			return ResponseMessage.error(ex.getExceptionCode(),
					ex.getExceptionMesssage());
		}
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public ResponseMessage systemException(Exception ex) {
		logger.error(ex.getMessage(), ex);
		return ResponseMessage.error();
	}

}
