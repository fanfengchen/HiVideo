package com.ebeijia.entity.vo.wechat

/**
 * 微信通用接口凭证
 * AccessToken
 * @author zhicheng.xu
 *         15/8/11
 */
class AccessToken {
  // 获取到的凭证
  private var token: String = null
  // 凭证有效时间，单位：秒
  private var expiresIn: Int = 0
  //用户刷新access_token
  private var refreshToken: String = null
  //用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
  private var openid: String = null
  //用户授权的作用域，使用逗号（,）分隔
  private var scope: String = null
  private var unionid: String = null

  def getToken: String = {
    token
  }

  def setToken(token: String) {
    this.token = token
  }

  def getExpiresIn: Int = {
    expiresIn
  }

  def setExpiresIn(expiresIn: Int) {
    this.expiresIn = expiresIn
  }

  def getRefreshToken: String = {
    refreshToken
  }

  def setRefreshToken(refreshToken: String) {
    this.refreshToken = refreshToken
  }
  def getOpenid: String = {
    openid
  }

  def setOpenid(openid: String) {
    this.openid = openid
  }
  def getScope: String = {
    scope
  }

  def setScope(scope: String) {
    this.scope = scope
  }
  def getUnionid: String = {
    unionid
  }

  def setUnionid(unionid: String) {
    this.unionid = unionid
  }
}
