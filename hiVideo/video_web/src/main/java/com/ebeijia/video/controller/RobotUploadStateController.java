package com.ebeijia.video.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ebeijia.video.service.RobotUploadStateService;
import com.ebeijia.videocore.constant.ApiResultCode;
import com.ebeijia.videocore.util.StringUtil;
import com.ebeijia.videocore.web.RequestMessage;
import com.ebeijia.videocore.web.ResponseMessage;

/**
 * 机器人上传资源状态
 * @author lijm
 * @date 2016-11-16
 *
 */

@RequestMapping("/")
@Controller
public class RobotUploadStateController {

	@Autowired
	private RobotUploadStateService upService;
	
	@RequestMapping("addRebotState")
	@ResponseBody
	public ResponseMessage addRebotState(RequestMessage reMessage)throws Exception{
		
		Map<String,Object> map = reMessage.getDataMap();
		
		String state = (String)map.get("state");//机器人状态
		String sname = (String)map.get("sname");
		
		String devId = reMessage.getDevId();
		if(StringUtil.checkNull(false, state,devId)){
			
			return upService.uploadState(devId, state, sname);	
		}else{
			
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(), ApiResultCode.Err_0002.getMsg());
		}			
	}
}
