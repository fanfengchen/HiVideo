package com.ebeijia.video.service;

import com.ebeijia.videocore.web.ResponseMessage;


/**
 * 短信验证码
 * @author lijm
 * @date 2016-11-08
 */
public interface VerificationService{
	
	ResponseMessage getVerifivation(String mobile,String operType);	

}
