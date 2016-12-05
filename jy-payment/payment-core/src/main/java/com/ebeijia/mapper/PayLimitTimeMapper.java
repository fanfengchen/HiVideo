package com.ebeijia.mapper;

import com.ebeijia.entity.PayLimitTime;

public interface PayLimitTimeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PayLimitTime record);

    int insertSelective(PayLimitTime record);

    PayLimitTime selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayLimitTime record);

    int updateByPrimaryKey(PayLimitTime record);

    PayLimitTime selectByFeeType(String feeType);
}