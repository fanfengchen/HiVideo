package com.ebeijia.robot.core.mapper;

import com.ebeijia.robot.core.pojo.TRobotinfo;

public interface TRobotinfoMapper {
    int deleteByPrimaryKey(Integer rrId);

    int insert(TRobotinfo record);

    int insertSelective(TRobotinfo record);

    TRobotinfo selectByPrimaryKey(Integer rrId);

    int updateByPrimaryKeySelective(TRobotinfo record);

    int updateByPrimaryKey(TRobotinfo record);
}