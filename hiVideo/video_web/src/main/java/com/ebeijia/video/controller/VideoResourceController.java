package com.ebeijia.video.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ebeijia.video.modle.api.EnshrineRes;
import com.ebeijia.video.modle.api.RobotVideoRes;
import com.ebeijia.video.modle.api.VideoResourceRes;
import com.ebeijia.video.service.VideoResourceService;
import com.ebeijia.videocore.constant.ApiResultCode;
import com.ebeijia.videocore.util.StringUtil;
import com.ebeijia.videocore.util.TokenUtil;
import com.ebeijia.videocore.web.RequestMessage;
import com.ebeijia.videocore.web.ResponseMessage;

@RequestMapping("/")
@Controller
public class VideoResourceController {
	@Autowired
	private VideoResourceService reService;

	/**
	 * 获取视频资源列表
	 * 
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getResource")
	@ResponseBody
	public ResponseMessage getVideoResource(RequestMessage reMessage)
			throws Exception {
		Map<String, Object> params = (Map<String, Object>) reMessage
				.getDataMap();
		String vtype = (String) params.get("vtype");
		String vname = (String) params.get("vname");
		Map<String, Object> reMap = new HashMap<String, Object>();
		List<RobotVideoRes> list = reService.getVideoResource(vtype, vname);
		reMap.put("list", list);

		return ResponseMessage.success(reMap);
	}

	/**
	 * 添加用户收藏列表
	 * 
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAddUserResource")
	@ResponseBody
	public ResponseMessage addUserResource(RequestMessage reMessage)
			throws Exception {
		Map<String, Object> params = (Map<String, Object>) reMessage
				.getDataMap();
		String vid = (String) params.get("vid");
		String token = reMessage.getAuthToken();
		Map tokenInfo = TokenUtil.getToken(token);
		if (tokenInfo == null) {
			return ResponseMessage.error(ApiResultCode.Err_1003.getCode(),
					ApiResultCode.Err_1003.getMsg());
		}

		if (StringUtil.checkNull(false, vid)) {
			return reService.addUserResource(vid, token);
		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}

	}

	/**
	 * 获取用户收藏视频列表
	 * 
	 * @param resMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getUserResource")
	@ResponseBody
	public ResponseMessage getUserResource(RequestMessage resMessage)
			throws Exception {
		Map<String, Object> params = (Map<String, Object>) resMessage
				.getDataMap();
		String vtype = (String) params.get("vtype");
		String vname = (String) params.get("vname");
		String token = resMessage.getAuthToken();
		Map tokenInfo = TokenUtil.getToken(token);
		if (tokenInfo == null) {
			return ResponseMessage.error(ApiResultCode.Err_1003.getCode(),
					ApiResultCode.Err_1003.getMsg());
		}
		List<VideoResourceRes> list = reService.getUserVideoResource(vtype,
				vname, token);
		Map<String, Object> reMap = new HashMap<String, Object>();
		reMap.put("list", list);
		return ResponseMessage.success(reMap);
	}

	/**
	 * 查询视频是否被收藏
	 * 
	 * @param resMessage
	 * @return
	 * @throws Exception
	 * ff
	 */
	@RequestMapping(value = "isEnshrine")
	@ResponseBody
	public ResponseMessage isEnshrine(RequestMessage resMessage)
			throws Exception {
		
		Map<String, Object> reData = (Map<String, Object>) resMessage
				.getDataMap();
		String vid = reData.get("vid").toString();
		String token = resMessage.getAuthToken();

		if (TokenUtil.getToken(token) == null) {
			return ResponseMessage.error(ApiResultCode.Err_1003.getCode(),
					ApiResultCode.Err_1003.getMsg());
		}

		if (!StringUtil.checkNull(false, vid)) {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}

		EnshrineRes enshrineRes = reService.isEnshrine(token, vid);

		return ResponseMessage.success(enshrineRes);

	}
}
