package com.ebeijia.controller.coffee;

import com.ebeijia.ajax.resp.AjaxResp;
import com.ebeijia.annotation.MyLog;
import com.ebeijia.entity.coffee.TblCoupon;
import com.ebeijia.module.coffee.service.CouponService;
import com.ebeijia.util.core.RespCode;
import com.ebeijia.util.core.SystemProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by lf on 2016/9/19.
 */
@Controller
@RequestMapping("coffee")
public class CouponController {

    private static Logger log = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    private CouponService couponService;

    @RequestMapping(value = "addCoupon.html",method = RequestMethod.POST)
    @ResponseBody
    @MyLog(remark = "新增奖品")
    public Map<String , Object> addCoupon(HttpServletRequest request, HttpServletResponse response) {
        //上传图片
        Map<String, Object> map = couponService.updateLoadImg(request, response);
        String updateLoadCode = (String)map.get("code");
        if(updateLoadCode == null || "".equals(updateLoadCode) || "-1".equals(updateLoadCode)){
            return AjaxResp.getReturn(RespCode.COFFEE_UPLOAD_ERROR,"");
        }

        TblCoupon tblCoupon = null;
        try{
            tblCoupon = couponService.getCouponByRequest(request);
        }catch(Exception e){
            log.error("从request中获取数据错误" , e);
        }
        if(tblCoupon == null){
            return AjaxResp.getReturn(RespCode.COUPON_ADD_ERRNULL,"");
        }

        //新的文件夹名称
        String fileName = (String)map.get("name");
        tblCoupon.setImageUrl(SystemProperties.getProperties("coffee_images_save_path") + fileName);

        Map<String , Object> result = couponService.addCoupon(tblCoupon);
        Map<String, Object> resData = result;
        String rescode = (String) resData.get("resCode");
        if(rescode != null && "-2".equals(rescode)){
            return AjaxResp.getReturn(RespCode.COUPON_ADD_ERRNULL,"");
        }
        if(rescode != null && "-3".equals(rescode)){
            return AjaxResp.getReturn(RespCode.COUPON_ADD_ERR,"");
        }
        if(rescode != null && "-1".equals(rescode)){
            return AjaxResp.getReturn(RespCode.COUPON_ADD_ERROR,"");
        }
        if(rescode != null && "0".equals(rescode)){
            resData.put("id",tblCoupon.getId());
            return  AjaxResp.getReturn(RespCode.SUCCESS_CODE,"");
        }
        return AjaxResp.getReturn(RespCode.ERROR_CODE,"");
    }

    @RequestMapping(value = "updateCoupon.html",method = RequestMethod.POST)
    @ResponseBody
    @MyLog(remark = "修改奖品")
    public Map<String , Object> updateCoupon(HttpServletRequest request, HttpServletResponse response){

        //上传图片
        Map<String, Object> map = couponService.updateLoadImg(request, response);
        String updateLoadCode = (String)map.get("code");
        //上传文件失败
        if(updateLoadCode == null || "".equals(updateLoadCode) || "-1".equals(updateLoadCode)){
            return AjaxResp.getReturn(RespCode.COFFEE_UPLOAD_ERROR,"");
        }

        TblCoupon tblCoupon = null;
        try{
            tblCoupon = couponService.getCouponByRequest(request);
        }catch(Exception e){
            log.error("从request中获取数据错误" , e);
        }
        if(tblCoupon == null){
            return AjaxResp.getReturn(RespCode.COUPON_ADD_ERRNULL,"");
        }

        //新的文件夹名称
        String fileName = (String)map.get("name");
        tblCoupon.setImageUrl(SystemProperties.getProperties("coffee_images_save_path") + fileName);

        Map<String ,Object> mapData = couponService.updateCoupon(tblCoupon);
        String rescode = (String) mapData.get("resCode");
        if(rescode != null && "-3".equals(rescode)){
            return AjaxResp.getReturn(RespCode.COUPON_ADD_ERR,"");
        }
        if(rescode != null && "-2".equals(rescode)){
            return AjaxResp.getReturn(RespCode.COUPON_UPDATE_ERRNULL,"");
        }
        if(rescode != null && "-1".equals(rescode)){
            return AjaxResp.getReturn(RespCode.COUPON_UPDATE_ERROR,"");
        }
        if(rescode != null && "0".equals(rescode)){
            return AjaxResp.getReturn(RespCode.SUCCESS_CODE,"");
        }
        return AjaxResp.getReturn(RespCode.ERROR_CODE,"");
    }

    @RequestMapping(value = "queryCouponList.html", method = RequestMethod.POST)
    @ResponseBody
    @MyLog(remark = "查询奖品")
    public Map<String, Object> queryCouponList(String aoData){
        Map<String, Object> resData = couponService.queryCouponList(aoData);
        String resCode = (String)resData.get("resCode");
        if(resCode != null && "-1".equals(resCode)){
            return AjaxResp.getReturn(RespCode.COUPON_ERR_LIMIT, "");
        }
        return AjaxResp.getReturn(RespCode.SUCCESS_CODE,resData);
    }

    @RequestMapping(value = "deleteCoupon.html", method = RequestMethod.POST)
    @ResponseBody
    @MyLog(remark = "删除奖品")
    public Map<String, Object> deleteCoupon(String id){
        Map<String ,Object> result = couponService.deleteCoupon(id);
        String rescope = (String) result.get("resCode");
        if(rescope != null && "-1".equals(rescope)){
            return AjaxResp.getReturn(RespCode.COUPON_DELETE_ERR,"");
        }
        return AjaxResp.getReturn(RespCode.SUCCESS_CODE,"");
    }
}
