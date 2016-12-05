package com.ebeijia.entity.system.system

import java.io.Serializable
import javax.persistence.{AttributeOverride, AttributeOverrides, Column, EmbeddedId, Entity, Table}

/**
 * TblRoleMap
 * @author zhicheng.xu
 *         15/8/11
 */


@Entity
@Table(name = "TBL_SYS_ROLE_MAP")
@SerialVersionUID(1L)
class TblSysRoleMap extends Serializable {
  private var id: TblSysRoleMapId = null

  @EmbeddedId
  @AttributeOverrides(Array(new AttributeOverride(name = "roleId", column = new Column(name = "ROLE_ID", nullable = false, length = 6))
    , new AttributeOverride(name = "funcId", column = new Column(name = "FUNC_ID", nullable = false, length = 6))
    , new AttributeOverride(name = "brhNo", column = new Column(name = "BRH_NO", nullable = false, length = 11))))
  def getId: TblSysRoleMapId = {
    id
  }

  def setId(id: TblSysRoleMapId) {
    this.id = id
  }
}
