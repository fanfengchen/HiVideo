package com.ebeijia.entity.wechat.base

import javax.persistence._

/**
 * TblWechatMchtInf
 * @author zhicheng.xu
 *         15/8/12
 */


@Entity
@Table(name = "TBL_WECHAT_MCHT_INF")
@SerialVersionUID(1L)
class TblWechatMchtInf extends java.io.Serializable {
  private var id: TblWechatMchtInfId = null
  private var url: String = null
  private var token: String = null
  private var appid: String = null
  private var appsecret: String = null
  private var accessToken: String = null
  private var expiresTime: String = null
  private var wechatType: String = null
  private var nickname: String = null
  private var createTime: String = null
  private var updateTime: String = null
  private var template1: String = null
  private var template2: String = null


  @EmbeddedId
  @AttributeOverrides(Array(new AttributeOverride(name = "brhNo", column = new Column(name = "BRH_NO", nullable = false, length = 11)), new AttributeOverride(name = "mchtId", column = new Column(name = "MCHT_ID", nullable = false, length = 8)))) def getId: TblWechatMchtInfId = {
    id
  }

  def setId(id: TblWechatMchtInfId) {
    this.id = id
  }

  @Column(name = "URL", length = 256)
  def getUrl: String = {
    url
  }

  def setUrl(url: String) {
    this.url = url
  }

  @Column(name = "TOKEN", length = 64)
  def getToken: String = {
    token
  }

  def setToken(token: String) {
    this.token = token
  }

  @Column(name = "APPID", length = 64)
  def getAppid: String = {
    appid
  }

  def setAppid(appid: String) {
    this.appid = appid
  }

  @Column(name = "APPSECRET", length = 64)
  def getAppsecret: String = {
    appsecret
  }

  def setAppsecret(appsecret: String) {
    this.appsecret = appsecret
  }

  @Column(name = "ACCESS_TOKEN", length = 20)
  def getAccessToken: String = {
    accessToken
  }

  def setAccessToken(accessToken: String) {
    this.accessToken = accessToken
  }

  @Column(name = "EXPIRES_TIME", length = 14)
  def getExpiresTime: String = {
    expiresTime
  }

  def setExpiresTime(expiresTime: String) {
    this.expiresTime = expiresTime
  }

  @Column(name = "WECHAT_TYPE", length = 1)
  def getWechatType: String = {
    wechatType
  }

  def setWechatType(wechatType: String) {
    this.wechatType = wechatType
  }

  @Column(name = "NICKNAME", length = 20)
  def getNickname: String = {
    nickname
  }

  def setNickname(nickname: String) {
    this.nickname = nickname
  }

  @Column(name = "CREATE_TIME", length = 14)
  def getCreateTime: String = {
    createTime
  }

  def setCreateTime(createTime: String) {
    this.createTime = createTime
  }

  @Column(name = "UPDATE_TIME", length = 14)
  def getUpdateTime: String = {
    updateTime
  }

  def setUpdateTime(updateTime: String) {
    this.updateTime = updateTime
  }

  @Column(name = "TEMPLATE1", length = 6)
  def getTemplate1: String = {
    template1
  }

  def setTemplate1(template1: String) {
    this.template1 = template1
  }

  @Column(name = "TEMPLATE2", length = 6)
  def getTemplate2: String = {
    template2
  }

  def setTemplate2(template2: String) {
    this.template2 = template2
  }
}

