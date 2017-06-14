package com.ebeijia.entity.wechat.base

import javax.persistence.{Column, Embeddable}

/**
 * TblWechatRedpacketId
 * @author zhicheng.xu
 *         15/8/12
 */


@Embeddable
@SerialVersionUID(1L)
class TblWechatRedpacketId extends java.io.Serializable {
  private var nonceStr: String = null
  private var mchtId: String = null



  @Column(name = "NONCE_STR", unique = true, nullable = false, length = 32)
  def getNonceStr: String = {
    nonceStr
  }

  def setNonceStr(nonceStr: String) {
    this.nonceStr = nonceStr
  }

  @Column(name = "MCHT_ID", length = 15) def getMchtId: String = {
    mchtId
  }

  def setMchtId(mchtId: String) {
    this.mchtId = mchtId
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[TblWechatRedpacketId]

  override def equals(other: Any): Boolean = other match {
    case that: TblWechatRedpacketId =>
      (that canEqual this) &&
        nonceStr == that.nonceStr &&
        mchtId == that.mchtId
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(nonceStr, mchtId)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

