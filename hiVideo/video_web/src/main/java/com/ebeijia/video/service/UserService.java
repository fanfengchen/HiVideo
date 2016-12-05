package com.ebeijia.video.service;

import com.ebeijia.videocore.pojo.TUser;
import com.ebeijia.videocore.web.ResponseMessage;


/**
 * 用户基本信息
 * @author lijm
 * @date 2016-11-07
 *
 */
public interface UserService {

	
	//查询用户是否已经注册
	String isRegisted(String user)throws Exception;
	
	//用户注册
	ResponseMessage registedUser(TUser userInfo,String verfCode)throws Exception;
	
	//用户登录
	ResponseMessage userLogin(String userId,String password,String pwdType,String devId,String appType,String seyKey) throws Exception;

   //修改密码
	ResponseMessage setPassword(String oldPwd,String newPwd,String token)throws Exception;
	
	//忘记密码
	ResponseMessage resetPassWord(String userId,String passPord) throws Exception;
	
	//用户登出
	ResponseMessage loginOut(String authToken) throws Exception;

}
