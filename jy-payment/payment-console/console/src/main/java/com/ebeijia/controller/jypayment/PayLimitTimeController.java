package com.ebeijia.controller.jypayment;

import com.ebeijia.ajax.resp.AjaxResp;
import com.ebeijia.annotation.MyLog;
import com.ebeijia.entity.jypayment.TblPayLimitTime;
import com.ebeijia.module.jypayment.service.PayLimitTimeService;
import com.ebeijia.util.core.RespCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by lf on 2016/10/25.
 */
@Controller
@RequestMapping("jypayment")
public class PayLimitTimeController {
    private static Logger log = LoggerFactory.getLogger(PayLimitTimeController.class);

    @Autowired
    private PayLimitTimeService payLimitTimeService;

    @RequestMapping(value = "queryPaymentDate",method = RequestMethod.POST)
    @ResponseBody
    @MyLog(remark = "查询缴费时间")
    public Map<String ,Object> queryPaymentDate(String aoData){
        try{
            Map<String, Object> map = payLimitTimeService.queryPaymentDate(aoData);
            String rescode = (String) map.get("resCode");
            if(rescode != null && "-1".equals(rescode)){
                return AjaxResp.getReturn(RespCode.COUPON_ERR_LIMIT, "");
            }
            return AjaxResp.getReturn(RespCode.SUCCESS_CODE,map);
        }catch (Exception e){
            log.error(e.getMessage() , e);
        }

        return AjaxResp.getReturn(RespCode.ERROR_CODE,"");
    }

    @RequestMapping(value = "paymentDate",method = RequestMethod.POST)
    @ResponseBody
    @MyLog(remark = "设置缴费时间")
    public Map<String ,Object> paymentDate(TblPayLimitTime tblPayLimitTime){
        try{
            Map<String ,Object> map = payLimitTimeService.paymentDate(tblPayLimitTime);
            String rescode = (String) map.get("resCode");

            if(rescode != null && "-1".equals(rescode)){
                return AjaxResp.getReturn(RespCode.JY_NOTNULL, "");
            }
            if(rescode != null && "-2".equals(rescode)){
                return AjaxResp.getReturn(RespCode.JY_CHOOSEONE, "");
            }
            if(rescode != null && "-3".equals(rescode)){
                return AjaxResp.getReturn(RespCode.JY_CHOOSEERR, "");
            }
            if(rescode != null && "-4".equals(rescode)){
                return AjaxResp.getReturn(RespCode.JY_DATE_ERR, "");
            }
            if(rescode != null && "-5".equals(rescode)){
                return AjaxResp.getReturn(RespCode.JY_TIME_ERR, "");
            }
            if(rescode != null && "0".equals(rescode)){
                return AjaxResp.getReturn(RespCode.SUCCESS_CODE,"");
            }
        }catch (Exception e){
            log.error(e.getMessage() , e);
        }

        return AjaxResp.getReturn(RespCode.ERROR_CODE,"");
    }
}
