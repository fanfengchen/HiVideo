package com.ebeijia.controller.system.role

import java.util.Map
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.module.system.service.roleInf.SysRoleInfService
import com.ebeijia.util.core.{FuncCode, RespCode}
import org.ebeijia.tools.Validate4J
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
  * RoleAddAction
  *
  * @author zhicheng.xu
  *         15/8/10
  */
@Controller
@RequestMapping(value = Array("/sys/role"))
class SysRoleAddAction extends BaseValidRoleFunc {

  @Autowired
  private val roleInfService: SysRoleInfService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[SysRoleAddAction])

  @RequestMapping(value = Array("add.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "角色新增")
  @ResponseBody def add(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, request.getParameter("roleId"), FuncCode.ROLE_FUNC_ADDITION)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      //      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    //roleList 功能id
    val roleList: String = request.getParameter("roleList")
    val name: String = request.getParameter("name")
    val roleType: String = request.getParameter("roleType")
    val dsc: String = request.getParameter("dsc")
    val brhNo: String = request.getParameter("brhId")
    val s: Array[Array[String]] = Array(Array(dsc, "0", "64"), Array(brhNo, "1", "32"), Array(name, "1", "32"), Array(roleList, "6", null))
    if (!Validate4J.checkStrArrLen(s)) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
    }
    try {
      //判断角色名称唯一
      val roleName = roleInfService.isRoleName(0, name)
      if (roleName > 0) {
        return AjaxResp.getReturn(RespCode.ROLE_NAME_ERROR_UNIQ,"")
      }
      if(roleType.equals("0")) {
        val count: Int = roleInfService.isRoleUniq(roleType)
        if (count > 1) {
          return AjaxResp.getReturn(RespCode.ERROR_CODE, "", "管理员角色已存在!!!")
        }
      }
      roleInfService.save(brhNo,name, dsc, roleList,roleType)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        logger.info("角色新增失败")
        AjaxResp.getReturn(RespCode.ROLE_ERROR_ADD,"")
      }
    }
  }
}
