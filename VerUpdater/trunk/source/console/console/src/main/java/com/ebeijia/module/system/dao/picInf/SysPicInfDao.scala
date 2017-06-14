package com.ebeijia.module.system.dao.picInf

import java.util.{ArrayList, List, Map}

import com.ebeijia.dao.base.{BaseDao, BaseDaoImplHibernate}
import com.ebeijia.entity.system.page.{Page, PageEntity}
import com.ebeijia.entity.system.system.TblSysPicInf
import org.springframework.stereotype.Repository

/**
 * PicInfDao
 * @author zhicheng.xu
 *         15/8/13
 */

@Repository("sysPicInfDao")
class SysPicInfDao extends BaseDaoImplHibernate[TblSysPicInf] with BaseDao[TblSysPicInf] {
  def getPicInfList: List[_] = {
    var picInfList: List[_] = new ArrayList[TblSysPicInf]
    val hql: String = "FROM TblSysPicInf "
    picInfList = getHibernateTemplate.find(hql)
    picInfList
  }

  /**
   * 获取实体信息的总数
   * @return Integer 当前实体类在数据库的信息总数
   */
  def countTotalNum(picInf: TblSysPicInf): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblSysPicInf"
    (this.getHibernateTemplate.find(hql).get(0).asInstanceOf[Long]).intValue
  }

  /**
   * 分页方法
   * @param page:当前第几页
   * @param size:当前页面每页显示信息的个数
   * @return NewsInfoList<T> 集合
   */
  def findByPage(picInf: TblSysPicInf, page: Int, size: Int): List[TblSysPicInf] = {
    val hql: String = "FROM TblSysPicInf"
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
   * 获取实体信息的id最大值
   * @return Integer 当前实体类在数据库的信息总数
   */
  def getMaxId(): Integer = {
    val hql: String = "SELECT MAX(picId) FROM TblSysPicInf"
    val a:Any=this.getHibernateTemplate.find(hql).get(0)
    if(a!=null){
      a.asInstanceOf[Integer].intValue()
    }else{
      0
    }
    // (this.getHibernateTemplate.find(hql).get(0).asInstanceOf[Long]).intValue
  }

}
