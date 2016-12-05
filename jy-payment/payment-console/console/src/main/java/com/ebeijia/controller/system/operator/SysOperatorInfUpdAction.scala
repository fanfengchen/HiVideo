
package com.ebeijia.controller.system.operator

import java.util.{List, Map}
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.system.system.{TblSysRoleInfId, TblSysRoleInf, TblSysOperatorInfId, TblSysOperatorInf}
import com.ebeijia.module.system.service.operatorInf.SysOperatorService
import com.ebeijia.module.system.service.roleInf.SysRoleInfService
import com.ebeijia.util.common.EncryptMD5Util
import com.ebeijia.util.core.{Constant, FuncCode, RespCode}
import org.ebeijia.tools.{DateTime4J, Validate4J}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}


/**
  * OperatorInfUpdAction
  *
  * @author xiong.wang
  *         16/6/17
  * 操作员查询
  */
@Controller
@RequestMapping(value = Array("/sys/opr"))
class SysOperatorInfUpdAction  extends BaseValidRoleFunc{

  private val logger: Logger = LoggerFactory.getLogger(classOf[SysOperatorInfUpdAction])
  @Autowired
  private val operatorService: SysOperatorService = null
  @Autowired
  private val roleInfService: SysRoleInfService = null

  @RequestMapping(value = Array("upd.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "操作员修改")
  def upd(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),FuncCode.OPR_FUNC_MAINTAIN)
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    val oldbrhId: String = request.getParameter("oldbrhId")
    val newbrhId: String = request.getParameter("newbrhId")
    val oprIdd: String = request.getParameter("oprId")
    val oprName: String = request.getParameter("oprName")
    val roleIdd: String = request.getParameter("roleIdd")
    val oprDsc: String = request.getParameter("dsc")
    //    val code: String = request.getParameter("code")
    val s: Array[Array[String]] = Array(Array(oprIdd, "1", "10"),Array(roleIdd, "1", "6"), Array(oprName, "0", "32"),
      Array(oprDsc, "0", "128"))
    if (!Validate4J.checkStrArrLen(s)) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
    }
    val oprId:Int=oprIdd.toInt
    val roleId:Int=roleIdd.toInt
    val toi:TblSysOperatorInfId = new TblSysOperatorInfId
    toi.setBrhNo(oldbrhId)
    toi.setOprId(oprId)
    val tbloprInf: TblSysOperatorInf = operatorService.getById(toi)
    if (tbloprInf == null) {
      return  AjaxResp.getReturn(RespCode.PARAM_ERROR,"")

    }
    //判断角色是否是opr
    //如果角色是opr（角色id=1），授权码必填
    if(roleId==1){
      //判断是否只有一个opr角色
      val roleList:List[TblSysOperatorInf]=operatorService.getOperatorRoleId(roleId,oprId)
      if(roleList.size()>0){
        return  AjaxResp.getReturn(RespCode.OPER_ROLE_ERROR_UNIQ,"")
      }
    }else{
      if(tbloprInf.getRoleId == 1){
        return  AjaxResp.getReturn(RespCode.OPER_ROLE_ERROR_DONOT,"")
      }
    }

    tbloprInf.getId.setOprId(oprId)
    tbloprInf.getId.setBrhNo(newbrhId)
    tbloprInf.setOprName(oprName)
//    tbloprInf.setRoleId(roleIdd.toInt)
    tbloprInf.setOprDsc(oprDsc)
    tbloprInf.setUpdTime(DateTime4J.getCurrentDateTime)
    try {
      //判断操作员用户名称唯一
      val oprName:Int=operatorService.isOperatorName(tbloprInf.getId.getOprId,tbloprInf.getOprName)
      if(oprName>0){
        return AjaxResp.getReturn(RespCode.OPER_NAME_ERROR_CONFIT,"")
      }
      operatorService.updateBySql(tbloprInf,roleIdd)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        AjaxResp.getReturn(RespCode.ERROR_CODE,"")
      }
    }
  }

  @RequestMapping(value = Array("passUpd.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "操作员修改密码")
  @ResponseBody
  def passUpd(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    //判断是否有权限
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),"100002")
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    val oldPwd: String = request.getParameter("oldPwd")
    val newPwd: String = request.getParameter("newPwd")

    val s: Array[Array[String]] = Array(Array(usrId, "1", "10"),Array(oldPwd, "1", "32"), Array(newPwd, "1", "32"))
    if (!Validate4J.checkStrArrLen(s)) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
    }
    if(oldPwd.equals(newPwd)){
      return AjaxResp.getReturn(RespCode.OPER_PWD_ISEXIST,"")
    }
    try {
      val list: List[TblSysOperatorInf] = operatorService.checkOperator(usrId, EncryptMD5Util.encrypt(oldPwd))
      if (list.isEmpty) {
        return AjaxResp.getReturn(RespCode.OPER_PWDORNAME_ERROR,"")
      }
      else {
        //修改密码 2015-10-21
        operatorService.updatePwd(usrId,EncryptMD5Util.encrypt(newPwd))
        // println("操作员修改密码成功：oprId="+oprId+"----没加密密码="+newPwd)
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
      }
    }
    catch {
      case e: Exception => {
        logger.info("操作员修改密码失败:"+e)
        AjaxResp.getReturn(RespCode.ERROR_CODE,"")
      }
    }
  }

  @RequestMapping(value = Array("del.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "操作员删除")
  def del(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),"100002")
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    val oprIdd: String = request.getParameter("oprId")
    val s: Array[Array[String]] = Array(Array(oprIdd, "1", "10"))
    if (!Validate4J.checkStrArrLen(s)) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
    }
    val oprId:Int=oprIdd.toInt
    try {
      val brhNo: String = request.getParameter("brhId")
      val toi:TblSysOperatorInfId = new TblSysOperatorInfId
      toi.setBrhNo(brhNo)
      toi.setOprId(oprId)
      val tblOprInf: TblSysOperatorInf = operatorService.getById(toi)
      if (tblOprInf == null) {
        return AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
      }
      else {
        if (tblOprInf.getRoleId == 1) {
          return AjaxResp.getReturn(RespCode.OPER_NAME_ERROR_DONOTDEL,"")
        }
        operatorService.delById(toi)
        if(usrId.equals(tblOprInf.getId.getOprId)){
          AjaxResp.getReturn(RespCode.ERROR_CODE,"","当前用户正被使用不予删除")
        }
        //如果删除的是本身则清除session
        //        val tblOperatorInf: TblOperatorInf = session.getAttribute("operator").asInstanceOf[TblOperatorInf]
        //        if (tblOperatorInf.getOprId.equals(tblOprInf.getOprId)) {
        //          session.removeAttribute("operator")
        //          session.removeAttribute("role")
        //          session.removeAttribute("roleId")
        //        }
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
      }
    }
    catch {
      case e: Exception => {
        logger.info("删除操作员失败:"+e)
        AjaxResp.getReturn(RespCode.ERROR_CODE,"","删除操作员失败,请联系操作员或稍后再试")
      }
    }
  }



  @RequestMapping(value = Array("resetUpd.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "操作员重置密码")
  @ResponseBody
  def resetUpd(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    //判断是否有权限
    /*val msg:String=this.validRoleFunc(session,"100002")
    if(msg!=null){
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }*/
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),"100002")
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    val oprIdd: String = request.getParameter("oprId")

    val s: Array[Array[String]] = Array(Array(oprIdd, "1", "10"))
    if (!Validate4J.checkStrArrLen(s)) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
    }
    val oprId:Int=oprIdd.toInt
    try {
      val brhNo: String = request.getParameter("brhId")
      val toi:TblSysOperatorInfId = new TblSysOperatorInfId
      toi.setBrhNo(brhNo)
      toi.setOprId(oprId)
      val opr: TblSysOperatorInf = operatorService.getById(toi)
      if (null==opr) {
        return AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
      }
      else {
        //重置密码 2016-03-16
        operatorService.updatePwd(usrId,Constant.PASSWORD)

        AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
      }
    }
    catch {
      case e: Exception => {
        logger.info("操作员重置密码失败:"+e)
        AjaxResp.getReturn(RespCode.OPR_ERROR_CODE,"")
      }
    }
  }
}

