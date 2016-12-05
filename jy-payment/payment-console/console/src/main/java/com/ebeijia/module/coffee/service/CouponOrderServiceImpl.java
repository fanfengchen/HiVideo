package com.ebeijia.module.coffee.service;

import com.ebeijia.entity.coffee.TblCouponOrder;
import com.ebeijia.module.coffee.dao.CouponOrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by win7 on 2016/9/19.
 */
@Service("couponOrderService")
public class CouponOrderServiceImpl implements CouponOrderService{

    private static Logger log = LoggerFactory.getLogger(CouponOrderServiceImpl.class);

    @Autowired
    private CouponOrderDao couponOrderDao;

    /**
     * 根据couponNo查询数据
     */
    public Map<String, Object> queryCouponOrder(String couponNo){
        Map<String, Object> map = new HashMap<String, Object>();
        if(StringUtils.isEmpty(couponNo)){
            map.put("code", "-1");
            return map;
        }
        List<Map<String, Object>> list = couponOrderDao.queryCouponOrder(couponNo);
        if(list == null || list.size() == 0){
            map.put("code", "-2");
            return map;
        }
        map.put("code", "0");
        Map<String, Object> data = list.get(0);
        data.put("userName", "");
        map.put("data", data);
        return map;
    }

    /**
     * 保存管理员操作的券
     */
    @Transactional
    public Map<String, Object> useCouponOrder(String id, String orderNo, String couponNo){
        Map<String, Object> map = new HashMap<String, Object>();
        if(StringUtils.isEmpty(id) || StringUtils.isEmpty(orderNo) || StringUtils.isEmpty(couponNo)){
            map.put("code", "-1");
            return map;
        }
        //根据couponNo验证数据是否正确
        List<Map<String, Object>> list = couponOrderDao.queryCouponOrder(couponNo);
        if(list == null || list.size() == 0){
            map.put("code", "-2");
            return map;
        }
        Map<String, Object> mapData = list.get(0);
        if(!id.equals(mapData.get("id"))){
            map.put("code", "-2");
            return map;
        }
        long exDate = 0l;
        try{
            exDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String)mapData.get("expireDate"))).getTime();
        }catch (Exception e){
            log.error("simpleDateFormat parse error : " , e);
        }
        if(exDate == 0 || new Date().getTime() > exDate){
            //表示券已经过期
            map.put("code", "-3");
            return map;
        }

        if(!"".equals(mapData.get("orderNo"))){
            //表示券已经使用过了
            map.put("code", "-4");
            return map;
        }

        TblCouponOrder cou = new TblCouponOrder();
        cou.setCouponNo(couponNo);
        cou.setOrderNo(orderNo);
        cou.setOrderSubTime(new Date());
        cou.setState("1");
        couponOrderDao.save(cou);
        int updateC = couponOrderDao.updateUserCouponById(id);
        map.put("code", "0");
        return map;
    }

    /**
     * 修改输入错误的券
     */
    @Transactional
    public Map<String, Object> updateCouponOrder(String couponNo, String orderNo, String id){
        Map<String, Object> map = new HashMap<String, Object>();
        if(StringUtils.isEmpty(id) || StringUtils.isEmpty(orderNo) || StringUtils.isEmpty(couponNo)){
            map.put("code", "-1");
            return map;
        }
        //根据couponNo验证数据是否正确
        List<Map<String, Object>> list = couponOrderDao.queryCouponOrder(couponNo);
        if(list == null || list.size() == 0){
            map.put("code", "-2");
            return map;
        }
        Map<String, Object> mapData = list.get(0);
        if(!id.equals(mapData.get("id"))){
            map.put("code", "-2");
            return map;
        }
        if("".equals(mapData.get("couponOrderId"))){
            //表示数据不存在不能修改
            map.put("code", "-4");
            return map;
        }

        long exDate = 0l;
        try{
            exDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String)mapData.get("expireDate"))).getTime();
        }catch (Exception e){
            log.error("simpleDateFormat parse error : " , e);
        }
        if(exDate == 0 || new Date().getTime() > exDate){
            //表示券已经过期
            map.put("code", "-3");
            return map;
        }
        couponOrderDao.updateCouponOrderById(id,(String)mapData.get("couponOrderId"), orderNo );
        map.put("code", "0");
        return map;
    }
}
