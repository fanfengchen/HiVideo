package com.ebeijia.entity.wechat.accountInfo

import java.io.Serializable
import javax.persistence.{Column, Embeddable}

/**
 * Created by xf on 2016/7/6.
 */
@Embeddable
@SerialVersionUID(1L)
class TAccountInfoId extends Serializable {
  private var openId:String = null
  private var accountNo:String = null

  @Column(name = "open_id", nullable = false, length = 50)
  def getOpenId: String = {
    openId
  }

  def setOpenId(openId: String) {
    this.openId = openId
  }

  @Column(name = "account_no", nullable = false, length = 50)
  def getAccountNo: String = {
    accountNo
  }

  def setAccountNo(accountNo: String) {
    this.accountNo = accountNo
  }


  def canEqual(other: Any): Boolean = other.isInstanceOf[TAccountInfoId]

  override def equals(other: Any): Boolean = other match {
    case that: TAccountInfoId =>
      (that canEqual this) &&
        openId == that.openId &&
        accountNo == that.accountNo
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(openId, accountNo)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
