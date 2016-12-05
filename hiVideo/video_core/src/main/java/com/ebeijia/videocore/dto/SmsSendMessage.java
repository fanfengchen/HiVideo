package com.ebeijia.videocore.dto;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ebeijia.videocore.util.PropertiesUtils;

/**
 * url 发送短息地址 appKeyTOP分配给应用的AppKey。 appSecret 分配的秘钥 默认值为pze分配的，如需变更可从配置文件配置读取
 * 
 * @author chyulin
 * 
 */
public class SmsSendMessage {

	private String url;
	private String appKey;
	private String appSecret;
	private String phoneNo;
	private String smsType = "normal";
	/**
	 * 短信签名，传入的短信签名必须是在阿里大鱼“管理中心-短信签名管理”中的可用签名。如“阿里大鱼”已在短信签名管理中通过审核，则可传入”阿里大鱼“（
	 * 传参时去掉引号）作为短信签名。短信效果示例：【阿里大鱼】欢迎使用阿里大鱼服务。
	 */
	private String smsFreeSignName;

	private String smsTemplate;

	private Map<String, String> sendMessage;

	public String getUrl() {
		if (StringUtils.isEmpty(url)) {
			String urlPro = PropertiesUtils.getProperties("sms.url");
			url = StringUtils.isEmpty(urlPro) ? "https://eco.taobao.com/router/rest"
					: urlPro;
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAppKey() {
		if (StringUtils.isEmpty(appKey)) {
			String appKeyPro = PropertiesUtils.getProperties("sms.appKey");
			appKey = StringUtils.isEmpty(appKeyPro) ? "23331866" : appKeyPro;
		}
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		if (StringUtils.isEmpty(appSecret)) {
			String appSecretPro = PropertiesUtils
					.getProperties("sms.appSecret");
			appSecret = StringUtils.isEmpty(appSecretPro) ? "ecb65bf12a1a1a7d8f27255ac1db53b8"
					: appSecretPro;
		}
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public Map<String, String> getSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(Map<String, String> sendMessage) {
		this.sendMessage = sendMessage;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	public String getSmsFreeSignName() {
		return smsFreeSignName;
	}

	public void setSmsFreeSignName(String smsFreeSignName) {
		this.smsFreeSignName = smsFreeSignName;
	}

	public String getSmsTemplate() {
		return smsTemplate;
	}

	public void setSmsTemplate(String smsTemplate) {
		this.smsTemplate = smsTemplate;
	}

}
