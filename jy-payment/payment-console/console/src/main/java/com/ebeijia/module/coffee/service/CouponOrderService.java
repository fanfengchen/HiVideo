package com.ebeijia.module.coffee.service;

import java.util.Map;

/**
 * Created by win7 on 2016/9/19.
 */
public interface CouponOrderService {

    public Map<String, Object> queryCouponOrder(String couponNo);

    public Map<String, Object> useCouponOrder(String id, String OrderNo, String CouponNo);

    public Map<String, Object> updateCouponOrder(String couponNo, String orderNo, String id);
}
