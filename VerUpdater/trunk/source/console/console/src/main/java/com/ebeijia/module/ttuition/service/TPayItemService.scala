package com.ebeijia.module.ttuition.service

import java.util.Map

import com.ebeijia.entity.ttuition.TPayItem

/**
  * 学校缴费项目管理
  * @author xiong.wang
  */
trait TPayItemService {
  def findBySql(name: String,state:String, pageData: String): Map[String, AnyRef]
  def save(data: TPayItem)
  def update(data: TPayItem)
  def getById(id: Int): TPayItem
  def delById(id: Int)
}
