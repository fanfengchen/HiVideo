package com.ebeijia.module.system.service.operatorInf

import java.util.{List, Map}

import com.ebeijia.entity.system.system.{TblSysOperatorInf, TblSysOperatorInfId}
/**
  * OperatorService
  * @author xiong.wang
  *         15/8/17
  */
trait SysOperatorService {
  //通过条件查询结果
  def findBySql(oprName: String, oprStat: String, pageData: String)
  : Map[String, AnyRef]

  //新增管理员
  def save(data: TblSysOperatorInf)

  //查询最近登录状况
  def listOperatorByUpTime: List[TblSysOperatorInf]

  //登录
  def login(usrName: String): List[TblSysOperatorInf]

  //查用户带头像信息
  def usrInfHead(usrId: Int): List[_]

  //更新管理员
  def update(data: TblSysOperatorInf)

  //验证管理员
  def checkOperator(oprName: String, pwd: String): List[TblSysOperatorInf]

  //根据id查信息
  def getById(id: TblSysOperatorInfId): TblSysOperatorInf

  //删除管理员
  def delById(id: TblSysOperatorInfId)

  //修改密码
  def updatePwd(oprName: String, pwd: String)

  /**
   * 判断管理员名称不能重复
   **/
  def isOperatorName(oprId: Int, oprName: String): Int

  /**
   * 根据角色判断是否有
   * 用户再使用
   **/
  def getOperator(role: Int): List[TblSysOperatorInf]

  /** 判断admin角色只能是一个
    * */
  def getOperatorRoleId(roleId: Int, oprId: Int): List[TblSysOperatorInf]

  /**
   * 验证授权码
   */
  def validCode(code: String): Boolean

  /** 获取最大角色id **/
  def getMaxId(): Int

  def vailRole(role: Int): Boolean

  def updateBySql(tbloprInf: TblSysOperatorInf,roleId:String)
}
