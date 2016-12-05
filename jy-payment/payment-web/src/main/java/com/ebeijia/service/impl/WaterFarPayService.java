package com.ebeijia.service.impl;

import com.ebeijia.api.socket.WaterFarPayApi;
import com.ebeijia.dto.OrderResultDto;
import com.ebeijia.dto.RefundDto;
import com.ebeijia.dto.socketModels.WaterPayReq;
import com.ebeijia.dto.socketModels.WaterPayRes;
import com.ebeijia.entity.*;
import com.ebeijia.mapper.*;
import com.ebeijia.pay.PayParam;
import com.ebeijia.pay.PayResult;
import com.ebeijia.pay.RefundResult;
import com.ebeijia.service.GeneratlorOrderService;
import com.ebeijia.service.PayHandleService;
import com.ebeijia.util.FastJson;
import com.ebeijia.util.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by LiYan on 2016/9/27.
 */
@Service("waterFarPayService")
public class WaterFarPayService extends OrderBaseService implements PayHandleService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaskInfoMapper taskInfoMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrdersDetailMapper ordersDetailMapper;
    @Autowired
    private PayInfoMapper payInfoMapper;
    @Autowired
    private RefundInfoMapper refundInfoMapper;

    @Resource
    private GeneratlorOrderService generatlorOrderService;

    @Resource
    private TemplateMessageService templateMessageService;

    @Override
    @Transactional
    public OrderResultDto toAuthPay(Orders orders, OrdersDetail ordersDetail) throws Exception {
        OrderResultDto orderResultDto = new OrderResultDto();
        if (super.addOrder(orders) > 0) {
            //插入订单详细

            Orders ordersN=ordersMapper.selectByOrderNo(orders.getOrderNo());
            ordersDetail.setOrderId(ordersN.getId());
            super.addOrderDetail(ordersDetail);

            //添加微信支付的相关参数
            PayParam payParam = new PayParam();
            payParam.setOutTradeNo(ordersN.getOrderNo());
            payParam.setOpenId(ordersN.getUserId());
            payParam.setTotalFee(ordersN.getOrderAmt().toString());
            payParam.setBody("江油缴费");
            payParam.setMchId(PropertiesUtils.getProperties("mch_id"));
            payParam.setKey(PropertiesUtils.getProperties("key"));
            payParam.setNotifyUrl(PropertiesUtils.getProperties("notify_url"));
            String callBack =PropertiesUtils.getProperties("callback_url")+"?orderNo="+ordersN.getOrderNo();
            payParam.setCallbackUrl(callBack);
            logger.info("payparm参数是"+FastJson.toJson(payParam));
            PayResult payResult = super.sentToPay(ordersN, payParam);
            logger.info("调用支付成功{}", FastJson.toJson(payResult));
            orderResultDto.setSentStatus(payResult.getStatus());
            orderResultDto.setTokeId(payResult.getTokenId());

        }
        return orderResultDto;
    }

    @Override
    @Transactional
    public String payResultHandle(Map<String, String> resultMap) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = new HashMap<>();
        map = super.payResultCode(resultMap);
        //默认失败
        String status = resultMap.get("result_code");
        String orderNo = resultMap.get("out_trade_no");
        Orders orders = ordersMapper.selectByOrderNo(orderNo);
        if(!"02".equals(orders.getOrderStatus())){
            logger.info("已经回调成功，此次不做任何处理");
            return "0";
        }
        OrdersDetail ordersDetail=ordersDetailMapper.selectByOrderId(orders.getId());
        //支付成功 更新订单表状态
        orders.setPaySuccTime(resultMap.get("time_end"));
        orders.setOrderStatus("03");
        try{
            PayInfo payInfo=payInfoMapper.selectByOrderNo(orderNo);
            //支付已经成功 现在去调用第三方接口缴费
            WaterPayReq req = new WaterPayReq();
            req.setUserNo(ordersDetail.getPayAccountNo());
            BigDecimal orderAmt=new BigDecimal(orders.getOrderAmt()).divide(new BigDecimal(100));
            String amt=orderAmt.toString();
            req.setFunctionCode("2001");
            req.setAmt(amt);
            req.setUserNo(ordersDetail.getPayAccountNo());
            req.setThridPayment(orderNo);
            req.setTerminalCode(PropertiesUtils.getProperties("socket.terminalCode"));
            req.setCommand(PropertiesUtils.getProperties("socket.command"));
            WaterPayRes res = WaterFarPayApi.payWaterBill(req);
            if (null == res) {
                //第三方接口无响应，插入异步任务表
                TaskInfo taskInfo = new TaskInfo();
                String taskNo=generatlorOrderService.generator("serival_no");
                taskInfo.setTaskNo(taskNo);
                taskInfo.setTaskServerNo(orderNo);
                taskInfo.setTaskResult("02");
                taskInfo.setTaskRetryCount(0);
                taskInfoMapper.insertSelective(taskInfo);

            }
            if ("1".equals(res.getResCode())) {
                ordersDetail.setSentResult("01");
                ordersDetail.setSerialNumber(res.getCopyPayment());
                ordersDetailMapper.updateByPrimaryKeySelective(ordersDetail);
                BigDecimal totalFee = new BigDecimal(resultMap.get("total_fee"));
                BigDecimal d100 = new BigDecimal(100);
                BigDecimal fee = totalFee.divide(d100,2,2);//小数点2位

                //推送缴费详细信息
                templateMessageService.sendPaymentSuccessMsg(orders.getUserId(),"",ordersDetail.getPayAccountNo(),simpleDateFormat.format(new Date()),"微信支付",fee.toString());
            }else{
                //第三方缴费失败 退款
                RefundDto refundDto=new RefundDto();
                ordersDetail.setSentResult("02");
                refundDto.setOrderId(orders.getId());
                refundDto.setOpenId(orders.getUserId());
                refundDto.setOrderNo(orderNo);
                refundDto.setPayAmt(Long.parseLong(resultMap.get("total_fee")));
                refundDto.setPaySeqNo(resultMap.get("transaction_id"));
                String outRefundNo= generatlorOrderService.generator("order_no");
                refundDto.setRefundSeqNo(outRefundNo);
                refundDto.setTotalAmt(Long.parseLong(resultMap.get("total_fee")));
                RefundResult refundResult=sentToRefund(refundDto);

                RefundInfo refundInfo=refundInfoMapper.selectByOrderId(orders.getId());
                if(refundResult.getResult_code().equals("0")){
                    orders.setOrderStatus("05");
                    BigDecimal totalFee = new BigDecimal(resultMap.get("total_fee"));
                    BigDecimal d100 = new BigDecimal(100);
                    BigDecimal fee = totalFee.divide(d100,2,2);//小数点2位
                    templateMessageService.sendRefundMsg(orders.getUserId(),"",ordersDetail.getPayAccountNo(),fee.toString(),"远传水表");

                    refundInfo.setRefundSts("05");
                }else{
                    orders.setOrderStatus("07");

                    refundInfo.setRefundSts("07");
                }
                refundInfoMapper.updateByPrimaryKeySelective(refundInfo);
            }

            ordersMapper.updateByPrimaryKeySelective(orders);
            // 支付失败无需任何处理

            // 标示接受回调处理成功
            status = "0";
            return status;
        }catch (Exception e){
            logger.error("");
        }
        return status;
    }

}
