package com.ebeijia.module.system.dao.softver

import java.util.{ArrayList, List, Map}

import com.ebeijia.dao.base.BaseDaoImplHibernate
import com.ebeijia.entity.system.page.{Page, PageEntity}
import com.ebeijia.entity.system.softver.Softver
import org.springframework.stereotype.Repository

/**
  * Created by xf on 2016/9/8.
  */
@Repository("SoftverDao")
class SoftverDao extends BaseDaoImplHibernate[Softver]{
  def getSoftverList: List[_] = {
    var softverList: List[_] = new ArrayList[Softver]
    val hql: String = " FROM Softver "
    softverList = getHibernateTemplate.find(hql)
    softverList
  }

  /**
    * 获取实体信息的总数
    *
    * @return Integer 当前实体类在数据库的信息总数
    */
  def countTotalNum(softver: Softver): Integer = {
    val hql: String = "SELECT COUNT(*) FROM Softver"
    (this.getHibernateTemplate.find(hql).get(0).asInstanceOf[Long]).intValue
  }

  /**
    * 分页方法
    *
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
