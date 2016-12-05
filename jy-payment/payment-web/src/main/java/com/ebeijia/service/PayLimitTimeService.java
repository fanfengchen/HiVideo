package com.ebeijia.service;

import com.ebeijia.entity.PayLimitTime;

/**
 * Created by LiYan on 2016/9/29.
 */
public interface PayLimitTimeService {
    PayLimitTime selectByType(String feeType);

    boolean isTimeInLimit(PayLimitTime payLimitTime) throws Exception;

    boolean isDateInLimit(PayLimitTime payLimitTime) throws Exception;
}
