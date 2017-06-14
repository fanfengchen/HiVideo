package com.ebeijia.entity.wechat.base

import javax.persistence.{Column, Embeddable}

@Embeddable
@SerialVersionUID(1L)
class TblWechatRespMsgId extends java.io.Serializable{
  private var respMsgId: String = null
  private var mchtId: String = null

  @Column(name = "RESP_MSG_ID", length = 8) def getRespMsgId: String = {
    respMsgId
  }

  def setRespMsgId(respMsgId: String) {
    this.respMsgId = respMsgId
  }

  @Column(name = "MCHT_ID", length = 8) def getMchtId: String = {
    mchtId
  }

  def setMchtId(mchtId: String) {
    this.mchtId = mchtId
  }


  def canEqual(other: Any): Boolean = other.isInstanceOf[TblWechatRespMsgId]

  override def equals(other: Any): Boolean = other match {
    case that: TblWechatRespMsgId =>
      (that canEqual this) &&
        respMsgId == that.respMsgId &&
        mchtId == that.mchtId
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(respMsgId, mchtId)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
