package com.ebeijia.robot.app.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ebeijia.robot.core.constant.ApiResultCode;
import com.ebeijia.robot.core.util.StringUtil;
import com.ebeijia.robot.core.web.RequestMessage;
import com.ebeijia.robot.core.web.ResponseMessage;
import com.ebeijia.robot.service.ILoginUserService;

/**
 * 手机端用户登录相关接口
 * @author lijm
 * @date 2016-12-14
 *
 */

@RequestMapping("/")
@Controller
public class LoginUserController {
	
	@Autowired
	private ILoginUserService loginService;
	
	/**
	 * 用户登录
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("login")
	@ResponseBody
	public ResponseMessage login(RequestMessage reMessage) throws Exception{
		
		Map<String, Object> map = reMessage.getDataMap();
		String user = (String) map.get("user");
		String passWord = (String) map.get("pwd");
		String pwdType = (String) map.get("pwdType");
		String devId = reMessage.getDevId();
		String appType = reMessage.getAppType();
		String seyKey = reMessage.getAuthToken();
		//判断传来的参数是不是为空
		if (StringUtil.checkNull(false, user, passWord, pwdType)) {
			return loginService.userLogin(user, passWord, pwdType, devId,
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
			return loginService.setPassword(oldPwd, newPwd, token);
		} else {

			return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
					ApiResultCode.Err_0002.getMsg());
		}
	}
	
	/**
	 * 用户登出 
	 * @param reMessage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("logout")
	@ResponseBody
	public ResponseMessage logout(RequestMessage reMessage) throws Exception {
		String authToken = reMessage.getAuthToken();
		return loginService.loginOut(authToken);

	}

}
