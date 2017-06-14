package com.ebeijia.robot.service;

import java.util.Map;

import com.ebeijia.robot.core.common.Order;
import com.ebeijia.robot.core.exception.ServiceException;
import com.ebeijia.robot.core.pojo.TAllocationUser;
import com.ebeijia.robot.core.web.ResponseMessage;
import com.ebeijia.robot.modle.api.AllocationUserList;

/**
 * 配置用户信息(基本的增删改查)，供app端使用
 * 
 * @author lijm
 * @date 2016-12-12
 *
 */
public interface IAllocationUserService {

	public AllocationUserList findAllocation(Map<String, Object> params,
			Order order, Integer pageNum, Integer pageSize)
			throws ServiceException;

	public ResponseMessage addAllocationUser(TAllocationUser locationUser) throws Exception;

	public void delAllocationUser(String userId) throws Exception;

	public void updateAllocationUser(TAllocationUser locationUser)
			throws Exception;

}
