package com.ebeijia.entity.system.system

import java.io.Serializable
import javax.persistence._

@Entity
@Table(name = "TBL_SYS_OPERATOR_INF")
@SerialVersionUID(1L)
class TblSysOperatorInf() extends Serializable{
  private var id:TblSysOperatorInfId = null
  private var oprName: String = null
  private var roleId: Int = 0
  private var oprPwd: String = null
  private var oprStat: String = null
  private var crtTime: String = null
  private var lastLogTime: String = null
  private var updTime: String = null
  private var oprDsc: String = null

  @EmbeddedId
  @AttributeOverrides(Array(new AttributeOverride(name = "oprId", column = new Column(name = "OPR_ID", nullable = false, length = 6)), new AttributeOverride(name = "brhNo", column = new Column(name = "BRH_NO", nullable = false, length = 6))))
  def getId: TblSysOperatorInfId = {
    id
  }

  def setId(id: TblSysOperatorInfId) {
    this.id = id
  }

  @Column(name = "OPR_NAME", length = 32)
  def getOprName: String = {
     oprName
  }

  def setOprName(oprName: String) {
    this.oprName = oprName
  }

  @Column(name = "ROLE_ID", length = 6)
  def getRoleId: Int = {
     roleId
  }

  def setRoleId(roleId: Int) {
    this.roleId = roleId
  }

  @Column(name = "OPR_PWD", length = 32)
  def getOprPwd: String = {
     oprPwd
  }

  def setOprPwd(oprPwd: String) {
    this.oprPwd = oprPwd
  }

  @Column(name = "OPR_STAT", length = 1)
  def getOprStat: String = {
     oprStat
  }

  def setOprStat(oprStat: String) {
    this.oprStat = oprStat
  }

  @Column(name = "CRT_TIME", length = 14)
  def getCrtTime: String = {
     crtTime
  }

  def setCrtTime(crtTime: String) {
    this.crtTime = crtTime
  }

  @Column(name = "LAST_LOG_TIME", length = 14)
  def getLastLogTime: String = {
     lastLogTime
  }

  def setLastLogTime(lastLogTime: String) {
    this.lastLogTime = lastLogTime
  }

  @Column(name = "UPD_TIME", length = 14)
  def getUpdTime: String = {
     updTime
  }

  def setUpdTime(updTime: String) {
    this.updTime = updTime
  }

  @Column(name = "OPR_DSC", length = 128)
  def getOprDsc: String = {
     oprDsc
  }

  def setOprDsc(oprDsc: String) {
    this.oprDsc = oprDsc
  }

}
