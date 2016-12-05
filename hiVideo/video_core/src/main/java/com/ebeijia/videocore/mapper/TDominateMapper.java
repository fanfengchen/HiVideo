package com.ebeijia.videocore.mapper;

import com.ebeijia.videocore.pojo.TDominate;

public interface TDominateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TDominate record);

    int insertSelective(TDominate record);

    TDominate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TDominate record);

    int updateByPrimaryKeyWithBLOBs(TDominate record);

    int updateByPrimaryKey(TDominate record);
}