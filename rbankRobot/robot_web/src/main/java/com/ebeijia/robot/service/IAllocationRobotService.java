package com.ebeijia.robot.service;

import java.util.List;
import java.util.Map;

import com.ebeijia.robot.core.common.Order;
import com.ebeijia.robot.core.exception.ServiceException;
import com.ebeijia.robot.core.pojo.TAllocationRobot;
import com.ebeijia.robot.core.web.ResponseMessage;
import com.ebeijia.robot.modle.api.AllocationApp;
import com.ebeijia.robot.modle.api.AllocationAppRes;
import com.ebeijia.robot.modle.api.AllocationRobotList;
import com.ebeijia.robot.modle.api.DeviceRes;
import com.ebeijia.robot.modle.api.RobotAppRes;



/**
 * 配置机器人信息(基本的增删改查)，供app端使用
 * @author lijm
 * @date 2016-12-15
 *
 */
public interface IAllocationRobotService{

	
	public List<AllocationAppRes> getAllUser(String branch);
	
	public AllocationApp getRobot(String deviceId);
	
	public RobotAppRes getRobotApp(String deviceId);
	
	public ResponseMessage uploadRobotState(String deviceId,String state);

	public ResponseMessage insertAllocationRobot(TAllocationRobot tallRobot);

	public void updateAllocationRobot(TAllocationRobot tallRobot);

	public void delAllocationRobot(String rId);

	public AllocationRobotList findAllocation(Map<String, Object> params,
			Order order, Integer pageNum, Integer pageSize)throws ServiceException;

	public List<DeviceRes> getDeviceId();
	
	public String getRobotState(String deviceId);

}
