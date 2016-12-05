package com.ebeijia.module.wechat.dao.accountInfo

import java.util.{ArrayList, List, Map}

import com.ebeijia.dao.base.BaseDaoImplHibernate
import com.ebeijia.entity.system.page.{Page, PageEntity}
import com.ebeijia.entity.wechat.accountInfo.TAccountInfo
import org.springframework.stereotype.Repository

/**
 * Created by xf on 2016/7/5.
 */
@Repository("TAccountInfoDao")
class TAccountInfoDao extends BaseDaoImplHibernate[TAccountInfo] {
  def getTNotice: List[_] = {
    var tAccountInfo: List[_] = new ArrayList[TAccountInfo]
    val hql: String = "FROM TAccountInfo "
    tAccountInfo = getHibernateTemplate.find(hql)
    tAccountInfo
  }

  /**
   * 获取实体信息的总数
   * @return Integer 当前实体类在数据库的信息总数
   */
  def countTotalNum(tdd: TAccountInfo): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TAccountInfo"
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
