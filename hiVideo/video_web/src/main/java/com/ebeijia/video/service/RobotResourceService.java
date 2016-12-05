package com.ebeijia.video.service;

import java.util.List;

import com.ebeijia.video.modle.api.RobotRes;
import com.ebeijia.video.modle.api.RobotResourceRes;
import com.ebeijia.videocore.pojo.TRobotResource;
import com.ebeijia.videocore.web.ResponseMessage;

public interface RobotResourceService {

	ResponseMessage addRobotResource(TRobotResource resource,String deviceId)throws Exception;
	
	ResponseMessage delRobotResorce(String id)throws Exception;
	ResponseMessage delRobotResorce(List<RobotRes> res)throws Exception;
	
	List<RobotResourceRes> getResource(String vtype,String vname,String deviceId,String token)throws Exception;
}
