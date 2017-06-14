package com.ebeijia.entity.vo.wechat.req

/**
 * BaseMessage消息基类（普通用户 -> 公众帐号）
 * @author zhicheng.xu
 *         15/8/12
 */
class BaseMessage {
  // 开发者微信号
  private var ToUserName: String = null
  // 发送方帐号（一个OpenID）
  private var FromUserName: String = null
  // 消息创建时间 （整型）
  private var CreateTime: Long = 0L
  // 消息类型（text/image/location/link）
  private var MsgType: String = null
  // 消息id，64位整型
  private var MsgId: Long = 0L

  def getToUserName: String = {
    ToUserName
  }

  def setToUserName(toUserName: String) {
    ToUserName = toUserName
  }

  def getFromUserName: String = {
    FromUserName
  }

  def setFromUserName(fromUserName: String) {
    FromUserName = fromUserName
  }

  def getCreateTime: Long = {
    CreateTime
  }

  def setCreateTime(createTime: Long) {
    CreateTime = createTime
  }

  def getMsgType: String = {
    MsgType
  }

  def setMsgType(msgType: String) {
    MsgType = msgType
  }

  def getMsgId: Long = {
    MsgId
  }

  def setMsgId(msgId: Long) {
    MsgId = msgId
  }
}

