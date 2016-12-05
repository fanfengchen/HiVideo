package com.ebeijia.video.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebeijia.video.cache.DomainLogCache;
import com.ebeijia.video.modle.api.DominateLogRes;
import com.ebeijia.video.service.DominateLogService;
import com.ebeijia.video.service.UserTokenService;
import com.ebeijia.videocore.cache.service.IMemcachedService;
import com.ebeijia.videocore.common.Order;
import com.ebeijia.videocore.constant.CommonTypeConstant;
import com.ebeijia.videocore.constant.MemcachedConstant;
import com.ebeijia.videocore.exception.ServiceException;
import com.ebeijia.videocore.mapper.TDominateLogMapper;
import com.ebeijia.videocore.mapper.TRobotMapper;
import com.ebeijia.videocore.pojo.DomateLog;
import com.ebeijia.videocore.pojo.TDominateLog;
import com.ebeijia.videocore.pojo.TRobot;
import com.ebeijia.videocore.pojo.TUser;
import com.ebeijia.videocore.util.DateUtil;

@Service("dominateLogService")
public class DominateLogServiceImpl implements DominateLogService {

	@Autowired
	private TDominateLogMapper logMapper;

	@Autowired
	private IMemcachedService memcachedService;
	
	@Autowired
    private DomainLogCache cache;
	
	@Autowired
	private UserTokenService userTokenService;
	
	@Autowired
	private TRobotMapper robotMapper;

	@Override
	public void findListByParams(Map<String, Object> params, Order order) {

		BlockingQueue<TDominateLog> userQueue = new LinkedBlockingQueue<TDominateLog>();// 需要推送队列

		params.put("dtype", CommonTypeConstant.DomainType.Automaticw.toString());// 自动推送
		params.put("beginTime", DateUtil.getTimes());
		params.put("res2", "1");
		List<TDominateLog> tList = logMapper.selectListByParams(params, null);
		if (tList != null && tList.size() > 0) {

			for (TDominateLog dLog : tList) {

				userQueue.offer(dLog);
				try {
					memcachedService.set(MemcachedConstant.DomainConfig, 0,
							userQueue);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

	}

	@Override
	public void update(TDominateLog log) {

		logMapper.updateByPrimaryKeySelective(log);

	}

	/**
	 * 查找自动推送列表
	 * ff
	 */
	@Override
	public List<DominateLogRes> getdominateLog(String devId) throws Exception {		
		
		Map<String, Object> params = new HashMap<String, Object>();
		TUser user = userTokenService.getUserToken(devId);
		params.put("userId", user.getUserId());
		List<TRobot> tList = robotMapper.selectListParams(params, null);
		String deviceId = "";
		List<DominateLogRes> list = new ArrayList<DominateLogRes>();
		if(tList!=null&&tList.size()>0){			
			
			for(TRobot bot:tList){
				
				deviceId +="'"+bot.getDeviceId()+"'"+",";
			}			
			params.clear();
			params.put("deviceIds", deviceId.substring(0, deviceId.length()-1));
			//params.put("dtype", CommonTypeConstant.DomainType.Automaticw.toString());// 自动推送
			List<TDominateLog> reList = logMapper.selectAllListParams(params, null);
			
			if (reList != null && reList.size() > 0){
				for (TDominateLog doml : reList) {
					
					DominateLogRes domRes = new DominateLogRes();
					domRes.setId(doml.getId().toString());
					domRes.setDeviceName(doml.getDeviceName());
					domRes.setdContext(doml.getDcontext());
					domRes.setDtime(DateUtil.DateToStr(doml.getDtime(),
							"yyyy-MM-dd HH:mm:ss"));
					domRes.setState(doml.getDstate());
					list.add(domRes);
				}
			}
		}
		
		return list;
	}

	/**
	 * 删除监控日志
	 */
	@Override
	public void delDominateLog(List<DomateLog> rList)throws Exception{
		
		try{
			/*BlockingQueue<TDominateLog> userQueue = new LinkedBlockingQueue<TDominateLog>();// 需要推送队列

			Map<String,Object> params = new HashMap<String,Object>();
			params.put("dtype", CommonTypeConstant.DomainType.Automaticw.toString());// 自动推送
			params.put("beginTime", DateUtil.getTimes());
			params.put("res2", "1");
			List<TDominateLog> tList = logMapper.selectListByParams(params, null);*/
		
			if(rList!=null){				
				
				for(DomateLog res:rList){
					
					//logMapper.deleteByPrimaryKey(deviceid)
					logMapper.deleteByPrimary(Integer.parseInt(res.getId()));	
				}			
				
				cache.init();
			}	
			
		}catch(Exception ex){
			
			throw new ServiceException("批量删除自动推送日志异常",ex.getMessage());
		}			
	}
	
}
