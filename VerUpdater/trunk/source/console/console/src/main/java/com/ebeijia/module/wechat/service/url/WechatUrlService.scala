package com.ebeijia.module.wechat.service.url

import java.util.Map

import com.ebeijia.entity.wechat.base.TblWechatUrl


/**
 * Created by ld on 2015/10/29.
 * 微信链接管理
 */
trait WechatUrlService {
  /**
   * 根据链接id，类型进行查询
   * 菜单信息
   * */
  def  findByUrlSql(urlId: String, urlType: String, pageData: String): Map[String, AnyRef]
  /**删除
    * */
  def delByUrlId(urlId: String)
  /**新增
    * 修改
    * */
  def saveOrUpdateUrl(tblWechatUrl:TblWechatUrl)
  /**判断url唯一
   * */
  def isUrl(urlId: String,url: String ): Int
}
