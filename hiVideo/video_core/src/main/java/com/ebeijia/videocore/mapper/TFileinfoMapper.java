package com.ebeijia.videocore.mapper;

import com.ebeijia.videocore.pojo.TFileinfo;

public interface TFileinfoMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(TFileinfo record);

    int insertSelective(TFileinfo record);

    TFileinfo selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(TFileinfo record);

    int updateByPrimaryKey(TFileinfo record);
}