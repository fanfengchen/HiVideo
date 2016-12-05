package com.ebeijia.entity.wechat.reminderTask

import java.util.Date
import javax.persistence._

/**
 * Created by chen on 2016/7/25.
 */
@Entity
@Table(name = "t_reminder_task")
@SerialVersionUID(1L)
class TReminderTask extends java.io.Serializable{
  private var id:Int = 0
  private var taskName:String = null
  private var excuteDay:Int = 0
  private var startTime:String = null
  private var endTime:String = null
  private var frequency:Int = 0
  private var targetPeriod:Int = 0
  private var createAt:Date = null
  private var status:Int = 0
  private var reserve1:String = null
  private var reserve2:String = null
  private var reserve3:String = null

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false, length = 20)
  def getId: Int = {
    id
  }
  def setId(id: Int) {
    this.id = id
  }
  @Column(name = "task_name")
  def getTaskName:String = {
    taskName
  }
  def setTaskName(taskName:String)={
    this.taskName = taskName
  }
  @Column(name = "excute_day")
  def getExcuteDay:Int = {
    excuteDay
  }
  def setExcuteDay(excuteDay:Int)={
    this.excuteDay = excuteDay
  }
  @Column(name = "start_time")
  def getStartTime:String = {
    startTime
  }
  def setStartTime(startTime:String)={
    this.startTime = startTime
  }
  @Column(name = "end_time")
  def getEndTime:String = {
    endTime
  }
  def setEndTime(endTime:String)={
    this.endTime = endTime
  }
  @Column(name = "frequency")
  def getFrequency:Int = {
    frequency
  }
  def setFrequency(frequency:Int)={
    this.frequency = frequency
  }
  @Column(name = "target_period")
  def getTargetPeriod:Int = {
    targetPeriod
  }
  def setTargetPeriod(targetPeriod:Int)={
    this.targetPeriod = targetPeriod
  }
  @Column(name = "create_at")
  def getCreateAt:Date = {
    createAt
  }
  def setCreateAt(createAt:Date)={
    this.createAt = createAt
  }
  @Column(name = "status")
  def getStatus:Int = {
    status
  }
  def setStatus(status:Int)={
    this.status = status
  }
  @Column(name = "reserve1", length = 100)
  def getReserve1: String = {
    reserve1
  }

  def setReserve1(reserve1: String) {
    this.reserve1 = reserve1
  }
  @Column(name = "reserve2", length = 100)
  def getReserve2: String = {
    reserve2
  }

  def setReserve2(reserve2: String) {
    this.reserve2 = reserve2
  }

  @Column(name = "reserve3", length = 100)
  def getReserve3: String = {
    reserve3
  }

  def setReserve3(reserve3: String) {
    this.reserve3 = reserve3
  }
}
