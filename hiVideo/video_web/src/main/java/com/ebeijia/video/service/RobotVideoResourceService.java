package com.ebeijia.video.service;

import java.util.List;

import com.ebeijia.video.modle.api.RobotVideoResourceRes;

public interface RobotVideoResourceService {


	List<RobotVideoResourceRes> getUerRobotVideo(String devId,String vid, String vtype,String vname);
	

}
