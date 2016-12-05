package com.ebeijia.entity.vo.wechat.resp

/**
 * Music音乐model
 * @author zhicheng.xu
 *         15/8/12
 */
class Music {
  // 音乐名称
  private var Title: String = null
  // 音乐描述
  private var Description: String = null
  // 音乐链接
  private var MusicUrl: String = null
  // 高质量音乐链接，WIFI环境优先使用该链接播放音乐
  private var HQMusicUrl: String = null
  //缩略图
  private var ThumbMediaId: String = null

  def getTitle: String = {
    Title
  }

  def setTitle(title: String) {
    Title = title
  }

  def getDescription: String = {
    Description
  }

  def setDescription(description: String) {
    Description = description
  }

  def getMusicUrl: String = {
    MusicUrl
  }

  def setMusicUrl(musicUrl: String) {
    MusicUrl = musicUrl
  }

  def getHQMusicUrl: String = {
    HQMusicUrl
  }

  def setHQMusicUrl(musicUrl: String) {
    HQMusicUrl = musicUrl
  }

  def getThumbMediaId: String = {
    ThumbMediaId
  }

  def setThumbMediaId(thumbMediaId: String) {
    ThumbMediaId = thumbMediaId
  }
}

