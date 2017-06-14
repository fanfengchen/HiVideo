package com.ebeijia.entity.wechat.base

import javax.persistence.{Column, Embeddable}

@Embeddable
@SerialVersionUID(1L)
class TblWechatReqMsgId extends java.io.Serializable{
  private var reqMsgId: String = null
  private var mchtId: String = null

  @Column(name = "REQ_MSG_ID", length = 8) def getReqMsgId: String = {
    reqMsgId
  }

  def setReqMsgId(reqMsgId: String) {
    this.reqMsgId = reqMsgId
  }

  @Column(name = "MCHT_ID", length = 8) def getMchtId: String = {
    mchtId
  }

  def setMchtId(mchtId: String) {
    this.mchtId = mchtId
  }


  def canEqual(other: Any): Boolean = other.isInstanceOf[TblWechatReqMsgId]

  override def equals(other: Any): Boolean = other match {
    case that: TblWechatReqMsgId =>
      (that canEqual this) &&
        reqMsgId == that.reqMsgId &&
        mchtId == that.mchtId
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(reqMsgId, mchtId)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
