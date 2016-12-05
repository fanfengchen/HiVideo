package com.ebeijia.video.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ebeijia.video.service.VerificationService;
import com.ebeijia.videocore.constant.ApiResultCode;
import com.ebeijia.videocore.constant.CommonConstant;
import com.ebeijia.videocore.constant.CommonTypeConstant;
import com.ebeijia.videocore.dto.SmsSendMessage;
import com.ebeijia.videocore.dto.SmsSendResult;
import com.ebeijia.videocore.mapper.TUserMapper;
import com.ebeijia.videocore.mapper.TVerificationMapper;
import com.ebeijia.videocore.pojo.TUser;
import com.ebeijia.videocore.pojo.TVerification;
import com.ebeijia.videocore.util.DateUtil;
import com.ebeijia.videocore.util.PropertiesUtils;
import com.ebeijia.videocore.util.SmsSendUtil;
import com.ebeijia.videocore.util.StringUtil;
import com.ebeijia.videocore.web.ResponseMessage;

@Service
public class VerificationServiceImpl implements VerificationService{

	@Autowired
	private TVerificationMapper verMapper;
	
	@Autowired
	private TUserMapper userMapper;
	
	@Override
	public ResponseMessage getVerifivation(String mobile, String operType) {
		
		/**
        * 注册：01、修改密码：02、重置密码：03
        */
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", mobile);
		params.put("state", "01");
		
		TUser user = userMapper.selectByParams(params);
		if("01".equals(operType)){
			
			if(user!=null){
				
				return ResponseMessage.error(ApiResultCode.Err_1002.getCode(),
						ApiResultCode.Err_1002.getMsg());
			}
		}else if("03".equals(operType)){
			
          if(user==null){
				
        	 return ResponseMessage.success();
		  }
		}
		if (StringUtil.checkNull(false, mobile, operType)) {
			
	           if(StringUtil.CheckPhoneNumber(mobile)){
	        	   
	        	   String vcode = "";
					for (int i = 0; i < 6; i++) {
						vcode = vcode + (int) (Math.random() * 9);// 生成6位数验证码
					}   			
	  			  TVerification ver = new TVerification();
	  			  if (sendSmsMessage(operType, mobile, vcode)) {

	  				 //0代表发送成功
	  				 ver.setMobile(mobile);
	  				 ver.setIsActive(CommonTypeConstant.IsActive.False.toString());
	  				verMapper.updateSelect(ver);//将之前的验证更新为失效
	  				 
	  				 ver.setVerfCode(vcode);// 发送的验证码
	  				 ver.setSendTime(DateUtil.getTimes());//发送时间
	  				 ver.setIsActive(CommonTypeConstant.IsActive.True.toString());
	  				 ver.setVerfType(operType);
	  				verMapper.insert(ver);
	                  
	  			 }
	           }else{
	        	   return ResponseMessage.error(ApiResultCode.Err_0013.getCode(),
	   					ApiResultCode.Err_0013.getMsg());
	        	   
	            }			
			} else {
				// 缺少参数
				return ResponseMessage.error(ApiResultCode.Err_0002.getCode(),
						ApiResultCode.Err_0002.getMsg());
			}
		
		return ResponseMessage.success();
		
	}
	private boolean sendSmsMessage(String type, String mobile, String code) {
		SmsSendMessage mesg = new SmsSendMessage();
		mesg.setPhoneNo(mobile);
		
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("code", code);
		maps.put("product",
				PropertiesUtils.getProperties("sms.register.product"));

		SmsSendUtil util = new SmsSendUtil();

		switch (type) {
		case CommonConstant.SMS_V_CODE_REGISTER:
			mesg.setSmsFreeSignName(PropertiesUtils
					.getProperties("sms.register.smsFreeSignName"));
			mesg.setSmsTemplate(PropertiesUtils
					.getProperties("sms.register.template"));
			break;
		case CommonConstant.SMS_V_CODE_MODIFY:
			mesg.setSmsFreeSignName(PropertiesUtils
					.getProperties("sms.modify.smsFreeSignName"));
			mesg.setSmsTemplate(PropertiesUtils
					.getProperties("sms.modify.template"));
			break;
		case CommonConstant.SMS_V_CODE_UNDO:
			mesg.setSmsFreeSignName(PropertiesUtils
					.getProperties("sms.modify.smsFreeSignName"));
			mesg.setSmsTemplate(PropertiesUtils
					.getProperties("sms.undo.template"));
			break;
		default:
			break;
		}
		mesg.setSendMessage(maps);
		SmsSendResult result = util.sendSmsMessage(mesg);
		return "0".equals(result.getErrorCode());
	}
}
