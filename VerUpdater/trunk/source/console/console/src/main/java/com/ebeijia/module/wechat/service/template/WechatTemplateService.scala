package com.ebeijia.module.wechat.service.template


/**
 * WechatSubGroupService
 * @author zhicheng.xu
 *         15/8/13
 */


trait WechatTemplateService {
  //发送模板消息
  def send(msg: String,mchtId:String): String
  //重置token的空方法
  def restToken()
}
