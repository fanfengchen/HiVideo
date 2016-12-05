package com.ebeijia.mapper;

import com.ebeijia.entity.InterInvokeInfo;

public interface InterInvokeInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InterInvokeInfo record);

    int insertSelective(InterInvokeInfo record);

    InterInvokeInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InterInvokeInfo record);

    int updateByPrimaryKey(InterInvokeInfo record);
}