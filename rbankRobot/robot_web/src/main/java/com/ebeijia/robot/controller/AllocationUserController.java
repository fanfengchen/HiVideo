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
import com.ebeijia.robot.core.pojo.TAllocationUser;
import com.ebeijia.robot.core.util.PasswordHandler;
import com.ebeijia.robot.core.util.StringUtil;
import com.ebeijia.robot.core.web.RequestMessage;
import com.ebeijia.robot.core.web.ResponseMessage;
import com.ebeijia.robot.modle.api.AllocationUserList;
import com.ebeijia.robot.service.IAllocationUserService;

/**
 * 配置用户信息(基本的增删改查)，供app端使用
 * 
 * @author lijm
 * @date 2016-12-12
 *
 */
@Controller
@RequestMapping("/")
public class AllocationUserController {

	@Autowired
	private IAllocationUserService allUserService;

	/**
	 * 获取配置用户列表
	 * 
	 * @param message
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("getAllocationUserList")
	@ResponseBody
	public ResponseMessage getAllUserList(RequestMessage message)
			throws ServiceException {
		AllocationUserList lList = null;
		try {
			Map<String, Object> data = message.getDataMap();

			String pageNo = data.get("pageNo").toString();
			String recCount = data.get("recCount").toString();
			String userId = data.get("userId").toString();
			String userName = data.get("userName").toString();
			Integer pageNum = 1;
			if (pageNo != null && pageNo != "") {
				pageNum = Integer.parseInt(pageNo);// 起始页
			}
			Integer pageSize = null;
			if (recCount != null && recCount != "") {
				pageSize = Integer.parseInt(recCount);// 最大记录数
			}
			Map<String, Object> parms = new HashMap<String, Object>();
			if (userId != null && userId != "") {
				parms.put("userId", userId);
			}
			if (userName != null && userName != "") {
				parms.put("userName", userName);
			}
			lList = allUserService.findAllocation(parms, Order.desc("LAST_TIME"), pageNum,
					pageSize);
		} catch (ServiceException ex) {
			ex.printStackTrace();
			return ResponseMessage.error(ApiResultCode.Err_0001.getCode(),
					ex.getMessage());
		}
		return ResponseMessage.success(lList);
	}

	/**
	 * 添加配置用户
	 * 
	 * @param resMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addAllocationUser")
	@ResponseBody
	public ResponseMessage addAllocationUser(RequestMessage resMessage)
			throws Exception {

		Map<String, Object> data = resMessage.getDataMap();
		String userId = (String) data.get("userId");
		String password = (String) data.get("pwd");
		String userName = (String) data.get("userName");
		String mobile = (String) data.get("mobile");
		String branch = (String) data.get("branch");
		String roleId =(String) data.get("roleId");
		String state = (String) data.get("state");
		TAllocationUser locationUser = null;
		// 判断参数是否为空
		if (StringUtil.checkNull(false, userId, password, userName, branch)) {
			locationUser = new TAllocationUser();
			locationUser.setUserId(userId);
			// 对拿到的密码进行加密处理
			locationUser.setPwd(PasswordHandler.getPassword(password));
			locationUser.setUserName(userName);
			locationUser.setMobile(mobile);
			locationUser.setBranch(branch);
			locationUser.setRoleId(Integer.parseInt(roleId));
			locationUser.setState(state);
			return allUserService.addAllocationUser(locationUser);

		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());

		}

	}

	/**
	 * 删除用户配置信息
	 * 
	 * @author ff
	 * @param resMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delAllocationUser")
	@ResponseBody
	public ResponseMessage delAllocationUser(RequestMessage resMessage)
			throws Exception {
		Map<String, Object> data = resMessage.getDataMap();
		String userId = (String) data.get("userId");
		if (StringUtil.checkNull(false, userId)) {
			allUserService.delAllocationUser(userId);

		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}

		return ResponseMessage.success();

	}

	/**
	 * 更新用户配置信息
	 * 
	 * @param resMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("updateAllocationUser")
	@ResponseBody
	public ResponseMessage updateAllocationUser(RequestMessage resMessage)
			throws Exception {
		Map<String, Object> data = resMessage.getDataMap();
		String userId = (String) data.get("userId");
		String userName = (String) data.get("userName");
		String mobile = (String) data.get("mobile");
		String branch = (String) data.get("branch");
		String roleId = (String) data.get("roleId");
		String state = (String) data.get("state");

		TAllocationUser locationUser = null;
		if (StringUtil.checkNull(false, userName, mobile, branch)) {
			locationUser = new TAllocationUser();
			locationUser.setUserId(userId);
			locationUser.setUserName(userName);
			locationUser.setMobile(mobile);
			locationUser.setBranch(branch);
			locationUser.setRoleId(Integer.parseInt(roleId));//roleId??
			locationUser.setState(state);
			allUserService.updateAllocationUser(locationUser);

		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}
		return ResponseMessage.success();

	}
}
