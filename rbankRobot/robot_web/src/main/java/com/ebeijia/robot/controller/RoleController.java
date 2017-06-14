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
import com.ebeijia.robot.core.util.StringUtil;
import com.ebeijia.robot.core.web.RequestMessage;
import com.ebeijia.robot.core.web.ResponseMessage;
import com.ebeijia.robot.modle.api.RoleList;
import com.ebeijia.robot.modle.api.RoleRes;
import com.ebeijia.robot.service.IRoleService;

@RequestMapping("/")
@Controller
public class RoleController {

	@Autowired
	private IRoleService roleService;

	/**
	 * 获取角色列表
	 * 
	 * @param resMessage
	 * @return
	 */
	@RequestMapping("getRoleList")
	@ResponseBody
	public ResponseMessage getRoleList(RequestMessage resMessage) {

		RoleList rList = null;
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

			rList = roleService.findRoleList(parms, Order.desc("ROLE_ID"),
					pageNum, pageSize);
		} catch (ServiceException ex) {
			ex.printStackTrace();
			return ResponseMessage.error(ApiResultCode.Err_0001.getCode(),
					ex.getMessage());
		}
		return ResponseMessage.success(rList);

	}

	/**
	 * 删除角色
	 * 
	 * @param resMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delRole")
	@ResponseBody
	public ResponseMessage deleteRole(RequestMessage resMessage)
			throws ServiceException {
		Map<String, Object> data = resMessage.getDataMap();
		String id = data.get("id").toString();
		if (StringUtil.checkNull(false, id)) {
			roleService.delRole(id);
		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}

		return ResponseMessage.success();
	}

	/**
	 * 设置角色
	 * 
	 * @param resMessage
	 * @return
	 */
	@RequestMapping("setRole")
	@ResponseBody
	public ResponseMessage setRole(RequestMessage resMessage) throws Exception {
		Map<String, Object> data = resMessage.getDataMap();
		String id = (String) data.get("id");
		String name = (String) data.get("name");

		if (StringUtil.checkNull(false, name)) {
			roleService.updateRole(id, name);
		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}
		return ResponseMessage.success();

	}

	/**
	 * 角色集合列表
	 * 
	 * @param resMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getRoleIdList")
	@ResponseBody
	public ResponseMessage getRoleIdList(RequestMessage resMessage)
			throws Exception {

		List<RoleRes> list = roleService.getRoleIdList();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);

		return ResponseMessage.success(map);

	}
}
