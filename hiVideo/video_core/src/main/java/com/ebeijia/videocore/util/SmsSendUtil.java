package com.ebeijia.videocore.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.ebeijia.videocore.dto.SmsSendMessage;
import com.ebeijia.videocore.dto.SmsSendResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.BizResult;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SmsSendUtil {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 发送短信
	 * 
	 * @param mesg
	 * @return SmsSendResult errorCode为0 发送成功
	 */
	public SmsSendResult sendSmsMessage(SmsSendMessage mesg) {
		SmsSendResult result = new SmsSendResult();
		try {
			log.info("发送短信开始:" + mesg.getSmsTemplate());

			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> sendParm = mesg.getSendMessage();
			String sendParamString = "";
			if (sendParm != null && !sendParm.isEmpty()) {
				sendParamString = mapper.writeValueAsString(sendParm);
			}
			TaobaoClient client = new DefaultTaobaoClient(mesg.getUrl(),
					mesg.getAppKey(), mesg.getAppSecret());
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			req.setExtend(mesg.getPhoneNo());
			req.setSmsType(mesg.getSmsType());
			req.setSmsFreeSignName(mesg.getSmsFreeSignName());
			req.setSmsParamString(sendParamString);
			req.setRecNum(mesg.getPhoneNo());
			req.setSmsTemplateCode(mesg.getSmsTemplate());
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			BeanUtils.copyProperties(rsp, result);
			// 发送成功
			if (StringUtils.isEmpty(rsp.getErrorCode())) {
				BizResult br = rsp.getResult();
				result.setErrorCode(br.getErrCode());
				result.setMsg(br.getMsg());
			}
			log.info("发送短信结束:" + mesg.getSmsTemplate());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setErrorCode("009");
		}
		return result;
	}
	
	/*public static void main(String[] args) {
		SmsSendMessage mesg=new SmsSendMessage();
		
		mesg.setPhoneNo("13818345548");
		mesg.setSmsFreeSignName("大鱼测试");
		mesg.setSmsTemplate("SMS_6635042");
		Map<String,String> map=new HashMap<String, String>();
		map.put("customer", "短息发送已经o了");
		mesg.setSendMessage(map);
		SmsSendResult result=new SmsSendUtil().sendSmsMessage(mesg);
		System.out.println(result.getErrorCode());
		
	}*/

}
