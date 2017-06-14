package com.ebeijia.filter.PackReqMess;

import java.util.Map;

import com.ebeijia.common.IPackType.PTMacVerify;
import com.ebeijia.util.exception.EPackException;

/**
 * 报尾校验
 * 
 * @author ff
 * @date 创建时间：2016年10月19日 下午5:52:34
 * @version 1.0
 * @parameter
 * @return
 */
public class PackBootReq extends PackHeadReq {

	public PackBootReq() {
		super();
	}

	public PackBootReq(Map<String, String[]> map) throws EPackException {
		super(map);
	}

	public void checkParam() throws EPackException {
		super.checkParam();
		checkParam(PTMacVerify.class);
	}


}
