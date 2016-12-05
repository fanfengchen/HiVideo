package com.ebeijia.module.app.service.dep

import java.util.Map

import com.ebeijia.entity.app.ban.TblAppBannerInf
import com.ebeijia.entity.app.dep.{TblAppDeployDocumentId, TblAppDeployDocument}
import org.springframework.web.multipart.MultipartFile

/**
  * 文章管理
  * @author xiong.wang
  */
trait AppDeployDocumentService {
  //通过条件查询结果
  def findBySql(docTitle: String, docType: String, state: String, pageData: String)
  : Map[String, AnyRef]

  //新增文章
  def save(data: TblAppDeployDocument, `type`: String, f: MultipartFile, ext: String)

  //更新文章
  def update(data: TblAppDeployDocument, `type`: String, f: MultipartFile, ext: String)

  //根据id查信息
  def getById(id: TblAppDeployDocumentId): TblAppDeployDocument

  //删除文章
  def delById(id: TblAppDeployDocumentId)

  def selByDocFlag(docFlag: String): TblAppDeployDocument
  /**
   * 最大id+1
   * */
  def maxId:Int
}
