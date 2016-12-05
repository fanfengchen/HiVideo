package com.ebeijia.module.wechat.dao.base

import java.util.{List, Map}

import com.ebeijia.dao.base.{BaseDao, BaseDaoImplHibernate}
import com.ebeijia.entity.system.page.{Page, PageEntity}
import com.ebeijia.entity.wechat.base.TblWechatUrl
import org.springframework.stereotype.Repository

/**
 * Created by ld on 2015/10/29.
 * 微信链接管理
 */
@Repository("WechatUrlDao")
class WechatUrlDao extends BaseDaoImplHibernate[TblWechatUrl] with BaseDao[TblWechatUrl]{
  def getWechatUrlList: List[TblWechatUrl] = {
    val hql: String = "FROM TblWechatUrl "
    this.find(hql)
    //getHibernateTemplate.find(hql).asInstanceOf
  }

  /**
   * 获取实体信息的总数
   * @return Integer 当前实体类在数据库的信息总数
   */
  def countTotalNum(wechatUrl: TblWechatUrl): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblWechatUrl"
   (this.getHibernateTemplate.find(hql).get(0).asInstanceOf[Long]).intValue
  }

  /**
   * 分页方法
   * @param query:sql语句
   * @param aoData:分页对象
   * @return Map<String,Object>
   */
  def findByPage(query: String, aoData: String): Map[String, AnyRef] = {
    var page: PageEntity = new PageEntity
    page = Page.init(aoData)
    this.findByPageAndTotal(query.toString, page.getiDisplayStart, page.getiDisplayLength)
  }

  /**
   * url地址唯一
   * @return Integer 当前实体类在数据库的信息总数
   */
  def isUrl(urlId: String,url: String ): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblWechatUrl where trim(url)='"+url.trim+"' and trim(urlId) not in('"+urlId+"')"
    (this.getHibernateTemplate.find(hql).get(0).asInstanceOf[Long]).intValue
  }
}
