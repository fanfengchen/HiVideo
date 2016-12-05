package com.ebeijia.service;

import com.ebeijia.entity.PayLimitTime;

/**
 * Created by YJ on 2016/10/13.
 */
public interface GeneratlorOrderService {

    String generator(String param);

    PayLimitTime getPaytimeLimit(String feeType);
}
