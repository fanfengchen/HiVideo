package com.ebeijia.intercepter;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.*;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;


import com.ebeijia.common.PackBase;
import com.ebeijia.common.AjaxResp;
import com.ebeijia.contstant.RespCode;
import com.ebeijia.filter.BaseInterceptor;


public class GetSoftVerInfoInterceptor extends BaseInterceptor{
	private  Logger logger = LoggerFactory.getLogger(GetSoftVerInfoInterceptor.class); 
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		super.preHandle(request, response, handler);
//		logger.info(request.getMethod());
//		//获取请求参数，并把参数打印成日志
		Map<String, String[]> map=request.getParameterMap();
		JSONObject json=new JSONObject();
		json.putAll(map);
		logger.info("请求参数："+json.toString());

		
		
	
//			String url=request.getRequestURI();
//			logger.info("url="+url);
//			String interName=url.substring(url.lastIndexOf("/")+1, url.lastIndexOf("."));//获取url的接口名
//			logger.info("interName="+interName);
//			
//			PackBase pack = new PackBase(map);//得到请求参数，以map(key,value[])的形式，即：key是String型，value是String型数组。 
//			pack.setInterfaceName(interName);//给PackBase的接口名赋值
//			pack.checkParamByInterfaceName();//检查参数的有效性和完整性
	
		
		String chnlId=request.getParameter("chnlId");//渠道ID
		String softId=request.getParameter("softId");//软件ID
		/*if(PackBase.isValid(chnlId)&&PackBase.isValid(softId)){
			return true;
		}
		else{
			Map<String, Object> map1 = AjaxResp.getReturn(RespCode.PARAM_ERROR, "");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				JSONObject js = JSONObject.fromObject(map);
				out.write(js.toString());
				
			} catch (Exception e) {
				logger.error(" GetSoftVerInfo interceptor error : " + e);
			} finally {
				if (out != null) {
					out.close();
				}
			}
			
			}*/
		return true;
		
	}
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("执行了postHandle方法");
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.info("执行了afterCompletion");
	}

}
