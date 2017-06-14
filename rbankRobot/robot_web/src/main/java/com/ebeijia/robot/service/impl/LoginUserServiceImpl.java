package com.ebeijia.robot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ebeijia.robot.core.constant.ApiResultCode;
import com.ebeijia.robot.core.mapper.TAllocationUserMapper;
import com.ebeijia.robot.core.mapper.TLogininfoMapper;
import com.ebeijia.robot.core.mapper.TRoleAccMapper;
import com.ebeijia.robot.core.mapper.TRoleMapper;
import com.ebeijia.robot.core.pojo.TAllocationUser;
import com.ebeijia.robot.core.pojo.TLogininfo;
import com.ebeijia.robot.core.pojo.TRole;
import com.ebeijia.robot.core.pojo.TRoleAcc;
import com.ebeijia.robot.core.util.DateUtil;
import com.ebeijia.robot.core.util.PasswordHandler;
import com.ebeijia.robot.core.util.PropertiesUtils;
import com.ebeijia.robot.core.util.TokenUtil;
import com.ebeijia.robot.core.web.ResponseMessage;
import com.ebeijia.robot.modle.api.LoginResult;
import com.ebeijia.robot.modle.api.RoleResult;
import com.ebeijia.robot.service.ILoginUserService;
import com.ebeijia.robot.service.IUserTokenService;

/**
 * 配置用户信息(基本的增删改查)，供app端使用
 * @author lijm
 * @date 2016-12-12
 *
 */
@Service
public class LoginUserServiceImpl implements ILoginUserService{
	
	@Autowired
	private TAllocationUserMapper userMapper;
	
	@Autowired
	private TLogininfoMapper loginMapper;
	
	@Autowired
	private IUserTokenService userService;
	
	@Autowired
	private TRoleMapper roleMapper;
	
	@Autowired
	private TRoleAccMapper roleAccMapper;
	
   /**
    * 用户登录信息
    */
	@Override
	public ResponseMessage userLogin(String userId, String password,
			String pwdType, String devId, String appType, String seyKey)
			throws Exception {
		
		LoginResult res = new LoginResult();
		Map<String, Object> params = new HashMap<String, Object>();
		String token = null;		
		TAllocationUser user = userMapper.selectByPrimaryKey(userId);
		// 检验用户是不是存在
		if (user == null) {
			return ResponseMessage.error(ApiResultCode.Err_0005.getCode(),
					ApiResultCode.Err_0005.getMsg());
		}
		TLogininfo loginResult = loginMapper.selectByKey(userId);// 根据userId查找登录信息表信息
		TLogininfo loginInfo = new TLogininfo();
		// 判断登录密码类型 用户密码登录
		if (pwdType.equals("01")) {

			if (!user.getPwd().equals(PasswordHandler.getPassword(password))) {
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
		//生成Token
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
			loginMapper.updateByPrimaryKeySelective(loginInfo);
		}else{
			loginInfo.setUserId(user.getUserId());
			loginInfo.setSerKey(token);
			loginInfo.setAuthToken(token);
			loginInfo.setType(appType);
			loginInfo.setIsLogged("1");
			loginInfo.setDevId(devId);
			loginInfo.setLastLoginTime(DateUtil.getTimes());
			loginMapper.insertSelective(loginInfo);
		}
		res.setAuthToken(token);
		res.setSerKey(token);
		res.setRoleId(user.getRoleId().toString());
		//获取角色
		String roleId = user.getRoleId().toString();
		TRole role = roleMapper.selectByPrimaryKey(Integer.parseInt(roleId));
		if(role!=null){
			
			res.setRoleName(role.getName());
			List<TRoleAcc> roleAcc = roleAccMapper.selectListIdRoleAcc(role.getRoleId());
			List<RoleResult> roleResult = new ArrayList<RoleResult>();
			
			if(roleAcc!=null && roleAcc.size()>0){
			  	
				for(TRoleAcc acc:roleAcc){
					RoleResult rest = new RoleResult();
					rest.setId(acc.getAccId().toString());//角色权限ID
					rest.setName(acc.getAccName());//角色权限名称
					roleResult.add(rest);
				}
			}	
			res.setRoleAcc(roleResult);//存放角色权限
		}	
		
		return ResponseMessage.success(res);
		
	}

	/**
	 * 修改密码
	 */
	@Override
	public ResponseMessage setPassword(String oldPwd, String newPwd, String token)
			throws Exception {
		
		//根据token获取用户登录记录
		TAllocationUser user = userService.getUserToken(token);
		if (!PasswordHandler.getPassword(oldPwd).equals(user.getPwd())) {
			return ResponseMessage.error(ApiResultCode.Err_1005.getCode(),
					ApiResultCode.Err_1005.getMsg());
		}else{
		
			user.setUserId(user.getUserId());
			user.setPwd(PasswordHandler.getPassword(newPwd));
			userMapper.updateByPrimaryKeySelective(user);
		}		
		return ResponseMessage.success();
	}
	
	/**
	 * 退出登录
	 */
	@Override
	public ResponseMessage loginOut(String authToken) throws Exception{
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("authToken", authToken);
		TLogininfo loginInfo = loginMapper.selectByParams(params);
		if(loginInfo!=null){
			
			TokenUtil.clearGetToken(authToken);
			loginInfo.setAuthToken("");
			loginInfo.setLastLogoutTime(DateUtil.getTimes());//登出时间
			loginMapper.updateByPrimaryKeySelective(loginInfo);
		}		
		TokenUtil.clearGetToken(authToken);		
		return ResponseMessage.success();		
	}

}
