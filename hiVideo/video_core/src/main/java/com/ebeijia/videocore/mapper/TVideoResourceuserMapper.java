package com.ebeijia.videocore.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ebeijia.videocore.pojo.TVideoResourceuser;

public interface TVideoResourceuserMapper {
	int deleteByPrimaryKey(Integer vuId);

	int insert(TVideoResourceuser record);

	int insertSelective(TVideoResourceuser record);

	TVideoResourceuser selectByPrimaryKey(Integer vuId);
	
	TVideoResourceuser selectByParams(@Param(value = "params") Map params);
	
	int updateByPrimaryKeySelective(TVideoResourceuser record);

	int updateByPrimaryKey(TVideoResourceuser record);

	List<TVideoResourceuser> selectUserVideoResource(
			@Param(value = "params") Map params,
			@Param(value = "orderParam") String orderParam);
	
	List<TVideoResourceuser> selectListByParams(
			@Param(value = "params") Map params,
			@Param(value = "orderParam") String orderParam);

	List<TVideoResourceuser> selectRobotNResource(	@Param(value = "params") Map params,
			@Param(value = "orderParam") String orderParam);
	
	int selectIsEnshrine(@Param(value = "params") Map params,
			@Param(value = "orderParam") String orderParam);

}