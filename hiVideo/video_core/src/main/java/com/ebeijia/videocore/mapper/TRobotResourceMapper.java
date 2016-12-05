package com.ebeijia.videocore.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ebeijia.videocore.pojo.TRobotResource;

public interface TRobotResourceMapper {
    int deleteByPrimaryKey(Integer rrId);
    
    int deleteByPrimary(Integer rId);
    int deleteByPrimaryParamsKey(String rrId);

    int insert(TRobotResource record);

    int insertSelective(TRobotResource record);

    TRobotResource selectByPrimaryKey(Integer rrId);
    
    TRobotResource selectByParams(@Param(value="params") Map<String,Object> params);

    int updateByPrimaryKeySelective(TRobotResource record);

    int updateByPrimaryKey(TRobotResource record);
    List<TRobotResource> selectListByParams(@Param(value="params") Map<String,Object> params,
			  @Param(value="orderParam") String orderParam );
    
	List<TRobotResource> selectListRobotResource(@Param(value="params") Map<String,Object> params,
			  @Param(value="orderParam") String orderParam );
	//获取用户机器人视频资源
    List<TRobotResource> selectUserReportResource(@Param(value="params") Map<String,Object> params,
			  @Param(value="orderParam") String orderParam );
    
    
}