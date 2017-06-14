package com.ebeijia.module.system.service.brh

import java.util.Map

import com.ebeijia.entity.system.brh.TblSysBrhInf

/**
  * 机构
  * TblBrhInfService
  * @author xiong.wang
  *         15/8/17
  */
trait SysBrhInfService {
  def findBySql(roleId:String,brhId:String,brhNo: String, brhName: String, pageData: String)
  : Map[String, AnyRef]

  def save(brhId: String,data: TblSysBrhInf):String

  def update(data: TblSysBrhInf)

  def saveOrUpdate(data: TblSysBrhInf)

  def getById(brhId: String): TblSysBrhInf

  def delById(brhId: String):String

  /*获取本行和下属机构*/
  def getBrhBelowId(id: String): String
  /**机构惟一约束**/
  def vailBrhUniq(brhId: String): Boolean

  def getBrhList(brhNo:String):Map[String, AnyRef]

  //获取所有上下级机构
  def getAllBrhList(brhNo:String,brhLeavel:String):Map[String, AnyRef]

  def getBrhBelowInf(brhId:String) :Map[String,AnyRef]
}
