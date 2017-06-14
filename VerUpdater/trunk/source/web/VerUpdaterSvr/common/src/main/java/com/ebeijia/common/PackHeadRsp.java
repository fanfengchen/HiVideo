package com.ebeijia.common;

import java.util.Date;

import com.ebeijia.util.exception.EPackException;
import com.ebeijia.common.IPackType.PTHeadRsp;

public class PackHeadRsp extends PackBase{
	
	public PackHeadRsp(){
		super();
	}
	
	public PackHeadRsp(String jsonString) throws EPackException{
		super(jsonString);
	}
	
	@Override
	public void checkParam() throws EPackException{
		checkParam(PTHeadRsp.class);
	}
	
	@Override
	public void Pack(){
		super.Pack();
		//需要外部传递进来的参数，这里就不用添加，在外部添加
		putParam(PTHeadRsp.rspCode, "");
		putParam(PTHeadRsp.rspMsg, "");
		putParam(PTHeadRsp.sendTime, new Date().getTime());
	}
}
