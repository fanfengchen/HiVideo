package com.ebeijia.entity.wechat.base

import javax.persistence.{Column, Embeddable}

@Embeddable
@SerialVersionUID(1L)
class TblWechatSubGroupId extends java.io.Serializable{
  private var id: String = null
  private var mchtId: String = null
  @Column(name = "ID", unique = true, nullable = false, length = 3)
  def getId: String = {
    id
  }

  def setId(id: String) {
    this.id = id
  }

  @Column(name = "MCHT_ID", length = 8) def getMchtId: String = {
    mchtId
  }

  def setMchtId(mchtId: String) {
    this.mchtId = mchtId
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[TblWechatSubGroupId]

  override def equals(other: Any): Boolean = other match {
    case that: TblWechatSubGroupId =>
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
