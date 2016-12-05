package com.ebeijia.module.jypayment.service;

import com.ebeijia.entity.jypayment.TblPayLimitTime;
import com.ebeijia.module.jypayment.dao.PayLimitTimeDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lf on 2016/10/24.
 */
@Service
public class PayLimitTimeServiceImpl implements PayLimitTimeService{

    @Autowired
    private PayLimitTimeDao payLimitTimeDao;

    public Map<String ,Object> queryPaymentDate(String aoData) throws Exception{
        Map<String ,Object> map = new HashMap<String, Object>();
        if(aoData == null || "".equals(aoData) || aoData.indexOf("[{") != 0){
            map.put("resCode","-1");
            return map;
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" from TblPayLimitTime ORDER BY id");
        map = payLimitTimeDao.queryPaymentDate(sql.toString(),aoData);
        return dataConversion(map);
    }

    public Map<String ,Object> dataConversion(Map<String ,Object> map) throws Exception{
        Map<String ,Object> res = new HashMap<String, Object>();

        List<TblPayLimitTime> data = (List<TblPayLimitTime>) map.get("list");
        JSONArray jsonArray = new JSONArray();

        SimpleDateFormat formatS = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatM = new SimpleDateFormat("HH:mm");

        for(TblPayLimitTime payLimitTime : data){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",payLimitTime.getId());
            jsonObject.put("feeType",payLimitTime.getFeeType());
            jsonObject.put("limitDateStart", payLimitTime.getLimitDateStart());
            jsonObject.put("limitDateEnd",payLimitTime.getLimitDateEnd());

            String stratTime = payLimitTime.getLimitTimeStart();
            String endTime = payLimitTime.getLimitTimeEnd();

            jsonObject.put("limitTimeStart",formatM.format(formatS.parse(stratTime)));
            jsonObject.put("limitTimeEnd",formatM.format(formatS.parse(endTime)));
            jsonArray.add(jsonObject);
        }
        res.put("data",jsonArray);
        res.put("count",map.get("total"));
        return res;
    }


    public Map<String,Object> paymentDate(TblPayLimitTime tblPayLimitTime) throws ParseException {
        Map<String ,Object> map = new HashMap<String, Object>();
        if(tblPayLimitTime.getId() == 0){
            map.put("resCode","-1");
            return map;
        }

        //按日期时间判断
        SimpleDateFormat sd = new SimpleDateFormat("YYYY-MM-DD");
        long start = sd.parse(tblPayLimitTime.getLimitDateStart()).getTime();
        long end = sd.parse(tblPayLimitTime.getLimitDateEnd()).getTime();
        if(start > end){
            map.put("resCode","-4");
            return map;
        }

        //按当日时间判断
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String startTimeTemp = tblPayLimitTime.getLimitTimeStart();
        String endTimeTemp = tblPayLimitTime.getLimitTimeEnd();
        long startTime=sdf.parse(startTimeTemp).getTime();
        long endTime=sdf.parse(endTimeTemp).getTime();
        if(startTime > endTime){
           map.put("resCode","-5");
            return map;
        }

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        tblPayLimitTime.setLimitTimeStart(format.format(sdf.parse(startTimeTemp)));
        tblPayLimitTime.setLimitTimeEnd(format.format(sdf.parse(endTimeTemp)));
        payLimitTimeDao.paymentDate(tblPayLimitTime);
        map.put("resCode","0");
        return map;
    }
}
