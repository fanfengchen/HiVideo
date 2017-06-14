package com.ebeijia.controller.list.system

import java.util
import java.util.Map
import javax.servlet.http.HttpServletRequest

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.module.system.service.roleInf.SysRoleInfService
import com.ebeijia.util.core.RespCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{ResponseBody, RequestMapping}

@Controller
@RequestMapping(value = Array("/list/btn"))
class SysButtonByMenu {
  @Autowired
  private val roleInfService: SysRoleInfService = null

  @RequestMapping(value = Array("btnList.html"))
  @ResponseBody def btnList(roleId:String,brhNo:String,parentId:String): Map[String, util.List[util.Map[String, String]]] = {
    roleInfService.funcRoleButton(roleId,brhNo,parentId)
  }
}
