package com.ebeijia.module.wechat.dao.base

/**
 * WechatRedpacketDao
 * @author zhicheng.xu
 *         15/8/18
 */

import java.util.{List, Map}

import com.ebeijia.dao.base.{BaseDao, BaseDaoImplHibernate}
import com.ebeijia.entity.system.page.{Page, PageEntity}
import com.ebeijia.entity.wechat.base.TblWechatRedpacket
import org.springframework.stereotype.Repository

@Repository("WechatRedpacketDao")
class WechatRedpacketDao extends BaseDaoImplHibernate[TblWechatRedpacket] with BaseDao[TblWechatRedpacket] {
  def getWechatConfigList: List[TblWechatRedpacket] = {
    val hql: String = "FROM TblWechatRedpacket "
    this.find(hql)
    //getHibernateTemplate.find(hql).asInstanceOf
  }

  /**
   * 获取实体信息的总数
   * @return Integer 当前实体类在数据库的信息总数
   */
  def countTotalNum(wechatConfig: TblWechatRedpacket): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblWechatRedpacket"
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
