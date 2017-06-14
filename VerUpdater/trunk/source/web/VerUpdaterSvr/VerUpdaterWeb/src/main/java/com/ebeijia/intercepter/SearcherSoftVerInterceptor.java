package com.ebeijia.intercepter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ebeijia.filter.BaseInterceptor;
import com.ebeijia.util.log.CustomLog;

public class SearcherSoftVerInterceptor extends BaseInterceptor {

	public static Map<String,String> paraInterceptor(HttpServletRequest request,HttpServletResponse response){
		 Map<String,String> map = new HashMap<String ,String>();
		CustomLog.getInstances().logInfo("开始接收数据");
		
		String chnlId=request.getParameter("chnlId");//渠道ID
		String content=request.getParameter("content");//搜索内容
		String limit=request.getParameter("limit");//查询记录偏移量
		String offset=request.getParameter("offset");//查询最大记录数
		CustomLog.getInstances().logInfo("数据接收完毕");
		CustomLog.getInstances().logInfo("得到的数据为："+"chnlId:"+chnlId+"content:"+content+"limit:"+limit+"offset:"+offset);	 
		 
		if(chnlId==null||content==null){
			map.put("resCode","-1"); 
		}
		else if(chnlId.length()>50||content.length()>50){
			map.put("resCode","-2");
		}
		else{
			map.put("resCode","0");
			map.put("chnlId", chnlId);
			map.put("content", content);
			map.put("limit", limit);
			map.put("offset", offset);
		}
		return map;
	
		
	}
}
