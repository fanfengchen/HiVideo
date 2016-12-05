package com.ebeijia.entity.wechat.orderDetail

import java.sql.Timestamp
import javax.persistence._

/**
 * 交易订单详情
 * Created by xf on 2016/6/16.
 */
@Entity
@Table(name = "t_order_detail")
@SerialVersionUID(1L)
class TOrderDetail extends Serializable{
  private var id:Int = 0
  private var orderId:String = null
  private var openId:String = null
  private var accountNo:String = null
  private var accountName:String = null
//  private var type:String = null
  private var userName:String = null
  private var accountDate:String = null
  private var totalFee:Int = 0
  private var payFee:Int = 0
  private var createTime:Timestamp = null
  private var payTime:Timestamp = null
  private var payStatus:String = null
  private var goodsTag:String = null
  private var couponFee:Int = 0
  private var feeType:String = null
  private var payInfo:String = null
  private var attach:String = null
  private var bankType:String = null
  private var tradeType:String = null
  private var transactionId:String = null
  private var bankBillNo:String = null
  private var outTransactionId:String = null
  private var timeEnd:String = null
  private var lsResult:String = null
  private var lsInfo:String = null
  private var lsSerialNo:String = null
  private var lsTime:Int = 0
  private var city:String = null
  private var unit:String = null
  private var refundId:String = null
  private var outRefundNo:String = null
  private var refundChannel:String = null
  private var couponRefundFee:Int = 0
  private var refundFee:Int = 0
  private var reserve1:String = null
  private var payDzStatus:String = null
  private var reserve2:String = null
  private var reserve3:String = null
  private var reserve4:String = null
  private var orgNo:String = null

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  def getId: Int = {
    id
  }
  def setId(id: Int) {
    this.id = id
  }

  @Column(name = "order_id" )
  def getOrderId: String = {
    orderId
  }

  def setOrderId(orderId: String) {
    this.orderId = orderId
  }

  @Column(name = "open_id")
  def getOpenId: String = {
    openId
  }

  def setOpenId(openId: String) {
    this.openId = openId
  }

  @Column(name = "account_no")
  def getAccountNo: String = {
    accountNo
  }

  def setAccountNo(accountNo: String) {
    this.accountNo = accountNo
  }

  @Column(name = "account_name")
  def getAccountName: String = {
    accountName
  }

  def setAccountName(accountName: String) {
    this.accountName = accountName
  }

//  @Column(name = "type", length = 1)
//  def getType: String = {
//    type
//  }
//
//  def setType(type: String) {
//    this.type = type
//  }

  @Column(name = "user_name")
  def getUserName: String = {
    userName
  }

  def setUserName(userName: String) {
    this.userName = userName
  }

  @Column(name = "account_date")
  def getAccountDate: String = {
    accountDate
  }

  def setAccountDate(accountDate: String) {
    this.accountDate = accountDate
  }

  @Column(name = "total_fee", length = 50)
  def getTotalFee: Int = {
    totalFee
  }

  def setTotalFee(totalFee: Int) {
    this.totalFee = totalFee
  }

  @Column(name = "pay_fee", length = 50)
  def getPayFee: Int = {
    payFee
  }

  def setPayFee(payFee: Int) {
    this.payFee = payFee
  }

  @Column(name = "create_time")
  def getCreateTime: Timestamp = {
    createTime
  }

  def setCreateTime(createTime: Timestamp) {
    this.createTime = createTime
  }

  @Column(name = "pay_time")
  def getPayTime: Timestamp = {
    payTime
  }

  def setPayTime(payTime: Timestamp) {
    this.payTime = payTime
  }

  @Column(name = "pay_status")
  def getPayStatus: String = {
    payStatus
  }

  def setPayStatus(payStatus: String) {
    this.payStatus = payStatus
  }

  @Column(name = "goods_tag")
  def getGoodsTag: String = {
    goodsTag
  }

  def setGoodsTag(goodsTag: String) {
    this.goodsTag = goodsTag
  }

  @Column(name = "coupon_fee")
  def getCouponFee: Int = {
    couponFee
  }

  def setCouponFee(couponFee: Int) {
    this.couponFee = couponFee
  }

  @Column(name = "fee_type")
  def getFeeType: String = {
    feeType
  }

  def setFeeType(feeType: String) {
    this.feeType = feeType
  }

  @Column(name = "pay_info")
  def getPayInfo: String = {
    payInfo
  }

  def setPayInfo(payInfo: String) {
    this.payInfo = payInfo
  }

  @Column(name = "attach")
  def getAttach: String = {
    attach
  }

  def setAttach(attach: String) {
    this.attach = attach
  }

  @Column(name = "bank_type")
  def getBankType: String = {
    bankType
  }

  def setBankType(bankType: String) {
    this.bankType = bankType
  }

  @Column(name = "trade_type")
  def getTradeType: String = {
    tradeType
  }

  def setTradeType(tradeType: String) {
    this.tradeType = tradeType
  }

  @Column(name = "transaction_id")
  def getTransactionId: String = {
    transactionId
  }

  def setTransactionId(transactionId: String) {
    this.transactionId = transactionId
  }

  @Column(name = "bank_bill_no")
  def getBankBillNo: String = {
    bankBillNo
  }

  def setBankBillNo(bankBillNo: String) {
    this.bankBillNo = bankBillNo
  }

  @Column(name = "out_transaction_id")
  def getOutTransactionId: String = {
    outTransactionId
  }

  def setOutTransactionId(outTransactionId: String) {
    this.outTransactionId = outTransactionId
  }

  @Column(name = "time_end")
  def getTimeEnd: String = {
    timeEnd
  }

  def setTimeEnd(timeEnd: String) {
    this.timeEnd = timeEnd
  }

  @Column(name = "ls_result")
  def getLsResult: String = {
    lsResult
  }

  def setLsResult(lsResult: String) {
    this.lsResult = lsResult
  }

  @Column(name = "ls_info")
  def getLsInfo: String = {
    lsInfo
  }

  def setLsInfo(lsInfo: String) {
    this.lsInfo = lsInfo
  }

  @Column(name = "ls_serial_no", length = 50)
  def getLsSerialNo: String = {
    lsSerialNo
  }

  def setLsSerialNo(lsSerialNo: String) {
    this.lsSerialNo = lsSerialNo
  }

  @Column(name = "ls_time", length = 20)
  def getLsTime: Int = {
    lsTime
  }

  def setLsTime(lsTime: Int) {
    this.lsTime = lsTime
  }

  @Column(name = "city", length = 50)
  def getCity: String = {
    city
  }

  def setCity(city: String) {
    this.city = city
  }

  @Column(name = "unit", length = 100)
  def getUnit: String = {
    unit
  }

  def setUnit(unit: String) {
    this.unit = unit
  }

  @Column(name = "refund_id", length = 32)
  def getRefundId: String = {
    refundId
  }

  def setRefundId(refundId: String) {
    this.refundId = refundId
  }

  @Column(name = "out_refund_no", length = 32)
  def getOutRefundNo: String = {
    outRefundNo
  }

  def setOutRefundNo(outRefundNo: String) {
    this.outRefundNo = outRefundNo
  }

  @Column(name = "refund_channel", length = 16)
  def getRefundChannel: String = {
    refundChannel
  }

  def setRefundChannel(refundChannel: String) {
    this.refundChannel = refundChannel
  }

  @Column(name = "coupon_refund_fee", length = 20)
  def getCouponRefundFee: Int = {
    couponRefundFee
  }

  def setCouponRefundFee(couponRefundFee: Int) {
    this.couponRefundFee = couponRefundFee
  }

  @Column(name = "refund_fee", length = 20)
  def getRefundFee: Int = {
    refundFee
  }

  def setRefundFee(refundFee: Int) {
    this.refundFee = refundFee
  }

  @Column(name = "reserve1", length = 100)
  def getReserve1: String = {
    reserve1
  }

  def setReserve1(reserve1: String) {
    this.reserve1 = reserve1
  }

  @Column(name = "pay_dz_status", length = 1)
  def getPayDzStatus: String = {
    payDzStatus
  }

  def setPayDzStatus(payDzStatus: String) {
    this.payDzStatus = payDzStatus
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

  @Column(name = "reserve4", length = 100)
  def getReserve4: String = {
    reserve4
  }

  def setReserve4(reserve4: String) {
    this.reserve4 = reserve4
  }

  @Column(name = "org_no", length = 20)
  def getOrgNo: String = {
    orgNo
  }

  def setOrgNo(orgNo: String) {
    this.orgNo = orgNo
  }

}
