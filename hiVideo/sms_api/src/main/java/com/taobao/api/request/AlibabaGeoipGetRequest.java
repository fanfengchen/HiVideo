package com.taobao.api.request;

import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;

import com.taobao.api.response.AlibabaGeoipGetResponse;

/**
 * TOP API: alibaba.geoip.get request
 * 
 * @author top auto create
 * @since 1.0, 2016.03.24
 */
public class AlibabaGeoipGetRequest extends BaseTaobaoRequest<AlibabaGeoipGetResponse> {
	
	

	public String getApiMethodName() {
		return "alibaba.geoip.get";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<AlibabaGeoipGetResponse> getResponseClass() {
		return AlibabaGeoipGetResponse.class;
	}

	public void check() throws ApiRuleException {
	}
	

}