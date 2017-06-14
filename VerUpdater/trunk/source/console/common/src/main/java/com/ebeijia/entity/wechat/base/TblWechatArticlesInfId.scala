package com.ebeijia.entity.wechat.base

import javax.persistence.{Column, Embeddable}

/**
  * 图文实体
  * TblArticlesInfId
  * @author xiong.wang
  * 2016/6/22
  */
@Embeddable
@SerialVersionUID(1L)
class TblWechatArticlesInfId extends java.io.Serializable{
  private var id: String = null
  private var mchtId: String = null
  @Column(name = "ID", unique = true, nullable = false, length = 10) def getId: String = {
    id
  }

  def setId(id: String) {
    this.id = id
  }

  @Column(name = "MCHT_ID", length = 15) def getMchtId: String = {
    mchtId
  }

  def setMchtId(mchtId: String) {
    this.mchtId = mchtId
  }


  def canEqual(other: Any): Boolean = other.isInstanceOf[TblWechatArticlesInfId]

  override def equals(other: Any): Boolean = other match {
    case that: TblWechatArticlesInfId =>
      (that canEqual this) &&
        id == that.id &&
        mchtId == that.mchtId
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(id, mchtId)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
