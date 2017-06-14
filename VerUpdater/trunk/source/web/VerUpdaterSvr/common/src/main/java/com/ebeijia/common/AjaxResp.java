package com.ebeijia.common;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ebeijia.contstant.RespCode;
import com.ebeijia.util.DateTime4J;

public class AjaxResp {
	public static Map<String, Object> getReturn(String respCode,Object o) {
		String sendTime=DateTime4J.getCurrentDateTime();
		Map<String,Object> map= new HashMap<String,Object>();
		    map.put("sendTime", sendTime);
		    map.put("rspCode", respCode);
		    map.put("rspMsg", RespCode.Msg(respCode));
		    map.put("data", o);
		   //String sign=EndResp.resp(sendTime, o);
		    map.put("signType", "01");
		   // map.put("sign", sign);
		    return map;
	}
	public  Map<String, Object> getReturn(String respCode,Object o,String msg){
		String sendTime=DateTime4J.getCurrentDateTime();
		Map<String,Object> map= new HashMap<String,Object>();
		 	map.put("sendTime", sendTime);   
		 	map.put("rspCode", respCode);
		    map.put("rspMsg", msg);
		    
		    map.put("content", o);
		   // String sign=EndResp.resp(sendTime, o);
		    map.put("signType", "01");
		    //map.put("sign", sign);
		    return map;
		  }
}	
