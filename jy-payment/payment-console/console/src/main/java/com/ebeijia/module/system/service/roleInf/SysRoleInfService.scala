package com.ebeijia.module.system.service.roleInf

import java.util
import java.util.Map

import com.ebeijia.entity.system.system.{TblSysOperatorInf, TblSysRoleInfId, TblSysRoleInf}

/**
  * RoleInfService
  *
  * @author zhicheng.xu
  *         15/8/13
  */

trait SysRoleInfService {
  //新增角色
  def save(brhNo:String,name: String, dsc: String, roleList: String,roleType:String)
  //登录查询菜单列表
  def funcFind(usrId: Int): String
  //查询已有权限和未添加权限列表
  def findRoleDef(roleId: Int): Map[String, AnyRef]
  //查询所有菜单列表
  def funcFindAll(): Map[String, AnyRef]
  //通过条件查询结果
  def findBySql(brhNo: String,roleId: Int, roleName: String, pageData: String): Map[String, AnyRef]
  //根据id查信息
  def getById(id: TblSysRoleInfId): TblSysRoleInf
  //删除角色
  def delById(id: TblSysRoleInfId)
  //更新角色
  def update(tblRoleInf: TblSysRoleInf, roleList: String)
  //查询角色列表
  def findRole: Map[String, AnyRef]

  /**判断角色名称唯一
    * */
  def isRoleName(roleId:Int,roleName:String): Int


  /**
    * 判断是否有权限
    *
    */
  def roleFunCount(roleId:String,funcId:String): Int

  def funcRolePer(roleId:String,brhNo:String): Map[String, AnyRef]
  def findAllListRole(selbrhId:String): Map[String, AnyRef]

  def vailDel(brhNo: String,roleId: Int): Boolean
  def updateBySql(tbloprInf: TblSysOperatorInf): Unit
  def funcRoleButton(roleId:String,brhNo:String,parent:String): Map[String, util.List[util.Map[String, String]]]
  //判断管理员角色唯一
  def isRoleUniq(roleType:String): Int
}
