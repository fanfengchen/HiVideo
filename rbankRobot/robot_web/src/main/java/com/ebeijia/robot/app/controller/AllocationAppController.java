package com.ebeijia.robot.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ebeijia.robot.core.constant.ApiResultCode;
import com.ebeijia.robot.core.util.StringUtil;
import com.ebeijia.robot.core.web.RequestMessage;
import com.ebeijia.robot.core.web.ResponseMessage;
import com.ebeijia.robot.modle.api.AllocaAppRes;
import com.ebeijia.robot.service.IAllocationAppService;
/**
 * 获取后端配置的app的数据
 * @author lijm
 * @date 2016-12-16
 *
 */

@RequestMapping("/")
@Controller
public class AllocationAppController {

	@Autowired
	private IAllocationAppService appService;
	
	/**
	 * 获取app列表数据
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAllocationMAppList")
	@ResponseBody
	public ResponseMessage getAllocationList(RequestMessage reMessage) throws Exception{
		
		Map<String, Object> map = reMessage.getDataMap();
		
		String deviceId = map.get("deviceId").toString();//机器人编号
		List<AllocaAppRes> rList = null;
        if(StringUtil.checkNull(false, deviceId)){
			
        	rList = appService.getAllApp(deviceId);		
		}else{
			
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}
       //对返回数据集进行封装处理
       Map<String,Object> params = new HashMap<String, Object>();
       params.put("list", rList);
	   return ResponseMessage.success(params);
	}
	
	/**
	 * 获取机器人状态
	 * 2016-12-29 lijm
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getRobotState")
	@ResponseBody
	public ResponseMessage getRobotState(RequestMessage reMessage) throws Exception{
		
		Map<String, Object> map = reMessage.getDataMap();
		
		String deviceId = map.get("deviceId").toString();//机器人编号
		List<AllocaAppRes> rList = null;
        if(StringUtil.checkNull(false, deviceId)){
			
        	rList = appService.getAllApp(deviceId);		
		}else{
			
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}
       //对返回数据集进行封装处理
       Map<String,Object> params = new HashMap<String, Object>();
       params.put("list", rList);
	   return ResponseMessage.success(params);
	}
	
}
