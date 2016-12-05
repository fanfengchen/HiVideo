package com.ebeijia.controller;

import com.ebeijia.entity.Orders;
import com.ebeijia.service.OrderService;
import com.ebeijia.service.UserOrderService;
import com.ebeijia.web.ResponseMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by ff on 2016/9/28.
 */
@Controller
public class UserOrderController {
    @Autowired
    private UserOrderService userOrderService;


    @Resource
    private OrderService orderService;

    /**
     * 查询用户订单
     *
     * @param openId
     * @param type 缴费订单类型
     * @return
     */
    @RequestMapping(value = "/queryPaymentList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage userOrderController(String openId, String type, HttpServletResponse res) {
        res.setHeader("Access-Control-Allow-Origin","*");
        Map<String, Object> map = userOrderService.getOrderList(openId, type);
        String code = (String) map.get("code");
        if (code.equals("-1")) {
            return ResponseMessage.error("输入的参数为空");
        }
        if (code.equals("-2")) {
            return  ResponseMessage.error("-2","查询结果为空");
        }

        if (code.equals("0")) {
            return ResponseMessage.success(map.get("data"));
        }
        return ResponseMessage.error("服务器内部错误");
    }


    @RequestMapping("/getOrderNo")
    @ResponseBody
    public ResponseMessage getOrders(String orderNo,HttpServletResponse res) throws ParseException {
        res.setHeader("Access-Control-Allow-Origin","*");
        if(StringUtils.isEmpty(orderNo)){
            return ResponseMessage.error("101","参数错误");
        }else{
            Orders orders = orderService.selectByOrdersDeatil(orderNo);
            BigDecimal totalFee = new BigDecimal(orders.getOrderAmt());
            BigDecimal d100 = new BigDecimal(100);
            BigDecimal fee = totalFee.divide(d100,2,2);//小数点2位
            orders.setFree(fee.toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = sdf.format(orders.getOrderTime());
            orders.setPaySuccTime(str);
            return ResponseMessage.success(orders);
        }
    }

}
