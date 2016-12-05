package com.ebeijia.videocore.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ebeijia.videocore.pojo.TDictdata;

public interface TDictdataMapper {
    int insert(TDictdata record);

    int insertSelective(TDictdata record);
    
    List<TDictdata> selectListByParams(@Param(value="params") Map<String,Object> params,
			  @Param(value="orderParam") String orderParam );
}