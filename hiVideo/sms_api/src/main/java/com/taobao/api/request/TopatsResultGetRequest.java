package com.taobao.api.request;

import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;

import com.taobao.api.response.TopatsResultGetResponse;

/**
 * TOP API: taobao.topats.result.get request
 * 
 * @author top auto create
 * @since 1.0, 2016.03.24
 */
public class TopatsResultGetRequest extends BaseTaobaoRequest<TopatsResultGetResponse> {
	
	

	public String getApiMethodName() {
		return "taobao.topats.result.get";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<TopatsResultGetResponse> getResponseClass() {
		return TopatsResultGetResponse.class;
	}

	public void check() throws ApiRuleException {
	}
	

}