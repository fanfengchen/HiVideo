package com.ebeijia.service.impl;

import com.ebeijia.entity.PayLimitTime;
import com.ebeijia.mapper.PayLimitTimeMapper;
import com.ebeijia.mapper.SequenceMapper;
import com.ebeijia.service.GeneratlorOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by YJ on 2016/10/13.
 */
@Service
public class GeneratlorOrderServiceImpl implements GeneratlorOrderService {

    @Resource
    private SequenceMapper sequenceMapper;
    @Resource
    private PayLimitTimeMapper payLimitTimeMapper;


    @Transactional
    @Override
    public String generator(String param) {
        return  sequenceMapper.genOrderNo(param);
    }

    @Override
    public PayLimitTime getPaytimeLimit(String feeType) {
        return  payLimitTimeMapper.selectByFeeType(feeType);
    }
}
