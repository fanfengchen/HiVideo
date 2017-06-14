package com.ebeijia.module.system.dao.role

import java.util.{ArrayList, List, Map}

import com.ebeijia.dao.base.{BaseDao, BaseDaoImplHibernate}
import com.ebeijia.entity.system.page.{Page, PageEntity}
import com.ebeijia.entity.system.system.TblSysRoleMap
import org.springframework.stereotype.Repository

/**
 * RoleMapDao
 * @author zhicheng.xu
 *         15/8/13
 */



@Repository("sysRoleMapDao")
class SysRoleMapDao extends BaseDaoImplHibernate[TblSysRoleMap] with BaseDao[TblSysRoleMap] {
  def getTxnLogList: List[_] = {
    var txnLogList: List[_] = new ArrayList[TblSysRoleMap]
    val hql: String = "FROM TblSysRoleMap "
    txnLogList = getHibernateTemplate.find(hql)
    txnLogList
  }

  /**
   * 获取实体信息的总数
   * @return Integer 当前实体类在数据库的信息总数
   */
  def countTotalNum(txnLog: TblSysRoleMap): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblSysRoleMap"
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
   * 判断是否有权限
   * @return Integer 当前实体类在数据库的信息总数
   */
  def roleFunCount(roleId:String,funcId:String): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblSysRoleMap where id.roleId='"+roleId+"' and id.funcId='"+funcId+"'"
    (this.getHibernateTemplate.find(hql).get(0).asInstanceOf[Long]).intValue
  }

}
