package com.ebeijia.video.service;

import java.util.List;

import com.ebeijia.video.modle.api.EnshrineRes;
import com.ebeijia.video.modle.api.RobotNResourceRes;
import com.ebeijia.video.modle.api.RobotVideoRes;
import com.ebeijia.video.modle.api.VideoResourceRes;
import com.ebeijia.videocore.web.ResponseMessage;

public interface VideoResourceService {
	public List<RobotVideoRes> getVideoResource(String vtype, String vname)
			throws Exception;

	public ResponseMessage addUserResource(String vid, String token) throws Exception;

	public List<VideoResourceRes> getUserVideoResource(String vtype,
			String vname, String token) throws Exception;
	
	public List<RobotNResourceRes> getRobotNResource(String token,String devId) throws Exception;

	 public EnshrineRes isEnshrine(String token,String vid) throws Exception;
	
}
