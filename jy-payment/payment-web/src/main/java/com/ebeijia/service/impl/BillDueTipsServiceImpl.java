package com.ebeijia.service.impl;

import com.ebeijia.entity.BillDueTips;
import com.ebeijia.mapper.BillDueTipsMapper;
import com.ebeijia.service.BillDueTipsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by YJ on 2016/10/18.
 */

@Service
public class BillDueTipsServiceImpl implements BillDueTipsService {
    @Resource
    private BillDueTipsMapper billDueTipsMapper;
    @Override
    public int insertBatch(List<BillDueTips> billDueTipsList) {
        return billDueTipsMapper.insertBatch(billDueTipsList);
    }

    @Override
    public int insertSelective(BillDueTips record) {
        return billDueTipsMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(BillDueTips record) {
        return billDueTipsMapper.updateByPrimaryKeySelective(record) ;
    }
}
