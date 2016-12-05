package com.ebeijia.quartz;

import com.ebeijia.common.CommonConstant;
import com.ebeijia.dto.RefundDto;
import com.ebeijia.entity.Orders;
import com.ebeijia.entity.OrdersDetail;
import com.ebeijia.entity.PayInfo;
import com.ebeijia.entity.RefundInfo;
import com.ebeijia.exception.ServiceException;
import com.ebeijia.pay.PaymentSDK;
import com.ebeijia.pay.RefundResult;
import com.ebeijia.service.*;
import com.ebeijia.service.impl.TemplateMessageService;
import com.ebeijia.util.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by YPJ on 2016/6/30.
 */
@Component("queryOrderQuartz")
@Lazy(false)
public class QueryOrderQuartz {

    private static Logger          log = LoggerFactory.getLogger(QueryOrderQuartz.class);

    @Resource
    private OrderService           orderService;

    @Resource
    private PayInfoService         payInfoService;

    @Resource(name = "waterFarPayService")
    private PayHandleService       waterFarPayService;

    @Resource(name = "payGasHandleBaseService")
    private PayHandleService       payGasHandleBaseService;

    @Resource
    private GeneratlorOrderService generatlorOrderService;

    @Resource
    private TemplateMessageService templateMessageService;

    @Resource
    private OrderDetailService     orderDetailService;
    @Autowired
    private RefundInfoService      refundInfoService;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void queryOrder() throws ServiceException {
        List<Orders> ordersList = orderService.selectByNotPay();
        for (Orders orders : ordersList) {
            Map<String, String> resMap = PaymentSDK.queryStateInfo(orders.getOrderNo());
            if (resMap.get("resCode").equals("0")) {
                Integer payAmt = Integer.parseInt((String) resMap.get("total_fee"));
                PayInfo payInfo = payInfoService.selectByOrderNo(orders.getOrderNo());
                if (payInfo != null) {
                    if (!CommonConstant.PayStatus.NotPay.getCode().equals(payInfo.getPayStatus())) {
                        LoggerUtil.info("已收到通知 ");
                        continue;
                    }
                    payInfo.setOldPayStatus(payInfo.getPayStatus());
                    payInfo.setTradeType(resMap.get("trade_type"));
                    payInfo.setPayResult(resMap.get("pay_result"));
                    payInfo.setPayInfo(resMap.get("pay_info"));
                    payInfo.setTransactionId(resMap.get("transaction_id"));
                    payInfo.setOutTransactionId(resMap.get("out_transaction_id"));
                    payInfo.setOutTradeNo(resMap.get("out_trade_no"));
                    String totalFee = resMap.get("total_fee");
                    String coupon = resMap.get("coupon_fee");
                    payInfo.setTotalFee(Integer.parseInt(StringUtils.isEmpty(totalFee) ? "0" : totalFee));
                    payInfo.setCouponFee(Integer.parseInt(StringUtils.isEmpty(coupon) ? "0" : coupon));
                    payInfo.setFeeType(resMap.get("fee_type"));
                    payInfo.setAttach(resMap.get("attach"));
                    payInfo.setBankType(resMap.get("bank_type"));
                    payInfo.setBankBillno(resMap.get("bank_billno"));
                    payInfo.setPayStatus(CommonConstant.PayStatus.PaySuccess.getCode());// 支付成功
                    payInfo.setPayTime(resMap.get("time_end"));
                    payInfo.setTradeState("SUCCESS");
                    payInfo.setUpdateTime(resMap.get("time_end"));
                    int temp =  payInfoService.updateByPrimaryKeySelective(payInfo);
                    if(temp>0){
                        //缴费入账
                        if (orders.getOrderType().equals("02")) {
                            waterFarPayService.payResultHandle(resMap);
                        } else {
                            payGasHandleBaseService.payResultHandle(resMap);
                        }
                    }



                }
            }else{
                log.info("订单未支付，关闭本笔订单{}",orders.getOrderNo());
                orders.setOrderStatus("04");
                orderService.updateByPrimaryKeySelective(orders);
            }

        }

    }

    //每天晚上九点执行当日订单已支付未缴费的订单进行退款
    @Scheduled(cron = "0 0 23 * * ?")
    public void RefundOrdersDay() throws ServiceException {
        log.info("定时退款任务执行成功");
        List<Orders> ordersList = orderService.selectByToDayPaySuccesss();

        for (Orders orders : ordersList) {
            //循环执行退款操作
            String typeName = "";
            RefundDto refundDto = new RefundDto();
            refundDto.setOrderId(orders.getId());
            refundDto.setOpenId(orders.getUserId());
            refundDto.setOrderNo(orders.getOrderNo());
            refundDto.setPayAmt(orders.getOrderAmt());
            String outRefundNo = generatlorOrderService.generator("serival_no");
            refundDto.setRefundSeqNo(outRefundNo);
            refundDto.setTotalAmt(orders.getOrderAmt());
            RefundResult refundResult = orderService.sentToRefund(refundDto);
            RefundInfo refundInfo = refundInfoService.findByOrderId(orders.getId());
            if (orders.getOrderType().equals("02")) {
                typeName = "远传水表";
            } else if (orders.getOrderType().equals("01")) {
                typeName = "自来水";
            } else {
                typeName = "天然气";
            }
            if (refundResult.getResult_code().equals("0")) {
                orders.setOrderStatus("05");
                BigDecimal totalFee = new BigDecimal(orders.getOrderAmt().toString());
                BigDecimal d100 = new BigDecimal(100);
                BigDecimal fee = totalFee.divide(d100, 2, 2);//小数点2位
                OrdersDetail ordersDetail = orderDetailService.selectByOrderId(orders.getId());
                templateMessageService.sendRefundMsg(orders.getUserId(), "", ordersDetail.getPayAccountNo(), fee.toString(), typeName);

                refundInfo.setRefundSts("05");
            } else {
                orders.setOrderStatus("07");

                refundInfo.setRefundSts("07");
            }
            orderService.updateByPrimaryKeySelective(orders);
            refundInfoService.updateByPrimaryKeySelective(refundInfo);
        }

    }

}
