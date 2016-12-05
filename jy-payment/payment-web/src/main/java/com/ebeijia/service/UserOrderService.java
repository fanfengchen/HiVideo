package com.ebeijia.service;

import java.util.Map;

/**
 * Created by ff on 2016/9/28.
 */
public interface UserOrderService {

    public Map<String, Object> getOrderList(String openId, String orderType);
}
