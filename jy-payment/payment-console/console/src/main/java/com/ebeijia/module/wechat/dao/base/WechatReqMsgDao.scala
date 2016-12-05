package com.ebeijia.module.wechat.dao.base

import java.util
import java.util.{List, Map}

import com.ebeijia.dao.base.{BaseDao, BaseDaoImplHibernate}
import com.ebeijia.entity.system.page.{Page, PageEntity}
import com.ebeijia.entity.wechat.base.{TblWechatArticlesInf, TblWechatReqMsg}
import org.springframework.stereotype.Repository

/**
 * WechatReqMsgDao
 * @author zhicheng.xu
 *         15/8/18
 */



@Repository("WechatReqMsgDao")
class WechatReqMsgDao extends BaseDaoImplHibernate[TblWechatReqMsg] with BaseDao[TblWechatReqMsg] {
  def getWechatReqMsgList: List[TblWechatReqMsg] = {
    val hql: String = "FROM TblWechatReqMsg "
    var list: List[TblWechatReqMsg] = new util.ArrayList[TblWechatReqMsg]
    this.find(hql)
   // getHibernateTemplate.find(hql).asInstanceOf
  }

  /**
   * 获取实体信息的总数
   * @return Integer 当前实体类在数据库的信息总数
   */
  def countTotalNum(wechatReqMsg: TblWechatReqMsg): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblWechatReqMsg"
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

  def getMaxSeq(mchtId:String):String = {
    val hql: String = "SELECT id.reqMsgId FROM TblWechatReqMsg where id.mchtId = '" + mchtId + "'"
    val seqList: List[_] = this.find(hql)
    var maxDevation: Integer = 0
    val totalCount: Int = seqList.size()
    if (totalCount >= 1) {
      var max: Integer = Integer.parseInt(seqList.get(0).toString)
      for (i <- 0 until totalCount) {
        val temp: Integer = Integer.parseInt(seqList.get(i).toString)
        if (temp > max) {
          max = temp
        }
        maxDevation = max
      }
    }
    return (maxDevation+1).toString
  }

}
