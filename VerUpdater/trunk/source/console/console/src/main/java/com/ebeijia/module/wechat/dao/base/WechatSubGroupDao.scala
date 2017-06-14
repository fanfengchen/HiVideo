package com.ebeijia.module.wechat.dao.base

import java.util.{List, Map}

import com.ebeijia.dao.base.{BaseDao, BaseDaoImplHibernate}
import com.ebeijia.entity.system.page.{Page, PageEntity}
import com.ebeijia.entity.wechat.base.TblWechatSubGroup
import org.springframework.stereotype.Repository

/**
 * WechatSubGroupDao
 * @author zhicheng.xu
 *         15/8/18
 */


@Repository("WechatSubGroupDao")
class WechatSubGroupDao extends BaseDaoImplHibernate[TblWechatSubGroup] with BaseDao[TblWechatSubGroup] {
  def getWechatMchtInfList: List[TblWechatSubGroup] = {
    val hql: String = "FROM TblWechatSubGroup "
    this.find(hql)
   // getHibernateTemplate.find(hql).asInstanceOf
  }

  /**
   * 获取实体信息的总数
   * @return Integer 当前实体类在数据库的信息总数
   */
  def countTotalNum(wechatMchtInf: TblWechatSubGroup): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblWechatSubGroup"
    (this.getHibernateTemplate.find(hql).get(0).asInstanceOf[Long]).intValue
  }

  /**
   * 分页方法
   * @param page:当前第几页
   * @param size:当前页面每页显示信息的个数
   * @return NewsInfoList<T> 集合
   */
  def findByPage(wechatMchtInf: TblWechatSubGroup, page: Int, size: Int): List[TblWechatSubGroup] = {
    val hql: String = "FROM TblWechatSubGroup"
    super.findByPage(hql, page, size)
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
   * 判断唯一
   * @return Integer  判断唯一
   */
  def isSubGroup(id:Int,name:String): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblWechatSubGroup where trim(name)='"+name.trim+"' and trim(id) not in('"+id+"')"
    (this.getHibernateTemplate.find(hql).get(0).asInstanceOf[Long]).intValue
  }
}
