package com.ebeijia.robot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebeijia.robot.core.common.Order;
import com.ebeijia.robot.core.constant.ApiResultCode;
import com.ebeijia.robot.core.exception.ServiceException;
import com.ebeijia.robot.core.pojo.TDictateRobot;
import com.ebeijia.robot.core.util.StringUtil;
import com.ebeijia.robot.core.web.RequestMessage;
import com.ebeijia.robot.core.web.ResponseMessage;
import com.ebeijia.robot.modle.api.DicateRobotList;
import com.ebeijia.robot.service.IDictateRobotService;

/**
 * 配置机器人指令操作
 * 
 * @author ff
 *
 */
@RequestMapping("/")
@Controller
public class DictateRobotController {
	@Autowired
	private IDictateRobotService dictateRobotService;

	/**
	 * 添加配置机器人指令
	 * 
	 * @param resMessage
	 * @return
	 */
	@RequestMapping("addDictate")
	@ResponseBody
	public ResponseMessage addDictate(RequestMessage resMessage) {
		Map<String, Object> data = resMessage.getDataMap();
		String dictateCode = data.get("dictateCode").toString();// 指令代码
		String dictateName = data.get("dictateName").toString();// 指令名称
		String dictateContext = data.get("dictateContext").toString();// 指令内容
		String deviceId = data.get("deviceId").toString();// 机器人编号
		String dType = data.get("dType").toString();// 指令类型
		String fileId = data.get("fileId").toString();// 图片路径
		String dAbout =  data.get("dAbout").toString();// 指令描述

		TDictateRobot dictateRobot = null;
		if (StringUtil.checkNull(false, dictateCode, dictateName, deviceId,
				dType)) {
			dictateRobot = new TDictateRobot();
			dictateRobot.setDictateCode(dictateCode);
			dictateRobot.setDictateName(dictateName);

			dictateRobot.setDictateContext(dictateContext);
			dictateRobot.setDeviceId(deviceId);
			dictateRobot.setdType(dType);
			dictateRobot.setFileId(fileId);
			dictateRobot.setRes2(dAbout);

			dictateRobotService.insertDictate(dictateRobot);

		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}

		return ResponseMessage.success();
	}

	/**
	 * 更新配置机器人指令
	 * 
	 * @param resMessage
	 * @return
	 */
	@RequestMapping("updateDictate")
	@ResponseBody
	public ResponseMessage updateDictate(RequestMessage resMessage) {
		Map<String, Object> data = resMessage.getDataMap();
		String dId = data.get("dId").toString();
		String deviceId = data.get("deviceId").toString();
		String dType = data.get("dType").toString();
		String dictateCode = data.get("dictateCode").toString();
		String dictateName = data.get("dictateName").toString();
		String dictateContext = data.get("dictateContext").toString();
		TDictateRobot dictateRobot = null;
		if (StringUtil.checkNull(false, deviceId, dType, dictateCode,
				dictateName)) {
			dictateRobot = new TDictateRobot();
			dictateRobot.setdId(Integer.parseInt(dId));
			dictateRobot.setDeviceId(deviceId);
			dictateRobot.setdType(dType);
			dictateRobot.setDictateCode(dictateCode);
			dictateRobot.setDictateName(dictateName);
			dictateRobot.setDictateContext(dictateContext);
			dictateRobotService.updateDictate(dictateRobot);

		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}

		return ResponseMessage.success();

	}

	/**
	 * 删除配置机器人指令
	 * 
	 * @param resMessage
	 * @return
	 */
	@RequestMapping("delDictate")
	@ResponseBody
	public ResponseMessage delDictate(RequestMessage resMessage) {
		Map<String, Object> data = resMessage.getDataMap();
		String dId = data.get("dId").toString();
		if (StringUtil.checkNull(false, dId)) {
			dictateRobotService.delDictate(dId);
		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}
		return ResponseMessage.success();
	}

	/**
	 * 获取配置机器人信息列表
	 * 
	 * @param resMessage
	 * @return
	 */
	@RequestMapping("getDictateList")
	@ResponseBody
	public ResponseMessage getDictateList(RequestMessage resMessage)
			throws ServiceException {
		DicateRobotList dList = null;
		try {
			Map<String, Object> data = resMessage.getDataMap();

			String pageNo = data.get("pageNo").toString();
			String recCount = data.get("recCount").toString();
			String deviceId = data.get("deviceId").toString();
			Integer pageNum = 1;
			if (pageNo != null && pageNo != "") {
				pageNum = Integer.parseInt(pageNo);// 起始页
			}
			Integer pageSize = null;
			if (recCount != null && recCount != "") {
				pageSize = Integer.parseInt(recCount);// 最大记录数
			}

			Map<String, Object> parms = new HashMap<String, Object>();
			if (deviceId != null && !"".equals(deviceId)) {
				parms.put("deviceId", deviceId);
			}
			
			dList = dictateRobotService.findDictateList(parms, Order.desc("D_ID"), pageNum,
					pageSize);
		} catch (ServiceException ex) {
			ex.printStackTrace();
			return ResponseMessage.error(ApiResultCode.Err_0001.getCode(),
					ex.getMessage());
		}
		return ResponseMessage.success(dList);

	}
}
