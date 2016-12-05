package com.taobao.api.response;

import com.taobao.api.internal.mapping.ApiField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: alibaba.aliqin.fc.flow.charge response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class AlibabaAliqinFcFlowChargeResponse extends TaobaoResponse {

	private static final long serialVersionUID = 1717962435852236794L;

	/** 
	 * error为true代表失败，code为失败代码，msg为失败原因
	 */
	@ApiField("value")
	private String value;


	public void setValue(String value) {
		this.value = value;
	}
	public String getValue( ) {
		return this.value;
	}
	


}
