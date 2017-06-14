package com.ebeijia.module.ttuition.service

import java.util.{List, Map}

import com.ebeijia.entity.ttuition.Ttuition

trait TtuitionService {
  //通过条件查询结果
  def findBySql(name: String,state:String, pageData: String): Map[String, AnyRef]

  //学生缴费增加
  def save(data: Ttuition)

  //学生缴费更新
  def update(data: Ttuition)

  //根据id查学生缴费
  def getById(id: Int): Ttuition

  //删除广告学生缴费
  def delById(id: Int)

  def saveExcel(productList:List[Ttuition])
}
