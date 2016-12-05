package com.ebeijia.video.service;

import java.util.List;

import com.ebeijia.video.modle.api.RebotRes;
import com.ebeijia.videocore.exception.ServiceException;
import com.ebeijia.videocore.pojo.TRobot;

/***
 * 获取机器人信息
 * @author lijm
 * @date 2016-11-09
 *
 */
public interface ReboteService {

	List<RebotRes> getRebotList(String rid,String rName,String token,String intervalTime)throws ServiceException;
	
	void delRebot(String id)throws ServiceException;
	
	void addRebot(TRobot rebot,String token)throws ServiceException;
}
