package com.ebeijia.module.system.service.mcht

import java.util.Map

import com.ebeijia.entity.system.system.{TblSysMchtInf, TblSysMchtInfId}

/**
  * 商户
  * MchtInfService
  * @author xiong.wang
  *         016/6/23
  */
trait MchtInfService {
  def findBySql(brhNo:String,mchtId: String, mchtName: String, pageData: String)
  : Map[String, AnyRef]

  def save(data: TblSysMchtInf)

  def update(data: TblSysMchtInf)

  def saveOrUpdate(data: TblSysMchtInf)

  def getById(id: TblSysMchtInfId): TblSysMchtInf

  def delById(id: TblSysMchtInfId)

  def vailMchtUniq(mchtId: String): Boolean

  def ListMchtId(brhNo:String): Map[String, AnyRef]

  def selByIdAndName(tblSysMchtInf:TblSysMchtInf):Int
}
