package com.ebeijia.entity.wechat.base

import javax.persistence._

/**
 * TblWechatGroup
 * @author zhicheng.xu
 *         15/8/12
 *         微信链接管理
 */
@Entity
@SerialVersionUID(1L)
@Table(name = "TBL_WECHAT_URL")
class TblWechatUrl{
  private var urlId: String = null
  private var url: String = null
  private var urlType: String = null
  private var urlDsc: String = null

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "URL_ID", unique = true, nullable = false, length = 4)
  def getUrlId: String = {
     urlId
  }

  def setUrlId(urlId: String) {
    this.urlId = urlId
  }

  @Column(name = "URL", unique = true, length = 256)
  def getUrl: String = {
     url
  }

  def setUrl(url: String) {
    this.url = url
  }

  @Column(name = "URL_TYPE", unique = true, length = 1)
  def getUrlType: String = {
     urlType
  }

  def setUrlType(urlType: String) {
    this.urlType = urlType
  }

  @Column(name = "USL_DSC", unique = true, length = 64)
  def getUrlDsc: String = {
     urlDsc
  }

  def setUrlDsc(urlDsc: String) {
    this.urlDsc = urlDsc
  }
}

