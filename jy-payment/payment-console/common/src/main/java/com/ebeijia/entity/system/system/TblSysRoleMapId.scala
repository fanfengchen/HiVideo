package com.ebeijia.entity.system.system

/**
 * TblRoleMapId
 * @author zhicheng.xu
 *         15/8/11
 */

import java.io.Serializable
import javax.persistence.{Column, Embeddable}

@Embeddable
@SerialVersionUID(1L)
class TblSysRoleMapId extends Serializable {
  private var roleId: String = null
  private var funcId: String = null
  private var brhNo :String = null
  @Column(name = "ROLE_ID", nullable = false, length = 6)
  def getRoleId: String = {
    roleId
  }

  def setRoleId(roleId: String) {
    this.roleId = roleId
  }

  @Column(name = "FUNC_ID", nullable = false, length = 6)
  def getFuncId: String = {
    funcId
  }

  def setFuncId(funcId: String) {
    this.funcId = funcId
  }

  @Column(name = "BRH_NO", nullable = false, length = 11)
  def getBrhNo: String = {
    brhNo
  }

  def setBrhNo(brhNo: String) {
    this.brhNo = brhNo
  }


  def canEqual(other: Any): Boolean = other.isInstanceOf[TblSysRoleMapId]

  override def equals(other: Any): Boolean = other match {
    case that: TblSysRoleMapId =>
      (that canEqual this) &&
        roleId == that.roleId &&
        funcId == that.funcId &&
        brhNo == that.brhNo
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(roleId, funcId, brhNo)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

