package com.ebeijia.entity.wechat.orderDetail

import javax.persistence._

/**
 * Created by chen on 2016/7/15.
 */
@Entity
@Table(name = "t_order_detail")
@SerialVersionUID(1L)
class TOrderDetailT {
  private var id:Integer = 0
  private var orderId:Integer =0
  private var itemType:Integer = 0
  private var itemDesc:String = null
  private var itemSum:Integer = 0
  private var preStatus:String = null
  private var sufStatus:String = null
  private var payTime:String = null
  private var heatNo:String = null
  private var heatArea:String = null
  private var heatAddress:String = null
  private var remark:String = null
  private var unpaidSum:Integer = 0
  private var reserve1:String = null
  private var reserve2:String = null
  private var reserve3:String = null
  private var reserve4:String = null

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  def getId: Integer = {
    id
  }

  def setId(id: Integer) {
    this.id = id
  }
  @Column(name = "order_id" )
  def  getOrderId:Integer = {
    orderId
  }
  def setOrderId(orderId:Integer) {
    this.orderId = orderId
  }
  @Column(name = "item_type" )
  def getItemType: Integer = {
    itemType
  }

  def setItemType(itemType: Integer) {
    this.itemType = itemType
  }
  @Column(name = "item_desc" )
  def getItemDesc:String={
    itemDesc
  }
  def setItemDesc(itemDesc:String){
    this.itemDesc=itemDesc
  }
  @Column(name = "item_sum" )
  def getItemSum: Integer = {
    itemSum
  }

  def setItemSum(itemSum: Integer) {
    this.itemSum = itemSum
  }
  @Column(name = "pre_status" )
  def getPreStatus:String={
    preStatus
  }
  def setPreStatus(preStatus:String){
    this.preStatus=preStatus
  }
  @Column(name = "suf_status" )
  def getSufStatus:String={
    sufStatus
  }
  def setSufStatus(sufStatus:String){
    this.sufStatus=sufStatus
  }
  @Column(name = "pay_time" )
  def getPayTime: String = {
    payTime
  }

  def setPayTime(payTime: String) {
    this.payTime = payTime
  }
  @Column(name = "heat_no" )
  def getHeatNo:String={
    heatNo
  }
  def setHeatNo(heatNo:String){
    this.heatNo=heatNo
  }
  @Column(name = "heat_area" )
  def getHeatArea:String={
    heatArea
  }
  def setHeatArea(heatArea:String){
    this.heatArea=heatArea
  }
  @Column(name = "heat_address" )
  def getHeatAddress:String={
    heatAddress
  }
  def setHeatAddress(heatAddress:String){
    this.heatAddress=heatAddress
  }
  @Column(name = "remark" )
  def getRemark:String={
    remark
  }
  def setRemark(remark:String){
    this.remark=remark
  }
  @Column(name = "unpaid_sum" )
  def getUnpaidSum: Integer = {
    unpaidSum
  }

  def setUnpaidSum(unpaidSum: Integer) {
    this.unpaidSum = unpaidSum
  }
  @Column(name = "reserve1" )
  def getReserve1:String={
    reserve1
  }
  def setReserve1(reserve1:String){
    this.reserve1 = reserve1
  }
  @Column(name = "reserve2" )
  def getReserve2:String={
    reserve2
  }
  def setReserve2(reserve2:String){
    this.reserve2 = reserve2
  }
  @Column(name = "reserve3" )
  def getReserve3:String={
    reserve3
  }
  def setReserve3(reserve3:String){
    this.reserve3 = reserve3
  }
  @Column(name = "reserve4" )
  def getReserve4:String={
    reserve4
  }
  def setReserve4(reserve4:String){
    this.reserve4 = reserve4
  }

}
