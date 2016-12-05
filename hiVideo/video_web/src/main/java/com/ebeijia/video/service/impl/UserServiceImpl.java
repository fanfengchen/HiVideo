package com.ebeijia.video.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebeijia.video.service.UserService;
import com.ebeijia.video.service.UserTokenService;
import com.ebeijia.videocore.constant.ApiResultCode;
import com.ebeijia.videocore.mapper.TLogininfoMapper;
import com.ebeijia.videocore.mapper.TUserMapper;
import com.ebeijia.videocore.pojo.TLogininfo;
import com.ebeijia.videocore.pojo.TUser;
import com.ebeijia.videocore.util.DateUtil;
import com.ebeijia.videocore.util.PropertiesUtils;
import com.ebeijia.videocore.util.StringUtil;
import com.ebeijia.videocore.util.TokenUtil;
import com.ebeijia.videocore.web.ResponseMessage;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TUserMapper userMapper;
	
	@Autowired
	private TLogininfoMapper logininfoMapper;
	@Autowired
	private UserTokenService userTokenService;

	/**
	 * 查询用户是否已经注册
	 */
	public String isRegisted(String user) {

		String registed = "";// 代表是否已经注册
		TUser userResult = userMapper.selectByPrimaryKey(user);
		if (userResult != null) {
			registed = "1";
		}else{
			registed = "0";
		}
		return registed;
	}

	/**
	 * 用户注册信息
	 */
	@Transactional
	@Override
	public ResponseMessage registedUser(TUser userInfo, String verfCode)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		// 验证手机号码是否合法
		if (!StringUtil.CheckPhoneNumber(userInfo.getUserId())) {
			return ResponseMessage.error(ApiResultCode.Err_0013.getCode(),
					ApiResultCode.Err_0013.getMsg());
		}
		// 判断手机号码是不是已经注册
		if (userMapper.selectByPrimaryKey(userInfo.getUserId()) != null) {
			return ResponseMessage.error(ApiResultCode.Err_1002.getCode(),
					ApiResultCode.Err_1002.getMsg());
		}

		/*
		 * TVerification verification =
		 * verificationMapper.selectByParams(userInfo.getUserId(), verfCode);
		 * 
		 * if (DateUtil.calLastedTime(verification.getSendTime()) > 60) { return
		 * ResponseMessage.error("验证码时间超时"); }
		 */
		userMapper.insertSelective(userInfo);
		String isLogine = logininfoMapper.selectByPrimaryKey(userInfo
				.getUserId());
		params.put("logged", isLogine);

		return ResponseMessage.success(params);
	}

	/**
	 * 用户登录
	 */
	@Transactional
	@Override
	public ResponseMessage userLogin(String userId, String password,
			String pwdType, String devId, String appType,String seyKey) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String token = null;
		
		TUser user = userMapper.selectByPrimaryKey(userId);// 根据userId查找user信息
		TLogininfo loginResult = logininfoMapper.selectByKey(userId);// 根据userId查找登录信息表信息
		TLogininfo loginInfo = new TLogininfo();
		// 检验用户是不是存在
		if (user == null) {
			return ResponseMessage.error(ApiResultCode.Err_0005.getCode(),
					ApiResultCode.Err_0005.getMsg());
		}

		// 判断登录密码类型 用户密码登录
		if (pwdType.equals("01")) {

			if (!password.equals(user.getPwd())) {
				return ResponseMessage.error(ApiResultCode.Err_1001.getCode(),
						ApiResultCode.Err_1001.getMsg());
			}

		
		}else{			
			// 服务器密码登录
			String seyKeyM = PropertiesUtils.getProperties("token.authToken");
			if (!seyKeyM.equals(seyKey)) {
				return ResponseMessage.error(
						ApiResultCode.Err_0010.getCode(),
						ApiResultCode.Err_0010.getMsg());
			} else if (!devId.equals(loginResult.getDevId())) {
				return ResponseMessage.error(
						ApiResultCode.Err_1004.getCode(),
						ApiResultCode.Err_1004.getMsg());
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 生成Token
		map.put("userInfo", user);
		token = TokenUtil.setToken(map);
		
		if(loginResult!=null&&loginResult.getAuthToken()!=null){
			
			//清除token数据
			TokenUtil.clearGetToken(loginInfo.getAuthToken());
			
			loginInfo.setUserId(user.getUserId());
			loginInfo.setSerKey(token);
			loginInfo.setAuthToken(token);
			loginInfo.setType(appType);
			loginInfo.setIsLogged("1");
			loginInfo.setDevId(devId);
			loginInfo.setLastLoginTime(DateUtil.getTimes());
			logininfoMapper.updateByPrimaryKeySelective(loginInfo);
		}else{
			loginInfo.setUserId(user.getUserId());
			loginInfo.setSerKey(token);
			loginInfo.setAuthToken(token);
			loginInfo.setType(appType);
			loginInfo.setIsLogged("1");
			loginInfo.setDevId(devId);
			loginInfo.setLastLoginTime(DateUtil.getTimes());
			logininfoMapper.insertSelective(loginInfo);
		}		
		
		params.clear();
		params.put("authToken", loginInfo.getAuthToken());
		params.put("serKey", loginInfo.getSerKey());
		return ResponseMessage.success(params);
	}

	/**
	 * 重设密码
	 */
	@Override
	public ResponseMessage setPassword(String oldPwd, String newPwd,
			String token) throws Exception {
		// 根据token获取用户登录记录
		TUser user = userTokenService.getUserToken(token);
		if (!oldPwd.equals(user.getPwd())) {
			return ResponseMessage.error(ApiResultCode.Err_1005.getCode(),
					ApiResultCode.Err_1005.getMsg());
		}else{
			userMapper.updatePassword(newPwd, user.getUserId());
		}
		
		return ResponseMessage.success();
	}

	/**
	 * 忘记密码
	 */
	@Override
	public ResponseMessage resetPassWord(String userId, String passPord)
			throws Exception {
		userMapper.updatePassword(passPord, userId);
		return ResponseMessage.success();
	}

	/**
	 * 用户登出
	 */
	@Override
	public ResponseMessage loginOut(String authToken) throws Exception {
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("authToken", authToken);
		TLogininfo loginInfo = logininfoMapper.selectByParams(params);
		if(loginInfo!=null){
			
			TokenUtil.clearGetToken(authToken);
			loginInfo.setAuthToken("");
			loginInfo.setLastLogoutTime(DateUtil.getTimes());//登出时间
			logininfoMapper.updateByPrimaryKeySelective(loginInfo);
		}
		
		/*TokenUtil.clearGetToken(authToken);
		logininfoMapper.updateToken(authToken);*/
		return ResponseMessage.success();
	}
}
