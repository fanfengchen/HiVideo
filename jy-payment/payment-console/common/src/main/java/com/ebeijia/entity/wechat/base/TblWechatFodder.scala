package com.ebeijia.entity.wechat.base

import javax.persistence._

/**
  * TblWechatFodder
  * @author zhicheng.xu
  *         15/8/12
  */


@Entity
@Table(name = "TBL_WECHAT_FODDER")
@SerialVersionUID(1L)
class TblWechatFodder extends java.io.Serializable {
  private var id: TblWechatFodderId = null
  private var name: String = null
  private var dsc: String = null
  private var `type`: String = null
  private var mediaType: String = null
  private var url: String = null
  private var wechatUrl: String = null
  private var createTime: String = null
  private var picId: String = null
  private var artId: String = null

  @EmbeddedId
  @AttributeOverrides(Array(new AttributeOverride(name = "media", column = new Column(name = "MEDIA", nullable = false, length = 50)), new AttributeOverride(name = "mchtId", column = new Column(name = "MCHT_ID", nullable = false, length = 11)))) def getId: TblWechatFodderId = {
    id
  }
  def setId(id: TblWechatFodderId) {
    this.id = id
  }

  @Column(name = "NAME", nullable = false, length = 30) def getName: String = {
    name
  }

  def setName(name: String) {
    this.name = name
  }

  @Column(name = "DSC", nullable = false, length = 64) def getDsc: String = {
    dsc
  }

  def setDsc(dsc: String) {
    this.dsc = dsc
  }

  @Column(name = "TYPE", nullable = false, length = 5) def getType: String = {
    `type`
  }

  def setType(`type`: String) {
    this.`type` = `type`
  }

  @Column(name = "MEDIA_TYPE", nullable = false, length = 1) def getMediaType: String = {
    mediaType
  }

  def setMediaType(mediaType: String) {
    this.mediaType = mediaType
  }

  @Column(name = "URL", nullable = false, length = 128) def getUrl: String = {
    url
  }

  def setUrl(url: String) {
    this.url = url
  }

  @Column(name = "WECHAT_URL", length = 128) def getWechatUrl: String = {
    wechatUrl
  }

  def setWechatUrl(wechatUrl: String) {
    this.wechatUrl = wechatUrl
  }

  @Column(name = "CREATE_TIME", nullable = false, length = 14) def getCreateTime: String = {
    createTime
  }

  def setCreateTime(createTime: String) {
    this.createTime = createTime
  }

  @Column(name = "PIC_ID", length = 8) def getPicId: String = {
    picId
  }

  def setPicId(picId: String) {
    this.picId = picId
  }

  @Column(name = "ART_ID", length = 8) def getArtId: String = {
    artId
  }

  def setArtId(artId: String) {
    this.artId = artId
  }
}

