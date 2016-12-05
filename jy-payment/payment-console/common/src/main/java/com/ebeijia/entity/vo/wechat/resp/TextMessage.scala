package com.ebeijia.entity.vo.wechat.resp

/**
 * TextMessage文本消息
 * @author zhicheng.xu
 *         15/8/12
 */
class TextMessage extends BaseMessage {
  // 回复的消息内容
  private var Content: String = null

  def getContent: String = {
    Content
  }

  def setContent(content: String) {
    Content = content
  }
}
