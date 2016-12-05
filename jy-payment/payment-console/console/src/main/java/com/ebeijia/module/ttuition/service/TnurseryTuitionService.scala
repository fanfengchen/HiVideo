package com.ebeijia.module.ttuition.service

import java.util.{List, Map}

import com.ebeijia.entity.ttuition.TnurseryTuition

/**
  * 学生缴费管理
  * @author xiong.wang
  */
trait TnurseryTuitionService {
  //通过条件查询结果
  def findBySql(name: String,state:String, pageData: String): Map[String, AnyRef]

  //学生缴费信息增加
  def save(data: TnurseryTuition)

  //学生缴费信息更新
  def update(data: TnurseryTuition)

  //根据id查信息
  def getById(id: Int): TnurseryTuition

  //删除学生缴费信息
  def delById(id: Int)
  //批量导入
  def saveExcel(productList:List[TnurseryTuition])
}
