package com.ebeijia.entity.vo.wechat.resp

class LocationMessage extends BaseMessage{
  //地理位置纬度
  private var Latitude:String = null
  //地理位置经度
  private var Longitude:String = null
  //地理位置精度
  private var Precision:String = null

  def getLatitude: String = {
    Latitude
  }

  def setLatitude(Latitude: String) {
    this.Latitude = Latitude
  }

  def getLongitude: String = {
    Longitude
  }

  def setLongitude(Longitude: String) {
    this.Longitude = Longitude
  }

  def getPrecision: String = {
    Precision
  }

  def setPrecision(Precision: String) {
    this.Precision = Precision
  }
}
