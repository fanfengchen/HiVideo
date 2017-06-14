package com.ebeijia.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ebeijia.common.AjaxResp;
import com.ebeijia.common.IPackType.PTHeadReq;
import com.ebeijia.common.IPackType.PTMacVerify;
import com.ebeijia.common.ResponseMessage;
import com.ebeijia.contstant.RespCode;
import com.ebeijia.filter.PackReqMess.PackAllReq;
import com.ebeijia.service.system.SysTokenSerivce;
import com.ebeijia.util.exception.EPackException;

/**
 * 
 * @author ff
 * @date 创建时间：2016年10月17日 上午11:25:08
 * @version 1.0
 * @parameter
 * @return
 */
public class BaseInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(BaseInterceptor.class);

	@Autowired
	private SysTokenSerivce sysTokenSerivce;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
  PrintWriter pw = new PrintWriter(response.getOutputStream());
		try {
			StringBuffer reqUrl = request.getRequestURL();
			PackAllReq pack = new PackAllReq(request.getParameterMap());
			logger.info("请求报文："+reqUrl + pack.getBufferString());
			String _url = reqUrl.substring(reqUrl.lastIndexOf("/") + 1);
			pack.checkParam(_url);
		} catch (EPackException e) {
		//	pw.write(ResponseMessage.error(RespCode.PACK_ERR_MSG);
			
			e.printStackTrace();
			return false; 
		}

		String token = request.getParameter(PTHeadReq.authToken.name());
		String sign = request.getParameter(PTMacVerify.sign.name());
		String sendTime = request.getParameter(PTHeadReq.sendTime.name());
		String reqData = request.getParameter(PTHeadReq.data.name());

		if (!sysTokenSerivce.judgeToken(token)) {
			respMess(RespCode.TOKEN_CODE, "", response);
			logger.info("Token失效");
			return false;
		}

		if (!sysTokenSerivce.judgeSign(sendTime, reqData, sign)) {
			respMess(RespCode.SIGN_CODE, "", response);
			logger.info("签名错误");
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info(response.getOutputStream().toString());
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.info("执行了base的afterCompletion");
	}

	public void respMess(String respCode, String data,
			HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		Map<String, Object> map = AjaxResp.getReturn(respCode, data);

		JSONObject js = JSONObject.fromObject(map);
		logger.info(js.toString());
		response.getOutputStream().write(js.toString().getBytes("UTF-8"));
		
	}

}
