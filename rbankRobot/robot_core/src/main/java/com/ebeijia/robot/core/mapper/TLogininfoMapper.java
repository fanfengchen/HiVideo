package com.ebeijia.robot.core.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ebeijia.robot.core.pojo.TLogininfo;

public interface TLogininfoMapper {
    int insert(TLogininfo record);

    int insertSelective(TLogininfo record);
    
    TLogininfo selectByKey(String userId);
    int updateByPrimaryKey(TLogininfo record);
	
	int updateByPrimaryKeySelective(TLogininfo record);
	
	TLogininfo selectByParams(@Param(value="params") Map<String,Object> params);
}