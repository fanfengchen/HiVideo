package com.ebeijia.service;

import com.ebeijia.entity.OrdersDetail;

/**
 * Created by LiYan on 2016/9/22.
 */
public interface OrderDetailService {
    int addOrderDetail(OrdersDetail ordersDetail);

    OrdersDetail selectByOrderId(Long orderId);

    int modifyOrderDetail(OrdersDetail ordersDetail);
}
