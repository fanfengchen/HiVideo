package com.ebeijia.service.impl;

import com.ebeijia.dto.OrderResultDto;
import com.ebeijia.dto.RefundDto;
import com.ebeijia.entity.Orders;
import com.ebeijia.entity.OrdersDetail;
import com.ebeijia.entity.PayInfo;
import com.ebeijia.entity.RefundInfo;
import com.ebeijia.exception.ServiceException;
import com.ebeijia.mapper.OrdersDetailMapper;
import com.ebeijia.mapper.OrdersMapper;
import com.ebeijia.mapper.PayInfoMapper;
import com.ebeijia.mapper.RefundInfoMapper;
import com.ebeijia.pay.PayParam;
import com.ebeijia.pay.PayResult;
import com.ebeijia.pay.RefundResult;
import com.ebeijia.service.GeneratlorOrderService;
import com.ebeijia.service.PayHandleService;
import com.ebeijia.util.DateUtil;
import com.ebeijia.util.FastJson;
import com.ebeijia.util.PropertiesUtils;
import com.ebeijia.wsdl.pay.WsServiceServlet;
import com.ebeijia.wsdl.pay.WsServiceServletServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YJ on 2016/9/21.
 */
@Service("payGasHandleBaseService")
public class PayGasHandleBaseService extends OrderBaseService implements PayHandleService {

    private Logger                 logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OrdersMapper           ordersMapper;
    @Autowired
    private OrdersDetailMapper     ordersDetailMapper;
    @Autowired
    private PayInfoMapper          payInfoMapper;
    @Autowired
    private RefundInfoMapper refundInfoMapper;
    @Autowired
    private OrderBaseService       orderBaseService;
    @Resource
    private GeneratlorOrderService generatlorOrderService;

    @Resource
    private TemplateMessageService templateMessageService;

    @Transactional
    @Override
    public OrderResultDto toAuthPay(Orders orders, OrdersDetail ordersDetail) throws Exception {

        OrderResultDto orderResultDto = new OrderResultDto();
        if (super.addOrder(orders) > 0) {
            //插入订单详细
            Orders ordersN = ordersMapper.selectByOrderNo(orders.getOrderNo());
            ordersDetail.setOrderId(ordersN.getId());
            super.addOrderDetail(ordersDetail);

            PayParam payParam = new PayParam();
            //添加微信支付的相关参数
            payParam.setOutTradeNo(ordersN.getOrderNo());
            payParam.setOpenId(ordersN.getUserId());
            payParam.setTotalFee(ordersN.getOrderAmt().toString());
            payParam.setBody("江油缴费");
            payParam.setMchId(PropertiesUtils.getProperties("mch_id"));
            payParam.setKey(PropertiesUtils.getProperties("key"));
            payParam.setNotifyUrl(PropertiesUtils.getProperties("notify_url"));
            String callBack = PropertiesUtils.getProperties("callback_url") + "?orderNo=" + ordersN.getOrderNo();
            payParam.setCallbackUrl(callBack);

            PayResult payResult = super.sentToPay(ordersN, payParam);
            logger.info("调用支付成功{}", FastJson.toJson(payResult));
            orderResultDto.setSentStatus(payResult.getStatus());
            orderResultDto.setTokeId(payResult.getTokenId());
        }
        return orderResultDto;
    }

    @Transactional
    @Override
    public String payResultHandle(Map<String, String> resultMap) throws ServiceException {
        Map<String, Object> map = new HashMap<>();
        map = super.payResultCode(resultMap);
        String status = resultMap.get("result_code");
        String orderNo = resultMap.get("out_trade_no");
        Orders orders = ordersMapper.selectByOrderNo(orderNo);
        if(!orders.getOrderStatus().equals("02")){
            //已经回调成功此次不做任何处理  直接返回
            logger.info("已经回调成功，此次不做任何处理");
            return "0";
        }
            orders.setPaySuccTime(resultMap.get("time_end"));
            orders.setOrderStatus("03");
            //支付已经成功 现在去调用第三方接口
            try {
                String type = orders.getOrderType();
                String typeName = "";
                if(type.equals("01")){
                    typeName="自来水";
                }else{
                    typeName="天然气";
                }
                OrdersDetail ordersDetail = ordersDetailMapper.selectByOrderId(orders.getId());
                PayInfo payInfo = payInfoMapper.selectByOrderNo(orderNo);

                WsServiceServlet ws = new WsServiceServletServiceLocator().getyxws();
                String time = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
                String date = DateUtil.formatDate(new Date(), "yyyyMMdd");
                //生成流水号
                String a = generatlorOrderService.generator("serival_no");
                String b = a.substring(a.length() - 5, a.length());
                String payType = PropertiesUtils.getProperties("web_payType");
                BigDecimal orderAmt = new BigDecimal(orders.getOrderAmt()).divide(new BigDecimal(100));
                String amt = orderAmt.toString();
                String flowNo = payType + date + b;
                logger.info("缴费请求报文体{}" + ordersDetail.getPayAccountNo() + "流水号" + flowNo + "金额" + amt + "时间" + time + "类型" + payType);
                String str = ws.interfacePayTrans(ordersDetail.getPayAccountNo(), flowNo,PropertiesUtils.getProperties("web_operatorId"), amt, time, "1", payType);
                logger.info("缴费返回报文体{}", str);
                if ("1002".equals(str)) {
                    //入账失败 退款
                    logger.info("缴费入失败，订单号{}{}", FastJson.toJson(map), orderNo);
                    RefundDto refundDto = new RefundDto();
                    refundDto.setOrderId(orders.getId());
                    refundDto.setOpenId(orders.getUserId());
                    refundDto.setOrderNo(orders.getOrderNo());
                    refundDto.setPayAmt(Long.parseLong(resultMap.get("total_fee")));
                    refundDto.setPaySeqNo(payInfo.getTransactionId());
                    String outRefundNo = generatlorOrderService.generator("order_no");
                    refundDto.setRefundSeqNo(outRefundNo);
                    refundDto.setTotalAmt(Long.parseLong(resultMap.get("total_fee")));
                    RefundResult refundResult = orderBaseService.sentToRefund(refundDto);

                    RefundInfo refundInfo=refundInfoMapper.selectByOrderId(orders.getId());
                    if (refundResult.getResult_code().equals("0")) {
                        orders.setOrderStatus("05");
                        BigDecimal totalFee = new BigDecimal(resultMap.get("total_fee"));
                        BigDecimal d100 = new BigDecimal(100);

                        BigDecimal fee = totalFee.divide(d100,2,2);//小数点2位
                        templateMessageService.sendRefundMsg(orders.getUserId(),"",ordersDetail.getPayAccountNo(),fee.toString(),typeName);
                        refundInfo.setRefundSts("05");
                        ordersDetail.setSentResult("02");
                    } else {
                        orders.setOrderStatus("07");

                        refundInfo.setRefundSts("07");
                    }
                    refundInfoMapper.updateByPrimaryKeySelective(refundInfo);
                }else{
                    //入账成功 更新表
                    logger.info("缴费入账成功,订单号{}{}", FastJson.toJson(map), orderNo);
                    ordersDetail.setSentResult("01");
                    BigDecimal totalFee = new BigDecimal(resultMap.get("total_fee"));
                    BigDecimal d100 = new BigDecimal(100);
                    BigDecimal fee = totalFee.divide(d100,2,2);//小数点2位
                    //推送缴费详细信息
                    templateMessageService.sendPaymentSuccessMsg(orders.getUserId(),"",ordersDetail.getPayAccountNo(),time,"微信支付",fee.toString());
                }
                ordersMapper.updateByPrimaryKey(orders);
                ordersDetailMapper.updateByPrimaryKeySelective(ordersDetail);
            } catch (Exception e) {
                throw new ServiceException(e);
            }


        // 支付失败无需任何处理

        // 标示接受回调处理成功
        status = "0";

        return status;
    }

}
