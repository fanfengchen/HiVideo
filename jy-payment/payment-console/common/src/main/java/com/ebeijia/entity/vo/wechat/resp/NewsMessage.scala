package com.ebeijia.entity.vo.wechat.resp

import java.util.List

/**
 * NewsMessage图文消息
 * @author zhicheng.xu
 *         15/8/17
 */

class NewsMessage extends BaseMessage {
  // 图文消息个数，限制为10条以内
  private var ArticleCount: Int = 0
  // 多条图文消息信息，默认第一个item为大图
  private var Articles: List[Article] = null

  def getArticleCount: Int = {
    ArticleCount
  }

  def setArticleCount(articleCount: Int) {
    ArticleCount = articleCount
  }

  def getArticles: List[Article] = {
    Articles
  }

  def setArticles(articles: List[Article]) {
    Articles = articles
  }
}
