package com.ebeijia.module.wechat.service.msg

import java.util
import java.util.{List, Map}

import com.ebeijia.module.wechat.dao.base.WechatRespMsgDao
import com.ebeijia.entity.wechat.base.{TblWechatMenu, TblWechatRespMsgId, TblWechatArticlesInf, TblWechatRespMsg}
import com.ebeijia.module.wechat.service.media.ArticlesService
import com.ebeijia.module.wechat.service.menu.WechatMenuService
import com.google.gson.{JsonArray, JsonObject, JsonParser}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * WechatRespMsgServiceImpl
  *
  * @author zhicheng.xu
 *         15/8/14
 */


@Service
final class WechatRespMsgServiceImpl extends WechatRespMsgService {
  @Autowired
  private val wechatRespMsgDao: WechatRespMsgDao = null
  @Autowired
  private val articlesService: ArticlesService = null
  @Autowired
  private val wechatMenuService: WechatMenuService = null
  @Cacheable(value = Array("wechatRespMsgCache"), key = "#root.method.name+#keywords+#mchtId+#pagaData+#subcribe")
  def findBySql(keywords:String,mchtId: String, pagaData: String,subcribe:String): Map[String, AnyRef] = {
    val list:List[TblWechatMenu] = wechatMenuService.queryWechatMenuList
    val size:Int = list.size()
    val sb:StringBuilder = new StringBuilder
    for (i <- 0 until size) {
      sb.append("'").append(list.get(i).getMenuKey).append("',")
    }
    val a:String = sb.toString()
    var str:String = ""
    if(a.length > 0){
      str = a.substring(0,a.length - 1)
    }
    val query: StringBuilder = new StringBuilder
    query.append("from TblWechatRespMsg")
    query.append(" where 1=1")
    query.append(" AND id.mchtId = '").append(mchtId).append("'")
    query.append(" AND keywords not in (").append(str).append(")")
    query.append(" ORDER BY id.respMsgId")
    val m: Map[String, AnyRef] = wechatRespMsgDao.findByPage(query.toString, pagaData)
    m
  }

  @Transactional
  @CacheEvict(value = Array("wechatRespMsgCache"), allEntries = true)
  def save(data: TblWechatRespMsg, articlesJson: String) {
    //图文Id，以逗号隔开
    val articleId: StringBuilder = new StringBuilder
    //如果传送的是图文消息，则保存简易图文表
    if (articlesJson != null) {
      val jsonparer: JsonParser = new JsonParser
      val json: JsonObject = jsonparer.parse(articlesJson).getAsJsonObject
      val array: JsonArray = json.get("articles").getAsJsonArray
      for (i <- 0 until array.size()) {
        val artData: TblWechatArticlesInf = new TblWechatArticlesInf
        val jsonElement: JsonObject = array.get(i).getAsJsonObject
        val title: String = jsonElement.get("title").getAsString
        val picUrl: String = jsonElement.get("picUrl").getAsString
        val description: String = jsonElement.get("description").getAsString
        val url: String = jsonElement.get("url").getAsString
        artData.setDescription(description)
        artData.setPicUrl(picUrl)
        artData.setTitle(title)
        artData.setUrl(url)
        articlesService.Save(artData)
        articleId.append(artData.getId).append(",")
      }
      data.setArticleIds(articleId.toString.substring(0, articleId.length - 1))
      wechatRespMsgDao.save(data)
    }
    else {
      if(data.getRespType != null){
        val respData:TblWechatRespMsg = getSubscribe(data.getMsgType)
        if(respData != null){
          data.setId(respData.getId)
          wechatRespMsgDao.update(data)
        }else{
          wechatRespMsgDao.save(data)
        }
      }else{
        data.setKeywordType("1")
        wechatRespMsgDao.save(data)
      }
    }
  }

  @Transactional
  @CacheEvict(value = Array("wechatRespMsgCache"), allEntries = true)
  def update(data: TblWechatRespMsg, articlesJson: String) {
    //图文Id，以逗号隔开
    val articleId: StringBuilder = new StringBuilder
    //如果传送的是图文消息，则保存简易图文表
    if (articlesJson != null) {
      //bug区
      //      val id: String = articlesService.getArticlesId
      //      articleId.append(id).append(",")
      data.setArticleIds(articleId.toString)
      wechatRespMsgDao.update(data)
    }
    else {
      wechatRespMsgDao.update(data)
    }
  }

//  @Transactional
//  @Cacheable(value = Array("wechatRespMsgCache"), key = "#root.method.name+#mchtId+#+#")
  def findByMchtType(mchtId: String, `type`: String, keywords: String): List[TblWechatRespMsg] = {
    val sb: StringBuilder = new StringBuilder
    sb.append("from TblWechatRespMsg where id.mchtId='")
    sb.append(mchtId).append("' ")
//    sb.append(" and msgType ='").append(`type`).append("'")
    if (keywords != null) {
      sb.append(" and keywords ='").append(keywords).append("'")
    }
    val result: List[TblWechatRespMsg] = wechatRespMsgDao.find(sb.toString)
    result
  }

  @Transactional
  @Cacheable(value = Array("wechatRespMsgCache"), key = "#root.method.name+#mchtId")
  def findBySubType(mchtId: String): List[TblWechatRespMsg] = {
    val query: StringBuilder = new StringBuilder
    query.append("from TblWechatRespMsg")
    query.append(" where 1=1")
    query.append(" AND id.mchtId = '").append(mchtId).append("'")
    query.append(" and respType ='").append("subscribe").append("'")
    val result: List[TblWechatRespMsg] = wechatRespMsgDao.find(query.toString)
    result
  }

  @Transactional
  @Cacheable(value = Array("wechatRespMsgCache"), key = "#root.method.name+#id")
  def getById(id:TblWechatRespMsgId): TblWechatRespMsg = {
    wechatRespMsgDao.getById(id)
  }

  @Transactional
  @CacheEvict(value = Array("wechatRespMsgCache"), allEntries = true)
  def delete(id:TblWechatRespMsgId) {
    wechatRespMsgDao.deleteById(id)
  }
  //验证回复是否重复
  def check(respId: String, mchtId: String, keywords: String): Int = {
    val sb: StringBuilder = new StringBuilder
    sb.append("from TblWechatRespMsg where id.mchtId='")
    sb.append(mchtId).append("' ")
    sb.append(" and keywords ='").append(keywords).append("'")
//    if (respId != null && !("" == respId)) {
//      sb.append(" AND id.respMsgId <>'").append(respId).append("'")
//    }
    val total: util.List[TblWechatRespMsg] = wechatRespMsgDao.find(sb.toString)
    total.size()
  }

  def getMaxRespId(mchtId:String):String = {
    val hql: String = "SELECT id.respMsgId FROM TblWechatRespMsg where id.mchtId = '" + mchtId + "'"
    val seqList: List[_] = wechatRespMsgDao.find(hql)
    var maxDevation: Integer = 0
    val totalCount: Int = seqList.size()
    if (totalCount >= 1) {
      var max: Integer = Integer.parseInt(seqList.get(0).toString)
      for (i <- 0 until totalCount) {
        val temp: Integer = Integer.parseInt(seqList.get(i).toString)
        if (temp > max) {
          max = temp
        }
        maxDevation = max
      }
    }
    return (maxDevation+1).toString
  }

  def getResp(mchtId:String,keywords:String): TblWechatRespMsg ={
    val sb: StringBuilder = new StringBuilder
    sb.append("from TblWechatRespMsg where id.mchtId='")
    sb.append(mchtId).append("' ")
    sb.append(" and keywords ='").append(keywords).append("'")
    val list:List[TblWechatRespMsg] = wechatRespMsgDao.find(sb.toString())
    list.get(0)
  }

  def getSubscribe(msgType: String): TblWechatRespMsg = {
    val sb: StringBuilder = new StringBuilder
    sb.append("from TblWechatRespMsg")
    sb.append(" where respType = 'subscribe'")
//    sb.append(" and msgType = '").append(msgType).append("'")
    val list:List[TblWechatRespMsg] = wechatRespMsgDao.find(sb.toString())
    if(list.size() == 0){
      return null
    }
    list.get(0)
  }
}
