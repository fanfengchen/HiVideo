package com.ebeijia.entity.vo.wechat.media

/**
  * UpMediaErr
  * @author xiong.wang
  *         2016/6/22
  */
class UpMediaErr {
  private var errcode: String = null
  private var errmsg: String = null

  def getErrcode: String = {
    errcode
  }

  def setErrcode(errcode: String) {
    this.errcode = errcode
  }

  def getErrmsg: String = {
    errmsg
  }

  def setErrmsg(errmsg: String) {
    this.errmsg = errmsg
  }

}
