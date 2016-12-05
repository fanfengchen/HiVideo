package com.ebeijia.controller.list.system

import java.util.List
import javax.servlet.http.HttpServletRequest

import com.ebeijia.entity.system.system.TblSysOperatorInf
import com.ebeijia.module.system.service.operatorInf.SysOperatorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

/**
  * ListUpTime
  * @author zhicheng.xu
  *         15/8/11
  */


@Controller
@RequestMapping(value = Array("/list/system"))
class SysListUpTime {
  @Autowired
  private val operatorService: SysOperatorService = null

  @RequestMapping(value = Array("upTimeList.html"))
  @ResponseBody def upTime(request: HttpServletRequest): List[TblSysOperatorInf] = {
    operatorService.listOperatorByUpTime
  }
}
