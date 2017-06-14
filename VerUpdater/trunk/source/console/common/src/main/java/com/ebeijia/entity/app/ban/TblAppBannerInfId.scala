package com.ebeijia.entity.app.ban

import javax.persistence._


/**
 * 广告TblAppBannerInf主键
 */
@Embeddable
@SerialVersionUID(1L)
class TblAppBannerInfId extends Serializable{
  private var brhNo:String = null//机构编号
  private var banId: Int = 0  //广告ID

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "BAN_ID", unique = true, nullable = false, length = 10)
  def getBanId: Int = {
      banId
  }
  def setBanId(banId: Int) {
    this.banId = banId
  }

  @Column(name = "BAN_ID", nullable = false, length = 10)
  def getBrhNo:String ={
    brhNo
  }

  def setBrhNo(brhNo:String): Unit ={
    this.brhNo = brhNo
  }


  def canEqual(other: Any): Boolean = other.isInstanceOf[TblAppBannerInfId]

  override def equals(other: Any): Boolean = other match {
    case that: TblAppBannerInfId =>
      (that canEqual this) &&
        brhNo == that.brhNo &&
        banId == that.banId
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(brhNo, banId)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
