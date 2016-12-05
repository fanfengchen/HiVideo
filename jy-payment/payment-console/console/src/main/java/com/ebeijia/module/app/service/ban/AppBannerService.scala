package com.ebeijia.module.app.service.ban

import java.util.Map

import com.ebeijia.entity.app.ban.{TblAppBannerInfId, TblAppBannerInf}
import org.springframework.web.multipart.MultipartFile

/**
  * 广告管理
  * @author xiong.wang
  */
trait AppBannerService {
  //通过条件查询结果
  def findBySql(name: String, state: String, pageData: String): Map[String, AnyRef]

  //广告信息增加
  def save(data: TblAppBannerInf, `type`: String, f: MultipartFile, ext: String)

  //广告信息更新
  def update(data: TblAppBannerInf, `type`: String, f: MultipartFile, ext: String)

  //根据id查信息
  def getById(id: TblAppBannerInfId): TblAppBannerInf

  //删除广告信息
  def delById(id: TblAppBannerInfId)

  //查询顺序
  def selSeq(Seq: String): Boolean
  /**
   * 最大id+1
   * */
  def maxId:Int
}
