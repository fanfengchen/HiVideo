package com.ebeijia.entity.vo.wechat.resp

/**
 * Article图文model
 * @author zhicheng.xu
 *         15/8/12
 */
class Article {
  // 图文消息名称
  private var Title: String = null
  // 图文消息描述
  private var Description: String = null
  // 图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80，限制图片链接的域名需要与开发者填写的基本资料中的Url一致
  private var PicUrl: String = null
  // 点击图文消息跳转链接
  private var Url: String = null

  def getTitle: String = {
    Title
  }

  def setTitle(title: String) {
    Title = title
  }

  def getDescription: String = {
    if (null == Description) "" else Description
  }

  def setDescription(description: String) {
    Description = description
  }

  def getPicUrl: String = {
    if (null == PicUrl) "" else PicUrl
  }

  def setPicUrl(picUrl: String) {
    PicUrl = picUrl
  }

  def getUrl: String = {
    if (null == Url) "" else Url
  }

  def setUrl(url: String) {
    Url = url
  }
  
}

