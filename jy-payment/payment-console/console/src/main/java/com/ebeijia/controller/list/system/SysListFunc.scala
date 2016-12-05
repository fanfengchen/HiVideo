package com.ebeijia.controller.list.system

import java.util.Map
import javax.servlet.http.HttpServletRequest

import com.ebeijia.module.system.service.roleInf.SysRoleInfService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

/**
  * ListFunc
  * @author zhicheng.xu
  *         15/8/11
  */
@Controller
@RequestMapping(value = Array("/list/system"))
class SysListFunc {
  @Autowired
  private val roleInfService: SysRoleInfService = null

  @RequestMapping(value = Array("funcInfList.html"))
  @ResponseBody def funcInf(request: HttpServletRequest): Map[String, AnyRef] = {
    roleInfService.funcFindAll()
  }
  @RequestMapping(value = Array("funcInfPer.html"))
  @ResponseBody def funcInfPer(request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId:String = request.getParameter("roleId")
    val brhNo:String = request.getParameter("brhNo")
    roleInfService.funcRolePer(roleId,brhNo)
  }
}
