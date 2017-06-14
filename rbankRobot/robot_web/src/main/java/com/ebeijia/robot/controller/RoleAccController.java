package com.ebeijia.robot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebeijia.robot.core.constant.ApiResultCode;
import com.ebeijia.robot.core.pojo.TRoleAcc;
import com.ebeijia.robot.core.util.StringUtil;
import com.ebeijia.robot.core.web.DataMap;
import com.ebeijia.robot.core.web.RequestMessage;
import com.ebeijia.robot.core.web.ResponseMessage;
import com.ebeijia.robot.modle.api.RoleAccRes;
import com.ebeijia.robot.service.IRoleAccService;

@RequestMapping("/")
@Controller
public class RoleAccController {
	@Autowired
	private IRoleAccService accService;

	/**
	 * 获取角色权限
	 * 
	 * @param resMessage
	 * @return
	 */
	@RequestMapping("getRoleAcc")
	@ResponseBody
	public ResponseMessage getRoleAccList(RequestMessage resMessage)
			throws Exception {
		Map<String, Object> data = resMessage.getDataMap();
		String id = data.get("id").toString();
		List<RoleAccRes> list = null;

		if (StringUtil.checkNull(false, id)) {
			list = accService.getRoleAccList(id);
		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return ResponseMessage.success(map);
	}
	
	/**
	 * 设置角色权限
	 */
	@RequestMapping(value="setRoleAcc")
	@ResponseBody
	public ResponseMessage setRoleAcc(RequestMessage resMessage)throws Exception{
		
		DataMap<String, Object> data = resMessage.getDataMap();
		String id = (String) data.get("id");
		if (StringUtil.checkNull(false, id)) {
			// 获取请求报文中的集合
			List<Map<String, Object>> maps = data.getValueObAsList("list");
			if (maps != null) {
				List<TRoleAcc> list = new ArrayList<TRoleAcc>();
				for (Map<String, Object> m : maps) {
					TRoleAcc tAcc = new TRoleAcc();
					tAcc.setAccId((String)m.get("id"));
					tAcc.setAccName((String)m.get("name"));
					list.add(tAcc);
				}
				accService.setRoleAcc(id,list);
			}

		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());

		}

		return ResponseMessage.success();
	}
}
