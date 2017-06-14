package com.ebeijia.entity.vo.wechat.media

/**
  * 上传永久素材返回
  * UpMediaRep
  * @author xiong.wang
  *         2016/6/22
  */
class UpMediaRep {
  private var mediaId: String = null
  private var url: String = null

  def getMediaId: String = {
    mediaId
  }

  def setMediaId(mediaId: String) {
    this.mediaId = mediaId
  }

  def getUrl: String = {
    url
  }

  def seUrl(url: String) {
    this.url = url
  }
}
