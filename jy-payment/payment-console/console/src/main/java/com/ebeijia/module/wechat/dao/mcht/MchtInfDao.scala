package com.ebeijia.module.wechat.dao.mcht

import java.util.{List, Map}

import com.ebeijia.dao.base.{BaseDao, BaseDaoImplHibernate}
import com.ebeijia.entity.system.page.{Page, PageEntity}
import com.ebeijia.entity.system.system.TblSysMchtInf
import org.hibernate.{Query, Session}
import org.springframework.stereotype.Repository

/**
 * MchtInfDao
 * @author zhicheng.xu
 *         15/8/17
 */
@Repository("mchtInfDao")
class MchtInfDao extends BaseDaoImplHibernate[TblSysMchtInf] with BaseDao[TblSysMchtInf] {
  @SuppressWarnings(Array("unchecked")) def getMchtInfList: List[TblSysMchtInf] = {
    var mchtInfList: List[TblSysMchtInf] = null
    val hql: String = "FROM TblSysMchtInf "
    this.find(hql)
  }

  /**
   * 获取实体信息的总数
   * @return Integer 当前实体类在数据库的信息总数
   */
  def countTotalNum(mchtInf: TblSysMchtInf): Integer = {
    val hql: String = "SELECT COUNT(*) FROM TblSysMchtInf"
    (this.getHibernateTemplate.find(hql).get(0).asInstanceOf[Long]).intValue
  }

  /**
   * 分页方法
   * @param page:当前第几页
   * @param size:当前页面每页显示信息的个数
   * @return NewsInfoList<T> 集合
   */
  def findByPage(mchtInf: TblSysMchtInf, page: Int, size: Int): List[TblSysMchtInf] = {
    val hql: String = "FROM TblSysMchtInf"
    super.findByPage(hql, page, size)
  }

  /**
   * 分页方法(商户平台ID)
   * @param mchtInf
   * @param page 当前第几页
   * @param size 当前页面每页显示信息的个数
   * @param mchtPlatId 商户平台ID
   * @return
   */
  def findByPage(mchtPlatId: String, mchtInf: TblSysMchtInf, page: Int, size: Int): List[TblSysMchtInf] = {
    val hql: String = "FROM TblSysMchtInf WHERE trim(mchtPlatId) ='" + mchtPlatId + "' AND mchtType != '99' ORDER BY mchtId"
    super.findByPage(hql, page, size)
  }

  /**
   * 获取商户ID(根据商户平台ID和request商户ID)
   *
   * @param mchtPlatId 商户平台ID
   * @param merchantId request商户ID
   * @return String 商户ID
   */
  @SuppressWarnings(Array("unchecked"))
  def findMchtIdByMerchant(mchtPlatId: String, merchantId: String): String = {
    var mchtId: String = ""
    val hql: String = "select mcht_id,mcht_stat FROM TblSysMchtInf WHERE TRIM(mcht_Plat_Id) = '" + mchtPlatId + "' AND TRIM(ptr_Mcht_Id) = '" + merchantId + "' "
    val listSql: List[_] = super.getListSQL(hql)

    if (null != listSql) {
      val tblMchtInfList: List[Array[AnyRef]] = listSql.asInstanceOf[List[Array[AnyRef]]]
      if (!tblMchtInfList.isEmpty) {
        if ("1" == tblMchtInfList.get(0)(1).toString) {
          mchtId = tblMchtInfList.get(0)(0).toString
        }
      }
    }

    mchtId
  }

  /**
   * 分页获取商户列表
   *
   * @param hql
   * @return
   */
  @SuppressWarnings(Array("unchecked"))
  def findObject(hql: String, page: Int, size: Int): List[Array[AnyRef]] = {
    val session: Session = getHibernateTemplate.getSessionFactory.openSession
    val q: Query = session.createQuery(hql)
    q.setFirstResult((page - 1) * size)
    q.setMaxResults(size)
    val list: List[_] = q.list()
    var anyList: List[Array[AnyRef]] = null
    if (null != list) {
      anyList = list.asInstanceOf[List[Array[AnyRef]]]
    }
    anyList
    //q.list.asInstanceOf
  }

  /**
   * 获得查询的所有数据  返回的是数组
   * @param hql
   * @return
   */
  @SuppressWarnings(Array("unchecked"))
  def findObject(hql: String): List[Array[AnyRef]] = {
    val session: Session = getHibernateTemplate.getSessionFactory.openSession
    val q: Query = session.createQuery(hql)
    val list: List[_] = q.list()
    var anyList: List[Array[AnyRef]] = null
    if (null != list) {
      anyList = list.asInstanceOf[List[Array[AnyRef]]]
    }
    anyList
    //  q.list.asInstanceOf
  }

  /**
   * 获得查询的所有数据  返回的是非数组
   * @param hql
   * @return
   */
  @SuppressWarnings(Array("unchecked")) def findObject2(hql: String): List[AnyRef] = {
    val session: Session = getHibernateTemplate.getSessionFactory.openSession
    val q: Query = session.createQuery(hql)
    val list: List[_] = q.list()
    var anyList: List[AnyRef] = null
    if (null != list) {
      anyList = list.asInstanceOf[List[AnyRef]]
    }
    anyList
    // session.createQuery(hql).asInstanceOf
  }

  def findAllObject(hql: String): List[Array[AnyRef]] = {
    val list: List[_] = getHibernateTemplate.find(hql)
    var anyList: List[Array[AnyRef]] = null
    if (null != list) {
      anyList = list.asInstanceOf[List[Array[AnyRef]]]
    }
    anyList
  }

  def findMchtList: List[Array[AnyRef]] = {
    val sb: StringBuilder = new StringBuilder
    sb.append("select MCHT_ID,MCHT_NAME ")
    sb.append("from TblSysMchtInf ")
    sb.append(" where MCHT_ID not in(select MCHT_ID from TBL_WECHAT_MCHT_INF) ")
    sb.append("order by MCHT_LVL,MCHT_ID")
    val listSql: List[_] = super.getListSQL(sb.toString)
    var anyList: List[Array[AnyRef]] = null
    if (null != listSql) {
      anyList = listSql.asInstanceOf[List[Array[AnyRef]]]
    }
    anyList
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