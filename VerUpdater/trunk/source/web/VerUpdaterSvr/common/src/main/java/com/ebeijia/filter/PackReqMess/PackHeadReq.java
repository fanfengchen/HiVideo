package com.ebeijia.filter.PackReqMess;

import java.util.Map;

import com.ebeijia.common.IPackType.PTHeadReq;
import com.ebeijia.common.PackBase;
import com.ebeijia.util.exception.EPackException;

/**
 * 验证请求报文头
 * 
 * @author ff
 * @date 创建时间：2016年10月19日 下午4:37:09
 * @version 1.0
 * @parameter
 * @return
 */
public class PackHeadReq extends PackBase {

	public PackHeadReq() {
		super();
	}

	public PackHeadReq(Map<String, String[]> map) throws EPackException {
		super(map);
	}

	@Override
	public void checkParam() throws EPackException {
		checkParam(PTHeadReq.class);
	}

}
