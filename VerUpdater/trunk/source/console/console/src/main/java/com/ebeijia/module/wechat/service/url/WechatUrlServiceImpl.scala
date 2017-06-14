package com.ebeijia.module.wechat.service.url

import java.util.Map

import com.ebeijia.module.wechat.dao.base.WechatUrlDao
import com.ebeijia.entity.wechat.base.TblWechatUrl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by ld on 2015/10/29.
 * 微信链接管理
 */
@Service("wechatUrlService")
class WechatUrlServiceImpl extends WechatUrlService{
  @Autowired
  private val wechatUrlDao:WechatUrlDao=null
  /**
   * 根据链接id，类型进行查询
   * 菜单信息
   * */

  @Transactional
  @Cacheable(value = Array("wechatUrlCache"), key = "#root.method.name+#urlId+#urlType+#pageData")
  def  findByUrlSql(urlId: String, urlType: String, pageData: String): Map[String, AnyRef]={
    val query: StringBuilder = new StringBuilder
    query.append("from TblWechatUrl ")
    query.append(" where 1=1")
    //Id
    if (urlId != null && !("" .equals(urlId) )) {
      query.append(" AND urlId like '%").append(urlId).append("%'")

    }
    //类型
    if (urlType != null && !("" .equals(urlType) )) {
      query.append(" AND urlType ='").append(urlType).append("' ")
    }
    query.append(" order by urlId desc")
    wechatUrlDao.findByPage(query.toString,pageData)
  }
  /**删除
    * */
  @Transactional
  @CacheEvict(value = Array("wechatUrlCache"), allEntries = true)
  def delByUrlId(urlId: String)={
    wechatUrlDao.deleteById(urlId)
  }
  /**新增
    * 修改
    * */
  @Transactional
  @CacheEvict(value = Array("wechatUrlCache"), allEntries = true)
  def saveOrUpdateUrl(tblWechatUrl:TblWechatUrl)={
    wechatUrlDao.saveOrUpdate(tblWechatUrl)
  }


  /**判断url唯一
    * */
  @Transactional
  @Cacheable(value = Array("wechatUrlCache"), key = "#root.method.name+#urlId+#url")
  def isUrl(urlId: String,url: String ): Int={
    wechatUrlDao.isUrl(urlId,url)
  }
}
