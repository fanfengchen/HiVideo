package com.ebeijia.service.impl;

import com.ebeijia.common.CommonCodeDefine;
import com.ebeijia.common.CommonConstant;
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
import com.ebeijia.pay.PaymentSDK;
import com.ebeijia.pay.RefundResult;
import com.ebeijia.util.DateUtil;
import com.ebeijia.util.FastJson;
import com.ebeijia.util.PropertiesUtils;
import com.ebeijia.util.SignUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YJ on 2016/9/21.
 */
@Service
public class OrderBaseService {

    private Logger        logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OrdersMapper  ordersMapper;
    @Resource
    private PayInfoMapper payInfoMapper;
    @Resource
    private OrdersDetailMapper ordersDetailMapper;
    @Autowired
    private RefundInfoMapper refundInfoMapper;

    public int addOrder(Orders orders) {
        return ordersMapper.insertSelective(orders);
    }

    public int addOrderDetail(OrdersDetail ordersDetail){
        return ordersDetailMapper.insertSelective(ordersDetail);
    }

    public Map<String, Object> payResultCode(Map<String, String> resultMap) {
        Map<String, Object> map = new HashMap<>();
        String status = "1";
        map.put("status", status);
        if (resultMap.containsKey("sign")) {
            // 验证签名失败直接返回
            if (!SignUtils.checkParam(resultMap, PropertiesUtils.getProperties("key"))) {

                return map;
            }

            if (!"0".equals(resultMap.get("status"))) {
                return map;
            }

            String resultCode = resultMap.get("result_code");

            // 支付成功
            if ("0".equals(resultCode)) {
                String orderNo = resultMap.get("out_trade_no");
                PayInfo payInfo = payInfoMapper.selectByOrderNo(orderNo);
                if (payInfo != null) {
                    if (!CommonConstant.PayStatus.NotPay.getCode().equals(payInfo.getPayStatus())) {
                        logger.info("已收到通知 ");
                        map.put("status", "0");
                        return map;
                    }
                    payInfo.setOldPayStatus(CommonConstant.PayStatus.NotPay.getCode());
                    payInfo.setTradeType(resultMap.get("trade_type"));
                    payInfo.setPayResult(resultMap.get("pay_result"));
                    payInfo.setPayInfo(resultMap.get("pay_info"));
                    payInfo.setTransactionId(resultMap.get("transaction_id"));
                    payInfo.setOutTransactionId(resultMap.get("out_transaction_id"));
                    payInfo.setOutTradeNo(resultMap.get("out_trade_no"));
                    String totalFee = resultMap.get("total_fee");
                    String coupon = resultMap.get("coupon_fee");
                    payInfo.setTotalFee(Integer.parseInt(StringUtils.isEmpty(totalFee) ? "0" : totalFee));
                    payInfo.setCouponFee(Integer.parseInt(StringUtils.isEmpty(coupon) ? "0" : coupon));
                    payInfo.setFeeType(resultMap.get("fee_type"));
                    payInfo.setAttach(resultMap.get("attach"));
                    payInfo.setBankType(resultMap.get("bank_type"));
                    payInfo.setBankBillno(resultMap.get("bank_billno"));
                    payInfo.setPayStatus(CommonConstant.PayStatus.PaySuccess.getCode());// 支付成功
                    payInfo.setPayTime(resultMap.get("time_end"));
                    payInfo.setTradeState("SUCCESS");
                    payInfo.setUpdateTime(resultMap.get("time_end"));
                    int upLen = payInfoMapper.updateByPrimaryKeySelective(payInfo);
                    map.put("status", "0");
                    map.put("upLen", upLen);
                }


            }
        }
        return map;
    }

    @Transactional
    public PayResult sentToPay(Orders orders, PayParam payParam) throws ServiceException {
        try {
            PayInfo payInfo = new PayInfo();
            payInfo.setOutTradeNo(orders.getOrderNo());
            Date curDate = new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
            String createTime=sdf.format(curDate);
            payInfo.setCreateTime(createTime);
            payInfo.setUpdateTime(createTime);
            payInfo.setOrderId(orders.getId());
            payInfo.setTotalFee(orders.getOrderAmt().intValue());
            payInfo.setPayStatus(CommonConstant.PayStatus.NotPay.getCode());// 待支付
            payInfoMapper.insertSelective(payInfo);
            PayResult payResult = PaymentSDK.payInfo(payParam);
            if (CommonCodeDefine.ERROR_PAY_FAIL.equals(payResult.getStatus())) {
                payInfo.setPayStatus(CommonConstant.PayStatus.PayUnPay.getCode());
                payInfo.setUpdateTime(sdf.format(new Date()));
                payInfoMapper.updateByPrimaryKeySelective(payInfo);
            }
            if(payResult!=null){
                //更新订单状态为支付中
                Map<String,Object> map = new HashMap<>();
                map.put("orderNo",orders.getOrderNo());
                map.put("orderStatus","02");
                ordersMapper.updateOrderStatus(map);
            }

            return payResult;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     *
     * @param refundDto
     * @return 退款方法
     * @throws ServiceException
     */
    @Transactional
    public RefundResult sentToRefund(RefundDto refundDto)
            throws ServiceException {
        try {
            PayInfo payInfo = payInfoMapper.selectByOrderNo(refundDto.getOrderNo());
            //创建退款记录
            RefundInfo refundInfo=new RefundInfo();
            refundInfo.setOrderId(refundDto.getOrderId());
            refundInfo.setRefundAmt(refundDto.getTotalAmt().intValue());
            refundInfo.setRefundNo(refundDto.getRefundSeqNo());
            refundInfo.setRefundReason("缴费失败");
            refundInfo.setRefundSts("06");
            refundInfoMapper.insertSelective(refundInfo);

            String refundResult = PaymentSDK.submitRefund(refundDto.getOpenId(),
                    refundDto.getOrderNo(),
                    refundDto.getPaySeqNo(),
                    refundDto.getRefundSeqNo(),
                    refundDto.getTotalAmt().toString(),
                    refundDto.getPayAmt().toString(), null);
            RefundResult result = FastJson.fromJson(refundResult, RefundResult.class);
            if (result.getResult_code().equals("0")) {
                payInfo.setTransactionId(result.getTransaction_id());
                payInfo.setOutRefundNo(result.getOut_refund_no());// 退款单号
                payInfo.setRefundId(result.getRefund_id());// 支付返回的
                payInfo.setRefundChannel(result.getRefund_channel());// 退款渠道
                if (result.getRefund_fee() != null) {
                    payInfo.setRefundFee(Integer.parseInt(result.getRefund_fee()));// 退款金额
                }
                if (result.getCoupon_refund_fee() != null) {

                    payInfo.setCouponRefundFee(Integer.parseInt(result.getCoupon_refund_fee()));
                }

                payInfo.setPayStatus(CommonConstant.PayStatus.RefundSuccess.getCode());
                payInfo.setRefundTime(DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"));
                payInfo.setRefundStatus("SUCCES");


            } else {
                payInfo.setPayStatus(CommonConstant.PayStatus.RefundFail.getCode());// 退款失败
                payInfo.setRefundStatus("FAIL");
                payInfo.setRemark(result.getMessage());
            }
            payInfoMapper.updateByPrimaryKeySelective(payInfo);
            return result;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
