package com.ebeijia.service;

import com.ebeijia.entity.PayInfo;

/**
 * Created by LiYan on 2016/9/22.
 */
public interface PayInfoService {
    int addPayInfo(PayInfo payInfo);

    PayInfo selectByOrderNo(String orderNo);

    int updateByPrimaryKeySelective(PayInfo record);
}
