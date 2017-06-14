package com.ebeijia.robot.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebeijia.robot.core.constant.ApiResultCode;
import com.ebeijia.robot.core.util.StringUtil;
import com.ebeijia.robot.core.web.RequestMessage;
import com.ebeijia.robot.core.web.ResponseMessage;
import com.ebeijia.robot.service.IAllocationRobotService;

/**
 * 上传机器人状态
 * @author lijm
 * @date 2016-12-16
 *
 */
@RequestMapping("/")
@Controller
public class UpdateRobotStateController {

	@Autowired
	private IAllocationRobotService robotService;
	
	@RequestMapping("uploadRobotState")
	@ResponseBody
	public ResponseMessage addRebotState(RequestMessage reMessage)throws Exception{
		
		Map<String,Object> map = reMessage.getDataMap();
		
		String state = (String)map.get("state");//机器人状态
		String deviceId = (String)map.get("deviceId");		
		
		if(StringUtil.checkNull(false, state,deviceId)){
			
			return robotService.uploadRobotState(deviceId, state);
		}else{
			
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(), ApiResultCode.Err_0002.getMsg());
		}			
	}
}
