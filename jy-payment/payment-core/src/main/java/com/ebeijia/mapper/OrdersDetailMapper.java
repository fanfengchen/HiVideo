package com.ebeijia.mapper;

import com.ebeijia.entity.OrdersDetail;

public interface OrdersDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrdersDetail record);

    int insertSelective(OrdersDetail record);

    OrdersDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrdersDetail record);

    int updateByPrimaryKey(OrdersDetail record);

    OrdersDetail selectByOrderId(Long orderId);
}