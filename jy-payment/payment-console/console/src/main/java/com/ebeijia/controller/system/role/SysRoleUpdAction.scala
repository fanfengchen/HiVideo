package com.ebeijia.controller.system.role

import java.util.Map
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.system.system.{TblSysRoleInf, TblSysRoleInfId}
import com.ebeijia.module.system.service.operatorInf.SysOperatorService
import com.ebeijia.module.system.service.roleInf.SysRoleInfService
import com.ebeijia.util.core.{FuncCode, RespCode}
import org.ebeijia.tools.Validate4J
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
  * RoleUpdAction
  * @author zhicheng.xu
  *         15/8/10
  */

@Controller
@RequestMapping(value = Array("/sys/role"))
class SysRoleUpdAction extends BaseValidRoleFunc {
  @Autowired
  private val roleInfService: SysRoleInfService = null
  @Autowired
  private val operatorService: SysOperatorService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[SysRoleUpdAction])

  @RequestMapping(value = Array("upd.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "角色修改")
  def upd(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, request.getParameter("roleId"), FuncCode.ROLE_FUNC_MAINTAIN)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      //      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    //roleList 功能id
    val roleList: String = request.getParameter("roleList")
    val roleIdd: String = request.getParameter("roleIdd")
    val name: String = request.getParameter("name")
    val oldBrhId: String = request.getParameter("oldbrhId")
    val newBrhId: String = request.getParameter("newbrhId")
    val dsc: String = request.getParameter("dsc")
    val s: Array[Array[String]] = Array(Array(dsc, "0", "64"), Array(name, "1", "32"), Array(roleIdd, "1", "6"), Array(roleList, "6", null))
    if (!Validate4J.checkStrArrLen(s)) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
    }
    val roleId: Int = roleIdd.toInt
    val tsr:TblSysRoleInfId = new TblSysRoleInfId
    tsr.setBrhNo(oldBrhId)
    tsr.setRuleId(roleId)
    val tblRoleInf: TblSysRoleInf = roleInfService.getById(tsr)
    if (tblRoleInf == null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
    }
    roleInfService.delById(tsr)
    tblRoleInf.getId.setBrhNo(newBrhId)
    tblRoleInf.setRoleName(name)
    tblRoleInf.setDsc(dsc)
    try {
      val roleName = roleInfService.isRoleName(roleId, name)
      if (roleName > 0) {
        return AjaxResp.getReturn(RespCode.ROLE_NAME_ERROR_UNIQ,"")
      }
      roleInfService.update(tblRoleInf, roleList)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        logger.info("修改角色失败:" + e)
        AjaxResp.getReturn(RespCode.ERROR_CODE,"")
      }
    }
  }

  @RequestMapping(value = Array("del.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "角色删除")
  def del(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, request.getParameter("roleId"), FuncCode.ROLE_FUNC_MAINTAIN)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    //println(request.getParameter("roleId"))
    val roleIdd: String = request.getParameter("roleIdd")
    val brhId: String = request.getParameter("brhId")
    val s: Array[Array[String]] = Array(Array(roleIdd, "1", "6"))
    if (!Validate4J.checkStrArrLen(s)) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
    }
    if (roleIdd.equals("1")) {
      return AjaxResp.getReturn(RespCode.ROLE_ADMIN_DELERROR,"")
    }
    val roleId: Int = roleIdd.toInt
    try {
      val ti:TblSysRoleInfId = new TblSysRoleInfId
      ti.setBrhNo(brhId)
      ti.setRuleId(roleId)
      val tblRoleInf: TblSysRoleInf = roleInfService.getById(ti)
      if (tblRoleInf == null) {
        return AjaxResp.getReturn(RespCode.OBJECT_ERROR_NULL,"")
      }

      else {
        val flag: Boolean = operatorService.vailRole(roleId)
        if(!flag){
          return  AjaxResp.getReturn(RespCode.ROLE_ISUSERING_NOW, "")
        }
        roleInfService.delById(ti)
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
      }
    }
    catch {
      case e: Exception => {
        logger.info("删除角色失败:" + e)
        AjaxResp.getReturn(RespCode.ROLE_ERROR_UPD,"")
      }
    }
  }
}