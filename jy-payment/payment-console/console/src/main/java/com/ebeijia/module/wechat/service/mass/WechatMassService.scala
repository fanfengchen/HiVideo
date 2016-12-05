package com.ebeijia.module.wechat.service.mass

import java.util.{List, Map}

import com.ebeijia.entity.wechat.base.TblWechatMass

/**
 * WechatMassService
  *
  * @author zhicheng.xu
 *         15/8/14
 */



trait WechatMassService {
  //上传图文素材
  def upGtMedia(mchtId: String, title: String, dsc: String, articles: String): String
  //上传视频素材
  def upVideoMedia(mchtId: String, title: String, dsc: String, mediaId: String): String
  //根据组别群发用户
  def sendByGroup(mchtId: String, content: String, `type`: String, groupId: String, media: String): String
  //根据组别群发用户
  def sendByUsr(mchtId: String, content: String, msgtype: String, toUsr: String, media: String): String
  //群发删除
  def sendDel(mchtId: String, mediaId: String): String
  //群发状态查询
  def sendStatusFind(mchtId: String, msgId: String): String
  //分页查询
  def findBySql(beginDate: String,endDate: String,msgType:String, pagaData: String): Map[String, AnyRef]
  //查询发送用户总数
  def sendUsrCount(groupId:String,count:Int):Int
  def queryMedia(list: List[TblWechatMass]): Map[String, AnyRef]
}
