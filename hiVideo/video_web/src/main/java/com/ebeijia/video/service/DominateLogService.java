package com.ebeijia.video.service;

import java.util.List;
import java.util.Map;

import com.ebeijia.video.modle.api.DominateLogRes;
import com.ebeijia.videocore.common.Order;
import com.ebeijia.videocore.pojo.DomateLog;
import com.ebeijia.videocore.pojo.TDominateLog;

public interface DominateLogService {

	void findListByParams(Map<String, Object> params, Order order);

	void update(TDominateLog log);

	List<DominateLogRes> getdominateLog(String devId) throws Exception;
	
	void delDominateLog(List<DomateLog> rList)throws Exception;
}
