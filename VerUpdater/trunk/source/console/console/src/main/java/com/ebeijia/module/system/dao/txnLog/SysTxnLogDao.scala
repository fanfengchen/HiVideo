package com.ebeijia.module.system.dao.txnLog

import java.util.{ArrayList, List, Map}

import com.ebeijia.dao.base.{BaseDao, BaseDaoImplHibernate}
import com.ebeijia.entity.system.log.TblSysTxnLog
import com.ebeijia.entity.system.page.{Page, PageEntity}
import org.springframework.stereotype.Repository

/**
 * TxnLogDao
 * @author zhicheng.xu
 *         15/8/13
 */



@Repository("sysTxnLogDao")
class SysTxnLogDao extends BaseDaoImplHibernate[TblSysTxnLog] with BaseDao[TblSysTxnLog] {
  def getTxnLogList: List[_] = {
    var txnLogList: List[_] = new ArrayList[TblSysTxnLog]
    val hql: String = "FROM TblSysTxnLog "
    txnLogList = getHibernateTemplate.find(hql)
    txnLogList.asInstanceOf
  }

  /**
   * 获取实体信息的总数
   * @return Integer 当前实体类在数据库的信息总数
   */
  def countTotalNum(txnLog: TblSysTxnLog): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblSysTxnLog"
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
