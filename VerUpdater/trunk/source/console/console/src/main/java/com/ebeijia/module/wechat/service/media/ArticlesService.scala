package com.ebeijia.module.wechat.service.media

import java.util.Map

import com.ebeijia.entity.wechat.base.{TblWechatArticlesInfId, TblWechatArticlesInf}


/**
  * ArticlesService
  * @author zhicheng.xu
  *         15/8/14
  */

trait ArticlesService {
  //分页查询
  def findBySql(mchtId: String, aoData: String): Map[String, AnyRef]
  //生成回复消息id
  def Save(data: TblWechatArticlesInf)
  //保存简易图文信息
  def getById(id: TblWechatArticlesInfId): TblWechatArticlesInf
  //获取最大索引
  def getMaxSeq():Integer
  def delArt(artId:String)
  def getByArtId(artId:String): TblWechatArticlesInf
  def querydef(artId:String): java.util.List[TblWechatArticlesInf]
}