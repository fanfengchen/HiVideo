package com.ebeijia.videocore.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ebeijia.videocore.pojo.TVideoResource;

public interface TVideoResourceMapper {
	int deleteByPrimaryKey(Integer vid);

	int insert(TVideoResource record);

	int insertSelective(TVideoResource record);

	TVideoResource selectByPrimaryKey(Integer vid);

	int updateByPrimaryKeySelective(TVideoResource record);

	int updateByPrimaryKey(TVideoResource record);

	List<TVideoResource> selectListVideoResource(@Param(value="params") Map params,
			@Param(value="orderParam") String orderParam);
}