package com.ebeijia.common;


import java.io.Serializable;

import com.ebeijia.contstant.RespCode;
import com.ebeijia.dto.VerSoftDto;
import com.ebeijia.util.DateTime4J;
import com.ebeijia.util.NullJsonObjectMaper;
import com.ebeijia.util.log.CustomLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseMessage implements Serializable {
	private String sendTime;
	private String rspCode;
	private String rspMsg;
	private String signType = "01";
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
		return new ResponseMessage(RespCode.SUCCESS_CODE, RespCode.Msg(RespCode.SUCCESS_CODE), data);
	}

	public static ResponseMessage success() {
		return success("");
	}

	public static ResponseMessage error(String code) {
		
		return new ResponseMessage(code, RespCode.Msg(code), null);
	}
	public static ResponseMessage error(String code, String message) {
		return new ResponseMessage(code, message, null);
	}

	public static ResponseMessage error() {
		return error("");
	}

	/*public static ResponseMessage error(String message) {
		return error("0", message);
	}*/
	
	public String getSendTime() {
		if (sendTime == null) {
			sendTime = String.valueOf(DateTime4J.getCurrentDateTime());
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
			CustomLog.getInstances().logInfo(e.getMessage(), e);
			return null;
		}
	}
	

}
