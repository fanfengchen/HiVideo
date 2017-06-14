package com.ebeijia.module.wechat.service.media

import java.util.Map
import com.ebeijia.module.wechat.dao.base.ArticlesDao
import com.ebeijia.entity.wechat.base.{TblWechatArticlesInfId, TblWechatArticlesInf}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
/**
  * ArticlesServiceImpl
  * @author zhicheng.xu
  *         15/8/14
  */


@Service
final class ArticlesServiceImpl extends ArticlesService {
  @Autowired
  private val articlesDao: ArticlesDao = null

  @Transactional
  @Cacheable(value = Array("articlesCache"))
  def findBySql(mchtId: String, pagaData: String): Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("from TblWechatRespMsg")
    query.append(" where 1=1")
    if (mchtId != null && !("" == mchtId)) {
      query.append(" AND mchtId like '%").append(mchtId).append("%'")
    }
    query.append(" ORDER BY id.mchtId")
    val m: Map[String, AnyRef] = articlesDao.findByPage(query.toString, pagaData)
    m
  }

  @Transactional
  @CacheEvict(value = Array("articlesCache"), allEntries = true)
  def Save(data: TblWechatArticlesInf) {
    articlesDao.save(data)
  }

  @Transactional
  @Cacheable(value = Array("articlesCache"), key = "#root.method.name+#id")
  def getById(id: TblWechatArticlesInfId): TblWechatArticlesInf = {
    articlesDao.getById(id)
  }

  //获取最大索引
  def getMaxSeq():Integer = {
    val hql:String = "SELECT MAX(id.id) FROM TblWechatArticlesInf"
    articlesDao.find(hql).get(0).asInstanceOf[Integer]
  }
  @Transactional
  @CacheEvict(value = Array("articlesCache"), allEntries = true)
  def delArt(artId:String)={
    val hql:String = "DELETE FROM TBL_WECHAT_ARTICLES_INF WHERE ID in ("+artId+")"
    articlesDao.executeSql(hql)
  }

  @Transactional
  @Cacheable(value = Array("articlesCache"), key = "#root.method.name+#artId")
  def getByArtId(artId:String): TblWechatArticlesInf = {
    val hql:String = "FROM TblWechatArticlesInf WHERE id.id = '"+artId+"'"
    val list:java.util.List[TblWechatArticlesInf] = articlesDao.find(hql)
    if(list.isEmpty || list.size() == 0){
      return null
    }
    list.get(0)
  }

  def querydef(artId:String): java.util.List[TblWechatArticlesInf] = {
    val hql:String = "FROM TblWechatArticlesInf WHERE id.id in ("+artId+")"
    val list:java.util.List[TblWechatArticlesInf] = articlesDao.find(hql)
    if(list.isEmpty || list.size() == 0){
      return null
    }
    list
  }
}
