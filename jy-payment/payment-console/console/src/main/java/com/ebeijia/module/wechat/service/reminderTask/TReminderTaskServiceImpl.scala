package com.ebeijia.module.wechat.service.reminderTask

import java.util.Map

import com.ebeijia.entity.wechat.reminderTask.TReminderTask
import com.ebeijia.module.wechat.dao.reminderTask.TReminderTaskDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by chen on 2016/7/25.
 */
@Service
class TReminderTaskServiceImpl extends TReminderTaskService{

  @Autowired
  private val tReminderTaskDao:TReminderTaskDao = null

  //通过条件查询结果
//  @Cacheable(value = Array("TReminderTaskCache"), key = "#root.method.name+#id+#pageData")
  @Transactional
  def findBySql(id: String,pageData: String): Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("from TReminderTask")
    query.append(" where 1 = 1 ")

    if(id != null && !"".equals(id) ){
      query.append(" AND id = '").append(id).append("'")
    }

    val m: Map[String, AnyRef] = tReminderTaskDao.findByPage(query.toString(), pageData)
    m
  }

  //缴费提醒 更新
  @Transactional
  @CacheEvict(value = Array("TReminderTaskCache"), allEntries = true)
  def update(data: TReminderTask){
    tReminderTaskDao.update(data)
  }

  //缴费提醒 增加
  @Transactional
  def save(data: TReminderTask){
    tReminderTaskDao.save(data)
  }
  //根据id查找通知
//  @Cacheable(value = Array("TReminderTaskCache"), key = "#root.method.name+#id")
  @Transactional
  def getById(id: Int): TReminderTask = {
    tReminderTaskDao.getById(id)
  }
}
