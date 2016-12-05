package com.ebeijia.controller.school.Ttuition

import java.util._
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.ttuition.TPayItem
import com.ebeijia.module.ttuition.service.TPayItemService
import com.ebeijia.util.core.{FuncCode, RespCode}
import org.ebeijia.tools.Validate4J
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
  * TPayItemAction
  * 学生缴费管理
  * @author xiong.wang
  *         16/6/17
  */
@Controller
@RequestMapping(value = Array("/school/tpay"))
class TPayItemAction extends BaseValidRoleFunc {
  private val logger: Logger = LoggerFactory.getLogger(classOf[TPayItemAction])
  @Autowired
  private val tPayItemService: TPayItemService = null

  @RequestMapping(value = Array("/queryList.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def queryList(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, request.getParameter("roleId"), FuncCode.STU_FUNC_MAINTAIN)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    //查询入参
    val name: String = request.getParameter("name")
    val status: String = request.getParameter("status")
    val pageData: String = request.getParameter("aoData")
    var map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: Map[String, AnyRef] = tPayItemService.findBySql(name, status, pageData)
      //多条连接 添加key
      val list: List[AnyRef] = new LinkedList[AnyRef]
      val m: AnyRef = mapTmp.get("list")
      val lists: List[_] = m.asInstanceOf[List[_]]
      val it: Iterator[_] = lists.iterator
      while (it.hasNext) {
        val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
        val o: TPayItem = it.next.asInstanceOf[TPayItem]
        hashMap.put("id", o.getId.toString)
        hashMap.put("name", o.getName)
        hashMap.put("amount", o.getAmount.toString)
        hashMap.put("target", o.getTarget)
        hashMap.put("status", o.getStatus.toString)
        hashMap.put("remark", o.getRemark)
        hashMap.put("Reserve1", o.getReserve1)
        hashMap.put("Reserve2", o.getReserve2)
        hashMap.put("Reserve3", o.getReserve3)
        list.add(hashMap)
      }
      //分页设定
      map.put("tpayList", list)
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      map = AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
      map
    }
    catch {
      case e: Exception => {
        logger.info("支付项目查询失败:" + e)
        AjaxResp.getReturn(RespCode.PAY_ERROR_SEL, "")
      }
    }
  }

  @RequestMapping(value = Array("/queryTpay.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def queryDep(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val id: String = request.getParameter("id")
    val msg: String = this.validRoleFunc(usrId, token, request.getParameter("roleId"), "100052")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val tbt: TPayItem = tPayItemService.getById(id.toInt)
      map.put("tpayList", tbt)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("支付项目详情查询失败:" + e)
        AjaxResp.getReturn(RespCode.PAY_ERROR_SEL, "")
      }
    }
  }


  @RequestMapping(value = Array("add.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "支付项目添加")
  @ResponseBody def add(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    //判断是否有权限
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val id: String = request.getParameter("id")
    val msg: String = this.validRoleFunc(usrId, token, request.getParameter("roleId"), "100052")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    val state: Boolean = true
    val tPayItem: TPayItem = this.build(request, state)
    if (tPayItem == null) {
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    try {
      tPayItemService.save(tPayItem)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        logger.info("支付项目新增失败:" + e)
        AjaxResp.getReturn(RespCode.PAY_ERROR_ADD, "")
      }
    }
  }

  private def build(request: HttpServletRequest, state: Boolean): TPayItem = {
    val name: String = request.getParameter("name")
    val amount: String = request.getParameter("amount")
    val target: String = request.getParameter("target")
    val remark: String = request.getParameter("remark")
    val Reserve1: String = request.getParameter("Reserve1")
    val Reserve2: String = request.getParameter("Reserve2")
    val Reserve3: String = request.getParameter("Reserve3")
    val s: Array[Array[String]] = Array(Array(name, "1", "50"), Array(amount, "2", "15"), Array(remark, "1", "200"))
    if (!Validate4J.checkStrArrLen(s)) {
      println("参数长度或格式不正确")
      AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
      return null
    }
    val tPayItem: TPayItem = new TPayItem
    tPayItem.setName(name)
    tPayItem.setStatus(1)
    tPayItem.setAmount(amount.toDouble)
    if (remark.equals("双语小学")) {
      tPayItem.setTarget("1")
    } else if (remark.equals("漪汾苑幼儿园")) {
      tPayItem.setTarget("2")
    }
    tPayItem.setReserve1(Reserve1)
    tPayItem.setReserve2(Reserve2)
    tPayItem.setReserve3(Reserve3)
    tPayItem.setRemark(remark)
    tPayItem.setStatus(0)
    tPayItem
  }


  @RequestMapping(value = Array("upd.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "支付项目信息修改")
  def upd(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    //判断是否有权限
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, request.getParameter("roleId"), "100052")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    val id: String = request.getParameter("id")
    val name: String = request.getParameter("name")
    val amount: String = request.getParameter("amount")
    val target: String = request.getParameter("target")
    val status: String = request.getParameter("status")
    val remark: String = request.getParameter("remark")
    val Reserve1: String = request.getParameter("Reserve1")
    val Reserve2: String = request.getParameter("Reserve2")
    val Reserve3: String = request.getParameter("Reserve3")
    val tPayItem: TPayItem = tPayItemService.getById(id.toInt)
    tPayItem.setName(name)
    tPayItem.setStatus(1)
    tPayItem.setAmount(amount.toDouble)
    tPayItem.setTarget(target)
    tPayItem.setReserve1(Reserve1)
    tPayItem.setReserve2(Reserve2)
    tPayItem.setReserve3(Reserve3)
    tPayItem.setRemark(remark)
    tPayItem.setStatus(status.toInt)
    try {
      tPayItemService.update(tPayItem)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        AjaxResp.getReturn(RespCode.PAY_ERROR_UPD, "")
      }
    }
  }


  @RequestMapping(value = Array("delete.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "支付项目删除")
  def del(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    //判断是否有权限
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, request.getParameter("roleId"), "100052")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    val id: String = request.getParameter("id")
    try {
      tPayItemService.delById(id.toInt)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        AjaxResp.getReturn(RespCode.PAY_ERROR_DEL, "")
      }
    }
  }

  @RequestMapping(value = Array("saveOrUpd.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "状态修改")
  def saveOrUpd(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    //判断是否有权限
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, request.getParameter("roleId"), "100051")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    try {
      val id: String = request.getParameter("id")
      val status: String = request.getParameter("status")
      val tPayItem: TPayItem = tPayItemService.getById(id.toInt)
      tPayItem.setStatus(status.toInt)
      tPayItemService.update(tPayItem)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        AjaxResp.getReturn(RespCode.STATUS_ERROR, "")
      }
    }
  }
}
