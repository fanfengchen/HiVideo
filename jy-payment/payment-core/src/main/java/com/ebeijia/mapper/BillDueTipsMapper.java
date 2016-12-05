package com.ebeijia.mapper;

import com.ebeijia.entity.BillDueTips;

import java.util.List;

public interface BillDueTipsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BillDueTips record);

    int insertSelective(BillDueTips record);

    BillDueTips selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BillDueTips record);

    int updateByPrimaryKey(BillDueTips record);

    int insertBatch(List<BillDueTips> billDueTipsList);
}