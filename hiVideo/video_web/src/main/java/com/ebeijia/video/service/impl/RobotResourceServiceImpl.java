package com.ebeijia.video.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebeijia.video.modle.api.RobotRes;
import com.ebeijia.video.modle.api.RobotResourceRes;
import com.ebeijia.video.service.RobotResourceService;
import com.ebeijia.videocore.constant.ApiResultCode;
import com.ebeijia.videocore.exception.ServiceException;
import com.ebeijia.videocore.mapper.TRobotMapper;
import com.ebeijia.videocore.mapper.TRobotResourceMapper;
import com.ebeijia.videocore.pojo.TRobot;
import com.ebeijia.videocore.pojo.TRobotResource;
import com.ebeijia.videocore.web.ResponseMessage;

/**
 * 机器人绑定资源信息
 * @author lijm
 * @date 2016-11-10
 *
 */
@Service
public class RobotResourceServiceImpl implements RobotResourceService{

	@Autowired
	private TRobotResourceMapper resMapper;
	
	@Autowired
	private TRobotMapper robotMapper;
	
	   private Logger logger=LoggerFactory.getLogger(this.getClass()); 
	/**
	 * 手机App添加机器人资源
	 */
	@Override
	public ResponseMessage addRobotResource(TRobotResource resource,String deviceId) throws Exception {
		
		try{
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("deviceId", deviceId);
			TRobot robot = robotMapper.selectByParams(params);
			if(robot!=null){
				
				params.clear();
				params.put("rId", robot.getrId());
				params.put("vid", resource.getVid());
				
				TRobotResource tRes = resMapper.selectByParams(params);
				if(tRes==null){
					
					TRobotResource res = new TRobotResource();					
					res.setAbout(resource.getAbout());
					res.setrId(robot.getrId());
					res.setVid(resource.getVid());
					
					resMapper.insertSelective(res);	
				}else{
					
					//之前已经绑定的视频资源不能重复绑定
					return ResponseMessage.error(ApiResultCode.Err_0011.getCode(), ApiResultCode.Err_0011.getMsg());
				}
				
			}else{
			
				//设备编码不存在
				return ResponseMessage.error(ApiResultCode.Err_0006.getCode(), ApiResultCode.Err_0006.getMsg());
			}
		}catch(Exception ex){
			throw new ServiceException(ApiResultCode.Err_0001.getCode(), ex.getMessage());
		}
		return ResponseMessage.success();
	}

	/**
	 * 删除手机App绑定资源信息
	 */
	@Override
	public ResponseMessage delRobotResorce(String id) throws Exception {
		
		try{
		
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("deviceId", id);
			TRobot robot = robotMapper.selectByParams(params);
			if(robot!=null){
				
				resMapper.deleteByPrimaryKey(robot.getrId());
			}else{
				return ResponseMessage.error(ApiResultCode.Err_0006.getCode(), ApiResultCode.Err_0006.getMsg());
			}
		}catch(Exception ex){
			throw new ServiceException(ApiResultCode.Err_0001.getCode(), ex.getMessage());
		}
		return ResponseMessage.success();
	}

	/**
	 * 加载机器人资源视频列表
	 */
	@Override
	public List<RobotResourceRes> getResource(String vtype, String vname,String deviceId,String token)
			throws Exception {
		
	
		List<RobotResourceRes> rList = new ArrayList<RobotResourceRes>();
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("vType", vtype);
		params.put("vName", vname);
		params.put("deviceId", deviceId);
		
		List<TRobotResource> rrList = resMapper.selectListRobotResource(params, null);
		if(rrList!=null&&rrList.size()>0){
			
			for(TRobotResource ret :rrList){				
				
				RobotResourceRes res = new RobotResourceRes();		
				res.setVid(ret.getVid().toString());
				res.setDeviceId(ret.getDeviceId());
				res.setDeviceName(ret.getDeviceName());
			
				res.setVtype(ret.getVtype());
				res.setVname(ret.getVname());
				res.setVage(ret.getVage());
				res.setUu(ret.getUu());
				res.setVu(ret.getVu());
				res.setImgpath(ret.getImgpath());
				res.setVurl(ret.getVurl());
				res.setAbout(ret.getAbout());
				rList.add(res);				
			}
		}
		return rList;
	}

	@Override
	public ResponseMessage delRobotResorce(List<RobotRes> res) throws Exception {
		
		try{
			
			String vid = "";
			String deviid ="";
			if(res!=null){
				
				
				for(RobotRes re:res){
					
					vid +=re.getVid()+",";
					deviid +="'"+re.getDeviceId()+"'"+",";
				}
			}
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("deviceIds", deviid.substring(0, deviid.length()-1));			
			List<TRobot> robotL = robotMapper.selectListByParams(params, null);
			if(robotL!=null&&robotL.size()>0){
				
				String rId = "";
				for(TRobot rb :robotL){
					
					rId +=rb.getrId()+",";
				}
				params.clear();
				params.put("rIds", rId.substring(0, rId.length()-1));
				params.put("vids", vid.substring(0, vid.length()-1));
				List<TRobotResource> reList = resMapper.selectListByParams(params, null);
				if(reList!=null&&reList.size()>0){
					
					//String rrId ="";
					for(TRobotResource rr:reList){
					
						resMapper.deleteByPrimaryKey(rr.getRrId());
					}
					//resMapper.deleteByPrimaryParamsKey("("+rrId.substring(0, rrId.length()-1)+")");
				}
				
			}else{
				return ResponseMessage.error(ApiResultCode.Err_0006.getCode(), ApiResultCode.Err_0006.getMsg());
			}
		}catch(Exception ex){
			throw new ServiceException(ApiResultCode.Err_0001.getCode(), ex.getMessage());
		}
		return ResponseMessage.success();
		
	}

}
