package com.ebeijia.video.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebeijia.video.modle.api.RobotState;
import com.ebeijia.video.service.RobotUploadStateService;
import com.ebeijia.videocore.constant.ApiResultCode;
import com.ebeijia.videocore.constant.CommonTypeConstant;
import com.ebeijia.videocore.mapper.TRobotMapper;
import com.ebeijia.videocore.mapper.TRobotStateMapper;
import com.ebeijia.videocore.mapper.TRobotinfoMapper;
import com.ebeijia.videocore.pojo.TRobot;
import com.ebeijia.videocore.pojo.TRobotState;
import com.ebeijia.videocore.pojo.TRobotinfo;
import com.ebeijia.videocore.util.DateUtil;
import com.ebeijia.videocore.util.LoggerUtil;
import com.ebeijia.videocore.web.ResponseMessage;

/**
 * 上传机器人状态
 * @author lijm
 * @date 2016-11-16
 *
 */
@Service
public class RobotUploadStateServiceImpl implements RobotUploadStateService{

	@Autowired
	private TRobotStateMapper roStateMapper;
	
	@Autowired
	private TRobotinfoMapper roInfoMapper;
	
	@Autowired
	private TRobotMapper robotMapper;
	
	/**
	 * 上传机器人状态
	 */
	@Override
	public ResponseMessage uploadState(String devId, String state, String sname) {
		
		//1>查询机器人信息是否存在
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("deviceId", devId);//机器人编号
		TRobot bot = robotMapper.selectByParams(params);
		if(bot!=null){
			
			//2>根据插入的信息插入机器人状态表（查询存在更新、不存在新增）
			params.clear();
			params.put("rId", bot.getrId());
			TRobotState tState = roStateMapper.selectByParams(params);
			if(tState!=null){
				
				tState.setLastTime(DateUtil.getTimes());
				tState.setSname(sname);
				tState.setState(state);
				roStateMapper.updateByPrimaryKeySelective(tState);
			}else{
				
				TRobotState rs = new TRobotState();
				rs.setrId(bot.getrId());
				rs.setSname(sname);
				rs.setState(state);
				rs.setLastTime(DateUtil.getTimes());
				roStateMapper.insertSelective(rs);				
			}
			
			//3>插入具体的机器人状态到记录表
			TRobotinfo info = new TRobotinfo();
			info.setrId(bot.getrId());
			info.setSname(sname);
			info.setState(state);
			info.setLastTime(DateUtil.getTimes());
			roInfoMapper.insertSelective(info);
		}else{
			//不存在设备信息
			return ResponseMessage.error(ApiResultCode.Err_0012.getCode(), ApiResultCode.Err_0012.getMsg());
		}
		
		return ResponseMessage.success();
	}

	/**
	 * 安卓手机端获取机器人状态
	 */
	@Override
	public RobotState rebotState(String devId, String intervalTime) {
		
		RobotState state = new RobotState();
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("deviceId", devId);//机器人编号
		TRobot bot = robotMapper.selectByParams(params);
		if(bot!=null){			
			
			params.clear();
			params.put("rId", bot.getrId());
			TRobotState tState = roStateMapper.selectByParams(params);
			if(tState!=null){
				
				//校验机器人最后一次状态
				state.setDeviceId(devId);
				state.setSname(tState.getSname());
				System.out.println("验证时间差:"+DateUtil.sec(tState.getLastTime().getTime()));
				if(DateUtil.sec(tState.getLastTime().getTime())<Integer.parseInt(intervalTime)){					
					
					state.setState(tState.getState());
				}else{
					state.setState(CommonTypeConstant.RobotState.Leave.toString());
				}
				
			}
		}		
		return state;
		
	}
}
