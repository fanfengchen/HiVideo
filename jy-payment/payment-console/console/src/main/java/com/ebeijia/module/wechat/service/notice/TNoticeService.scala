package com.ebeijia.module.wechat.service.notice

import java.util.Map

import com.ebeijia.entity.wechat.notice.{TNotice, TNoticeT}

trait TNoticeService {
  //通过条件查询结果
  def findBySql(title: String,id:String, pageData: String): Map[String, AnyRef]

  //添加通知
  def save(data: TNoticeT)

  //通知更新
  def update(data: TNoticeT)

  //根据id查找通知
  def getById(id: Int): TNoticeT

  //删除通知
  def delById(id: Int)
}
