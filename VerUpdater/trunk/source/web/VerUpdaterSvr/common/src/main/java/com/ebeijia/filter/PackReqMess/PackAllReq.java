package com.ebeijia.filter.PackReqMess;

import java.util.Map;

import com.ebeijia.common.IPackType.PTHeadReq;
import com.ebeijia.util.exception.EPackException;

public class PackAllReq extends PackBootReq {
	public PackAllReq() {

	}

	public PackAllReq(Map<String, String[]> map) throws EPackException {
		super(map);
	}

	public void checkParam(String url) throws EPackException {
		super.checkParam();
		checkParam(getBuffer().getJSONArray(PTHeadReq.data.name()).getJSONObject(0), url);
	}
}
