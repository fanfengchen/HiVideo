package com.ebeijia.entity.vo.wechat.media

import java.io.File

/**
  * 上传永久素材请求
  * UploadMedia
  * @author xiong.wang
  *         2016/6/22
  */
class UpMediaReq {

  private var mchtId: String = null
  private var upFile: File = null
  private var upType: String = null
  private var accessToken: String = null
  private var ext: String = null
  private var articles:String = null

  def getMchtId: String = {
    mchtId
  }

  def setMchtId(mchtId: String) {
    this.mchtId = mchtId
  }

  def getUpFile: File = {
    upFile
  }

  def setUpFile(upFile: File) {
    this.upFile = upFile
  }

  def getUpType: String = {
    upType
  }

  def setUpType(upType: String) {
    this.upType = upType
  }

  def getAccessToken: String = {
    accessToken
  }

  def setAccessToken(accessToken: String) {
    this.accessToken = accessToken
  }

  def getExt: String = {
    accessToken
  }

  def setExt(ext: String) {
    this.ext = ext
  }

  def getArticles: String = {
    articles
  }

  def setArticles(articles: String) {
    this.articles = articles
  }
}
