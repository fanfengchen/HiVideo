package com.ebeijia.module.wechat.service.media

import java.util.Map

import com.ebeijia.entity.wechat.base.{TblWechatFodderId, TblWechatMchtInfId}
import org.springframework.web.multipart.MultipartFile

/**
 * WechatMediaService
 * @author zhicheng.xu
 *         15/8/14
 */

trait WechatMediaService {
  //上传素材
  def upLoadMedia(twm:TblWechatMchtInfId, `type`: String, f: MultipartFile, mediaType: String, ext: String, name: String, dsc: String): String
  //下载素材
  def dowLoadMedia(wm:TblWechatMchtInfId, mediaId: String, mediaType: String): String
  //获得素材总数
  def mediaAllCount(tblWechatMchtInfId :TblWechatMchtInfId): Map[_, _]
  //上传图文素材
  def upGtMedia(id: TblWechatFodderId,picId:String,picUrl:String,mchtId: String, name: String, dsc: String, articles: String): Map[String,AnyRef]
  //获得图文素材列表
  def newsGet(tblWechatMchtInfId :TblWechatMchtInfId,media:String): String
  //修改图文素材
  def updateGtMedia(picId:String,picUrl:String,tid:TblWechatMchtInfId,id: TblWechatFodderId, name: String, dsc: String, media: String, articles: String): String
  def upLoadFImg(twm: TblWechatMchtInfId, `type`: String, f: MultipartFile, mediaType: String, ext: String, name: String, dsc: String): Map[String,AnyRef]
}
