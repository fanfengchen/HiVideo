package com.ebeijia.robot.service;

import java.util.List;
import java.util.Map;

import com.ebeijia.robot.core.common.Order;
import com.ebeijia.robot.core.exception.ServiceException;
import com.ebeijia.robot.modle.api.RoleList;
import com.ebeijia.robot.modle.api.RoleRes;

public interface IRoleService {

	RoleList findRoleList(Map<String, Object> params, Order order,
			Integer pageNum, Integer pageSize) throws ServiceException;

	void delRole(String id) throws ServiceException;

	void updateRole(String id, String name) throws Exception;

	List<RoleRes> getRoleIdList();

}
