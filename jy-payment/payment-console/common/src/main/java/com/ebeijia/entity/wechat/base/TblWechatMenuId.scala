package com.ebeijia.entity.wechat.base

import javax.persistence.{GenerationType, GeneratedValue, Column}


class TblWechatMenuId extends java.io.Serializable{
  private var menuId: Int = 0
  private var mchtId: String = null

  @Column(name = "MENU_ID", unique = true, nullable = false, length = 8)
  def getMenuId: Int = {
    menuId
  }

  def setMenuId(menuId: Int) {
    this.menuId = menuId
  }

  @Column(name = "MCHT_ID", length = 15) def getMchtId: String = {
    mchtId
  }

  def setMchtId(mchtId: String) {
    this.mchtId = mchtId
  }


  def canEqual(other: Any): Boolean = other.isInstanceOf[TblWechatMenuId]

  override def equals(other: Any): Boolean = other match {
    case that: TblWechatMenuId =>
      (that canEqual this) &&
        menuId == that.menuId &&
        mchtId == that.mchtId
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(menuId, mchtId)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
