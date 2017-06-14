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
import com.ebeijia.robot.core.pojo.TAllocationApp;
import com.ebeijia.robot.core.util.StringUtil;
import com.ebeijia.robot.core.web.RequestMessage;
import com.ebeijia.robot.core.web.ResponseMessage;
import com.ebeijia.robot.modle.api.AllocationAppList;
import com.ebeijia.robot.service.IAllocationAppService;

@RequestMapping("/")
@Controller
public class AllocationAppsController {

	@Autowired
	private IAllocationAppService allocationAppService;

	/**
	 * 添加配置应用APP
	 * 
	 * @param resMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addAllocationApp")
	@ResponseBody
	public ResponseMessage addAllocationApp(RequestMessage resMessage)
			throws Exception {
		Map<String, Object> data = resMessage.getDataMap();

		String aName = data.get("aName").toString();
		String bName = data.get("bName").toString();
		String version = data.get("version").toString();
		String fileId = data.get("fileId").toString();
		String deviceId = data.get("deviceId").toString();
		String context = data.get("context").toString();
		String state = data.get("state").toString();

		TAllocationApp allocationApp = null;
		if (StringUtil.checkNull(false, aName, bName, version, fileId,
				deviceId, context, state)) {
			allocationApp = new TAllocationApp();

			allocationApp.setAname(aName);
			allocationApp.setBname(bName);
			allocationApp.setVersion(version);
			allocationApp.setFileId(fileId);
			allocationApp.setDeviceId(deviceId);
			allocationApp.setContext(context);
			allocationApp.setState(state);

			allocationAppService.insertAllocationApp(allocationApp);

		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}
		return ResponseMessage.success();
	}

	/**
	 * 更新配置应用APP
	 * 
	 * @param resMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateAllocationApp")
	@ResponseBody
	public ResponseMessage updateAllocationApp(RequestMessage resMessage)
			throws Exception {
		Map<String, Object> data = resMessage.getDataMap();
		String aId = data.get("aId").toString();
		String aName = data.get("aName").toString();
		String bName = data.get("bName").toString();
		String deviceId = data.get("deviceId").toString();
		String version = data.get("version").toString();
		String state = data.get("state").toString();
		String context = data.get("context").toString();
		TAllocationApp allocationApp = null;
		if (StringUtil.checkNull(false, aName, bName, deviceId, version,
				context)) {
			allocationApp = new TAllocationApp();
			allocationApp.setaId(Integer.parseInt(aId));
			allocationApp.setAname(aName);
			allocationApp.setBname(bName);
			allocationApp.setDeviceId(deviceId);
			allocationApp.setVersion(version);
			allocationApp.setState(state);
			allocationApp.setContext(context);
			allocationAppService.updataAllocationApp(allocationApp);

		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}
		return ResponseMessage.success();
	}

	@RequestMapping("delAllocationApp")
	@ResponseBody
	public ResponseMessage delAllocationApp(RequestMessage resMessage)
			throws Exception {
		Map<String, Object> data = resMessage.getDataMap();
		String aId = data.get("aId").toString();
		if (StringUtil.checkNull(false, aId)) {
			allocationAppService.delAllocationApp(Integer.parseInt(aId));
		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}

		return ResponseMessage.success();

	}

	/**
	 * 获取配置应用App列表
	 * 
	 * @param resMessage
	 * @return
	 */
	@RequestMapping("getAllocationAppList")
	@ResponseBody
	public ResponseMessage getAllocationAppList(RequestMessage resMessage) {

		AllocationAppList aList = null;
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
			
			if (deviceId != null && deviceId != "") {
				parms.put("deviceId", deviceId);
			}

			aList = allocationAppService.findAllocationAppList(parms, Order.desc("A_ID"),
					pageNum, pageSize);
		} catch (ServiceException ex) {
			ex.printStackTrace();
			return ResponseMessage.error(ApiResultCode.Err_0001.getCode(),
					ex.getMessage());
		}
		return ResponseMessage.success(aList);

	}

	/**
	 * 设置APP的状态 lijm 2016-12-20
	 * 
	 * @param resMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateStateApp")
	@ResponseBody
	public ResponseMessage updateStateApp(RequestMessage resMessage)
			throws Exception {
		Map<String, Object> data = resMessage.getDataMap();
		String aId = data.get("aId").toString();
		String state = data.get("state").toString();
		if (StringUtil.checkNull(false, aId, state)) {
			allocationAppService.updateState(aId, state);
		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}

		return ResponseMessage.success();

	}
}
