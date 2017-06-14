package com.ebeijia.robot.service;

import com.ebeijia.robot.core.web.ResponseMessage;



/**
 * 用户登录相关接口，供app端使用
 * @author lijm
 * @date 2016-12-14
 *
 */
public interface ILoginUserService{
	

	//用户登录接口
	 ResponseMessage userLogin(String userId,String password,String pwdType,String devId,String appType,String seyKey) throws Exception;
	
	//修改密码
	ResponseMessage setPassword(String oldPwd,String newPwd,String token)throws Exception;
	
	//用户登出
	ResponseMessage loginOut(String authToken) throws Exception;

}
