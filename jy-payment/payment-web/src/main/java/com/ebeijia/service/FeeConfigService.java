package com.ebeijia.service;

import com.ebeijia.entity.FeeConfig;

/**
 * Created by LiYan on 2016/10/13.
 */
public interface FeeConfigService {
    FeeConfig findByFeeTypeAndOprType(String feeType,String oprType);


    String isAmtInLimit(String feeType,Long orderAmt,Long needPay);
}
