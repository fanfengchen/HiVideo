package com.ebeijia.entity.system.system

import javax.persistence._

/**
 * TblMchtInf
 * @author zhicheng.xu
 *         15/8/11
 */


@Entity
@Table(name = "TBL_SYS_MCHT_INF")
@SerialVersionUID(1L)
class TblSysMchtInf extends java.io.Serializable {
  private var id: TblSysMchtInfId = null
  private var mchtName: String = null
  private var mchtLvl: String = null
  private var upperMchtId: String = null
  private var mchtStat: String = null
  private var mchtAddr: String = null
  private var mchtPostCd: String = null
  private var mchtTel: String = null


  /** minimal constructor */
  @EmbeddedId
  @AttributeOverrides(Array(new AttributeOverride(name = "brhNo", column = new Column(name = "BRH_NO", nullable = false, length = 11)), new AttributeOverride(name = "mchtId", column = new Column(name = "MCHT_ID", nullable = false, length = 8)))) def getId: TblSysMchtInfId = {
    id
  }

  def setId(id: TblSysMchtInfId) {
    this.id = id
  }

  /** full constructor */
  def this(id: TblSysMchtInfId, mchtName: String, mchtAddr: String,
           mchtLvl: String, updperMchtId: String, mchtStat: String,
           mchtPostCd: String, mchtTel: String) {
    this()
    this.id = id
    this.mchtName = mchtName
    this.mchtLvl = mchtLvl
    this.upperMchtId = upperMchtId
    this.mchtStat = mchtStat
    this.mchtAddr = mchtAddr
    this.mchtPostCd = mchtPostCd
    this.mchtTel = mchtTel
  }

  @Column(name = "MCHT_NAME", length = 128)
  def getMchtName: String = {
    this.mchtName
  }

  def setMchtName(mchtName: String) {
    this.mchtName = mchtName
  }


  @Column(name = "MCHT_LVL", length = 2)
  def getMchtLvl: String = {
    this.mchtLvl
  }

  def setMchtLvl(mchtLvl: String) {
    this.mchtLvl = mchtLvl
  }

  @Column(name = "UPPER_MCHT_ID", length = 8)
  def getUpperMchtId: String = {
    this.upperMchtId
  }

  def setUpperMchtId(upperMchtId: String) {
    this.upperMchtId = upperMchtId
  }

  @Column(name = "MCHT_STAT", length = 1)
  def getMchtStat: String = {
    this.mchtStat
  }

  def setMchtStat(mchtStat: String) {
    this.mchtStat = mchtStat
  }


  @Column(name = "MCHT_ADDR", length = 128)
  def getMchtAddr: String = {
    this.mchtAddr
  }

  def setMchtAddr(mchtAddr: String) {
    this.mchtAddr = mchtAddr
  }

  @Column(name = "MCHT_POST_CD", length = 6)
  def getMchtPostCd: String = {
    this.mchtPostCd
  }

  def setMchtPostCd(mchtPostCd: String) {
    this.mchtPostCd = mchtPostCd
  }

  @Column(name = "MCHT_TEL", length = 15)
  def getMchtTel: String = {
    this.mchtTel
  }

  def setMchtTel(mchtTel: String) {
    this.mchtTel = mchtTel
  }
}
