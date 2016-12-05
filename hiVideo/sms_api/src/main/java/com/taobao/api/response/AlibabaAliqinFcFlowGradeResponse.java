package com.taobao.api.response;

import com.taobao.api.internal.mapping.ApiField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: alibaba.aliqin.fc.flow.grade response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class AlibabaAliqinFcFlowGradeResponse extends TaobaoResponse {

	private static final long serialVersionUID = 7737232173427935382L;

	/** 
	 * 档位
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
