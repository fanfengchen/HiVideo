package com.ebeijia.module.jypayment.service;

import com.ebeijia.entity.jypayment.TblPayLimitTime;

import java.text.ParseException;
import java.util.Map;

/**
 * Created by lf on 2016/10/24.
 */
public interface PayLimitTimeService {

    //查询缴费日期
    public Map<String ,Object> queryPaymentDate(String aoData) throws Exception;

    //设置缴费日期
    public Map<String,Object> paymentDate(TblPayLimitTime tblPayLimitTime) throws ParseException;
}
