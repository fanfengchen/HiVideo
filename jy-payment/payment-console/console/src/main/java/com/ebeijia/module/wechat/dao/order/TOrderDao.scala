package com.ebeijia.module.wechat.dao.order

import java.util.{ArrayList, List, Map}

import com.ebeijia.dao.base.BaseDaoImplHibernate
import com.ebeijia.entity.system.page.{Page, PageEntity}
import com.ebeijia.entity.wechat.order.TOrder
import org.springframework.stereotype.Repository

/**
 * Created by chen on 2016/7/12.
 */
@Repository("TOrderDao")
class TOrderDao extends BaseDaoImplHibernate[TOrder]{
  def getTOrder:List[_]={
    var tOrder:List[_] = new ArrayList[TOrder]
    val hql:String = "FROM TOrder"
    tOrder  = getHibernateTemplate.find(hql)
    tOrder
  }

  /**
    * 获取实体信息的总数
    * @return Integer 当前实体类在数据库的信息总数
    */
  def countTotalNum(tdd: TOrder): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TOrder"
    (this.getHibernateTemplate.find(hql).get(0).asInstanceOf[Long]).intValue
  }

  /**
    * 分页方法
    * @param query:sql语句
    * @param pageData:分页对象
    * @return Map<String,Object>
    */
  def findByPage(query:String,pageData:String): Map[String,AnyRef] = {
    var page: PageEntity = new PageEntity
    page = Page.init(pageData)
    this.findByPageAndTotal(query.toString, page.getiDisplayStart, page.getiDisplayLength)
  }
}
