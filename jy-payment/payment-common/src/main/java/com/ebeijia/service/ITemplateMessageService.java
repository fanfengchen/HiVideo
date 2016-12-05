package com.ebeijia.service;

import me.chanjar.weixin.mp.bean.WxMpCustomMessage;

/**
 */
public interface ITemplateMessageService {

	/**
	 * 发送 缴费成功 模板消息
	 *
	 * @param openId
	 */
	void sendPaymentSuccessMsg(String openId, String url,String remark, String payTime, String payType, String payFee);

	/**
	 * 发送 文本消息
	 *
	 * @param openId
	 * @param msg
	 */
	void sendTextMsg(String openId, String msg);

	/**
	 * 发送退款  模板消息
	 * @param openId
	 */
	void sendRefundMsg(String openId,String url,String userNo, String payFee, String remark );

	/**
	 * 发送图文消息
	 *
	 * @param openId
	 */
	void sendNewsMsg(String openId, WxMpCustomMessage.WxArticle wxArticle);

	/**
	 * 发送未交费账单提醒
	 * 

	 */
	String  sendUnpaidBillMsg(String type, String openId, String userName,
						   String userNo, String money, String date, String url,
						   String templateUnpaidBillRemark);

}