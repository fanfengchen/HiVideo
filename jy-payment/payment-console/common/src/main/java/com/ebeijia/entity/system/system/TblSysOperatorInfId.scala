package com.ebeijia.entity.system.system

import java.io.Serializable
import javax.persistence.{Column, Embeddable}

@Embeddable
@SerialVersionUID(1L)
class TblSysOperatorInfId extends Serializable{
  private var oprId: Int = 0
  private var brhNo:String = null

  @Column(name = "OPR_ID", nullable = false, length = 6)
  def getOprId: Int = {
    oprId
  }

  def setOprId(oprId: Int) {
    this.oprId = oprId
  }

  @Column(name = "BRH_NO", nullable = false, length = 6)
  def getBrhNo: String = {
    brhNo
  }

  def setBrhNo(brhNo: String) {
    this.brhNo = brhNo
  }


  def canEqual(other: Any): Boolean = other.isInstanceOf[TblSysOperatorInfId]

  override def equals(other: Any): Boolean = other match {
    case that: TblSysOperatorInfId =>
      (that canEqual this) &&
        oprId == that.oprId &&
        brhNo == that.brhNo
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(oprId, brhNo)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
