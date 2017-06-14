package com.ebeijia.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebeijia.common.ResponseMessage;
import com.ebeijia.common.RollPage;
import com.ebeijia.contstant.RespCode;
import com.ebeijia.dto.SeachSoftVerDto;
import com.ebeijia.service.SearcherSoftVerService;
import com.ebeijia.service.VerChannelService;
/**
 * 搜索软件版本
 */
@Controller
@RequestMapping("/")
public class SearcherSoftVerController {
	private  Logger logger = LoggerFactory.getLogger(SearcherSoftVerController.class);
	
	@Autowired
	private SearcherSoftVerService searcherSoftVerService;
	@Autowired 
	VerChannelService verChannelService;
	
	@RequestMapping(value="searchSoftVer",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage searcherSoftVer(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//获取并解析json数据
		String data=request.getParameter("data");
		JSONObject json = JSONObject.fromObject(data);
		String chnlId=json.getString("chnlId");
		String content=json.getString("content"); 
		String limit=null;
		String offset=null;
		if(json.containsKey("limit")){
			limit=json.getString("limit"); 
		}
		if(json.containsKey("offset")){
			offset=json.getString("offset"); 
		}

		Map<String, Object> map = new HashMap<String, Object>();
		//返回判断的软件渠道状态码
		Map<String, String> chanlmap =verChannelService.selectVerChannelByChnlId(chnlId);
		String resCode = (String)chanlmap.get("resCode");
		if(RespCode.RESULT_NULL.equals(resCode)){
			 return ResponseMessage.error(RespCode.Msg(resCode));//为空
		 }
		 else if(RespCode.CHANNEL_FORBIDDEN.equals(resCode)){
			 return ResponseMessage.error(RespCode.CHANNEL_FORBIDDEN);//禁用
		 }
		 else if(RespCode.PARAM_ERROR.equals(resCode)){
			 return ResponseMessage.error(RespCode.Msg(resCode));//为参数为“”
		 }
		 else{//渠道为启动状态
			 //返回查询出来的版本内容
			 RollPage<SeachSoftVerDto> resultList = searcherSoftVerService
					.selectCountBysoftId(content, limit, offset);
			 List<SeachSoftVerDto> listT = new ArrayList<SeachSoftVerDto>();
		 
			 if (!resultList.isSuccess()) {//失败
				 return ResponseMessage.error(resultList.getResMsg());
			 }
			 else{//成功
				for (SeachSoftVerDto seachSoftVer : resultList.getRecordList()) {
					listT.add(seachSoftVer);
				}
				map.put("list", listT);
				map.put("count", resultList.getRecordSum());
				return ResponseMessage.success(map);
			 }
		 }	
	}
}
