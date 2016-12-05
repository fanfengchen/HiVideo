package com.ebeijia.mapper;

import com.ebeijia.entity.InterfaceColnumInfo;

public interface InterfaceColnumInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InterfaceColnumInfo record);

    int insertSelective(InterfaceColnumInfo record);

    InterfaceColnumInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InterfaceColnumInfo record);

    int updateByPrimaryKey(InterfaceColnumInfo record);
}