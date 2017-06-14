package com.ebeijia.entity.app.soft

import javax.persistence._

/**
 * 软件TblAppSoftVerInf主键
 */
@Embeddable
@SerialVersionUID(1L)
class TblAppSoftVerInfId extends Serializable{
  private var verId : Int = 0
  private var brhNo:String=null
@GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "VER_ID", unique = true, nullable = false, length = 5)
  def getVerId:Int={
    verId
  }
  def setVerId(verId:Int): Unit ={
    this.verId=verId
  }

  @Column(name = "BRH_NO", nullable = false, length = 11)
  def getBrhNo:String={
    brhNo
  }
  def setBrhNo(brhNo:String): Unit ={
    this.brhNo = brhNo
  }


  def canEqual(other: Any): Boolean = other.isInstanceOf[TblAppSoftVerInfId]

  override def equals(other: Any): Boolean = other match {
    case that: TblAppSoftVerInfId =>
      (that canEqual this) &&
        verId == that.verId &&
      brhNo == that.brhNo
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(verId, brhNo)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
