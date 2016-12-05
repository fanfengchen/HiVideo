package com.taobao.api.request;

import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;

import com.taobao.api.response.TopatsTaskDeleteResponse;

/**
 * TOP API: taobao.topats.task.delete request
 * 
 * @author top auto create
 * @since 1.0, 2016.03.24
 */
public class TopatsTaskDeleteRequest extends BaseTaobaoRequest<TopatsTaskDeleteResponse> {
	
	

	public String getApiMethodName() {
		return "taobao.topats.task.delete";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<TopatsTaskDeleteResponse> getResponseClass() {
		return TopatsTaskDeleteResponse.class;
	}

	public void check() throws ApiRuleException {
	}
	

}