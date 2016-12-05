package com.ebeijia.video.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebeijia.video.modle.api.EnshrineRes;
import com.ebeijia.video.modle.api.RobotNResourceRes;
import com.ebeijia.video.modle.api.RobotVideoRes;
import com.ebeijia.video.modle.api.VideoResourceRes;
import com.ebeijia.video.service.UserTokenService;
import com.ebeijia.video.service.VideoResourceService;
import com.ebeijia.videocore.constant.ApiResultCode;
import com.ebeijia.videocore.mapper.TRobotMapper;
import com.ebeijia.videocore.mapper.TVideoResourceMapper;
import com.ebeijia.videocore.mapper.TVideoResourceuserMapper;
import com.ebeijia.videocore.pojo.TUser;
import com.ebeijia.videocore.pojo.TVideoResource;
import com.ebeijia.videocore.pojo.TVideoResourceuser;
import com.ebeijia.videocore.util.DateUtil;
import com.ebeijia.videocore.web.ResponseMessage;

@Service
public class VideoResourceServiceImpl implements VideoResourceService {
	@Autowired
	private TVideoResourceMapper resMapper;

	@Autowired
	private TVideoResourceuserMapper rerMapper;
	@Autowired
	private UserTokenService tokenService;

	@Autowired
	private TRobotMapper robotMapper;

	/**
	 * 获取视频资源列表
	 */
	@Override
	public List<RobotVideoRes> getVideoResource(String vtype, String vname)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		if (vtype != "") {
			params.put("vtype", vtype);
		}
		if (vname != "") {
			params.put("vname", vname);
		}

		List<TVideoResource> reList = resMapper.selectListVideoResource(params,
				null);
		List<RobotVideoRes> list = new ArrayList<RobotVideoRes>();
		if (reList != null && reList.size() > 0) {
			for (TVideoResource res : reList) {
				RobotVideoRes viRes = new RobotVideoRes();
				viRes.setVid(res.getVid().toString());
				viRes.setVname(res.getVname());
				viRes.setVtype(res.getVtype());
				viRes.setUu(res.getUu());
				viRes.setVu(res.getVu());
				viRes.setVage(res.getVage());
				viRes.setImgpath(res.getImgpath());
				viRes.setVurl(res.getvUrl());
				viRes.setAbout(res.getAbout());
				list.add(viRes);
			}
		}
		return list;
	}

	/**
	 * 添加收藏列表
	 */
	@Override
	public ResponseMessage addUserResource(String vid, String token)
			throws Exception {

		TUser userInfo = tokenService.getUserToken(token);
		// 添加收藏之前去验证呢用户资源是否已经添加了
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("vid", vid);
		params.put("userId", userInfo.getUserId());
		TVideoResourceuser rer = rerMapper.selectByParams(params);
		if (rer == null) {

			// 代表未绑定
			TVideoResourceuser record = new TVideoResourceuser();
			record.setVid(new Integer(vid));
			record.setUserId(userInfo.getUserId());
			record.setvTime(DateUtil.getTimes());
			rerMapper.insertSelective(record);
		} else {
			return ResponseMessage.error(ApiResultCode.Err_0011.getCode(),
					ApiResultCode.Err_0011.getMsg());
		}

		return ResponseMessage.success();

	}

	/**
	 * 获取用户收藏视频列表
	 */
	@Override
	public List<VideoResourceRes> getUserVideoResource(String vtype,
			String vname, String token) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		TUser userInfo = tokenService.getUserToken(token);
		// params.put("userId", "15000743278");
		params.put("userId", userInfo.getUserId());
		params.put("vtype", vtype);
		params.put("vname", vname);
		List<TVideoResourceuser> reList = rerMapper.selectUserVideoResource(
				params, null);
		List<VideoResourceRes> list = new ArrayList<VideoResourceRes>();
		if (reList != null && reList.size() > 0) {
			for (TVideoResourceuser res : reList) {

				VideoResourceRes viRes = new VideoResourceRes();
				viRes.setVid(res.getVid().toString());
				viRes.setVuid(res.getVuId().toString());
				viRes.setVname(res.getVname());
				viRes.setVage(res.getVage());
				viRes.setUu(res.getUu());
				viRes.setVu(res.getVu());
				viRes.setImgpath(res.getImgpath());
				viRes.setAbout(res.getAbout());
				list.add(viRes);
			}
		}
		return list;
	}

	/**
	 * 获取机器人可添加的视频列表 ff
	 */
	@Override
	public List<RobotNResourceRes> getRobotNResource(String token, String devId)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		TUser userInfo = tokenService.getUserToken(token);// 获取Token中的用户登录信息
		params.put("userId", userInfo.getUserId());
		params.put("devId", devId);
		List<RobotNResourceRes> list = new ArrayList<RobotNResourceRes>();
		if (robotMapper.selectCountByParams(params) > 0) {
			List<TVideoResourceuser> reList = rerMapper.selectRobotNResource(
					params, null);// 查询到的结果集

			if (reList != null && reList.size() > 0) {
				for (TVideoResourceuser res : reList) {

					RobotNResourceRes viRes = new RobotNResourceRes();
					viRes.setVuid(res.getVuId().toString());
					viRes.setVid(res.getVid().toString());
					viRes.setVtype(res.getVtype());
					viRes.setVname(res.getVname());
					viRes.setVage(res.getVage());
					viRes.setImgpath(res.getImgpath());
					viRes.setVurl(res.getvUrl());
					viRes.setAbout(res.getAbout());
					list.add(viRes);
				}
			}

		}

		return list;
	}

	/***
	 * 判断视频是否被收藏
	 */
	@Override
	public EnshrineRes isEnshrine(String token, String vid) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		TUser userInfo = tokenService.getUserToken(token); // 获取Token中的用户登录信息
		params.put("userId", userInfo.getUserId());
		params.put("vid", vid);
		EnshrineRes shrRes = new EnshrineRes();
		if (rerMapper.selectIsEnshrine(params, null) == 0) {
			shrRes.setEnshrine("0");// 未被收藏
		} else {
			shrRes.setEnshrine("1");// 被收藏
		}
		return shrRes;
	}
}
