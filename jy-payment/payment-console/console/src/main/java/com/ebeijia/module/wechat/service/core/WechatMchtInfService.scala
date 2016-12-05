package com.ebeijia.module.wechat.service.core

import java.util.{List, Map}

import com.ebeijia.entity.wechat.base.{TblWechatMchtInf, TblWechatMchtInfId}
import org.springframework.web.multipart.MultipartFile


/**
 * WechatMchtInfService
  *
  * @author zhicheng.xu
 *         15/8/13
 */


trait WechatMchtInfService {
  def updateWechatMchtInf(wechatMchtInf: TblWechatMchtInf)

  def queryWechatMchtInf(mchtId:String): TblWechatMchtInf
  //查询已绑定公众号的商户
  def findMchtList: Map[String, AnyRef]

  def findBySql(mchtId: String, pagaData: String): Map[String, AnyRef]

  def findByNikeName(nikeName: String): TblWechatMchtInf
  //绑定微信账号
  def addWechatMchtInf(wechatMchtInf: TblWechatMchtInf): String
  //解除绑定
  def deleteWechatMchtInf(wechatMchtInf: TblWechatMchtInf)
  //通过主键获取微信账号信息
  def getById(id: TblWechatMchtInfId): TblWechatMchtInf
  //通过appid查询商户号
  def getByAppid(appid: String): TblWechatMchtInf
  /**检查是否绑定
   * */
  def isListMchtInf(wechatMchtInf:TblWechatMchtInf): List[TblWechatMchtInf]
  //查询机构号下的所有商户
  def selbrhMchtInf(brhNo:String): List[TblWechatMchtInf]
  //查询商户
  def selMchtList(id:String):TblWechatMchtInf
  def selbrhdel(brhNo:String): Int
}
