package com.ebeijia.module.ttuition.dao

import java.util.{ArrayList, List, Map}

import com.ebeijia.dao.base.BaseDaoImplHibernate
import com.ebeijia.entity.system.page.{Page, PageEntity}
import com.ebeijia.entity.ttuition.Ttuition
import org.springframework.stereotype.Repository

@Repository("TuitionDao")
class TtuitionDao extends BaseDaoImplHibernate[Ttuition]{
  def getTnurseryTuition: List[_] = {
    var tnurseryTuition: List[_] = new ArrayList[Ttuition]
    val hql: String = "FROM Ttuition "
    tnurseryTuition = getHibernateTemplate.find(hql)
    tnurseryTuition
  }

  /**
    * 获取实体信息的总数
    * @return Integer 当前实体类在数据库的信息总数
    */
  def countTotalNum(tdd: Ttuition): Integer = {
    val hql: String = "SELECT COUNT(*) FROM ttuition"
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
