package com.ebeijia.entity.wechat.order

import javax.persistence._


/**
 * Created by chen on 2016/7/11.
 */

@Entity
@Table(name = "t_order")
@SerialVersionUID(1L)
class TOrder{
  private var id:Int=0
  private var userId:Int=0
  private var accountNo:String=null
  private var remarkName:String=null
  private var _type:String = null
  private var accountDate:String=null
  private var totalFee:Int = 0
  private var payFee:Int = 0
  private var createTime:Long=0
  private var payTime:Long = 0

  private var payStatus:String = null
  private var outTradeNo:String = null
  private var goodsTag:String = null
  private var couponFee:Integer = null
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
  private var lsTime:String = null
  private var city:String = null
  private var unit:String = null
  private var refundId:String = null
  private var outRefundNo:String = null
  private var refundChannel:String = null
  private var couponRefundFee:Integer = null
  private var refundFee:Integer = null
  private var _isActive:String = null
  private var reserve1:String = null
  private var reserve2:String = null
  private var reserve3:String = null
  private var reserve4:String = null

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  def getId: Int = {
    id
  }
  def setId(id: Int) {
    this.id = id
  }

  @Column(name = "user_id" )
  def  getUserId:Int = {
    userId
  }
  def setUserId(userId:Int) {
    this.userId = userId
  }

  @Column(name = "account_no" )
  def getAccountNo:String={
    accountNo
  }
  def setAccountNo(accountNo:String){
    this.accountNo=accountNo
  }

  @Column(name = "remark_name" )
  def getRemarkName:String = {
    remarkName
  }
  def setRemarkName(remarkName:String) {
    this.remarkName = remarkName
  }

  @Column(name = "type" )
  def getType:String = {
    _type
  }
  def setType(_type:String) {
    this._type = _type
  }

  @Column(name = "account_date" )
  def getAccountDate:String={
    accountDate
  }
  def setAccountDate(accountDate:String){
    this.accountDate = accountDate
  }

  @Column(name = "total_fee" )
  def getTotalFee:Int={
    totalFee
  }
  def setTotalFee(totalFee:Int){
    this.totalFee = totalFee
  }

  @Column(name = "pay_fee" )
  def getPayFee:Int={
    payFee
  }
  def setPayFee(payFee:Int){
    this.payFee = payFee
  }

  @Column(name = "create_time" )
  def getCreateTime:Long={
    createTime
  }
  def setCreateTime(createTime:Long){
    this.createTime = createTime
  }

  @Column(name = "pay_time" )
  def getPayTime:Long={
    payTime
  }
  def setPayTime(payTime:Long){
    this.payTime = payTime
  }

  @Column(name = "pay_status" )
  def getPayStatus:String={
    payStatus
  }
  def setPayStatus(payStatus:String){
    this.payStatus = payStatus
  }

  @Column(name = "out_trade_no" )
  def getOutTradeNo:String={
    outTradeNo
  }
  def setOutTradeNo(outTradeNo:String){
    this.outTradeNo = outTradeNo
  }

  @Column(name = "goods_tag" )
  def getGoodsTag:String={
    goodsTag
  }
  def setGoodsTag(goodsTag:String){
    this.goodsTag = goodsTag
  }

  @Column(name = "coupon_fee" )
  def getCouponFee:Integer={
    couponFee
  }
  def setCouponFee(couponFee:Integer){
    this.couponFee = couponFee
  }

  @Column(name = "fee_type" )
  def getFeeType:String={
    feeType
  }
  def setFeeType(feeType:String){
    this.feeType = feeType
  }

  @Column(name = "pay_info" )
  def getPayInfo:String={
    payInfo
  }
  def setPayInfo(payInfo:String){
    this.payInfo = payInfo
  }

  @Column(name = "attach" )
  def getAttach:String={
    attach
  }
  def setAttach(attach:String){
    this.attach = attach
  }

  @Column(name = "bank_type" )
  def getBankType:String={
    bankType
  }
  def setBankType(bankType:String){
    this.bankType = bankType
  }

  @Column(name = "trade_type" )
  def getTradeType:String={
    tradeType
  }
  def setTradeType(tradeType:String){
    this.tradeType = tradeType
  }

  @Column(name = "transaction_id" )
  def getTransactionId:String={
    transactionId
  }
  def setTransactionId(transactionId:String){
    this.transactionId = transactionId
  }

  @Column(name = "bank_bill_no" )
  def getBankBillNo:String={
    bankBillNo
  }
  def setBankBillNo(bankBillNo:String){
    this.bankBillNo = bankBillNo
  }

  @Column(name = "out_transaction_id" )
  def getOutTransactionId:String={
    outTransactionId
  }
  def setOutTransactionId(outTransactionId:String){
    this.outTransactionId = outTransactionId
  }

  @Column(name = "time_end" )
  def getTimeEnd:String={
    timeEnd
  }
  def setTimeEnd(timeEnd:String){
    this.timeEnd = timeEnd
  }

  @Column(name = "ls_result" )
  def getLsResult:String={
    lsResult
  }
  def setLsResult(lsResult:String){
    this.lsResult = lsResult
  }

  @Column(name = "ls_info" )
  def getLsInfo:String={
    lsInfo
  }
  def setLsInfo(lsInfo:String){
    this.lsInfo = lsInfo
  }

  @Column(name = "ls_serial_no" )
  def getLsSerialNo:String={
    lsSerialNo
  }
  def setLsSerialNo(lsSerialNo:String){
    this.lsSerialNo = lsSerialNo
  }

  @Column(name = "ls_time" )
  def getLsTime:String={
    lsTime
  }
  def setLsTime(lsTime:String){
    this.lsTime = lsTime
  }

  @Column(name = "city" )
  def getCity:String={
    city
  }
  def setCity(city:String){
    this.city = city
  }

  @Column(name = "unit" )
  def getUnit:String={
    unit
  }
  def setUnit(unit:String){
    this.unit = unit
  }

  @Column(name = "refund_id" )
  def getRefundId:String={
    refundId
  }
  def setRefundId(refundId:String){
    this.refundId = refundId
  }

  @Column(name = "out_refund_no" )
  def getOutRefundNo:String={
    outRefundNo
  }
  def setOutRefundNo(outRefundNo:String){
    this.outRefundNo = outRefundNo
  }

  @Column(name = "refund_channel" )
  def getRefundChannel:String={
    refundChannel
  }
  def setRefundChannel(refundChannel:String){
    this.refundChannel = refundChannel
  }

  @Column(name = "coupon_refund_fee" )
  def getCouponRefundFee:Integer={
    couponRefundFee
  }
  def setCouponRefundFee(couponRefundFee:Integer){
    this.couponRefundFee = couponRefundFee
  }

  @Column(name = "refund_fee" )
  def getRefundFee:Integer={
    refundFee
  }
  def setRefundFee(refundFee:Integer){
    this.refundFee = refundFee
  }

  @Column(name = "is_active" )
  def getIsActive:String={
    _isActive
  }
  def setIsActive(_isActive:String){
    this._isActive = _isActive
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
