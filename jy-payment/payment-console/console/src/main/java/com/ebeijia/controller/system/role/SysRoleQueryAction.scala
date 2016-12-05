package com.ebeijia.controller.system.role

import java.util._
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.system.system.{TblSysRoleInfId, TblSysRoleInf}
import com.ebeijia.module.system.service.roleInf.SysRoleInfService
import com.ebeijia.util.core.{FuncCode, RespCode, Constant}
import org.ebeijia.tools.Validate4J
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
  * RoleQueryAction
  * @author zhicheng.xu
  *         15/8/10
  */

@Controller
@RequestMapping(value = Array("/sys/role"))
class SysRoleQueryAction extends BaseValidRoleFunc {
  private val logger: Logger = LoggerFactory.getLogger(classOf[SysRoleQueryAction])

  @Autowired
  private val roleInfService: SysRoleInfService = null

  @RequestMapping(value = Array("query.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def query(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    //判断是否有权限(查询跟维护权限有一就可通过)
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, request.getParameter("roleId"), FuncCode.ROLE_FUNC_QUERY)
    val msgUpdate: String = this.validRoleFunc(usrId, token, request.getParameter("roleId"), FuncCode.ROLE_FUNC_MAINTAIN)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      //      if (msgUpdate != null && msg != null) {
      //        return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
      //      }
    }
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    val roleIdd: String = request.getParameter("roleIdd")
    val selbrhId: String = request.getParameter("selbrhId")
    var roleId: Int = 0
    if (roleIdd != null && !("" == roleIdd)) {
      roleId = roleIdd.toInt
    }
    val brhNo: String = request.getParameter("brhNo")
    val roleName: String = request.getParameter("roleName")
    val pageData: String = request.getParameter("aoData")
    try {
      val mapTmp: Map[String, AnyRef] = roleInfService.findBySql(brhNo,roleId, roleName, pageData)
      if(pageData == null || "".equals(pageData)){
        val m: Map[String, AnyRef] = roleInfService.findAllListRole(selbrhId)
        return AjaxResp.getReturn(RespCode.SUCCESS_CODE, m)
      }
      val list: List[AnyRef] = new LinkedList[AnyRef]
      val m: AnyRef = mapTmp.get("list")
      val lists: List[_] = m.asInstanceOf[List[_]]
      val it: Iterator[_] = lists.iterator
      while (it.hasNext) {
        val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
        val o: TblSysRoleInf = it.next.asInstanceOf[TblSysRoleInf]
        hashMap.put("roleId", o.getId.getRuleId.toString)
        hashMap.put("brhNo", o.getId.getBrhNo)
        hashMap.put("dsc", o.getDsc)
        hashMap.put("roleName", o.getRoleName)
        hashMap.put("roleType", o.getRoleType)
        list.add(hashMap)
      }
      map.put("roleList", list)
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("角色查询失败:" + e)
        AjaxResp.getReturn(RespCode.ERROR_CODE, "")
      }
    }
  }

  @RequestMapping(value = Array("queryDsc.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "角色权限查询")
  def queryDsc(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, request.getParameter("roleId"), FuncCode.OPR_FUNC_QUERY)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      //      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    val roleIdd: String = request.getParameter("roleIdd")
    val s: Array[Array[String]] = Array(Array(roleIdd, "1", "6"))
    if (!Validate4J.checkStrArrLen(s)) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "")
    }
    val roleId: Int = roleIdd.toInt
    try {
      val mapTmp: Map[String, AnyRef] = roleInfService.findRoleDef(roleId)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, mapTmp)
    }
    catch {
      case e: Exception => {
        logger.info("角色详情查询失败:" + e)
        AjaxResp.getReturn(RespCode.ROLE_ERROR_SEL, "")
      }
    }
  }
}

