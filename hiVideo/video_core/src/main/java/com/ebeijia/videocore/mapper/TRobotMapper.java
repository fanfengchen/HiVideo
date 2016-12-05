package com.ebeijia.videocore.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ebeijia.videocore.pojo.TRobot;

public interface TRobotMapper {
	int deleteByPrimaryKey(Integer rId);

	int deleteBySelective(String id);

	int insert(TRobot record);

	int insertSelective(TRobot record);

	int selectCountByParams(@Param(value = "params") Map<String, Object> params);

	TRobot selectByPrimaryKey(Integer rId);

	TRobot selectByParams(@Param(value = "params") Map<String, Object> params);

	int updateByPrimaryKeySelective(TRobot record);

	int updateByPrimaryKey(TRobot record);

	List<TRobot> selectListByParams(
			@Param(value = "params") Map<String, Object> params,
			@Param(value = "orderParam") String orderParam);

	List<TRobot> selectListParams(
			@Param(value = "params") Map<String, Object> params,
			@Param(value = "orderParam") String orderParam);
}