package com.ebeijia.mapper;

import com.ebeijia.entity.InterfaceDefine;

public interface InterfaceDefineMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InterfaceDefine record);

    int insertSelective(InterfaceDefine record);

    InterfaceDefine selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InterfaceDefine record);

    int updateByPrimaryKey(InterfaceDefine record);
}