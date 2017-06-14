package com.ebeijia.robot.service;

import java.util.List;
import java.util.Map;

import com.ebeijia.robot.core.common.Order;
import com.ebeijia.robot.core.exception.ServiceException;
import com.ebeijia.robot.core.pojo.TAllocationApp;
import com.ebeijia.robot.modle.api.AllocaAppRes;
import com.ebeijia.robot.modle.api.AllocationAppList;

public interface IAllocationAppService {

	public List<AllocaAppRes> getAllApp(String deviceId);

	public void updataAllocationApp(TAllocationApp allocationApp) throws Exception;

	public void delAllocationApp(int aId) throws Exception;

	public AllocationAppList findAllocationAppList(Map<String, Object> params,
			Order order, Integer pageNum, Integer pageSize) throws ServiceException;


	public void insertAllocationApp(TAllocationApp allocationApp)throws Exception;

	
	public void updateState(String aId,String state);

}
