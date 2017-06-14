package com.ebeijia.robot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebeijia.robot.core.common.Order;
import com.ebeijia.robot.core.constant.ApiResultCode;
import com.ebeijia.robot.core.exception.ServiceException;
import com.ebeijia.robot.core.pojo.TAllocationRobot;
import com.ebeijia.robot.core.util.StringUtil;
import com.ebeijia.robot.core.web.RequestMessage;
import com.ebeijia.robot.core.web.ResponseMessage;
import com.ebeijia.robot.modle.api.AllocationRobotList;
import com.ebeijia.robot.modle.api.DeviceRes;
import com.ebeijia.robot.service.IAllocationRobotService;

/**
 * 后台配置机器人
 * 
 * @author ff
 *
 */
@RequestMapping("/")
@Controller
public class AllocationRobotController {

	@Autowired
	private IAllocationRobotService robotService;

	/**
	 * 添加配置机器人
	 * 
	 * @param resMessage
	 * @return
	 */
	@RequestMapping("addAllocationRobot")
	@ResponseBody
	public ResponseMessage addAllocationRobot(RequestMessage resMessage) {
		Map<String, Object> data = resMessage.getDataMap();
		String ip = data.get("ip").toString();// Ip地址
		String port = data.get("port").toString();// 端口号
		String deviceId = data.get("deviceId").toString();// 机器人编号 验证 不能重复
		String deviceName = data.get("deviceName").toString();// 机器人名称
		String dType = data.get("dType").toString();// 使用类型
		String branch = data.get("branch").toString();// 所属营业厅
		String rPwd = data.get("rPwd").toString();// 设置密码
		TAllocationRobot tallRobot = null;
		if (StringUtil.checkNull(false, ip, port, deviceId, deviceName, dType,
				branch)) {
			tallRobot = new TAllocationRobot();
			tallRobot.setIp(ip);
			tallRobot.setPort(port);
			tallRobot.setDeviceId(deviceId);
			tallRobot.setDeviceName(deviceName);
			tallRobot.setDtype(dType);
			tallRobot.setBranch(branch);
			tallRobot.setState("01");
			tallRobot.setRpwd(rPwd);
			return robotService.insertAllocationRobot(tallRobot);
		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}

	}

	/**
	 * 更新机器人配置
	 * 
	 * @param resMessage
	 * @return
	 */
	@RequestMapping("updateAllocationRobot")
	@ResponseBody
	public ResponseMessage updateAllocationRobot(RequestMessage resMessage) {
		Map<String, Object> data = resMessage.getDataMap();
		String rId = data.get("rId").toString();// 配置Id
		String ip = data.get("ip").toString();// Ip地址
		String port = data.get("port").toString();// 端口号
		String deviceName = data.get("deviceName").toString();// 机器人名称
		String dType = data.get("dType").toString();// 使用类型
		String branch = data.get("branch").toString();// 所属营业厅
		String state = data.get("state").toString();// 状态
		TAllocationRobot tallRobot = null;
		if (StringUtil.checkNull(false, ip, port, deviceName, dType, branch)) {
			tallRobot = new TAllocationRobot();
			tallRobot.setrId(Integer.parseInt(rId));
			tallRobot.setIp(ip);
			tallRobot.setPort(port);
			tallRobot.setDeviceName(deviceName);
			tallRobot.setDtype(dType);
			tallRobot.setBranch(branch);
			tallRobot.setState(state);
			robotService.updateAllocationRobot(tallRobot);
		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}
		return ResponseMessage.success();
	}

	/**
	 * 删除配置机器人信息
	 * 
	 * @param resmMessage
	 * @return
	 */
	@RequestMapping("delAllocationRobot")
	@ResponseBody
	public ResponseMessage delAllocationRobot(RequestMessage resMessage) {
		Map<String, Object> data = resMessage.getDataMap();
		String rId = data.get("rId").toString();
		if (StringUtil.checkNull(false, rId)) {
			robotService.delAllocationRobot(rId);
		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}

		return ResponseMessage.success();

	}

	/**
	 * 获取配置机器人列表
	 * 
	 * @param resMessage
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("getAllocationRobotList")
	@ResponseBody
	public ResponseMessage getAllocationRobotList(RequestMessage resMessage)
			throws ServiceException {
		AllocationRobotList lList = null;
		try {
			Map<String, Object> data = resMessage.getDataMap();

			String pageNo = data.get("pageNo").toString();
			String recCount = data.get("recCount").toString();

			Integer pageNum = 1;
			if (pageNo != null && pageNo != "") {
				pageNum = Integer.parseInt(pageNo);// 起始页
			}
			Integer pageSize = null;
			if (recCount != null && recCount != "") {
				pageSize = Integer.parseInt(recCount);// 最大记录数
			}
			Map<String, Object> parms = new HashMap<String, Object>();
           
			lList = robotService.findAllocation(parms, Order.desc("R_ID"), pageNum, pageSize);
		} catch (ServiceException ex) {
			ex.printStackTrace();
			return ResponseMessage.error(ApiResultCode.Err_0001.getCode(),
					ex.getMessage());
		}
		return ResponseMessage.success(lList);

	}

	/**
	 * 获取设备编号
	 * 
	 * @param resMessage
	 * @return
	 */
	@RequestMapping("getDeviceId")
	@ResponseBody
	public ResponseMessage getDeviceId(RequestMessage resMessage) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DeviceRes> resList = robotService.getDeviceId();
		map.put("list", resList);
		return ResponseMessage.success(map);

	}
}
