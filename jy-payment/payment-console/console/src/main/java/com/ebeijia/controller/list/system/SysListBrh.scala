package com.ebeijia.controller.list.system

import java.util.Map

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.module.system.service.brh.SysBrhInfService
import com.ebeijia.util.core.RespCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

/**
  * SysListBrh
  *
  * @author xiong.wang
  * 2016/6/30
  */
@Controller
@RequestMapping(value = Array("/list/brh"))
class SysListBrh {
  @Autowired
  val sysBrhInfService:SysBrhInfService = null
  @RequestMapping(value = Array("brhList.html"))
  @ResponseBody def getBrhList(brhNo:String): Map[String, AnyRef] = {
//    sysBrhInfService.getBrhList(brhNo)
      sysBrhInfService.getBrhBelowInf(brhNo)
//    AjaxResp.getReturn(RespCode.SUCCESS_CODE,sysBrhInfService.getBrhBelowId(brhNo))
  }

  @RequestMapping(value = Array("allBrhList.html"))
  @ResponseBody def getAllBrhList(brhNo:String,brhLeavel:String): Map[String, AnyRef] = {
    sysBrhInfService.getAllBrhList(brhNo,brhLeavel)
  }
}
