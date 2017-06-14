package com.ebeijia.entity.wechat.base

import javax.persistence.{Column, Embeddable, GeneratedValue, GenerationType}


/**
 * TblWechatRuleId
 * @author zhicheng.xu
 *         15/8/12
 */


@Embeddable
@SerialVersionUID(1L)
class TblWechatRuleId extends java.io.Serializable {
  private var modId: String = null
  private var ruleId: String = null



  @Column(name = "MOD_ID", length = 20) def getModId: String = {
    modId
  }

  def setModId(modId: String) {
    this.modId = modId
  }

  @Column(name = "RULE_ID", length = 20)
  @GeneratedValue(strategy = GenerationType.AUTO)
  def getRuleId: String = {
    ruleId
  }

  def setRuleId(ruleId: String) {
    this.ruleId = ruleId
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[TblWechatRuleId]

  override def equals(other: Any): Boolean = other match {
    case that: TblWechatRuleId =>
      (that canEqual this) &&
        modId == that.modId &&
        ruleId == that.ruleId
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(modId, ruleId)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
