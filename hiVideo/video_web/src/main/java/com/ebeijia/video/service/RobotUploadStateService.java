package com.ebeijia.video.service;

import com.ebeijia.video.modle.api.RobotState;
import com.ebeijia.videocore.web.ResponseMessage;

/**
 * 上传机器人状态
 * @author lijm
 *
 */
public interface RobotUploadStateService {

	ResponseMessage uploadState(String devId,String state,String sname);
	
	RobotState rebotState(String devId,String intervalTime);
	
}
