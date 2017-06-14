package com.ebeijia.entity.wechat.base

import javax.persistence._

/**
 * TblArticlesInf
 * @author zhicheng.xu
 *         15/8/12
 */


@Entity
@Table(name = "TBL_WECHAT_ARTICLES_INF")
@SerialVersionUID(1L)
class TblWechatArticlesInf extends java.io.Serializable {
  private var id: TblWechatArticlesInfId = null
  private var title: String = null
  private var author: String = null
  private var digest: String = null
  private var thumbMediaId: String = null
  private var description: String = null
  private var picUrl: String = null
  private var url: String = null
  private var localPicId:String = null
  private var content: String = null


  @EmbeddedId
  @AttributeOverrides(Array(new AttributeOverride(name = "id", column = new Column(name = "ID", nullable = false, length = 10)), new AttributeOverride(name = "mchtId", column = new Column(name = "MCHT_ID", nullable = false, length = 11)))) def getId: TblWechatArticlesInfId = {
    id
  }

  def setId(id: TblWechatArticlesInfId) {
    this.id = id
  }

  @Column(name = "TITLE", length = 64)
  def getTitle: String = {
    title
  }

  def setTitle(title: String) {
    this.title = title
  }

  @Column(name = "DESCRIPTION", length = 64)
  def getDescription: String = {
    description
  }

  def setDescription(description: String) {
    this.description = description
  }

  @Column(name = "PIC_URL", length = 128)
  def getPicUrl: String = {
    picUrl
  }

  def setPicUrl(picUrl: String) {
    this.picUrl = picUrl
  }

  @Column(name = "URL", length = 128)
  def getUrl: String = {
    url
  }

  def setUrl(url: String) {
    this.url = url
  }
  @Column(name = "AUTHOR", length = 64)
  def getAuthor: String = {
    author
  }

  def setAuthor(author: String) {
    this.author = author
  }
  @Column(name = "DIGEST", length = 300)
  def getDigest: String = {
    digest
  }

  def setDigest(digest: String) {
    this.digest = digest
  }
  @Column(name = "THUMB_MEDIA_ID", length = 64)
  def getThumbMediaId: String = {
    thumbMediaId
  }

  def setThumbMediaId(thumbMediaId: String) {
    this.thumbMediaId = thumbMediaId
  }

  @Column(name = "LOCAL_PIC_ID", length = 64)
  def getLocalPicId: String = {
    localPicId
  }

  def setLocalPicId(localPicId: String) {
    this.localPicId = localPicId
  }

  @Column(name = "CONTENT", length = 9999)
  def getContent: String = {
    content
  }

  def setContent(content: String) {
    this.content = content
  }

}

