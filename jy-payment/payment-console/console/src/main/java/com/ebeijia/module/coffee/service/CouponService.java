package com.ebeijia.module.coffee.service;

import com.ebeijia.entity.coffee.TblCoupon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Map;

/**
 * Created by lf on 2016/9/19.
 */
public interface CouponService {

    //添加奖品
    public Map<String ,Object> addCoupon(TblCoupon tblCoupon);

    //修改奖品
    public Map<String , Object> updateCoupon(TblCoupon tblCoupon);

    //删除奖品
    public Map<String ,Object> deleteCoupon(String id);

    //获取奖品列表
    public Map<String , Object> queryCouponList(String aoData);

    //上传文件
    public Map<String, Object> updateLoadImg(HttpServletRequest request, HttpServletResponse response);

    //从request中获取数据，将数据转换为对象
    public TblCoupon getCouponByRequest(HttpServletRequest request) throws Exception;
}
