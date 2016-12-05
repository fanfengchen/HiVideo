package com.ebeijia.entity.wechat.base

import javax.persistence.{Column, Embeddable}

/**
  * TblWechatFodderId
  * @author xiong.wang
  *         2016/6/22
  */
@Embeddable
@SerialVersionUID(1L)
class TblWechatFodderId extends java.io.Serializable {
  private var media: String = null
  private var mchtId: String = null

  @Column(name = "MEDIA", unique = true, nullable = false, length = 100) def getMedia: String = {
    media
  }

  def setMedia(media: String) {
    this.media = media
  }

  @Column(name = "MCHT_ID", nullable = false, length = 15) def getMchtId: String = {
    mchtId
  }

  def setMchtId(mchtId: String) {
    this.mchtId = mchtId
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[TblWechatFodderId]

  override def equals(other: Any): Boolean = other match {
    case that: TblWechatFodderId =>
      (that canEqual this) &&
        media == that.media &&
        mchtId == that.mchtId
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(media, mchtId)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
