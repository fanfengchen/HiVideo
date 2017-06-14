package com.ebeijia.module.wechat.service.media

import java.util.Map

import com.ebeijia.entity.wechat.base.{TblWechatFodder, TblWechatFodderId}
import org.springframework.web.multipart.MultipartFile


/**
 * WechatFodderService
 * @author zhicheng.xu
 *         15/8/14
 */


trait WechatFodderService {
  // 分页查询
  def findBySql(mchtId: String, `type`: String, mediaType: String, aoData: String): Map[String, AnyRef]
  //修改素材(只修改本地)
  def upFodder(id: TblWechatFodderId, name: String, dsc: String)
  //删除素材(需和服务器同步)
  def delFodder(fid:TblWechatFodderId): String
  //	//图片素材列表
  //	public Map<String, Object> imageList(String mchtId);
  //根据类型素材列表
  def mediaList(mchtId: String, msgType: String): Map[String, AnyRef]
  // 分页查询图文
  def findBySqltoNews(mchtId:String,name: String, aoData: String): Map[String, AnyRef]
  //保存素材
  def save(data: TblWechatFodder)
  def saveFodder(data: TblWechatFodder,`type`: String, f: MultipartFile, ext: String)
  //根据id获取主键
  def getById(id: TblWechatFodderId): TblWechatFodder
  def getByPic(picId:String) :TblWechatFodder
}
