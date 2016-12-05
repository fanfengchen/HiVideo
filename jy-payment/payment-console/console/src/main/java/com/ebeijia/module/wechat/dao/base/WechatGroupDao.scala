package com.ebeijia.module.wechat.dao.base

import java.util.List

import com.ebeijia.dao.base.{BaseDao, BaseDaoImplHibernate}
import com.ebeijia.entity.wechat.base.TblWechatMenuGroup
import org.springframework.stereotype.Repository

/**
 * WechatGroupDao
 * @author zhicheng.xu
 *         15/8/17
 */
@Repository("WechatGroupDao")
class WechatGroupDao extends BaseDaoImplHibernate[TblWechatMenuGroup] with BaseDao[TblWechatMenuGroup] {
  def getWechatConfigList: List[TblWechatMenuGroup] = {
    val hql: String = "FROM TblWechatMenuGroup "
    this.find(hql)
    // getHibernateTemplate.find(hql).asInstanceOf
  }

  /**
   * 获取实体信息的总数
   * @return Integer 当前实体类在数据库的信息总数
   */
  def countTotalNum(wechatConfig: TblWechatMenuGroup): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblWechatMenuGroup"
    (this.getHibernateTemplate.find(hql).get(0).asInstanceOf[Long]).intValue
  }

  /**
   * 分页方法
   * @param page:当前第几页
   * @param size:当前页面每页显示信息的个数
   * @return NewsInfoList<T> 集合
   */
  def findByPage(wechatConfig: TblWechatMenuGroup, page: Int, size: Int): List[TblWechatMenuGroup] = {
    val hql: String = "FROM TblWechatMenuGroup"
    super.findByPage(hql, page, size)
  }
}