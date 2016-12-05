package com.ebeijia.pay;

import com.ebeijia.common.CommonCodeDefine;
import com.ebeijia.util.MD5;
import com.ebeijia.util.PropertiesUtils;
import com.ebeijia.util.SignUtils;
import com.ebeijia.util.XmlUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class PaymentSDK {

    private static Logger       logger    = LoggerFactory.getLogger(PaymentSDK.class);
    private final static String version   = "2.0";
    private final static String charset   = "UTF-8";
    private final static String sign_type = "MD5";

    /**
     * 订单提交
     *
     * @param payParam 支付参数
     * @return
     */
    public static PayResult payInfo(PayParam payParam) throws Exception {
        PayResult payResult = new PayResult();

        /**
         * （out_trade_no--
         * 、body--、attach--附加信息、total_fee--总金额、mch_create_ip--终端IP）必须存在
         * time_start--订单生成时间、time_expire--订单超时时间[格式都是yyyymmddhhmmss]
         */
        try {

            SortedMap<String, String> map = new TreeMap<String, String>();// 写入集合的数据是有序的
            Map<String, String> result = new HashMap<String, String>();

            map.put("out_trade_no", payParam.getOutTradeNo());// 商户订单号
            map.put("sub_openid", payParam.getOpenId());// 用户openid/
            map.put("body", payParam.getBody());// 商品描述
            map.put("attach", payParam.getAttach());// 附加信息
            map.put("total_fee", payParam.getTotalFee());// 总金额
            map.put("mch_create_ip", "127.0.0.1");// 终端IP
            map.put("time_start", payParam.getTimeStart());// 订单生成时间
            map.put("time_expire", payParam.getTimeExpire());// 订单超时时间
            map.put("service", "pay.weixin.jspay");
            map.put("version", version);
            map.put("charset", charset);
            map.put("sign_type", sign_type);
            map.put("mch_id",payParam.getMchId());
            map.put("notify_url",payParam.getNotifyUrl());
            map.put("callback_url", payParam.getCallbackUrl());
            map.put("nonce_str", String.valueOf(new Date().getTime()));
            Map<String, String> params = SignUtils.paraFilter(map);
            StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
            SignUtils.buildPayParams(buf, params, false);
            String preStr = buf.toString();
            String sign = MD5.sign(preStr, "&key=" + payParam.getKey(), "utf-8");
            map.put("sign", sign);

            String reqUrl = PropertiesUtils.getProperties("req_url");
            CloseableHttpResponse response = null;
            CloseableHttpClient client = null;
            String res = null;
            Map<String, String> resultMap = null;
            try {
                HttpPost httpPost = new HttpPost(reqUrl);
                StringEntity entityParams = new StringEntity(XmlUtils.parseXML(map), "utf-8");
                httpPost.setEntity(entityParams);
                httpPost.setHeader("Content-Type", "text/xml;charset=ISO-8859-1");
                client = HttpClients.createDefault();
                response = client.execute(httpPost);
                logger.info("调用支付请求参数{}", XmlUtils.parseXML(map));
                if (response != null && response.getEntity() != null) {
                    resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
                    res = XmlUtils.toXml(resultMap);
                    logger.info("调用支付返回参数{}", res);
                    if (!SignUtils.checkParam(resultMap, PropertiesUtils.getProperties("key"))) {
                        res = "验证签名不通过";
                    } else {
                        if ("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))) {
                            res = "ok";
                        }
                    }
                } else {
                    res = "操作失败";
                }
            } catch (Exception e) {
                res = "系统异常";
                logger.error("================调用支付接口初始化出现异常===================", e);
            } finally {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            }

            if ("ok".equals(res)) {
                payResult.setStatus(CommonCodeDefine.ERROR_PAY_SUCCESS.getCode());
                payResult.setTokenId(resultMap.get("token_id"));
            } else {
                payResult.setStatus(CommonCodeDefine.ERROR_PAY_FAIL.getCode());
            }
            logger.info("===============支付初始化返回结果============={}：", res);

        } catch (Exception ex) {
            payResult.setStatus(CommonCodeDefine.ERROR_PAY_FAIL.getCode());
            logger.error("================调用支付接口初始化出现异常===================", ex);

        }
        return payResult;
    }

    /**
     * 支付退款接口
     *
     * @param outTradeNo
     * @param transactionId
     * @param outRefundNo
     * @param totalFee
     * @param refundFee
     */

    public static String submitRefund(String openId, String outTradeNo, String transactionId, String outRefundNo, String totalFee, String refundFee,
                                      String refundChannel) throws Exception {
        RefundResult refundResult = new RefundResult();
        logger.info("=============================支付退款接口进入=====================");
        SortedMap<String, String> map = new TreeMap<String, String>();
        map.put("out_trade_no", outTradeNo);// 商户订单号
        map.put("transaction_id", transactionId);// 威富通订单号
        map.put("out_refund_no", outRefundNo);// 商户退单单号
        map.put("total_fee", totalFee);// 总金额
        map.put("refund_fee", refundFee);// 退款金额
        map.put("op_user_id", PropertiesUtils.getProperties("mch_id"));// 默认为商户号
        /**
         * 退款渠道，目前2中 ORIGINAL-原路退款，默认BALANCE-余额
         */
        map.put("refund_channel", refundChannel);// 退款渠道
        map.put("service", "trade.single.refund");
        map.put("version", version);
        map.put("charset", charset);
        map.put("sign_type", sign_type);

        String key = PropertiesUtils.getProperties("key");
        String reqUrl = PropertiesUtils.getProperties("req_url");
        map.put("mch_id", PropertiesUtils.getProperties("mch_id"));
        map.put("nonce_str", String.valueOf(new Date().getTime()));

        Map<String, String> params = SignUtils.paraFilter(map);
        StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
        SignUtils.buildPayParams(buf, params, false);
        String preStr = buf.toString();
        String sign = MD5.sign(preStr, "&key=" + key, "utf-8");
        map.put("sign", sign);
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        String res = null;
        Map<String, String> resultMap = null;
        try {
            HttpPost httpPost = new HttpPost(reqUrl);
            StringEntity entityParams = new StringEntity(XmlUtils.parseXML(map), "utf-8");
            httpPost.setEntity(entityParams);
            httpPost.setHeader("Content-Type", "text/xml;charset=ISO-8859-1");
            client = HttpClients.createDefault();
            response = client.execute(httpPost);
            logger.info("调用退款请求参数{}", XmlUtils.parseXML(map));
            if (response != null && response.getEntity() != null) {
                resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
                res = XmlUtils.toXml(resultMap);

                logger.info("================退款请求结果=================" + res);
                if (!SignUtils.checkParam(resultMap, key)) {
                    res = "验证签名不通过";
                }
            } else {
                res = "操作失败!";
            }
        } catch (Exception e) {
            res = "操作失败";
        } finally {
            if (response != null) {
                response.close();
            }
            if (client != null) {
                client.close();
            }
        }
        if (res.startsWith("<")) {
            refundResult.setResultCode("0");
            refundResult.setMessage("操作成功");
        } else {
            refundResult.setResultCode("500");
            refundResult.setMessage(res);
        }
        ObjectMapper om = new ObjectMapper();
        om.setDateFormat(new SimpleDateFormat("yyyyMMddHH:mm:ss"));
        String json = om.writeValueAsString(resultMap);
        logger.info("===================退款接口返回结果=======================" + json);
        return json;
    }

    /**
     * 查询订单信息
     * @param outTradeNo
     * @return
     */

    public static Map<String, String> queryStateInfo(String outTradeNo) {
        Map<String, String> resMap = new HashMap<String, String>();

        try {
            Map<String, String> resultMap = new HashMap<String, String>();
            logger.info("=============================查询接口进入=====================");
            String nonce_str = String.valueOf(new Date().getTime());
            SortedMap<String, String> map = new TreeMap<String, String>();
            map.put("service", "trade.single.query");
            map.put("mch_id", PropertiesUtils.getProperties("mch_id"));
            map.put("out_trade_no", outTradeNo);
            map.put("nonce_str", nonce_str);
            map.put("version", version);
            map.put("charset", charset);
            map.put("sign_type", sign_type);

            Map<String, String> params = SignUtils.paraFilter(map);
            StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
            SignUtils.buildPayParams(buf, params, false);
            String preStr = buf.toString();
            String sign = MD5.sign(preStr, "&key=" + PropertiesUtils.getProperties("key"), "utf-8");
            map.put("sign", sign);
            String reqUrl = PropertiesUtils.getProperties("req_url");
            CloseableHttpResponse response = null;
            CloseableHttpClient client = null;
            String res = null;
            try {
                HttpPost httpPost = new HttpPost(reqUrl);
                StringEntity entityParams = new StringEntity(XmlUtils.parseXML(map), "utf-8");
                httpPost.setEntity(entityParams);
                httpPost.setHeader("Content-Type", "text/xml;charset=ISO-8859-1");
                client = HttpClients.createDefault();
                response = client.execute(httpPost);
                logger.info("请求参数{}", XmlUtils.parseXML(map));
                if (response != null && response.getEntity() != null) {
                    resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
                    res = XmlUtils.toXml(resultMap);
                    logger.info("================查询结果=================" + res);
                    if (!SignUtils.checkParam(resultMap, PropertiesUtils.getProperties("key"))) {
                        res = "验证签名不通过";
                    } else {
                        if ("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))
                            && "SUCCESS".equals(resultMap.get("trade_state"))) {
                            res = "ok";
                            logger.info("SUCCESS : " + outTradeNo);
                        }
                    }
                } else {
                    res = "操作失败";
                }
            } catch (Exception e) {
                res = "系统异常";
            } finally {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            }
            if ("ok".equals(res)) {
                resMap.put("resCode", "0");
                resMap.put("trade_type", resultMap.get("trade_type"));
                resMap.put("openid", resultMap.get("openid"));
                resMap.put("is_subscribe", resultMap.get("is_subscribe"));
                resMap.put("transaction_id", resultMap.get("transaction_id"));
                resMap.put("out_trade_no", resultMap.get("out_trade_no"));
                resMap.put("total_fee", resultMap.get("total_fee"));
                resMap.put("coupon_fee", resultMap.get("coupon_fee"));
                resMap.put("fee_type", resultMap.get("fee_type"));
                resMap.put("attach", resultMap.get("attach"));
                resMap.put("bank_type", resultMap.get("bank_type"));
                resMap.put("bank_billno", resultMap.get("bank_billno"));
                resMap.put("time_end", resultMap.get("time_end"));
            } else {
                resMap.put("resCode", "-1");
            }
        } catch (Exception ex) {
            resMap.put("resCode", "-1");
        }
        logger.info("===================查询接口结束=======================");
        return resMap;
    }

}
