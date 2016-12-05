package com.ebeijia.videocore.web;

import java.io.Serializable;
import java.util.Map;
import com.ebeijia.videocore.exception.WebInfoException;
import com.ebeijia.videocore.util.PropertiesUtils;
import com.ebeijia.videocore.util.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class RequestMessage implements Serializable {
	private String sendTime;
	private String appType;
	private String data;
	private String authToken;
	private String devId;
	private DataMap<String, Object> dataMap;

	private Map<Object, Object> currentObj;

	public String getSendTime() {
		return sendTime;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
		String defToken = PropertiesUtils.getProperties("token.authToken");
		if (!authToken.equals(defToken)) {
			currentObj = TokenUtil.getToken(authToken);
		}
	}

	public String getData() {
		return data;
	}

	public void setData(String data) throws WebInfoException {
		this.data = data;
		ObjectMapper objMapper = new ObjectMapper();
		try {

			dataMap = objMapper.readValue(data, DataMap.class);
		} catch (Exception e) {
			throw new WebInfoException("数据格式错误");
		}
	}

	public DataMap<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(DataMap<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public Map<Object, Object> getCurrentObj() {
		return currentObj;
	}

	public void setCurrentObj(Map<Object, Object> currentObj) {
		this.currentObj = currentObj;
	}

}
