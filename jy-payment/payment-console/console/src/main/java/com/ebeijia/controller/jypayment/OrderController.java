package com.ebeijia.controller.jypayment;

import com.ebeijia.ajax.resp.AjaxResp;
import com.ebeijia.annotation.MyLog;
import com.ebeijia.entity.jypayment.OrderDto;
import com.ebeijia.module.jypayment.service.OrderService;
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
 * Date : 2016/10/24
 */
@Controller
@RequestMapping("jypayment")
public class OrderController {

    private static Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "queryPayDetail", method = RequestMethod.POST)
    @ResponseBody
    @MyLog(remark = "查询交易明细")
    public Map<String, Object> queryPayDetail(OrderDto orderDto, String aoData){
        try{
            Map<String, Object> resMap = orderService.queryPayDetail(orderDto, aoData);
            String code = (String)resMap.get("code");
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

    @RequestMapping(value = "downloadPayDetail")
    @ResponseBody
    @MyLog(remark = "下载交易明细")
    public Map<String, Object> downloadPayDetail(OrderDto orderDto,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        try{
            Map<String, Object> map = orderService.downLoad(request, response, orderDto);
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

}
