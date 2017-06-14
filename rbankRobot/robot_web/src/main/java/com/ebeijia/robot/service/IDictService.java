package com.ebeijia.robot.service;

import java.util.List;
import java.util.Map;
import com.ebeijia.robot.core.common.Order;
import com.ebeijia.robot.core.pojo.TDict;
import com.ebeijia.robot.modle.api.DictListRes;
import com.ebeijia.robot.modle.api.DictRes;


public interface IDictService {

	DictListRes getDict(Map<String, Object> params,
			Order order, Integer pageNum, Integer pageSize) throws Exception;

	List<DictRes> getDict() throws Exception;
	void setDict(TDict tDict) throws Exception;

	void delRebot(String id) throws Exception;

}
