package com.ebeijia.controller.jypayment;

import com.ebeijia.ajax.resp.AjaxResp;
import com.ebeijia.annotation.MyLog;
import com.ebeijia.module.jypayment.service.DayStatementService;
import com.ebeijia.util.core.RespCode;
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
 * Created with com.ebeijia.controller.jypayment
 * User : zc
 * Date : 2016/10/25
 */
@Controller
@RequestMapping("jypayment")
public class DayStatementController {

    private static Logger log = LoggerFactory.getLogger(DayStatementController.class);

    @Autowired
    private DayStatementService dayStatementService;

    @RequestMapping(value = "queryDayStatement", method = RequestMethod.POST)
    @ResponseBody
    @MyLog(remark = "查询日报表")
    public Map<String, Object> queryPayDetail(String orderTimeStart,
                                              String orderTimeEnd,
                                              String orderType,
                                              String aoData) {
        try{
            Map<String, Object> resMap = dayStatementService.getDayStatement(orderTimeStart,orderTimeEnd,orderType,aoData);
            String code = (String)resMap.get("code");
            if("-3".equals(code)){
                return AjaxResp.getReturn(RespCode.JY_PARAM_DATE_ERROR,"");
            }
            if("-2".equals(code)){
                return AjaxResp.getReturn(RespCode.JY_PARAM_MONTH_ERROR,"");
            }
            if("-1".equals(code)){
                return AjaxResp.getReturn(RespCode.JY_PARAM_ERROR,"");
            }
            if("0".equals(code)){
                return AjaxResp.getReturn(RespCode.SUCCESS_CODE,resMap.get("data"));
            }
        }catch (Exception e){
            log.error(e.getMessage() , e);
        }
        return AjaxResp.getReturn(RespCode.ERROR_CODE,"");
    }

    @RequestMapping(value = "downloadDayStatement")
    @ResponseBody
    @MyLog(remark = "下载日报表")
    public Map<String, Object> downloadPayDetail(String orderTimeStart,
                                                 String orderTimeEnd,
                                                 String orderType,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {
        try{
            Map<String, Object> map = dayStatementService.downloadPayDetail(orderTimeStart,
                    orderTimeEnd, orderType, request, response);
            String code = (String)map.get("code");
            if("-1".equals(code)){
                return AjaxResp.getReturn(RespCode.JY_PARAM_ERROR,"");
            }
            if("0".equals(code)){
                return AjaxResp.getReturn(RespCode.SUCCESS_CODE,"");
            }
        }catch (Exception e){
            log.error(e.getMessage() , e);
        }
        return AjaxResp.getReturn(RespCode.ERROR_CODE,"");
    }

    @RequestMapping(value = "downloadMonthStatement")
    @ResponseBody
    @MyLog(remark = "下载月报表")
    public Map<String, Object> downloadMonthDetail(String orderTimeStart,
                                                 String orderTimeEnd,
                                                 String orderType,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {
        try{
            Map<String, Object> map = dayStatementService.downloadMonthDetail(orderTimeStart,
                    orderTimeEnd, orderType, request, response);
            String code = (String)map.get("code");
            if("-1".equals(code)){
                return AjaxResp.getReturn(RespCode.JY_PARAM_ERROR,"");
            }
            if("0".equals(code)){
                return AjaxResp.getReturn(RespCode.SUCCESS_CODE,"");
            }
        }catch (Exception e){
            log.error(e.getMessage() , e);
        }
        return AjaxResp.getReturn(RespCode.ERROR_CODE,"");
    }

    @RequestMapping(value = "queryMonthStatement", method = RequestMethod.POST)
    @ResponseBody
    @MyLog(remark = "查询月报表")
    public Map<String, Object> queryMonthDetail(String orderTimeStart,
                                              String orderTimeEnd,
                                              String orderType,
                                              String aoData) {
        try {
            Map<String, Object> resMap = dayStatementService.getMonthStatement(orderTimeStart, orderTimeEnd, orderType, aoData);
            String code = (String)resMap.get("code");
            if("-3".equals(code)){
                return AjaxResp.getReturn(RespCode.JY_PARAM_DATE_ERROR,"");
            }
            if("-2".equals(code)){
                return AjaxResp.getReturn(RespCode.JY_PARAM_MONTH_ERROR,"");
            }
            if("-1".equals(code)){
                return AjaxResp.getReturn(RespCode.JY_PARAM_ERROR,"");
            }
            if("0".equals(code)){
                return AjaxResp.getReturn(RespCode.SUCCESS_CODE,resMap.get("data"));
            }
        }catch (Exception e){
            log.error(e.getMessage() , e);
        }
        return AjaxResp.getReturn(RespCode.ERROR_CODE,"");
    }


}
