package com.ebeijia.entity.wechat.accountInfo

import java.util.Date
import javax.persistence._

/**
 * Created by xf on 2016/7/5.
 */
@Entity
@Table(name = "t_account_info")
@SerialVersionUID(1L)
class TAccountInfo  extends Serializable{
  private var id: TAccountInfoId = null
  private var remarkName:String = null
  private var status:String = null
  private var remiderDate:String = null
  private var city:String = null
  private var unit:String = null
  private var orgNo:String = null
  private var createTime:Date = null
  private var updateTime:Date = null
  private var _isActive:String = null
  private var reserve1:String = null
  private var reserve2:String = null
  private var reserve3:String = null

  @EmbeddedId
  @AttributeOverrides(Array(new AttributeOverride(name = "openId", column = new Column(name = "open_id", nullable = false, length = 50))
    , new AttributeOverride(name = "accountNo", column = new Column(name = "account_no", nullable = false, length = 50))))
  def getId: TAccountInfoId = {
    id
  }

  def setId(id: TAccountInfoId) {
    this.id = id
  }

  @Column(name = "remark_name", length = 50)
  def getRemarkName: String = {
    remarkName
  }

  def setRemarkName(remarkName: String) {
    this.remarkName = remarkName
  }

  @Column(name = "status", length = 1)
  def getStatus: String = {
    status
  }

  def setStatus(status: String) {
    this.status = status
  }

  @Column(name = "remider_date", length = 4)
  def getRemiderDate: String = {
    remiderDate
  }

  def setRemiderDate(remiderDate: String) {
    this.remiderDate = remiderDate
  }

  @Column(name = "city", length = 20)
  def getCity: String = {
    city
  }

  def setCity(city: String) {
    this.city = city
  }

  @Column(name = "unit", length = 50)
  def getUnit: String = {
    unit
  }

  def setUnit(unit: String) {
    this.unit = unit
  }

  @Column(name = "org_no", length = 20)
  def getOrgNo: String = {
    orgNo
  }

  def setOrgNo(orgNo: String) {
    this.orgNo = orgNo
  }

  @Column(name = "create_time")
  def getCreateTime: Date = {
    createTime
  }

  def setCreateTime(createTime: Date) {
    this.createTime = createTime
  }

  @Column(name = "update_time")
  def getUpdateTime: Date = {
    updateTime
  }

  def setUpdateTime(updateTime: Date) {
    this.updateTime = updateTime
  }

  @Column(name = "is_active", length = 1)
  def getIsActive: String = {
    _isActive
  }

  def setIsActive(_isActive: String) {
    this._isActive = _isActive
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
