package com.ebeijia.entity.wechat.notice

import java.util.Date
import javax.persistence._

/**
 * 通知公告
 * Created by xf on 2016/6/13.
 */

@Entity
@Table(name = "t_notice")
@SerialVersionUID(1L)
class TNotice extends Serializable{
  private var id:Int = 0
  private var title:String = null
  private var subTitle:String = null
  private var content:String = null
  private var unit:String = null
  private var beginTime:Date = null
  private var endTime:Date = null
  private var createTime:Date = null
  private var orgNo:String = null
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
  def getBeginTime: Date = {
    beginTime
  }

  def setBeginTime(beginTime: Date) {
    this.beginTime = beginTime
  }

  @Column(name = "end_time")
  def getEndTime: Date = {
    endTime
  }

  def setEndTime(endTime: Date) {
    this.endTime = endTime
  }

  @Column(name = "create_time")
  def getCreateTime: Date = {
    createTime
  }

  def setCreateTime(createTime: Date) {
    this.createTime = createTime
  }

  @Column(name = "org_no", length = 20)
  def getOrgNo: String = {
    orgNo
  }

  def setOrgNo(orgNo: String) {
    this.orgNo = orgNo
  }
}
