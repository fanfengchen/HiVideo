package com.ebeijia.service.impl;

import com.ebeijia.entity.PayLimitTime;
import com.ebeijia.mapper.PayLimitTimeMapper;
import com.ebeijia.service.PayLimitTimeService;
import com.ebeijia.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ebeijia.util.DateUtil.fomatDate;

/**
 * Created by LiYan on 2016/9/29.
 */
@Service
public class PayLimitTimeServiceImpl implements PayLimitTimeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PayLimitTimeMapper payLimitTimeMapper;
    @Override
    public PayLimitTime selectByType(String feeType) {
        return payLimitTimeMapper.selectByFeeType(feeType);
    }

    @Override
    public boolean isTimeInLimit(PayLimitTime payLimitTime) throws Exception{
        //按当日时间判断
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Long curTime=sdf.parse(sdf.format(new Date())).getTime();
        Long startTime=sdf.parse(payLimitTime.getLimitTimeStart()).getTime();
        Long endTime=sdf.parse(payLimitTime.getLimitTimeEnd()).getTime();
        if(curTime > startTime && curTime < endTime){
            return true;
        }
        logger.error("当前时间不在缴费时间内");
        return false;
    }

    @Override
    public boolean isDateInLimit(PayLimitTime payLimitTime) throws Exception {
        //按日期判断
        Date daTemp = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Long curDate =DateUtil.fomatDate(sdf.format(daTemp)).getTime();

        Long startDate = fomatDate(payLimitTime.getLimitDateStart()).getTime();
        Long endDate = fomatDate(payLimitTime.getLimitDateEnd()).getTime();
        if (curDate >= startDate && curDate <= endDate) {
            return true;
        }
        logger.error("当前日期不在缴费区间内");
        return false;
    }


}
