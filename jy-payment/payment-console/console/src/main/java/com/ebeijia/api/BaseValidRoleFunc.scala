package com.ebeijia.api

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.entity.system.vo.RoleFuncParam
import com.ebeijia.module.system.service.roleInf.SysRoleInfService
import com.ebeijia.module.system.service.systemToken.SysTokenSerivce
import org.ebeijia.tools.Validate4J
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by ld on 2015/11/26.
 * 判断是否有角色权限
 */
class BaseValidRoleFunc {
  @Autowired
  private val roleInfService: SysRoleInfService = null
  @Autowired
  val systeToken :SysTokenSerivce= null;
  def validRoleFunc(id:String, token:String, role:String, funcId: String): String = {
    //操作功能时更新token时间
    val newToken: String = systeToken.setToken(id)
    if (!newToken.equals(token)) {
      return "tokenLose"
    }
    val s: Array[Array[String]] = Array( Array(funcId, "1", "12"))
    if (!Validate4J.checkStrArrLen(s)) {
        "权限参数长度或格式不正确"
    } else {
      if (null==role){
        return "会话超时，请重新登录"
      }
      val roleId=role.toString
      val count:Int= roleInfService.roleFunCount(roleId,funcId)
      if(count==0){
        return "当前用户无权限"
      }
     null
    }
  }

  def validRoleFunc(rfp:RoleFuncParam): String = {
    //操作功能时更新token时间
    val newToken: String = systeToken.setToken(rfp.getUsrId)
    if (!newToken.equals(rfp.getToken)) {
      return "tokenLose"
    }
    val s: Array[Array[String]] = Array( Array(rfp.getFuncId, "1", "12","1"))
    if (ActionValidate.checkArray(s) != null) {
      return ActionValidate.checkArray(s)
    } else {
      if (null==rfp.getRoleId){
        return "会话超时，请重新登录"
      }
      val roleId=rfp.getRoleId.toString
      val count:Int= roleInfService.roleFunCount(roleId,rfp.getFuncId)
      if(count==0){
        return "当前用户无权限"
      }
      null
    }
  }
}
