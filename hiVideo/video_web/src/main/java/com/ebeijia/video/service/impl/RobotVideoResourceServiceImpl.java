package com.ebeijia.video.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ebeijia.video.modle.api.RobotVideoResourceRes;
import com.ebeijia.video.service.RobotVideoResourceService;
import com.ebeijia.videocore.mapper.TRobotResourceMapper;
import com.ebeijia.videocore.pojo.TRobotResource;

@Service
public class RobotVideoResourceServiceImpl implements RobotVideoResourceService {
	@Autowired
	private TRobotResourceMapper robotRes;

	/**
	 * 获取视频资源列表
	 */
	@Override
	public List<RobotVideoResourceRes> getUerRobotVideo(String devId,
			String vId, String vtype, String vname) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("deviceId", devId);
		if(vId!=null){
			params.put("vid", Integer.parseInt(vId));
		}
		
		//判断type 是不是为空
		if (vtype != "") {
			params.put("vtype", vtype);
		}
		//判断name是不是为空
		if (vname != "") {
			params.put("vname", vname);
		}
		List<TRobotResource> relist = robotRes.selectUserReportResource(params,
				null);
		List<RobotVideoResourceRes> list = new ArrayList<RobotVideoResourceRes>();
		for (TRobotResource res : relist) {
			RobotVideoResourceRes viRes = new RobotVideoResourceRes();

			viRes.setVid(res.getVid().toString());
			viRes.setRrid(res.getRrId().toString());
			viRes.setVname(res.getVname());
			viRes.setVu(res.getVu());
			viRes.setUu(res.getUu());
			viRes.setVtype(res.getVtype());
			viRes.setVage(res.getVage());
			viRes.setImgpath(res.getImgpath());
			viRes.setVurl(res.getVurl());
			viRes.setAbout(res.getAbout());
			list.add(viRes);
		}
		return list;

	}

}
