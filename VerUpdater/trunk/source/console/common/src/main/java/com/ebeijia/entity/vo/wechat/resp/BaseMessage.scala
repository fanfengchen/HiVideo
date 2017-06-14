package com.ebeijia.entity.vo.wechat.resp

/**
 * BaseMessage消息基类（公众帐号 -> 普通用户）
 * @author zhicheng.xu
 *         15/8/12
 */
class BaseMessage {

  // 接收方帐号（收到的OpenID）
  private var ToUserName: String = null
  // 开发者微信号
  private var FromUserName: String = null
  // 消息创建时间 （整型）
  private var CreateTime: Long = 0L
  // 消息类型（text/music/news）
  private var MsgType: String = null
  // 位0x0001被标志时，星标刚收到的消息
  private var FuncFlag: Int = 0

  def getToUsrName: String = {
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

  def getFuncFlag: Int = {
    FuncFlag
  }

  def setFuncFlag(funcFlag: Int) {
    FuncFlag = funcFlag
  }
}

