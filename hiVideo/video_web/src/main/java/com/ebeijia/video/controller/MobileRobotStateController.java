package com.ebeijia.video.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ebeijia.video.modle.api.RobotState;
import com.ebeijia.video.service.RobotUploadStateService;
import com.ebeijia.videocore.constant.ApiResultCode;
import com.ebeijia.videocore.util.PropertiesUtils;
import com.ebeijia.videocore.util.StringUtil;
import com.ebeijia.videocore.web.RequestMessage;
import com.ebeijia.videocore.web.ResponseMessage;

/**
 * 安卓手机获取机器人目前状态
 * @author lijm
 * @date 2016-11-16
 * 
 */
@RequestMapping("/")
@Controller
public class MobileRobotStateController {

	@Autowired
	private RobotUploadStateService upService;
	
	@RequestMapping("getDominnateState")
	@ResponseBody
	public ResponseMessage getDominnateState(RequestMessage reMessage,HttpServletRequest request)throws Exception{
		
		Map<String,Object> map = reMessage.getDataMap();		
		String deviceId = (String)map.get("deviceId");//设备编号
		
		RobotState state= null;
		String intervalTime= PropertiesUtils.getProperties("intervalTime");//间隔时间
		if(StringUtil.checkNull(false, deviceId)){
			
			 state = upService.rebotState(deviceId, intervalTime);
		}else{
			
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}
		return ResponseMessage.success(state);
	}
}
