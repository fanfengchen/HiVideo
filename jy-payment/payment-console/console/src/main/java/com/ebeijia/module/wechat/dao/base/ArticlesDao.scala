package com.ebeijia.module.wechat.dao.base

/**
 * ArticlesDao
 * @author zhicheng.xu
 *         15/8/17
 */

import java.util.{List, Map}

import com.ebeijia.dao.base.{BaseDao, BaseDaoImplHibernate}
import com.ebeijia.entity.system.page.{Page, PageEntity}
import com.ebeijia.entity.wechat.base.TblWechatArticlesInf
import org.springframework.stereotype.Repository

@Repository("ArticlesDao")
class ArticlesDao extends BaseDaoImplHibernate[TblWechatArticlesInf] with BaseDao[TblWechatArticlesInf] {
  def getWechatRespMsgList: List[TblWechatArticlesInf] = {

    val hql: String = "FROM TblArticlesInf "
    this.find(hql)

  }

  /**
   * 获取实体信息的总数
   * @return Integer 当前实体类在数据库的信息总数
   */
  def countTotalNum(wechatRespMsg: TblWechatArticlesInf): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblWechatArticlesInf"
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

  def getMaxSeq(): String ={
    var seq:String = ""
    val hql:String = "SELECT MAX(id.id) FROM TblWechatArticlesInf"
    val list:List[TblWechatArticlesInf] = this.find(hql)
    if(list.get(0) == null){
      seq ="1"
    }else{
      seq = (Integer.parseInt(this.find(hql).get(0).toString)+1).toString
    }
    seq
  }
}
