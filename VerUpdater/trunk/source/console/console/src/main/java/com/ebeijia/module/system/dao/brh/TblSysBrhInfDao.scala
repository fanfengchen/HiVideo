package com.ebeijia.module.system.dao.brh

import java.util.{ArrayList, List, Map}

import com.ebeijia.dao.base.BaseDaoImplHibernate
import com.ebeijia.entity.system.brh.TblSysBrhInf
import com.ebeijia.entity.system.page.{Page, PageEntity}
import org.springframework.stereotype.Repository

@Repository("sysBrhInfDao")
class TblSysBrhInfDao extends BaseDaoImplHibernate[TblSysBrhInf]{
  def getTblBrhList: List[_] = {
    var tbList: List[_] = new ArrayList[TblSysBrhInf]
    val hql: String = "FROM TblSysBrhInf "
    tbList = getHibernateTemplate.find(hql)
    tbList.asInstanceOf
  }

  /**
    * 获取实体信息的总数
    * @return Integer 当前实体类在数据库的信息总数
    */
  def countTotalNum(tb: TblSysBrhInf): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblSysBrhInf"
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
