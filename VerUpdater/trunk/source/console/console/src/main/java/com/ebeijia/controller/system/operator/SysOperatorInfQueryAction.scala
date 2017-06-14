package com.ebeijia.controller.system.operator

import java.util._
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.system.system.{TblSysOperatorInf, TblSysOperatorInfId}
import com.ebeijia.module.system.service.operatorInf.SysOperatorService
import com.ebeijia.util.core.{FuncCode, RespCode}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
  * OperatorInfQueryAction
  * @author xiong.wang
  *         15/8/7
  * 操作员查询
  */
@Controller
@RequestMapping(value = Array("/sys/opr"))
class SysOperatorInfQueryAction extends BaseValidRoleFunc{
  private val logger: Logger = LoggerFactory.getLogger(classOf[SysOperatorInfQueryAction])
  @Autowired
  private val operatorService: SysOperatorService = null

  @RequestMapping(value = Array("/query.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def query(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    //判断是否有权限(查询跟维护权限有一就可通过)
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
//    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),"100052")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),FuncCode.OPR_FUNC_QUERY)
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"")
    }
    val oprName: String = request.getParameter("oprName")
    val oprStat: String = request.getParameter("oprStat")
    val pageData: String = request.getParameter("aoData")
    var map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: Map[String, AnyRef] = operatorService.findBySql(oprName, oprStat, pageData)
      //多条连接 添加key
      val list: List[AnyRef] = new LinkedList[AnyRef]
      val m:AnyRef=mapTmp.get("list")
      val lists:List[_]=m.asInstanceOf[List[_]]

      val it: Iterator[_] = lists.iterator
      while (it.hasNext) {
        val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
        val o: Array[AnyRef] = it.next.asInstanceOf[Array[AnyRef]]
        hashMap.put("brhNo", o(0))
        hashMap.put("oprId", o(1))
        hashMap.put("oprName", o(2))
        hashMap.put("roleId", o(3))
        hashMap.put("oprDsc", o(4))
        hashMap.put("oprStat", o(5))
        hashMap.put("roleName", o(6))
        list.add(hashMap)
      }
      map.put("oprList", list)
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      map = AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
      map
    }
    catch {
      case e: Exception => {
        logger.info("系统操作员查询失败:"+e)
        AjaxResp.getReturn(RespCode.OPR_ERROR_CODE,"")
      }
    }
  }



  @RequestMapping(value = Array("/queryList.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def queryList(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {

    val oprIdd: String = request.getParameter("oprId")
    var oprId :Int=0
    if(null!=oprIdd && !("".equals(oprIdd))){
      oprId= oprIdd.toInt
    }
    val oprName: String = request.getParameter("oprName")
    val oprStat: String = request.getParameter("oprStat")
    val pageData: String = request.getParameter("aoData")
    var map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: Map[String, AnyRef] = operatorService.findBySql(oprName, oprStat, pageData)
      //多条连接 添加key
      val list: List[AnyRef] = new LinkedList[AnyRef]
      val m:AnyRef=mapTmp.get("list")
      val lists:List[_]=m.asInstanceOf[List[_]]

      val it: Iterator[_] = lists.iterator
      while (it.hasNext) {
        val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
        val o: Array[AnyRef] = it.next.asInstanceOf[Array[AnyRef]]
        hashMap.put("picUrl", o(0))
        hashMap.put("oprId", o(1))
        hashMap.put("oprName", o(2))
        hashMap.put("roleId", o(3))
        hashMap.put("oprDsc", o(4))
        hashMap.put("oprStat", o(5))
        hashMap.put("roleName", o(6))
        hashMap.put("code", o(7))
        list.add(hashMap)
      }
      map.put("oprList", list)
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      map = AjaxResp.getReturn(RespCode.SUCCESS_CODE,map)
      map
    }
    catch {
      case e: Exception => {
        logger.info("系统操作员查询失败:"+e)
        AjaxResp.getReturn(RespCode.OPR_ERROR_CODE,"")//"")// "系统操作员查询失败,请联系操作员或稍后再试", "")
      }
    }
  }


  @RequestMapping(value = Array("/queryopr.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def queryopr(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val oprIdd: String = request.getParameter("oprId")
    var oprId :Int=0
    if(null!=oprIdd && !("".equals(oprIdd))){
      oprId= oprIdd.toInt
    }
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val brhNo: String = request.getParameter("brhNo")
      val toi:TblSysOperatorInfId = new TblSysOperatorInfId
      toi.setBrhNo(brhNo)
      toi.setOprId(oprId)
      val opr: TblSysOperatorInf = operatorService.getById(toi)
      map.put("oprList", opr)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)

    }
    catch {
      case e: Exception => {
        logger.info("系统操作员查询用户失败:"+e)
        AjaxResp.getReturn(RespCode.OPR_ERROR_CODE,"")
      }
    }
  }

}
