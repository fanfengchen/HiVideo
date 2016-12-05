package com.ebeijia.module.wechat.service.group

import java.util.Map

import com.ebeijia.entity.wechat.base.{TblWechatSubGroupId, TblWechatSubGroup}


/**
 * WechatSubGroupService
 * @author zhicheng.xu
 *         15/8/13
 */


trait WechatSubGroupService {
  //添加关注者组别
  def save(mchtId: String, name: String): Map[String, AnyRef]
  //修改关注者组别
  def update(mchtId: String, groupId: String, name: String): String
  //删除关注者组别
  def del(mchtId: String, groupId: String): String
  //验证分组限制
  def findCount(mchtId: String): Int
  //通过条件查询结果
  def findBySql(mchtId: String,name: String, pageData: String): Map[String, AnyRef]
  //根据商户id查组别列表
  def listGroupByMchtId(mchtId: String): Map[String, AnyRef]
  //根据id获取实体对象
  def getById(id: TblWechatSubGroupId): TblWechatSubGroup
  //判断唯一
  def isSubGroup(id:Int,name:String): Int
}
