package com.taobao.api.response;

import com.taobao.api.internal.mapping.ApiField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: alibaba.aliqin.fc.flow.query response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class AlibabaAliqinFcFlowQueryResponse extends TaobaoResponse {

	private static final long serialVersionUID = 1344535575312536486L;

	/** 
	 * "id":"唯一流水号",     "time":"提交时间",     "phone":"号码",     "error":"false",     "reason":"原因",     "status":"充值状态",     "flow":"流量",     "operator":"中国移动"
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
