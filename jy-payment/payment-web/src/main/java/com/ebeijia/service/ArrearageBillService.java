package com.ebeijia.service;

import java.util.Map;

/**
 * Created with com.ebeijia.service
 * User : zc
 * Date : 2016/9/28
 */
public interface ArrearageBillService {

    Map<String, Object> queryWaterBill(String userNo, String openid);

    Map<String, Object> queryGasBill(String userNo, String openid);

    Map<String, Object> querRechargeBill(String userNo, String openid);
}
