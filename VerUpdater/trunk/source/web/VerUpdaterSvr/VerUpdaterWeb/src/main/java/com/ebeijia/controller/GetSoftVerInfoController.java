package com.ebeijia.controller;


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
import com.ebeijia.dto.VerSoftDto;
import com.ebeijia.service.GetSoftVerInfoService;
import com.ebeijia.service.VerChannelService;

/**
 * 获取版本内容
 */
@Controller
@RequestMapping("/")
public class GetSoftVerInfoController {
	@Autowired
	private GetSoftVerInfoService getSoftVerInfoService;
	@Autowired 
	VerChannelService verChannelService;
	
	private  Logger logger = LoggerFactory.getLogger(GetSoftVerInfoController.class);

	@RequestMapping(value="getSoftVerInfo",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getSoftVerInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//获取并解析json数据
		String data=request.getParameter("data");
		JSONObject json = JSONObject.fromObject(data);
		String chnlId=json.getString("chnlId");
		String softId=json.getString("softId");
		
		//返回判断的软件渠道状态码
		 Map<String, String> chanlmap =verChannelService.selectVerChannelByChnlId(chnlId);
		 String resCode = (String)chanlmap.get("resCode");
		 if(RespCode.RESULT_NULL.equals(resCode)){
			 return ResponseMessage.error(RespCode.Msg(resCode));//为null
		 }
		 else if(RespCode.PARAM_ERROR.equals(resCode)){
			 return ResponseMessage.error(RespCode.Msg(resCode));//为参数为“”
		 }
		 else if(RespCode.CHANNEL_FORBIDDEN.equals(resCode)){
			 return ResponseMessage.error(RespCode.CHANNEL_FORBIDDEN);//禁用
		 }
		 else{//渠道为启动状态
			 //返回查询出来的版本内容
			 RollPage<VerSoftDto> versoftlist=getSoftVerInfoService.selectByPrimaryKey(softId);
			 if (!versoftlist.isSuccess()) {//失败
				 return ResponseMessage.error(versoftlist.getResMsg());
			 }else {//成功
				 return ResponseMessage.success(versoftlist.getVerSoftDto());
			 }
		 }
	}
}

