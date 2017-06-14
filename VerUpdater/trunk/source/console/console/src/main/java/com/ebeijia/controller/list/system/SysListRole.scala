package com.ebeijia.controller.list.system

import java.util.Map
import javax.servlet.http.HttpServletRequest

import com.ebeijia.module.system.service.roleInf.SysRoleInfService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

/**
  * ListRole
  * @author zhicheng.xu
  *         15/8/11
  */


@Controller
@RequestMapping(value = Array("/list/system"))
class SysListRole {
  @Autowired
  private val roleInfService: SysRoleInfService = null

  @RequestMapping(value = Array("roleList.html"))
  @ResponseBody def role(request: HttpServletRequest): Map[String, AnyRef] = {
    roleInfService.findRole
  }
}
