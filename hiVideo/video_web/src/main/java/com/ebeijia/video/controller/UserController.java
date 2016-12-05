package com.ebeijia.video.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebeijia.video.service.UserService;
import com.ebeijia.videocore.constant.ApiResultCode;
import com.ebeijia.videocore.pojo.TUser;
import com.ebeijia.videocore.util.StringUtil;
import com.ebeijia.videocore.web.RequestMessage;
import com.ebeijia.videocore.web.ResponseMessage;

@RequestMapping("/")
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 用户注册
	 * 
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("regDominate")
	@ResponseBody
	public ResponseMessage regDominate(RequestMessage reMessage,
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = reMessage.getDataMap();
		TUser userInfo = new TUser();
		userInfo.setUserId((String) map.get("user"));
		userInfo.setPwd((String) map.get("pwd"));
		userInfo.setRegType((String) map.get("regType"));
		String verfCode = (String) map.get("verfCode");
		return userService.registedUser(userInfo, verfCode);

	}

	/**
	 * 用户是否已注册
	 * 
	 * @param reMessage
	 * @return
	 */
	@RequestMapping("isRegisted")
	@ResponseBody
	public ResponseMessage isRegisted(RequestMessage reMessage)
			throws Exception {

		Map<String, Object> map = (Map<String, Object>) reMessage.getDataMap();
		String user = (String) map.get("user");// 用户名
		String registed = userService.isRegisted(user);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("registed", registed);// 返回客户端
		return ResponseMessage.success(params);
	}

	/**
	 * 用户登录
	 * 
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("login")
	@ResponseBody
	public ResponseMessage login(RequestMessage reMessage) throws Exception {
		Map<String, Object> map = reMessage.getDataMap();
		String user = (String) map.get("user");
		String passWord = (String) map.get("pwd");
		String pwdType = (String) map.get("pwdType");
		String devId = reMessage.getDevId();
		String appType = reMessage.getAppType();
		String seyKey = reMessage.getAuthToken();
		// 判断传来的参数是不是为空
		if (StringUtil.checkNull(false, user, passWord, pwdType)) {
			return userService.userLogin(user, passWord, pwdType, devId,
					appType,seyKey);

		} else {
			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}

	}

	/**
	 * 修改密码
	 * 
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("setPwd")
	@ResponseBody
	public ResponseMessage setPassword(RequestMessage reMessage)
			throws Exception {
		Map<String, Object> map = reMessage.getDataMap();
		String oldPwd = (String) map.get("oldPwd");
		String newPwd = (String) map.get("newPwd");
		String token = reMessage.getAuthToken();
		 
		if (StringUtil.checkNull(false, oldPwd, newPwd)) {
			return userService.setPassword(oldPwd, newPwd, token);
		} else {

			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}

	}

	/**
	 * 忘记密码
	 * 
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("resetPwdAndVerf")
	@ResponseBody
	public ResponseMessage resetPwdAndVerf(RequestMessage reMessage)
			throws Exception {
		Map<String, Object> map = reMessage.getDataMap();
		String userId = map.get("user").toString();
		String password = map.get("newPwd").toString();

		if (StringUtil.checkNull(false, userId, password)) {
			return userService.resetPassWord(userId, password);
		} else {

			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}
	}

	/**
	 * 用户登出
	 * 
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("logout")
	@ResponseBody
	public ResponseMessage logout(RequestMessage reMessage) throws Exception {
		String authToken = reMessage.getAuthToken();
		return userService.loginOut(authToken);

	}
}
