package com.ebeijia.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.ebeijia.service.ITemplateMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage.WxArticle;
import me.chanjar.weixin.mp.bean.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.WxMpTemplateMessage;

/**
 * Created by zb on 2015/6/24.
 */

@Service
public class TemplateMessageService implements ITemplateMessageService {

    @Autowired
    private WxMpService wxMpService;

    private String      paymentTemplateId;

    private String      paymentTemplateFirst;

    private String      paymentTemplateRemark;

    private String      refundTemplateId;

    private String      refundTemplateFirst;

    private String      refundTemplateRemark;

    private String      billTemplateId;

    private String      billTemplateFirst;

    private String      billTemplateRemark;

    final Logger        logger = LoggerFactory.getLogger(this.getClass());

    public TemplateMessageService() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("global");
        this.paymentTemplateId = resourceBundle.getString("template.msg.payment.id");
        this.paymentTemplateFirst = resourceBundle.getString("template.msg.payment.success.first");
        this.paymentTemplateRemark = resourceBundle.getString("template.msg.payment.success.remark");

        this.refundTemplateId = resourceBundle.getString("template.msg.refund.id");
        this.refundTemplateFirst = resourceBundle.getString("template.msg.refund.first");
        this.refundTemplateRemark = resourceBundle.getString("template.msg.refund.remark");

        this.billTemplateId = resourceBundle.getString("template.msg.bill.id");
        this.billTemplateFirst = resourceBundle.getString("template.msg.bill.first");
        this.billTemplateRemark = resourceBundle.getString("template.msg.bill.remark");

    }

    @Override
    public void sendPaymentSuccessMsg(String openId, String url,String remark, String payTime, String payType, String payFee) {
        try {
            logger.info("发送缴费成功模板消息：获取到的消息地址：" + url + " openId:" + openId + " templateId:" + paymentTemplateId);
            WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
            templateMessage.setToUser(openId);
            templateMessage.setTemplateId(paymentTemplateId);
            templateMessage.setUrl(url);
            List<WxMpTemplateData> datas = new ArrayList<WxMpTemplateData>();
            datas.add(new WxMpTemplateData("first", paymentTemplateFirst.replaceAll("remark", remark)));
            datas.add(new WxMpTemplateData("JFSJ", payTime));
            datas.add(new WxMpTemplateData("JFFS", payType));
            datas.add(new WxMpTemplateData("JFJE", payFee));
            datas.add(new WxMpTemplateData("REMARK", paymentTemplateRemark));
            templateMessage.setDatas(datas);
            String msgId = wxMpService.templateSend(templateMessage);

            logger.info("发送模板消息结束，返回消息id:" + msgId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    @Override
    public void sendRefundMsg(String openId,String url,String userNo, String payFee, String remark ) {

        try {
            logger.info("发送退款模板消息：获取到的消息地址：" + url + " openId:" + openId + " templateId:" + refundTemplateId);
            WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
            templateMessage.setToUser(openId);
            templateMessage.setTemplateId(refundTemplateId);
            templateMessage.setUrl(url);
            List<WxMpTemplateData> datas = new ArrayList<WxMpTemplateData>();
            datas.add(new WxMpTemplateData("first", refundTemplateFirst));
            datas.add(new WxMpTemplateData("keyword1", userNo));
            datas.add(new WxMpTemplateData("keyword2", payFee));
            datas.add(new WxMpTemplateData("remark", refundTemplateRemark.replaceAll("remark", remark)));
            templateMessage.setDatas(datas);

            String msgId = wxMpService.templateSend(templateMessage);

            logger.info("发送模板消息结束，返回消息id:" + msgId);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

    }

    @Override
    public void sendNewsMsg(String openId, WxArticle wxArticle) {
        try {
            WxMpCustomMessage message = WxMpCustomMessage.NEWS().toUser(openId).addArticle(wxArticle).build();
            wxMpService.customMessageSend(message);
        } catch (WxErrorException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     *
     *
     * 账单推送模板消息
     * @param openId
     * @param userName
     * @param userNo
     * @param money
     * @param date
     * @param url
     * @param templateUnpaidBillRemark
     */

    @Override
    public String sendUnpaidBillMsg(String type, String openId, String userName, String userNo, String money, String date, String url,
                                    String templateUnpaidBillRemark) {
        String msgId = null;
        try {
            WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
            templateMessage.setToUser(openId);
            templateMessage.setTemplateId(billTemplateId);
            templateMessage.setUrl(url);
            List<WxMpTemplateData> datas = new ArrayList<WxMpTemplateData>();
            datas.add(new WxMpTemplateData("first", billTemplateFirst));
            datas.add(new WxMpTemplateData("keyword1", type));
            datas.add(new WxMpTemplateData("keyword2", userName));
            datas.add(new WxMpTemplateData("keyword3", userNo));
            datas.add(new WxMpTemplateData("keyword4", money));
            datas.add(new WxMpTemplateData("keyword5", date));
            datas.add(new WxMpTemplateData("remark", billTemplateRemark.replaceAll("remark", templateUnpaidBillRemark)));
            templateMessage.setDatas(datas);
            msgId = wxMpService.templateSend(templateMessage);
        } catch (Exception e) {
            logger.error("向用户发送未缴费账单异常:", e);
        }
        return msgId;
    }

    @Override
    public void sendTextMsg(String openId, String msg) {
        try {
            WxMpCustomMessage message = WxMpCustomMessage.TEXT().toUser(openId).content(msg).build();
            wxMpService.customMessageSend(message);
        } catch (WxErrorException e) {
            logger.error(e.getMessage(), e);

        }

    }
}
