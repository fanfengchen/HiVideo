package com.ebeijia.service;

import com.ebeijia.entity.RefundInfo;

/**
 * Created by LiYan on 2016/10/19.
 */
public interface RefundInfoService {
    int addRefundInfo(RefundInfo refundInfo);

    int updateByPrimaryKeySelective(RefundInfo refundInfo);

    RefundInfo findByOrderId(Long orderId);
}
