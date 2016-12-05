package com.ebeijia.controller.coffee;

import com.ebeijia.ajax.resp.AjaxResp;
import com.ebeijia.annotation.MyLog;
import com.ebeijia.module.coffee.service.CouponOrderService;
import com.ebeijia.util.core.RespCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zc on 2016/9/19.
 *
 * 用于管理员人工 将券设置为已使用
 *  先根据券号查询出券的信息
 *  判读券是否已经使用过了
 *  将券的设置为已使用
 *  添加人工修改记录
 */
@Controller
@RequestMapping("coffee")
public class CouponOrderController {

    @Autowired
    private CouponOrderService couponOrderService;

    /**
     * 根据券号查询券信息
     */
    @RequestMapping(value = "queryUserCouponByCouponNo.html", method = RequestMethod.POST)
    @ResponseBody
    @MyLog(remark = "管理员查询券")
    public Map<String, Object> queryUserCouponByCouNo(String couponNo){
        Map<String, Object> map = couponOrderService.queryCouponOrder(couponNo);
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("count", "0");
        res.put("list", "");
        String code = (String)map.get("code");
        if("-2".equals(code)){
            //查询的数据不存在
            return AjaxResp.getReturn(RespCode.COFFEE_DATA_NONENTITY, res);
        }
        if("-1".equals(code)){
            //输入的参数错误
            return AjaxResp.getReturn(RespCode.PARAM_NULL_ERROR, res);
        }
        if("0".equals(code)){
            res.put("count", "1");
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            list.add((Map<String, Object>)map.get("data"));
            res.put("list", list);
            return AjaxResp.getReturn(RespCode.SUCCESS_CODE, res);
        }
        return AjaxResp.getReturn(RespCode.ERROR_CODE, null);
    }

    /**
     * 使用券
     */
    @RequestMapping(value = "useCouponOrder.html", method = RequestMethod.POST)
    @ResponseBody
    @MyLog(remark = "管理员设置券")
    public Map<String, Object> useCouponOrder(String id, String couponNo, String orderNo){
        Map<String, Object> map = couponOrderService.useCouponOrder(id, orderNo, couponNo);
        String code = (String)map.get("code");
        if("-4".equals(code)){
            //券已经使用过了
            return AjaxResp.getReturn(RespCode.COFFEE_COUPON_USE, null);
        }
        if("-3".equals(code)){
            //券已经过期
            return AjaxResp.getReturn(RespCode.COFFEE_COUPON_OUT_DATE, null);
        }
        if("-2".equals(code)){
            //输入的参数错误
            return AjaxResp.getReturn(RespCode.COFFEE_PARAM_ERROR, null);
        }
        if("-1".equals(code)){
            //输入的参数错误
            return AjaxResp.getReturn(RespCode.COFFEE_PARAM_ERROR, null);
        }
        if("0".equals(code)){
            return AjaxResp.getReturn(RespCode.SUCCESS_CODE, null);
        }
        return AjaxResp.getReturn(RespCode.ERROR_CODE, null);
    }

    /**
     * 修改券
     */
    @RequestMapping(value = "updateCouponOrder.html", method = RequestMethod.POST)
    @ResponseBody
    @MyLog(remark = "管理员修改券")
    public Map<String, Object> updateCouponOrder(String id, String couponNo, String orderNo){
        Map<String, Object> map = couponOrderService.updateCouponOrder(couponNo, orderNo, id);
        String code = (String)map.get("code");
        if("-4".equals(code)){
            //券已经使用过了
            return AjaxResp.getReturn(RespCode.COFFEE_COUPON_USE_ERROR, null);
        }
        if("-3".equals(code)){
            //券已经过期
            return AjaxResp.getReturn(RespCode.COFFEE_COUPON_OUT_DATE, null);
        }
        if("-2".equals(code)){
            //输入的参数错误
            return AjaxResp.getReturn(RespCode.COFFEE_PARAM_ERROR, null);
        }
        if("-1".equals(code)){
            //输入的参数错误
            return AjaxResp.getReturn(RespCode.COFFEE_PARAM_ERROR, null);
        }
        if("0".equals(code)){
            return AjaxResp.getReturn(RespCode.SUCCESS_CODE, null);
        }
        return AjaxResp.getReturn(RespCode.ERROR_CODE, null);
    }

}
