package com.ebeijia.service;

import com.ebeijia.entity.BillDueTips;

import java.util.List;

/**
 * Created by YJ on 2016/10/18.
 */
public interface BillDueTipsService {
    int insertBatch(List<BillDueTips> billDueTipsList);

    int insertSelective(BillDueTips record);

    int updateByPrimaryKeySelective(BillDueTips record);
}
