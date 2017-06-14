package com.ebeijia.module.wechat.dao.base

import java.util.{List, Map}

import com.ebeijia.dao.base.{BaseDao, BaseDaoImplHibernate}
import com.ebeijia.entity.system.page.{Page, PageEntity}
import com.ebeijia.entity.wechat.base.TblWechatMenu
import org.springframework.stereotype.Repository

/**
 * WechatMenuDao
 * @author zhicheng.xu
 *         15/8/18
 */


@Repository("WechatMenuDao") class WechatMenuDao extends BaseDaoImplHibernate[TblWechatMenu] with BaseDao[TblWechatMenu] {
  def getWechatMenuList: List[TblWechatMenu] = {
    val hql: String = "FROM TblWechatMenu "
    this.find(hql)
    //getHibernateTemplate.find(hql).asInstanceOf
  }

  /**
   * 获取实体信息的总数
   * @return Integer 当前实体类在数据库的信息总数
   */
  def countTotalNum(wechatMenu: TblWechatMenu): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblWechatMenu"
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
   * 判断微信菜单名称唯一
   * @return Integer 当前实体类在数据库的信息总数
   */
  def isMenuNameCount(mchtId:String,menuId: String,menuName: String): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblWechatMenu where id.mchtId = '"+mchtId+"' AND TRIM(menuName)='"+menuName.trim+"' and TRIM(id.menuId) not in("+menuId+")"
    (this.getHibernateTemplate.find(hql).get(0).asInstanceOf[Long]).intValue
  }

  /**
   * 判断排序号 唯一
   * */
  def getOrderNoCount(menuId: String,orderNo: String):Integer={
    val hql: String = "SELECT COUNT(*) FROM TblWechatMenu where TRIM(orderNo)='"+orderNo.trim+"' and TRIM(id.menuId) not in("+menuId+")"
    (this.getHibernateTemplate.find(hql).get(0).asInstanceOf[Long]).intValue
  }
}
