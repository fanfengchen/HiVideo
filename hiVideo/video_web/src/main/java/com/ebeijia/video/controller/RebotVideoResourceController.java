package com.ebeijia.video.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebeijia.video.modle.api.RobotVideoResourceRes;
import com.ebeijia.video.service.RobotVideoResourceService;
import com.ebeijia.videocore.web.RequestMessage;
import com.ebeijia.videocore.web.ResponseMessage;

/**
 * 获取用户机器人的视频资源列表
 * 
 * @author ff
 *
 */
@RequestMapping("/")
@Controller
public class RebotVideoResourceController {

	@Autowired
	private RobotVideoResourceService resourceRes;

	@RequestMapping("getRebotUserResource")
	@ResponseBody
	public ResponseMessage getRebotUserResource(RequestMessage reMessage) {
		Map map = reMessage.getDataMap();
		String vid = (String) map.get("vid");
		String vtype = (String) map.get("vtype");
		String vname = (String) map.get("vname");	
		String devId = reMessage.getDevId();

		List<RobotVideoResourceRes> relist = null;
		// if (StringUtil.checkNull(false, vid, devId)) {
		relist = resourceRes.getUerRobotVideo(devId, vid, vtype, vname);

		/*
		 * } else { return
		 * ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
		 * ApiResultCode.Err_0002.getMsg()); }
		 */
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("list", relist);
		return ResponseMessage.success(params);

	}

}
