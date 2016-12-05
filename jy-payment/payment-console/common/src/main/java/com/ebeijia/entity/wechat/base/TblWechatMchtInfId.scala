package com.ebeijia.entity.wechat.base

import javax.persistence.{Column, Embeddable}

@Embeddable
@SerialVersionUID(1L)
class TblWechatMchtInfId extends java.io.Serializable {
  private var brhNo: String = null
  private var mchtId: String = null
  @Column(name = "BRH_NO", unique = true, nullable = false, length = 11)
  def getBrhNo: String = {
    brhNo
  }

  def setBrhNo(brhNo: String) {
    this.brhNo = brhNo
  }

  @Column(name = "MCHT_ID", length = 8) def getMchtId: String = {
    mchtId
  }

  def setMchtId(mchtId: String) {
    this.mchtId = mchtId
  }


  def canEqual(other: Any): Boolean = other.isInstanceOf[TblWechatMchtInfId]

  override def equals(other: Any): Boolean = other match {
    case that: TblWechatMchtInfId =>
      (that canEqual this) &&
        brhNo == that.brhNo &&
        mchtId == that.mchtId
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(brhNo, mchtId)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
