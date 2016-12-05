package com.ebeijia.videocore.mapper;

import com.ebeijia.videocore.pojo.TRobotinfo;

public interface TRobotinfoMapper {
    int deleteByPrimaryKey(Integer rrId);

    int insert(TRobotinfo record);

    int insertSelective(TRobotinfo record);

    TRobotinfo selectByPrimaryKey(Integer rrId);

    int updateByPrimaryKeySelective(TRobotinfo record);

    int updateByPrimaryKey(TRobotinfo record);
    
    
}