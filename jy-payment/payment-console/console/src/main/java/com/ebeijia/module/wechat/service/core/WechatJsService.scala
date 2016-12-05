package com.ebeijia.module.wechat.service.core

/**
 * WechatJsService
 * @author zhicheng.xu
 *         15/11/10
 */


trait WechatJsService {

  //通过appid查询JsTicket
  def getJsTicket(url :String,mchtId:String): String
}
