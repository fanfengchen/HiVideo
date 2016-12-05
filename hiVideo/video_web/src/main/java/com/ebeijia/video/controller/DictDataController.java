package com.ebeijia.video.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebeijia.video.modle.api.DictDataRes;
import com.ebeijia.video.service.DictDataService;
import com.ebeijia.videocore.constant.ApiResultCode;
import com.ebeijia.videocore.util.StringUtil;
import com.ebeijia.videocore.web.RequestMessage;
import com.ebeijia.videocore.web.ResponseMessage;

/**
 * 数据字典信息
 * @author lijm
 * @date 2016-11-11
 *
 */
@RequestMapping("/")
@Controller
public class DictDataController {

	@Autowired
	private DictDataService dataService;
	
	/**
	 * 获取机器人列表
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getDictData")
	@ResponseBody
	public ResponseMessage getDictData(RequestMessage reMessage,HttpServletRequest request)throws Exception{
		
	  Map<String,Object> map = reMessage.getDataMap();
	  
	  String id = (String)map.get("id");//数据字典代号	
	  
	  List<DictDataRes> dList = null;
	  if(StringUtil.checkNull(false,id)){
			
		  dList = dataService.getDict(id);
	  }else{
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
	  }
	    
	  Map<String,Object> res = new HashMap<String, Object>();
	  res.put("list", dList);
	  return ResponseMessage.success(res);
	}
}
