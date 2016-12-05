package com.ebeijia.service.impl;

import com.ebeijia.entity.Orders;
import com.ebeijia.mapper.OrdersMapper;
import com.ebeijia.service.UserOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ff on 2016/9/28.
 */
@Service
public class UserOrderServiceImpl implements UserOrderService {

    private static Logger log = LoggerFactory.getLogger(UserOrderServiceImpl.class);

    @Autowired
    private OrdersMapper orderMapper;

    @Override
    public Map<String, Object> getOrderList(String openId, String orderType) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        //判断openId是不是为空
        if (StringUtils.isEmpty(openId)) {
            resMap.put("code", "-1");
            return resMap;
        }
        List<Orders> list = orderMapper.selectAllResult(openId, orderType);
        List<Object> newList = new ArrayList<>();

        //判断list是否为空
        if (list == null || list.isEmpty()) {
            resMap.put("code", "-2");
            return resMap;
        }
        //查询结果按月分组
        Map<String,List<Orders>> map=new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        for(Orders orders:list){
            BigDecimal orAmt=new BigDecimal(orders.getOrderAmt()).divide(new BigDecimal(100),2,2);
            //orders.setOrderAmt(orAmt.longValue());
            orders.setOrderAmtString(orAmt.toString());
            String payMonth=orders.getPayMonth();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
            String orderTime=sdf.format(orders.getOrderTime());

            //String paySucTime=orders.getPaySuccTime();
            if(null != orderTime && !"".equals(orderTime)){
                String month=orderTime.substring(4,6);
                String day=orderTime.substring(6,8);
                String hour=orderTime.substring(8,10);
                String min=orderTime.substring(10,12);
                String orderTimeTemp=month+"-"+day+"  "+hour+":"+min;
                orders.setPaySuccTime(orderTimeTemp);
            }
            List<Orders> tempList = map.get(payMonth);
            if(tempList == null || tempList.size() == 0){
                List<Orders> listT = new ArrayList<Orders>();
                listT.add(orders);
                map.put(payMonth, listT);
            }else{
                tempList.add(orders);
                //map.put(payMonth,tempList);
            }
        }

        resMap.put("data", map);
        resMap.put("code", "0");

        return resMap;
    }

    public Date dataCon(String date) {
        SimpleDateFormat foramt = new SimpleDateFormat("yyyyMMddHHmmss");
        Date res_date = new Date();
        try {
            res_date = foramt.parse(date);
        } catch (ParseException e) {
            log.error("时间类型转换错误", e);
        }
        return res_date;

    }

}
