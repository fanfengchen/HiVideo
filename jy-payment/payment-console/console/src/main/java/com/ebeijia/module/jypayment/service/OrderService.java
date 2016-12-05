package com.ebeijia.module.jypayment.service;

import com.ebeijia.entity.jypayment.OrderDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created with com.ebeijia.module.jypayment.service
 * User : zc
 * Date : 2016/10/24
 */
public interface OrderService {

    public Map<String, Object> queryPayDetail(OrderDto orderDto, String aoData) throws Exception;

    public Map<String, Object> downLoad(HttpServletRequest request,
                                        HttpServletResponse response,
                                        OrderDto orderDto)throws Exception;
}
