package com.ebeijia.entity.vo.wechat.media

/**
  * 图文请求
  * FodderReq
  * @author xiong.wang
  *         2016/6/22
  */
class FodderReq {
  //  title	是	标题
  //  thumb_media_id	是	图文消息的封面图片素材id（必须是永久mediaID）
  //  author	是	作者
  //  digest	是	图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空
  //  show_cover_pic	是	是否显示封面，0为false，即不显示，1为true，即显示
  //  content	是	图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS
  //  content_source_url	是	图文消息的原文地址，即点击“阅读原文”后的URL
  private var title: String = null
  private var thumbMediaId: String = null
  private var author: String = null
  private var digest: String = null
  private var showCoverPic: String = null
  private var content: String = null
  private var contentSourceUrl: String = null
  def getTitle: String = {
    title
  }

  def setTitle(title: String) {
    this.title = title
  }

  def getThumbMediaId: String = {
    thumbMediaId
  }

  def setThumbMediaId(thumbMediaId: String) {
    this.thumbMediaId = thumbMediaId
  }

  def getAuthor: String = {
    author
  }

  def setAuthor(author: String) {
    this.author = author
  }

  def getDigest: String = {
    digest
  }

  def setDigest(digest: String) {
    this.digest = digest
  }

  def getShowCoverPic: String = {
    showCoverPic
  }

  def setShowCoverPic(showCoverPic: String) {
    this.showCoverPic = showCoverPic
  }

  def getContentSourceUrl: String = {
    contentSourceUrl
  }

  def setContentSourceUrl(contentSourceUrl: String) {
    this.contentSourceUrl = contentSourceUrl
  }

  def getContent: String = {
    content
  }

  def setContent(content: String) {
    this.content = content
  }
}
