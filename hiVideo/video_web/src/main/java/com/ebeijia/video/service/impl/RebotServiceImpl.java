package com.ebeijia.video.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebeijia.video.modle.api.RebotRes;
import com.ebeijia.video.service.ReboteService;
import com.ebeijia.video.service.UserTokenService;
import com.ebeijia.videocore.constant.CommonTypeConstant;
import com.ebeijia.videocore.exception.ServiceException;
import com.ebeijia.videocore.mapper.TRobotMapper;
import com.ebeijia.videocore.mapper.TRobotResourceMapper;
import com.ebeijia.videocore.mapper.TRobotStateMapper;
import com.ebeijia.videocore.pojo.TRobot;
import com.ebeijia.videocore.pojo.TRobotState;
import com.ebeijia.videocore.pojo.TUser;
import com.ebeijia.videocore.util.DateUtil;


@Service
public class RebotServiceImpl implements ReboteService{

	@Autowired
	private TRobotMapper rebotMapper;
	
	@Autowired
	private TRobotResourceMapper robotReMapper;
	
	@Autowired 
	private UserTokenService userTokenService;
	
    @Autowired
    private TRobotStateMapper stateMapper;
	/**
	 * 获取机器人接口列表
	 * lijm
	 */
	@Override
	public List<RebotRes> getRebotList(String rid, String rName,String token,String intervalTime)throws ServiceException {
		
		List<RebotRes> resList = new ArrayList<RebotRes>();
		try{
			
			TUser user = userTokenService.getUserToken(token);			
			Map<String,Object> params = new HashMap<String,Object>();			
			params.put("deviceName", rName);
			params.put("userId", user.getUserId());
			params.put("deviceId", rid);
			List<TRobot> reList = rebotMapper.selectListByParams(params, null);
			if(reList!=null&&reList.size()>0){
				
				for(TRobot rt:reList){
					
					RebotRes res = new RebotRes();
					res.setRid(rt.getrId().toString());
					res.setDeviceId(rt.getDeviceId());
					res.setDeviceName(rt.getDeviceName());
					res.setdType(rt.getDtype());
					res.setSname(rt.getSname());
					//对返回客户端的状态进行处理
					if(DateUtil.sec(rt.getLastTime().getTime())<Integer.parseInt(intervalTime)){					
						
						res.setState(rt.getState());
					}else{
						res.setState(CommonTypeConstant.RobotState.Leave.toString());
					}					
					
					resList.add(res);
				}				
			}
			
		}catch(Exception ex){
			
			throw new ServiceException("获取机器人列表异常E1",ex.getMessage());		
		}
		return resList;
	}


	/**
	 * 手机端删除已经绑定的机器人数据
	 * lijm
	 */
	@Override
	@Transactional
	public void delRebot(String id) throws ServiceException {
		
		//删除机器人的同时，删除该机器人之前已经绑定的资源信息
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("deviceId", id);//传入的设备编号
		TRobot bot = rebotMapper.selectByParams(params);
		if(bot!=null){
			
			rebotMapper.deleteBySelective(id);	
			robotReMapper.deleteByPrimary(bot.getrId());
			stateMapper.deleteByPrimary(bot.getrId());
		}
		
	}


	/**
	 * 手机app端添加扫描的机器人数据
	 * lijm
	 */
	@Override
	public void addRebot(TRobot rebot,String token) throws ServiceException {
		
		TUser user = userTokenService.getUserToken(token);//获取用户userId
		
		TRobot rb = new TRobot();
		rb.setDtype(rebot.getDtype());
		rb.setDeviceId(rebot.getDeviceId());
		rb.setDeviceName(rebot.getDeviceName());
		rb.setUserId(user.getUserId());		
		
		rebotMapper.insertSelective(rb);
		//添加机器人状态
		TRobotState state = new TRobotState();
		state.setrId(rb.getrId());
		state.setState(CommonTypeConstant.RobotState.Online.toString());
		state.setLastTime(DateUtil.getTimes());
		stateMapper.insertSelective(state);
	}

}
