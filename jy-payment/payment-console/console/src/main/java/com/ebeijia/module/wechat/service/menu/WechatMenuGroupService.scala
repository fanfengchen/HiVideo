package com.ebeijia.module.wechat.service.menu

import java.util.{List, Map}

import com.ebeijia.entity.wechat.base.TblWechatMenuGroup


/**
 * WechatMenuGroupService
 * @author zhicheng.xu
 *         15/8/14
 */


trait WechatMenuGroupService {
  //根据商户id查组别
  def find(mchtId: String): List[TblWechatMenuGroup]
  //下拉列表
  def listFind(mchtId: String): Map[String, AnyRef]
}
