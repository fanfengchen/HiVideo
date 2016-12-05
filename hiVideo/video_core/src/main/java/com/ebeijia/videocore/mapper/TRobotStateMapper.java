package com.ebeijia.videocore.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ebeijia.videocore.pojo.TRobotState;

public interface TRobotStateMapper {
    int deleteByPrimaryKey(Integer rSid);

    int deleteByPrimary(Integer rId);
    
    int insert(TRobotState record);

    int insertSelective(TRobotState record);

    TRobotState selectByPrimaryKey(Integer rSid);

    int updateByPrimaryKeySelective(TRobotState record);

    int updateByPrimaryKey(TRobotState record);
    
    TRobotState selectByParams(@Param(value="params") Map<String,Object> params);
}