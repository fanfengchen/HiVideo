package com.ebeijia.module.wechat.dao.orderDetail

import java.util.{ArrayList, List, Map}

import com.ebeijia.dao.base.BaseDaoImplHibernate
import com.ebeijia.entity.system.page.{Page, PageEntity}
import com.ebeijia.entity.wechat.orderDetail.{TOrderDetail, TOrderDetailT}
import org.springframework.stereotype.Repository

@Repository("TOrderDetailDao")
class TOrderDetailDao extends BaseDaoImplHibernate[TOrderDetailT]{
  def getTNotice: List[_] = {
    var tNotice: List[_] = new ArrayList[TOrderDetailT]
    val hql: String = "FROM TOrderDetail "
    tNotice = getHibernateTemplate.find(hql)
    tNotice
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
