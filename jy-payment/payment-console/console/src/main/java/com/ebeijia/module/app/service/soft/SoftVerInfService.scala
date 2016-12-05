package com.ebeijia.module.app.service.soft

import java.util.Map

import com.ebeijia.entity.app.soft.{TblAppSoftVerInfId, TblAppSoftVerInf}
import org.springframework.web.multipart.MultipartFile

/**
  * 软件管理
  * @author xiong.wang
  */
trait SoftVerInfService {
  //通过条件查询结果
  def findBySql(verNo: String, uTime: String, pageData: String): Map[String, AnyRef]

  //新增版本
  def save(data: TblAppSoftVerInf, `type`: String, f: MultipartFile, ext: String)

  //更新版本
  def update(data: TblAppSoftVerInf)

  //根据id查信息
  def getById(id: TblAppSoftVerInfId): TblAppSoftVerInf

  //删除版本
  def delById(id: TblAppSoftVerInfId)

  //查询版本号
  def selSoft(verNo: String,_type:String): Boolean

  /**
   * 最大id+1
   * */
  def maxId:Int
}
