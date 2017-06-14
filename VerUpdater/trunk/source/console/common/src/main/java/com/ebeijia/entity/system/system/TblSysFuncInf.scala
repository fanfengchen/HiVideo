package com.ebeijia.entity.system.system

import java.io.Serializable
import javax.persistence.{Column, Entity, Id, Table}

/**
 * TblFuncInf
 * @author zhicheng.xu
 *         15/8/11
 */


@Entity
@Table(name = "TBL_SYS_FUNC_INF")
@SerialVersionUID(1L)
class TblSysFuncInf extends Serializable {
  private var funcId: String = null
  private var funcName: String = null
  private var parentId: String = null
  private var funcType: String = null
  @Id
  @Column(name = "FUNC_ID", unique = true, nullable = false, length = 6)
  def getFuncId: String = {
    funcId
  }

  def setFuncId(funcId: String) {
    this.funcId = funcId
  }

  @Column(name = "FUNC_NAME", length = 32)
  def getFuncName: String = {
    funcName
  }

  def setFuncName(funcName: String) {
    this.funcName = funcName
  }

  @Column(name = "PARENT_ID", length = 6)
  def getParentId: String = {
    parentId
  }

  def setParentId(parentId: String) {
    this.parentId = parentId
  }

  @Column(name = "FUNC_TYPE", length = 6)
  def getFuncType: String = {
    funcType
  }

  def setFuncType(funcType: String) {
    this.funcType = funcType
  }
}
