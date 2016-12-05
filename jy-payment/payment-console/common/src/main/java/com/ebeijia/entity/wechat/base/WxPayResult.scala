package com.ebeijia.entity.wechat.base

/**
 * WxPayResult 微信支付回调
 * @author zhicheng.xu
 *         15/11/4
 */

class WxPayResult {
  private var appid: String = null
  private var bank_type: String = null
  private var cash_fee: String = null
  private var fee_type: String = null
  private var is_subscribe: String = null
  private var mch_id: String = null
  private var nonce_str: String = null
  private var openid: String = null
  private var out_trade_no: String = null
  private var result_code: String = null
  private var return_code: String = null
  private var sign: String = null
  private var time_end: String = null
  private var total_fee: String = null
  private var trade_type: String = null
  private var transaction_id: String = null


  def getAppid: String = {
    appid
  }


  def setAppid(appid: String) {
    this.appid = appid
  }


  def getBankType: String = {
    bank_type
  }


  def setBankType(bank_type: String) {
    this.bank_type = bank_type
  }


  def getCashFee: String = {
    cash_fee
  }


  def setCashFee(cash_fee: String) {
    this.cash_fee = cash_fee
  }


  def getFeeType: String = {
    fee_type
  }

  def setFeeType(fee_type: String) {
    this.fee_type = fee_type
  }


  def getIsSubscribe: String = {
    is_subscribe
  }


  def setIsSubscribe(is_subscribe: String) {
    this.is_subscribe = is_subscribe
  }


  def getMchId: String = {
    mch_id
  }


  def setMchId(mch_id: String) {
    this.mch_id = mch_id
  }


  def getNonceStr: String = {
    nonce_str
  }


  def setNonceStr(nonce_str: String) {
    this.nonce_str = nonce_str
  }


  def getOpenid: String = {
    openid
  }


  def setOpenid(openid: String) {
    this.openid = openid
  }


  def getOutTradeNo: String = {
    out_trade_no
  }


  def setOutTradeNo(out_trade_no: String) {
    this.out_trade_no = out_trade_no
  }


  def getResultCode: String = {
    result_code
  }


  def setResultCode(result_code: String) {
    this.result_code = result_code
  }


  def getReturnCode: String = {
    return_code
  }


  def setReturnCode(return_code: String) {
    this.return_code = return_code
  }


  def getSign: String = {
    sign
  }


  def setSign(sign: String) {
    this.sign = sign
  }


  def getTimeEnd: String = {
    time_end
  }


  def setTimeEnd(time_end: String) {
    this.time_end = time_end
  }


  def getTotalFee: String = {
    total_fee
  }


  def setTotalFee(total_fee: String) {
    this.total_fee = total_fee
  }


  def getTradeType: String = {
    trade_type
  }


  def setTradeType(trade_type: String) {
    this.trade_type = trade_type
  }


  def getTransactionId: String = {
    transaction_id
  }


  def setTransactionId(transaction_id: String) {
    this.transaction_id = transaction_id
  }
}