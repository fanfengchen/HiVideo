package com.ebeijia.mapper;

import com.ebeijia.entity.RefundInfo;

public interface RefundInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RefundInfo record);

    int insertSelective(RefundInfo record);

    RefundInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RefundInfo record);

    int updateByPrimaryKey(RefundInfo record);

    RefundInfo selectByOrderId(Long orderId);
}