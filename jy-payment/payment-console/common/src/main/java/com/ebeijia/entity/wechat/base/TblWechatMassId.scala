package com.ebeijia.entity.wechat.base

import javax.persistence.{Column, Embeddable}

/**
 * TblWechatMassId
 * @author zhicheng.xu
 *         15/8/12
 */


@Embeddable
@SerialVersionUID(1L)
class TblWechatMassId extends java.io.Serializable {
  private var msgId: String = null
  private var mchtId: String = null



  @Column(name = "MSG_ID", unique = true, nullable = false, length = 10) def getMsgId: String = {
    msgId
  }

  def setMsgId(msgId: String) {
    this.msgId = msgId
  }

  @Column(name = "MCHT_ID", length = 15) def getMchtId: String = {
    mchtId
  }

  def setMchtId(mchtId: String) {
    this.mchtId = mchtId
  }


  def canEqual(other: Any): Boolean = other.isInstanceOf[TblWechatMassId]

  override def equals(other: Any): Boolean = other match {
    case that: TblWechatMassId =>
      (that canEqual this) &&
        msgId == that.msgId &&
        mchtId == that.mchtId
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(msgId, mchtId)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

