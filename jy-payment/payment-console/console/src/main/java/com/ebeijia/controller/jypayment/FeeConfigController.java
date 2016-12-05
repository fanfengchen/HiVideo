package com.ebeijia.controller.jypayment;

import com.ebeijia.ajax.resp.AjaxResp;
import com.ebeijia.annotation.MyLog;
import com.ebeijia.entity.jypayment.TblFeeConfig;
import com.ebeijia.module.jypayment.service.FeeConfigService;
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
 * Created by lf on 2016/10/9.
 */
@Controller
@RequestMapping("jypayment")
public class FeeConfigController {

    private static Logger log = LoggerFactory.getLogger(FeeConfigController.class);

    @Autowired
    private FeeConfigService feeConfigService;

    @RequestMapping(value = "queryArrearsCeiling",method = RequestMethod.POST)
    @ResponseBody
    //@MyLog(remark = "查询欠、缴费")
    public Map<String ,Object> queryArrearsCeiling(String aoData){
        try {
            Map<String ,Object> map = feeConfigService.queryArrearsCeiling(aoData);
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



    @RequestMapping(value = "arrearsCeiling",method = RequestMethod.POST)
    @ResponseBody
   // @MyLog(remark = "设置欠、缴费金额")
    public Map<String ,Object> arrearsCeiling(TblFeeConfig tblFeeConfig){
        try {
            Map<String ,Object> result = feeConfigService.arrearsCeiling(tblFeeConfig);
            String rescode = (String) result.get("resCode");
            if(rescode != null && "-1".equals(rescode)){
                return AjaxResp.getReturn(RespCode.JY_NOTNULL,"");
            }
            if(rescode != null && "-2".equals(rescode)){
                return AjaxResp.getReturn(RespCode.JY_CHOOSEONE,"");
            }
            if(rescode != null && "-3".equals(rescode)){
                return AjaxResp.getReturn(RespCode.JY_CHOOSEERR,"");
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
