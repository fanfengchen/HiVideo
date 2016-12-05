package com.ebeijia.module.wechat.service.reminderTask

import java.util.Map

import com.ebeijia.entity.wechat.reminderTask.TReminderTask

/**
 * Created by chen on 2016/7/25.
 */
trait TReminderTaskService {
  def findBySql(id:String,pageData: String): Map[String, AnyRef]

  //缴费提醒 增加
  def save(data: TReminderTask)

  //缴费提醒 更新
  def update(data: TReminderTask)

  //根据id查找通知
  def getById(id: Int):TReminderTask
}
