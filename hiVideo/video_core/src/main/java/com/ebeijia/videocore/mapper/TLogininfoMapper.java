package com.ebeijia.videocore.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ebeijia.videocore.pojo.TLogininfo;

public interface TLogininfoMapper {

	int updateByPrimaryKey(TLogininfo record);
	
	int updateByPrimaryKeySelective(TLogininfo record);

	TLogininfo selectByKey(String userId);
	
	TLogininfo selectByParams(@Param(value="params") Map<String,Object> params);

	String selectByPrimaryKey(String userId);

	int insert(TLogininfo record);

	int insertSelective(TLogininfo record);

}