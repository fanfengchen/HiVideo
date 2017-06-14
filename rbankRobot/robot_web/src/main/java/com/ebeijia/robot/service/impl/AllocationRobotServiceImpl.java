package com.ebeijia.robot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ebeijia.robot.core.common.Order;
import com.ebeijia.robot.core.constant.ApiResultCode;
import com.ebeijia.robot.core.common.RollPage;
import com.ebeijia.robot.core.exception.ServiceException;
import com.ebeijia.robot.core.mapper.TAllocationRobotMapper;
import com.ebeijia.robot.core.mapper.TRobotinfoMapper;
import com.ebeijia.robot.core.pojo.TAllocationRobot;
import com.ebeijia.robot.core.pojo.TRobotinfo;
import com.ebeijia.robot.core.util.DateUtil;
import com.ebeijia.robot.core.web.ResponseMessage;
import com.ebeijia.robot.modle.api.AllocationApp;
import com.ebeijia.robot.modle.api.AllocationAppRes;
import com.ebeijia.robot.modle.api.AllocationRobotList;
import com.ebeijia.robot.modle.api.AllocationRobotRes;
import com.ebeijia.robot.modle.api.DeviceRes;
import com.ebeijia.robot.modle.api.RobotAppRes;
import com.ebeijia.robot.service.IAllocationRobotService;

/**
 * 配置用户信息(基本的增删改查)，供app端使用
 * 
 * @author lijm
 * @date 2016-12-12
 *
 */
@Service
public class AllocationRobotServiceImpl implements IAllocationRobotService {
	protected @Value("${global.pageSize}") Integer pageSizeDefault;
	protected @Value("${global.intervalTime}") String intervalTime;

	@Autowired
	private TAllocationRobotMapper robotMapper;

	@Autowired
	private TRobotinfoMapper infoMapper;

	/**
	 * app端接口，获取机器人列表信息
	 */
	@Override
	public List<AllocationAppRes> getAllUser(String branch) {

		List<AllocationAppRes> aList = new ArrayList<AllocationAppRes>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("branch", branch);
		//params.put("states", "01");
		List<TAllocationRobot> uList = robotMapper.selectListParams(params,
				Order.desc("branch").toString());
		if (uList != null && uList.size() > 0) {

			for (TAllocationRobot use : uList) {

				AllocationAppRes res = new AllocationAppRes();
				res.setDeviceId(use.getDeviceId());
				res.setDeviceName(use.getDeviceName());
				res.setdType(use.getDtype());
				res.setBranch(use.getBranch());
				res.setRpwd(use.getRpwd());			
				res.setState(use.getState());
				aList.add(res);

			}
		}
		return aList;
	}

	/**
	 * 获取机器人信息
	 */
	@Override
	public AllocationApp getRobot(String deviceId) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deviceId", deviceId);

		AllocationApp app = new AllocationApp();
		TAllocationRobot robot = robotMapper.selectByParams(params);
		if (robot != null) {

			app.setIp(robot.getIp());
			app.setPort(robot.getPort());
			app.setDeviceId(robot.getDeviceId());
			app.setDeviceName(robot.getDeviceName());
			app.setdType(robot.getDtype());
			app.setBranch(robot.getBranch());
			app.setRpwd(robot.getRpwd());
		}
		return app;
	}

	/**
	 * 后台接口 添加配置机器人
	 */
	@Transactional
	@Override
	public ResponseMessage insertAllocationRobot(TAllocationRobot tallRobot) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (tallRobot != null) {
			params.put("deviceId", tallRobot.getDeviceId());
			if (robotMapper.selectByParams(params) != null) {
				return ResponseMessage.error(ApiResultCode.Err_0011.getCode(),
						ApiResultCode.Err_0011.getMsg());
			} else {
				robotMapper.insertSelective(tallRobot);
			}
		}

		return ResponseMessage.success();

	}

	/**
	 * 后台接口 更新配置机器人
	 */
	@Transactional
	@Override
	public void updateAllocationRobot(TAllocationRobot tallRobot) {
		robotMapper.updateByPrimaryKeySelective(tallRobot);

	}

	/**
	 * 后台接口 删除配置机器人
	 */
	@Transactional
	@Override
	public void delAllocationRobot(String rId) {

		robotMapper.deleteByPrimaryKey(Integer.parseInt(rId));
	}

	/**
	 * 获取配置机器人列表
	 */
	@Override
	public AllocationRobotList findAllocation(Map<String, Object> params,
			Order order, Integer pageNum, Integer pageSize)
			throws ServiceException {
		AllocationRobotList rList = new AllocationRobotList();// 返回集合
		try {

			String orderParam = (order == null) ? null : order.toString();
			Integer recordSum = robotMapper.selectCountByParams(params);// 获取总行数

			RollPage<TAllocationRobot> rollPage = new RollPage<TAllocationRobot>();
			rollPage.setRecordSum(recordSum);

			if (pageSize == null)
				rollPage.setPageSize(pageSizeDefault);
			else
				rollPage.setPageSize(pageSize);

			pageNum = (pageNum == null) ? 1 : pageNum;
			rollPage.setPageNum(pageNum);
			Integer pageOffset = (rollPage.getPageNum() - 1)
					* rollPage.getPageSize();
			if (recordSum > 0) {

				rollPage.setRecordList(robotMapper.selectListByParams(params,
						pageOffset, pageSize, orderParam));
				// 返回客户端信息进行业务处理
				rList.setCount(rollPage.getRecordSum().toString());
				List<AllocationRobotRes> resList = new ArrayList<AllocationRobotRes>();
				for (TAllocationRobot robot : rollPage.getRecordList()) {
					AllocationRobotRes res = new AllocationRobotRes();
					res.setrId(robot.getrId().toString());
					res.setIp(robot.getIp());
					res.setPort(robot.getPort());
					res.setDeviceId(robot.getDeviceId());
					res.setDeviceName(robot.getDeviceName());
					res.setDtype(robot.getDtype());
					res.setBranch(robot.getBranch());
					res.setState(robot.getState());
					resList.add(res);
				}
				rList.setList(resList);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ServiceException("获取配置机器人列表异常", ex.getMessage());
		}
		return rList;
	}

	/**
	 * 机器人端获取设备信息
	 */
	@Override
	public RobotAppRes getRobotApp(String deviceId) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deviceId", deviceId);
		RobotAppRes res = new RobotAppRes();
		TAllocationRobot robot = robotMapper.selectByParams(params);
		if (robot != null) {

			res.setDeviceId(robot.getDeviceId());
			res.setIp(robot.getIp());
			res.setPort(robot.getPort());
		}
		return res;
	}

	/**
	 * 上传机器人状态
	 */
	@Override
	public ResponseMessage uploadRobotState(String deviceId, String state) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deviceId", deviceId);

		TAllocationRobot robot = robotMapper.selectByParams(params);
		if (robot != null) {

			robot.setState(state);
			robotMapper.updateByPrimaryKeySelective(robot);
		}

		TRobotinfo info = new TRobotinfo();
		info.setDeviceid(deviceId);
		info.setState(state);
		info.setLastTime(DateUtil.getTimes());
		infoMapper.insertSelective(info);

		return ResponseMessage.success();
	}

	/**
	 * 后台接口 获取设备编号
	 */
	@Override
	public List<DeviceRes> getDeviceId() {

		Map<String, Object> params = new HashMap<String, Object>();
		// 得到列表
		List<TAllocationRobot> robotList = robotMapper.selectListParams(params,
				null);
		// 返回数据格式 的列表
		List<DeviceRes> dlist = new ArrayList<DeviceRes>();
		if (robotList != null && robotList.size() > 0) {
			for (TAllocationRobot arobot : robotList) {
				DeviceRes dRes = new DeviceRes();
				dRes.setDeviceId(arobot.getDeviceId());
				dRes.setDeviceName(arobot.getDeviceName());
				dlist.add(dRes);
			}
		}
		return dlist;
	}
	
	/**
	 * 获取机器人状态
	 * lijm 2016-12-29
	 */
	@Override
	public String getRobotState(String deviceId) {
		
		String state = "";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("deviceId", deviceId);
		TAllocationRobot robot = robotMapper.selectByParams(params);
		if(robot!=null){
			
			state = robot.getState();//机器人状态
		}
		return state;
	}
}
