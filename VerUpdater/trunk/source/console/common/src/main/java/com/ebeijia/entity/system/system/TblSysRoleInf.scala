package com.ebeijia.entity.system.system

import java.io.Serializable
import javax.persistence._

/**
 * TblRoleInf
 * @author zhicheng.xu
 *         15/8/11
 */


@Entity
@Table(name = "TBL_SYS_ROLE_INF")
@SerialVersionUID(1L)
class TblSysRoleInf extends Serializable {
  private var id: TblSysRoleInfId = null
  private var dsc: String = null
  private var roleName: String = null
  private var roleType: String = null

  @EmbeddedId
  @AttributeOverrides(Array(new AttributeOverride(name = "ruleId", column = new Column(name = "RULE_ID", nullable = false, length = 6)), new AttributeOverride(name = "brhNo", column = new Column(name = "BRH_NO", nullable = false, length = 11))))
  def getId: TblSysRoleInfId = {
    id
  }

  def setId(id: TblSysRoleInfId) {
    this.id = id
  }

  @Column(name = "DSC", length = 64)
  def getDsc: String = {
    dsc
  }

  def setDsc(dsc: String) {
    this.dsc = dsc
  }

  @Column(name = "ROLE_NAME", length = 32)
  def getRoleName: String = {
    roleName
  }

  def setRoleName(roleName: String) {
    this.roleName = roleName
  }

  @Column(name = "ROLE_TYPE", length = 32)
  def getRoleType: String = {
    roleType
  }

  def setRoleType(roleType: String) {
    this.roleType = roleType
  }
}
