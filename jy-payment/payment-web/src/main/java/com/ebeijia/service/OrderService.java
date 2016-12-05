package com.ebeijia.service;

import com.ebeijia.dto.RefundDto;
import com.ebeijia.entity.Orders;
import com.ebeijia.exception.ServiceException;
import com.ebeijia.pay.RefundResult;

import java.util.List;

/**
 * Created by LiYan on 2016/9/22.
 */
public interface OrderService {
    int addOrder(Orders orders);

    int modifyOrder(Orders orders);

    Orders findByOrderNo(String No);

    List<Orders> selectByNotPay();

    int updateByPrimaryKeySelective(Orders record);


    List<Orders> selectByToDayPaySuccesss();

    Orders selectByOrdersDeatil(String orderNo);

    RefundResult sentToRefund(RefundDto refundDto) throws ServiceException;


}
