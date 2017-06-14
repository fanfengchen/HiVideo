package com.ebeijia.module.wechat.dao.base

import java.util.{ArrayList, List, Map}

import com.ebeijia.dao.base.{BaseDao, BaseDaoImplHibernate}
import com.ebeijia.entity.system.page.{Page, PageEntity}
import com.ebeijia.entity.wechat.base.TblWechatFodder
import org.springframework.stereotype.Repository

/**
 * WechatFodderDao
 * @author zhicheng.xu
 *         15/8/17
 */


@Repository("WechatFodderDao")
class WechatFodderDao extends BaseDaoImplHibernate[TblWechatFodder] with BaseDao[TblWechatFodder] {
  def getWechatConfigList: List[TblWechatFodder] = {
    var wechatConfigList: List[TblWechatFodder] = new ArrayList[TblWechatFodder]
    val hql: String = "FROM TblWechatFodder "
    this.find(hql)
   // getHibernateTemplate.find(hql).asInstanceOf
  }

  /**
   * 获取实体信息的总数
   * @return Integer 当前实体类在数据库的信息总数
   */
  def countTotalNum(wechatConfig: TblWechatFodder): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblWechatFodder"
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
}
