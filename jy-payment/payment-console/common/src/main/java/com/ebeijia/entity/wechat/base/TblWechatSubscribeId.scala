package com.ebeijia.entity.wechat.base

import javax.persistence.{Column, Embeddable}

@Embeddable
@SerialVersionUID(1L)
class TblWechatSubscribeId extends java.io.Serializable {
  private var subcribeId: String = null
  private var mchtId: String = null

  @Column(name = "SUBCRIBE_ID", unique = true, nullable = false, length = 15)
  def getSubcribeId: String = {
    subcribeId
  }

  def setSubcribeId(subcribeId: String) {
    this.subcribeId = subcribeId
  }

  @Column(name = "MCHT_ID", length = 8) def getMchtId: String = {
    mchtId
  }

  def setMchtId(mchtId: String) {
    this.mchtId = mchtId
  }


  def canEqual(other: Any): Boolean = other.isInstanceOf[TblWechatSubscribeId]

  override def equals(other: Any): Boolean = other match {
    case that: TblWechatSubscribeId =>
      (that canEqual this) &&
        subcribeId == that.subcribeId &&
        mchtId == that.mchtId
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(subcribeId, mchtId)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
