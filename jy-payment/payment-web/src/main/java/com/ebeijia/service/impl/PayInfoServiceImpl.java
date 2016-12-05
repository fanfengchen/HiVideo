package com.ebeijia.service.impl;

import com.ebeijia.entity.PayInfo;
import com.ebeijia.mapper.PayInfoMapper;
import com.ebeijia.service.PayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by LiYan on 2016/9/22.
 */
@Service
public class PayInfoServiceImpl implements PayInfoService {
    @Autowired
    private PayInfoMapper payInfoMapper;

    @Override
    @Transactional
    public int addPayInfo(PayInfo payInfo) {
        return payInfoMapper.insert(payInfo);
    }

    @Override
    public PayInfo selectByOrderNo(String orderNo) {
        return payInfoMapper.selectByOrderNo(orderNo);
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(PayInfo record) {
        return payInfoMapper.updateByPrimaryKeySelective(record);
    }
}
