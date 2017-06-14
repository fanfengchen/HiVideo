package com.ebeijia.robot.service;

import java.util.List;
import java.util.Map;

import com.ebeijia.robot.core.common.Order;
import com.ebeijia.robot.core.exception.ServiceException;
import com.ebeijia.robot.core.pojo.TDictdata;
import com.ebeijia.robot.modle.api.DictDataListRes;
import com.ebeijia.robot.modle.api.DictDataRes;

public interface IDictDataService {

	DictDataListRes getDictData(Map<String, Object> params, Order order,
			Integer pageNum, Integer pageSize) throws Exception;

	void setDictData(String dictId, List<TDictdata> list)
			throws ServiceException;

	List<DictDataRes> getDictData(String dictId) throws Exception;

	void delDictData(String dictId, String dictDataKey) throws ServiceException;

}
