package com.ebeijia.module.wechat.service.subscribe

import java.util.{List, Map}

import com.ebeijia.entity.wechat.base.{TblWechatSubscribeId, TblWechatSubscribe}


/**
 * WechatSubscribeService
 * @author zhicheng.xu
 *         15/8/14
 *         微信关注者信息
 */

trait WechatSubscribeService {
  //保存关注用户
  def addWechatSubscribe(wechatSubscribe: TblWechatSubscribe)
  //分页查询 根据关注者Id，公共号Id
  def findBySql(mchtId:String,groupId:String,subcribeId: String, openid: String,nickname:String, aoData: String): Map[String, AnyRef]
  //批量移动用户分页查询
  def findByBatch( groupId: String, aoData: String): Map[String, AnyRef]
  //根据用户组查用户
  def findByGroup( groupId: String): List[TblWechatSubscribe]
  //同步关注用户
  def synUsr(mchtId:String): String
  //修改用户备注
  def upRemark(data: TblWechatSubscribe): String
  //修改用户组别
  def upGroup(data: TblWechatSubscribe): String
  //批量修改用户组别
  def batchUpGroup( subList: String, groupId: String): String
  //根据id获得实体类
  def getById(id: TblWechatSubscribeId): TblWechatSubscribe
  //更新当前组别的用户为空
  def upGroup(groupId: String)
}
