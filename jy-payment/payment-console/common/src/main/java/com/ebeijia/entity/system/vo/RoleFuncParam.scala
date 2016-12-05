package com.ebeijia.entity.system.vo

/**
  * RoleFuncParam
  * 通用验证
  * @author xiong.wang
  *         2016/7/4
  */
class RoleFuncParam {
  var usrId: String = null
  var token: String = null
  var roleId: String = null
  var funcId: String = null


  def getUsrId: String = {
    usrId
  }

  def setUsrId(usrId: String) {
    this.usrId = usrId
  }

  def getToken: String = {
    token
  }

  def setToken(token: String) {
    this.token = token
  }

  def getRoleId: String = {
    roleId
  }

  def setRoleId(roleId: String) {
    this.roleId = roleId
  }

  def getFuncId: String = {
    funcId
  }

  def setFuncId(funcId: String) {
    this.funcId = funcId
  }


  def cloneBean(): AnyRef = {
    var roleFuncParam: RoleFuncParam = null
    try {
      roleFuncParam = super.clone().asInstanceOf[RoleFuncParam]
    } catch {
      case e: Exception => {
        e.printStackTrace()
      }
    }
    roleFuncParam
  }
}
