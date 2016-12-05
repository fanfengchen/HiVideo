package com.ebeijia.entity.system.system

import java.io.Serializable
import javax.persistence.Column

/**
  * TblRoleInfId
  * @author xiong.wang
  *         2016/6/20
  */
class TblSysRoleInfId extends Serializable {
  private var ruleId: Int = 0
  private var brhNo: String = null


  @Column(name = "RULE_ID", nullable = false, length = 6)
  def getRuleId: Int = {
    ruleId
  }

  def setRuleId(ruleId: Int) {
    this.ruleId = ruleId
  }

  @Column(name = "BRH_NO", nullable = false, length = 11)
  def getBrhNo: String = {
    brhNo
  }

  def setBrhNo(brhNo: String) {
    this.brhNo = brhNo
  }


  def canEqual(other: Any): Boolean = other.isInstanceOf[TblSysRoleInfId]

  override def equals(other: Any): Boolean = other match {
    case that: TblSysRoleInfId =>
      (that canEqual this) &&
        ruleId == that.ruleId &&
        brhNo == that.brhNo
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(ruleId, brhNo)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
