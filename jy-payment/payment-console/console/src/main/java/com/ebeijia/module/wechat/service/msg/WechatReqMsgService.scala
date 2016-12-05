package com.ebeijia.module.wechat.service.msg

import java.util.Map

import com.ebeijia.entity.wechat.base.TblWechatReqMsg


/**
 * WechatReqMsgService
 * @author zhicheng.xu
 *         15/8/14
 */


trait WechatReqMsgService {
  //验证msgId不重复
  def findByMsgId(msgId: String): TblWechatReqMsg
  //保存微信收到的消息
  def save(data: TblWechatReqMsg)
  //分页查询
  def findBySql(mchtId: String, aoData: String): Map[String, AnyRef]
}
