package com.ebeijia.service.impl;

import com.ebeijia.entity.RefundInfo;
import com.ebeijia.mapper.RefundInfoMapper;
import com.ebeijia.service.RefundInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by LiYan on 2016/10/19.
 */
@Service
public class RefundInfoServiceImpl implements RefundInfoService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RefundInfoMapper refundInfoMapper;

    @Override
    public int addRefundInfo(RefundInfo refundInfo) {
        return refundInfoMapper.insertSelective(refundInfo);
    }

    @Override
    public int updateByPrimaryKeySelective(RefundInfo refundInfo) {
        return refundInfoMapper.updateByPrimaryKeySelective(refundInfo);
    }

    @Override
    public RefundInfo findByOrderId(Long orderId) {
        return refundInfoMapper.selectByOrderId(orderId);
    }
}
