package com.ebeijia.module.system.dao.operatorInf

import java.util.{ArrayList, List, Map}

import com.ebeijia.dao.base.BaseDaoImplHibernate
import com.ebeijia.entity.system.page.{Page, PageEntity}
import com.ebeijia.entity.system.system.TblSysOperatorInf
import org.springframework.stereotype.Repository

/**
 * AdminDao
 * @author zhicheng.xu
 *         15/8/4
 */
@Repository("sysOperatorInfDao")
class SysOperatorInfDao extends BaseDaoImplHibernate[TblSysOperatorInf]  {
  def getOperatorInfList: List[_] = {
    var operatorInfList: List[_] = new ArrayList[TblSysOperatorInf]
    val hql: String = "FROM TblSysOperatorInf "
    operatorInfList = getHibernateTemplate.find(hql)
    operatorInfList
  }

  /**
   * 获取实体信息的总数
   * @return Integer 当前实体类在数据库的信息总数
   */
  def countTotalNum(operatorInf: TblSysOperatorInf): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblSysOperatorInf"
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
   * 获取实体信息的判断管理员名称不重复
   * @return Integer 判断管理员名称不重复
   */
  def isOperatorName(operatorId: Int,operatorName: String): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblSysOperatorInf where TRIM(oprName)='"+operatorName.trim+"' and TRIM(id.oprId) not in ('"+operatorId+"')"
    (this.getHibernateTemplate.find(hql).get(0).asInstanceOf[Long]).intValue
  }

}
