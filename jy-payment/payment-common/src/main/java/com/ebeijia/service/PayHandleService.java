package com.ebeijia.service;

import com.ebeijia.dto.OrderResultDto;
import com.ebeijia.dto.RefundDto;
import com.ebeijia.entity.Orders;
import com.ebeijia.entity.OrdersDetail;
import com.ebeijia.exception.ServiceException;
import com.ebeijia.pay.RefundResult;

import java.text.ParseException;
import java.util.Map;

/**
 * Created by YJ on 2016/9/20.
 */
public interface PayHandleService {


    /**
     *
     * @return 返回支付的token和相关参数
     * @throws ServiceException
     * @throws ParseException
     */
    OrderResultDto toAuthPay(Orders orders, OrdersDetail ordersDetail) throws Exception;

    String payResultHandle(Map<String, String> resultMap) throws ServiceException;



}
