package com.ebeijia.module.wechat.dao.reminderTask

import java.util.{ArrayList, List, Map}

import com.ebeijia.dao.base.BaseDaoImplHibernate
import com.ebeijia.entity.system.page.{Page, PageEntity}
import com.ebeijia.entity.wechat.reminderTask.TReminderTask
import org.springframework.stereotype.Repository

/**
 * Created by chen on 2016/7/25.
 */
@Repository("TReminderTaskDao")
class TReminderTaskDao extends BaseDaoImplHibernate[TReminderTask]{
  def getTReminderTask: List[_] = {
    var tReminderTask: List[_] = new ArrayList[TReminderTask]
    val hql: String = "FROM TReminderTask "
    tReminderTask = getHibernateTemplate.find(hql)
    tReminderTask
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
