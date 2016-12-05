package com.ebeijia.web;


import com.ebeijia.util.DateUtil;
import com.ebeijia.util.LoggerUtil;
import com.ebeijia.web.json.NullJsonObjectMaper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

public class ResponseMessage implements Serializable {
	private String sendTime;
	private String rspCode;
	private String rspMsg;
	private String signType = "02";
	private String sign;
	private Object data;

	public ResponseMessage(String rspCode, String rspMsg, Object data) {
		// super();
		this.rspCode = rspCode;
		this.rspMsg = rspMsg;
		this.data = data;
	}
	
	//{"data":{},rspCode:....}

	public static ResponseMessage success(Object data) {
		return new ResponseMessage("1", "", data);
	}

	public static ResponseMessage success() {
		return success("");
	}

	public static ResponseMessage error(String code, String message) {
		return new ResponseMessage(code, message, null);
	}

	public static ResponseMessage error() {
		return error("");
	}

	public static ResponseMessage error(String message) {
		return error("0", message);
	}
	
	public String getSendTime() {
		if (sendTime == null) {
			sendTime = String.valueOf(DateUtil.getCurrentDateTime());
		}
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getRspCode() {
		return rspCode;
	}

	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

	public String getRspMsg() {
		return rspMsg;
	}

	public void setRspMsg(String rspMsg) {
		this.rspMsg = rspMsg;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSign() throws JsonProcessingException {
		// 签名为空并且数据data不为空 做签名
		if (sign == null) {
			// 处理加密数据
			ObjectMapper mapper = new NullJsonObjectMaper();
			String dataSrc = mapper.writeValueAsString(this.getData());
			StringBuffer srcSign = new StringBuffer();
			srcSign.append("sendTime=").append(this.getSendTime())
					.append("data=").append(dataSrc);
			//sign = Md5.md5Upper(srcSign.toString());
			//this.data = dataSrc;
		}
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Object getData() throws JsonProcessingException {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new NullJsonObjectMaper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			LoggerUtil.error(e.getMessage(), e);
			return null;
		}
	}
	

}
