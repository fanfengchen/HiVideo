package com.ebeijia.controller.pay;

import com.ebeijia.common.CommonConstant;
import com.ebeijia.entity.Orders;
import com.ebeijia.service.OrderService;
import com.ebeijia.service.PayHandleService;
import com.ebeijia.util.XmlUtils;
import com.ebeijia.web.ResponseMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by LiYan on 2016/9/27.
 */
@RestController
public class PayResultController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "waterFarPayService")
    private PayHandleService waterFarPayService;

    @Resource(name = "payGasHandleBaseService")
    private PayHandleService payGasHandleBaseService;
    @Resource
    private OrderService orderService;

    @RequestMapping(value = "waterFarPayResult")
    public ResponseMessage waterFarPayResult(HttpServletRequest req){
        String respString = "error";
        try {
            req.setCharacterEncoding("utf-8");
            String resString = XmlUtils.parseRequst(req);
            logger.info("======================支付通知请求接口数据==================："
                    + resString);
            Map<String, String> map = null;
            if (StringUtils.isNotEmpty(resString)) {
                map = XmlUtils.toMap(resString.getBytes("utf-8"), "utf-8");
                String orderNo=map.get("out_trade_no");
                Orders orders=orderService.findByOrderNo(orderNo);
                String type=orders.getOrderType();

                String result=null;
                if(CommonConstant.WATER_FAY_PAY.equals(type)){
                    result = waterFarPayService.payResultHandle(map);
                }else{
                    result = payGasHandleBaseService.payResultHandle(map);
                }

                if ("0".equals(result)) {
                    respString = "success";
                }
            } else {
                respString = "fail";
            }
        } catch (Exception e) {
            respString = "fail";
            logger.error(e.getMessage(), e);
        }
        return ResponseMessage.success(respString);
    }
}
