package com.ebeijia.robot.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebeijia.robot.core.web.RequestMessage;
import com.ebeijia.robot.core.web.ResponseMessage;
import com.ebeijia.robot.modle.api.DictateRobotRes;
import com.ebeijia.robot.service.IDictateRobotService;

/**
 * 手机端获取机器人使用指令
 * @author lijm
 * @date 2016-12-15
 *
 */
@RequestMapping("/")
@Controller
public class DictateRobotAppController {

	@Autowired
	private IDictateRobotService robotService;
	
	
	/**
	 * 获取机器人指令集
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getDictateRobotApp")
	@ResponseBody
	public ResponseMessage getAllocationUserBranch(RequestMessage reMessage) throws Exception{
		
		Map<String, Object> map = reMessage.getDataMap();
		String deviceId = map.get("deviceId").toString();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("deviceIds", deviceId);
		List<DictateRobotRes> res = robotService.getDictsteRobot(params);
		params.clear();
		params.put("list", res);
		return ResponseMessage.success(params);
	}
}
