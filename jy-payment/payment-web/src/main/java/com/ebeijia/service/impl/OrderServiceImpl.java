package com.ebeijia.service.impl;

import com.ebeijia.dto.RefundDto;
import com.ebeijia.entity.Orders;
import com.ebeijia.exception.ServiceException;
import com.ebeijia.mapper.OrdersDetailMapper;
import com.ebeijia.mapper.OrdersMapper;
import com.ebeijia.mapper.PayInfoMapper;
import com.ebeijia.pay.RefundResult;
import com.ebeijia.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by LiYan on 2016/9/22.
 */
@Service
public class OrderServiceImpl extends OrderBaseService implements OrderService{
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    @Transactional
    public int addOrder(Orders orders) {
        return ordersMapper.insert(orders);
    }

    @Override
    @Transactional
    public int modifyOrder(Orders orders) {
        return ordersMapper.updateByPrimaryKey(orders);
    }

    @Override
    public Orders findByOrderNo(String orderNo) {
        return ordersMapper.selectByOrderNo(orderNo);
    }

    @Override
    public List<Orders> selectByNotPay() {
        return ordersMapper.selectByNotPay();
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(Orders record) {
        return ordersMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<Orders> selectByToDayPaySuccesss() {
        return ordersMapper.selectByToDayPaySuccesss();
    }

    @Override
    public Orders selectByOrdersDeatil(String orderNo) {
        return ordersMapper.selectByOrdersDeatil(orderNo);
    }

    @Override
    public RefundResult sentToRefund(RefundDto refundDto) throws ServiceException {
        return super.sentToRefund(refundDto);
    }
}
