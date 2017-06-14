package com.ebeijia.module.app.dao.ban

import java.util.{ArrayList, List, Map}

import com.ebeijia.dao.base.BaseDaoImplHibernate
import com.ebeijia.entity.app.ban.TblAppBannerInf
import com.ebeijia.entity.system.page.{Page, PageEntity}
import org.springframework.stereotype.Repository

@Repository("AppBannerInfDao")
class AppBannerInfDao extends BaseDaoImplHibernate[TblAppBannerInf]{
  def getBannerList: List[_] = {
    var bannerList: List[_] = new ArrayList[TblAppBannerInf]
    val hql: String = "FROM TblAppBannerInf "
    bannerList = getHibernateTemplate.find(hql)
    bannerList
  }

  /**
    * 获取实体信息的总数
    * @return Integer 当前实体类在数据库的信息总数
    */
  def countTotalNum(tdd: TblAppBannerInf): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblAppBannerInf"
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
    val hql: String = "SELECT MAX(id.banId) FROM TblAppBannerInf"
    val a:Any=this.getHibernateTemplate.find(hql).get(0)
    if(a!=null){
      a.asInstanceOf[Integer].intValue()
    }else{
      0
    }
    // (this.getHibernateTemplate.find(hql).get(0).asInstanceOf[Long]).intValue
  }
}
