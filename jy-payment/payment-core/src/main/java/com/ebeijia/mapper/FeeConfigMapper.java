package com.ebeijia.mapper;

import com.ebeijia.entity.FeeConfig;
import org.apache.ibatis.annotations.Param;

public interface FeeConfigMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(FeeConfig record);

    int insertSelective(FeeConfig record);

    FeeConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FeeConfig record);

    int updateByPrimaryKey(FeeConfig record);

    FeeConfig selectByFeeTypeAndOprType(@Param("feeType") String feeType, @Param("oprType") String oprType);
}
