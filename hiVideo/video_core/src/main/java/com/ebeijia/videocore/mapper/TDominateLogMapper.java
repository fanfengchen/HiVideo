package com.ebeijia.videocore.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ebeijia.videocore.pojo.TDominateLog;

public interface TDominateLogMapper {
	
    int deleteByPrimary(Integer deviceid);

    int insert(TDominateLog record);

    int insertSelective(TDominateLog record);

    TDominateLog selectByPrimaryKey(String deviceid);

    int updateByPrimaryKeySelective(TDominateLog record);

    int updateByPrimaryKey(TDominateLog record);
    
    List<TDominateLog> selectListByParams(@Param(value="params") Map<String,Object> params,
			  @Param(value="orderParam") String orderParam );
    
    List<TDominateLog> selectAllListParams(@Param(value="params") Map<String,Object> params,
			  @Param(value="orderParam") String orderParam );
    
}