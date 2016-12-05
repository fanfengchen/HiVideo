package com.ebeijia.video.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ebeijia.video.modle.api.RobotNResourceRes;
import com.ebeijia.video.service.VideoResourceService;
import com.ebeijia.videocore.constant.ApiResultCode;
import com.ebeijia.videocore.util.StringUtil;
import com.ebeijia.videocore.util.TokenUtil;
import com.ebeijia.videocore.web.RequestMessage;
import com.ebeijia.videocore.web.ResponseMessage;

/**
 * 获取机器人可添加的视频列表
 * 
 * @author ff
 *
 */
@Controller
@RequestMapping("/")
public class RobotNResourceController {
	@Autowired
	private VideoResourceService videoResourceService;

	@RequestMapping(value = "getRobotNResource")
	@ResponseBody
	public ResponseMessage getRobotNResource(RequestMessage reMessage)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
	    Map<String,Object> data = reMessage.getDataMap();
		String devId = data.get("deviceId").toString();
		String token = reMessage.getAuthToken();
		if (TokenUtil.getToken(token) == null) {
			return ResponseMessage.error(ApiResultCode.Err_1003.getCode(),
					ApiResultCode.Err_1003.getMsg());
		}

		if (!StringUtil.checkNull(false, devId)) {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}
		List<RobotNResourceRes> list = videoResourceService.getRobotNResource(
				token, devId);
		map.put("list", list);
		return ResponseMessage.success(map);
	}
}
