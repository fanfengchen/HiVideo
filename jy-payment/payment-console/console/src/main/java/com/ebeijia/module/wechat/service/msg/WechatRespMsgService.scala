package com.ebeijia.module.wechat.service.msg

import java.util.{List, Map}

import com.ebeijia.entity.wechat.base.{TblWechatRespMsgId, TblWechatRespMsg}

/**
 * WechatRespMsgService
 * @author zhicheng.xu
 *         15/8/14
 */

trait WechatRespMsgService {
  //分页查询
  def findBySql(keywords:String,mchtId: String, aoData: String,subcribe:String): Map[String, AnyRef]
  //新增回复消息设置
  def save(data: TblWechatRespMsg, articlesJson: String)
  //修改回复消息设置
  def update(data: TblWechatRespMsg, articlesJson: String)
  //根据商户,回复类型,关键字查询回复数据
  def findByMchtType(mcht: String, msgType: String, keywords: String): List[TblWechatRespMsg]
  //根据id获取对象
  def getById(id:TblWechatRespMsgId): TblWechatRespMsg
  //根据id删除对象
  def delete(id: TblWechatRespMsgId)
  //验证回复是否重复
  def check(respMsgId: String, mchtId: String, keywords: String): Int
  def getMaxRespId(mchtId:String):String
  def getResp(mchtId:String,keywords:String): TblWechatRespMsg
  //关注着消息回复验证是否存在
  def getSubscribe(msgType:String):TblWechatRespMsg
  def findBySubType(mchtId: String): List[TblWechatRespMsg]
}
