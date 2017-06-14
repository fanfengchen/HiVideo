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
import com.ebeijia.robot.modle.api.AllocationApp;
import com.ebeijia.robot.modle.api.AllocationAppRes;
import com.ebeijia.robot.modle.api.RobotAppRes;
import com.ebeijia.robot.service.IAllocationRobotService;

/**
 * 手机app端接口
 * @author lijm
 * @date 2016-12-15
 *
 */
@RequestMapping("/")
@Controller
public class AllocationRobotAppController {

	@Autowired
	private IAllocationRobotService robotService;
	
	/**
	 *获取机器人列表以及状态
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAllocationUserBranchList")
	@ResponseBody
	public ResponseMessage getAllocationUserBranch(RequestMessage reMessage) throws Exception{
		
		Map<String, Object> map = reMessage.getDataMap();
		
		String branch = map.get("branch").toString();//所属营业厅
		List<AllocationAppRes> aList = null;
		if(StringUtil.checkNull(false, branch)){
			
			aList = robotService.getAllUser(branch);
		}else{
			
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}
		//对返回数据集进行封装处理
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("list", aList);
		return ResponseMessage.success(params);
	}
	
	/**
	 * 获取机器人信息
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAllocationUserBranch")
	@ResponseBody
	public ResponseMessage getAllocationRobotBranch(RequestMessage reMessage) throws Exception{
		
		Map<String, Object> map = reMessage.getDataMap();
		
		String deviceId = map.get("deviceId").toString();//机器人编号
		AllocationApp res = null;
		if(StringUtil.checkNull(false, deviceId)){
			
			res = robotService.getRobot(deviceId);			
		}else{
			
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}
		return ResponseMessage.success(res);
	}
	
	
	/**
	 * 机器人端 
	 * 获取设备编号信息
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getRebotDevice")
	@ResponseBody
	public ResponseMessage getRebotDevice(RequestMessage reMessage) throws Exception{
		
		Map<String, Object> map = reMessage.getDataMap();
		
		String deviceId = map.get("deviceId").toString();//机器人编号
		RobotAppRes res = null;
		if(StringUtil.checkNull(false, deviceId)){
			
			res = robotService.getRobotApp(deviceId);
		}else{
			
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}
		return ResponseMessage.success(res);
	}
	
	/**
	 * 获取机器人状态
	 * lijm 2016-12-29
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getRobotStateNew")
	@ResponseBody
	public ResponseMessage getState(RequestMessage reMessage) throws Exception{
		
		Map<String, Object> map = reMessage.getDataMap();
		
		String deviceId =map.get("deviceId").toString();//机器人编号
		String state = "";
        if(StringUtil.checkNull(false, deviceId)){
			
        	state = robotService.getRobotState(deviceId);
		}else{
			
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}
        Map<String,Object> states = new HashMap<String,Object>();
        states.put("state", state);
		return ResponseMessage.success(states);
	}
}
