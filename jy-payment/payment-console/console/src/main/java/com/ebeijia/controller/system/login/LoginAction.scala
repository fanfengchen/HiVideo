package com.ebeijia.controller.system.login

import java.util
import java.util.{HashMap, List, Map}
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.entity.system.system.{TblSysMchtInfId, TblSysMchtInf, TblSysOperatorInf}
import com.ebeijia.entity.wechat.base.TblWechatMchtInf
import com.ebeijia.module.system.service.OperTask.OperTaskSerivce
import com.ebeijia.module.system.service.mcht.MchtInfService
import com.ebeijia.module.system.service.operatorInf.SysOperatorService
import com.ebeijia.module.system.service.roleInf.SysRoleInfService
import com.ebeijia.module.system.service.systemToken.SysTokenSerivce
import com.ebeijia.module.wechat.service.core.WechatMchtInfService
import com.ebeijia.util.common.EncryptMD5Util
import com.ebeijia.util.core.RespCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * LoginAction
  *
  * @author zhicheng.xu
 *         15/8/7
 */

@Controller
class LoginAction {
  @Autowired
  private val adminService: SysOperatorService = null
  @Autowired
  private val roleInfService: SysRoleInfService = null
  @Autowired
  val systeToken :SysTokenSerivce= null;
  @Autowired
  val wechatMchtInfService:WechatMchtInfService = null
  @Autowired
  val mchtInfService:MchtInfService = null
  @Autowired
  val operTaskSerivce:OperTaskSerivce = null

  @RequestMapping(value = Array("login.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "用户登录")
  @throws(classOf[Exception])
  @ResponseBody
  def Login(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val usrId: String = request.getParameter("usrId")
    val loginPwd: String = request.getParameter("loginPwd")
    if (loginPwd.trim == null || ("".equals(loginPwd.trim)) || usrId.trim == null || ("".equals(usrId.trim))) {
      return  AjaxResp.getReturn(RespCode.lOGIN_USER_ERROR_EMPTY,"")
    }
    if (loginPwd.length < 6) {
      return  AjaxResp.getReturn(RespCode.LOGIN_PWD_ERROR_NOTVALID,"")
    }
    val operaInfs: List[TblSysOperatorInf] = adminService.login(usrId)
    if (operaInfs.isEmpty) {
      return  AjaxResp.getReturn(RespCode.lOGIN_USER_ERROR_EXIS,"")
    }
    val tblOperatorInf: TblSysOperatorInf = operaInfs.get(0)
    if (!(EncryptMD5Util.encrypt(loginPwd) == tblOperatorInf.getOprPwd)) {
      return  AjaxResp.getReturn(RespCode.LOGIN_USER_ERROR_INPUT,"")
    }
    val role: String = roleInfService.funcFind(tblOperatorInf.getId.getOprId)
    if (role == null) {
      return  AjaxResp.getReturn(RespCode.LOGIN_USER_ERROR_WITHOUT,"")
    }
    session.setAttribute("operator", tblOperatorInf)
    session.setAttribute("roleId", tblOperatorInf.getRoleId)
    val token = systeToken.setToken(usrId)
    //取得机构商户
    val list:List[TblWechatMchtInf] = wechatMchtInfService.selbrhMchtInf(tblOperatorInf.getId.getBrhNo)
    val list1:List[util.Map[String,String]] = new util.LinkedList[util.Map[String,String]]()
    val mcMap:Map[String,String] = new util.HashMap[String,String]()
    for (i <- 0 until list.size()) {
//      mcMap.put(list.get(i).getId.getMchtId,list.get(i).getNickname)
      val tblSysMchtInf:TblSysMchtInfId = new TblSysMchtInfId
      tblSysMchtInf.setBrhNo(tblOperatorInf.getId.getBrhNo)
      tblSysMchtInf.setMchtId(list.get(i).getId.getMchtId)
      val tsmi:TblSysMchtInf = mchtInfService.getById(tblSysMchtInf)
      mcMap.put("key",list.get(i).getId.getMchtId)
      mcMap.put("value",tsmi.getMchtName)
      list1.add(mcMap)
    }
    //首次登录获取新token
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    map.put("usrName",usrId)
    map.put("operator", tblOperatorInf)
    map.put("roleId", tblOperatorInf.getRoleId.toString)
    map.put("token",token)
    map.put("mchtList",list1)
    map.put("brhNo",tblOperatorInf.getId.getBrhNo)
    operTaskSerivce.setOper(usrId)
    System.out.println(usrId+":"+token)
    AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
  }
}

