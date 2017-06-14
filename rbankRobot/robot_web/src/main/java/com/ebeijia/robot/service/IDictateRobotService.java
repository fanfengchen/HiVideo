package com.ebeijia.robot.service;

import java.util.List;
import java.util.Map;

import com.ebeijia.robot.core.common.Order;
import com.ebeijia.robot.core.exception.ServiceException;
import com.ebeijia.robot.core.pojo.TDictateRobot;
import com.ebeijia.robot.modle.api.DicateRobotList;
import com.ebeijia.robot.modle.api.DictateRobotRes;

public interface IDictateRobotService {

	public List<DictateRobotRes> getDictsteRobot(Map<String,Object> params);

	public void updateDictate(TDictateRobot dictateRobot);

	public void delDictate(String dId);

	public DicateRobotList findDictateList(Map<String, Object> params,
			Order order, Integer pageNum, Integer pageSize) throws ServiceException;

	public void insertDictate(TDictateRobot dictateRobot);
}
