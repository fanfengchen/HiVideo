package com.ebeijia.controller.system.login

/**
 * LogoutAction
  *
  * @author zhicheng.xu
 *         15/8/7
 */

import java.util.Map
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.module.system.service.OperTask.OperTaskSerivce
import com.ebeijia.util.core.RespCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

@Controller
class LogoutAction {

  @Autowired
  val operTaskSerivce:OperTaskSerivce = null

  @RequestMapping(value = Array("logout.html"))
  @ResponseBody def logout(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    session.removeAttribute("operator")
    session.removeAttribute("role")
    session.removeAttribute("roleId")
    operTaskSerivce.removeOperToken()
    AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
  }
}
