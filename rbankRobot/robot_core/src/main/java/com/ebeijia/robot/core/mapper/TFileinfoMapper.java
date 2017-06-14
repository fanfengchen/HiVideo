package com.ebeijia.robot.core.mapper;

import com.ebeijia.robot.core.pojo.TFileinfo;

public interface TFileinfoMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(TFileinfo record);

    int insertSelective(TFileinfo record);

    TFileinfo selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(TFileinfo record);

    int updateByPrimaryKey(TFileinfo record);

	TFileinfo selectByPrimary(TFileinfo fileInfo);
}