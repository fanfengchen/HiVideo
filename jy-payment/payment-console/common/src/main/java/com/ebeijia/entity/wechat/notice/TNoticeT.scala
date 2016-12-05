package com.ebeijia.entity.wechat.notice

import javax.persistence._

/**
 * Created by chen on 2016/7/15.
 */
@Entity
@Table(name = "t_notice")
@SerialVersionUID(1L)
class TNoticeT {

  private var id:Int = 0
  private var title:String = null
  private var subTitle:String = null
  private var content:String = null
  private var unit:String = null
  private var beginTime:Long = 0
  private var endTime:Long = 0
  private var createTime:Long = 0
  private var _isActive:String = null

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false, length = 20)
  def getId: Int = {
    id
  }
  def setId(id: Int) {
    this.id = id
  }

  @Column(name = "title", length = 50)
  def getTitle: String = {
    title
  }

  def setTitle(title: String) {
    this.title = title
  }

  @Column(name = "sub_title", length = 100)
  def getSubTitle: String = {
    subTitle
  }

  def setSubTitle(subTitle: String) {
    this.subTitle = subTitle
  }

  @Column(name = "content", length = 1000)
  def getContent: String = {
    content
  }

  def setContent(content: String) {
    this.content = content
  }

  @Column(name = "unit", length = 50)
  def getUnit: String = {
    unit
  }

  def setUnit(unit: String) {
    this.unit = unit
  }

  @Column(name = "begin_time")
  def getBeginTime: Long = {
    beginTime
  }

  def setBeginTime(beginTime: Long) {
    this.beginTime = beginTime
  }

  @Column(name = "end_time" )
  def getEndTime: Long = {
    endTime
  }

  def setEndTime(endTime: Long) {
    this.endTime = endTime
  }

  @Column(name = "create_time")
  def getCreateTime: Long = {
    createTime
  }

  def setCreateTime(createTime: Long) {
    this.createTime = createTime
  }

  @Column(name = "is_active")
  def get_IsActive:String = {
    _isActive
  }

  def set_IsActive(_isActive: String) {
    this._isActive = _isActive
  }



}
