package com.ebeijia.service.system;

public interface SysTokenSerivce {

	public Boolean judgeToken(String token);

	public Boolean judgeSign(String sendTime, String reqData,String sign);

}
