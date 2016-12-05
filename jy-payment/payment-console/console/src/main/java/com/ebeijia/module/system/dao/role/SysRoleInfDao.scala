package com.ebeijia.module.system.dao.role

import java.util.{ArrayList, List, Map}

import com.ebeijia.dao.base.{BaseDao, BaseDaoImplHibernate}
import com.ebeijia.entity.system.page.{Page, PageEntity}
import com.ebeijia.entity.system.system.TblSysRoleInf
import org.springframework.stereotype.Repository

/**
 * RoleInfDao
 * @author zhicheng.xu
 *         15/8/13
 */

@Repository("sysRoleInfDao")
class SysRoleInfDao extends BaseDaoImplHibernate[TblSysRoleInf] with BaseDao[TblSysRoleInf] {
  def getRoleInfList: List[_] = {
    var txnLogList: List[_] = new ArrayList[TblSysRoleInf]
    val hql: String = "FROM TblSysRoleInf "
    txnLogList = getHibernateTemplate.find(hql)
    txnLogList
  }

  /**
   * 获取实体信息的总数
   * @return Integer 当前实体类在数据库的信息总数
   */
  def countTotalNum(txnLog: TblSysRoleInf): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblSysRoleInf"
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
   * 获取实体信息的id最大值
   * @return Integer 当前实体类在数据库的信息总数
   */
  def getMaxId(): Integer = {
    val hql: String = "SELECT MAX(id.ruleId) FROM TblSysRoleInf"
    val a:Any=this.getHibernateTemplate.find(hql).get(0)
    if(a!=null){
      a.asInstanceOf[Integer].intValue()
    }else{
      0
    }
    // (this.getHibernateTemplate.find(hql).get(0).asInstanceOf[Long]).intValue
  }

  /**
   * 判断角色名称
   * @return Integer
   */
  def isRoleName(roleId:Int,roleName:String): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblSysRoleInf where trim(roleName) ='"+roleName.trim+"' AND trim(id.ruleId) not in('"+roleId+"')"
    (this.getHibernateTemplate.find(hql).get(0).asInstanceOf[Long]).intValue
  }

}
