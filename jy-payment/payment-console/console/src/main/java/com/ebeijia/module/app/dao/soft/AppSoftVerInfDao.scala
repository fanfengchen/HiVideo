package com.ebeijia.module.app.dao.soft

import java.util.{ArrayList, List, Map}

import com.ebeijia.dao.base.BaseDaoImplHibernate
import com.ebeijia.entity.app.soft.TblAppSoftVerInf
import com.ebeijia.entity.system.page.{Page, PageEntity}
import org.springframework.stereotype.Repository

@Repository("AppSoftVerInfDao")
class AppSoftVerInfDao extends BaseDaoImplHibernate[TblAppSoftVerInf]{
  def getSoftVerInfList: List[_] = {
    var softVerInfList: List[_] = new ArrayList[TblAppSoftVerInf]
    val hql: String = "FROM TblAppSoftVerInf "
    softVerInfList = getHibernateTemplate.find(hql)
    softVerInfList
  }

  /**
    * 获取实体信息的总数
    * @return Integer 当前实体类在数据库的信息总数
    */
  def countTotalNum(softVerInf: TblAppSoftVerInf): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblAppSoftVerInf"
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
    val hql: String = "SELECT MAX(id.artId) FROM TblAppDeployDocument"
    val a:Any=this.getHibernateTemplate.find(hql).get(0)
    if(a!=null){
      a.asInstanceOf[Integer].intValue()
    }else{
      0
    }
    // (this.getHibernateTemplate.find(hql).get(0).asInstanceOf[Long]).intValue
  }
}
