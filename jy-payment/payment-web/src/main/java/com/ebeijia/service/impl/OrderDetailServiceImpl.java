package com.ebeijia.service.impl;

import com.ebeijia.entity.OrdersDetail;
import com.ebeijia.mapper.OrdersDetailMapper;
import com.ebeijia.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by LiYan on 2016/9/22.
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrdersDetailMapper ordersDetailMapper;

    @Override
    @Transactional
    public int addOrderDetail(OrdersDetail ordersDetail) {
        return ordersDetailMapper.insert(ordersDetail);
    }

    @Override
    public OrdersDetail selectByOrderId(Long orderId) {
        return ordersDetailMapper.selectByOrderId(orderId);
    }

    @Override
    @Transactional
    public int modifyOrderDetail(OrdersDetail ordersDetail) {
        return ordersDetailMapper.updateByPrimaryKeySelective(ordersDetail);
    }
}
