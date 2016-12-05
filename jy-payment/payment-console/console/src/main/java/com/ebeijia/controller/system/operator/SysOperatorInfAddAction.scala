package com.ebeijia.controller.system.operator

import java.util.Map
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.system.system.{TblSysOperatorInf, TblSysOperatorInfId}
import com.ebeijia.module.system.service.operatorInf.SysOperatorService
import com.ebeijia.util.common.EncryptMD5Util
import com.ebeijia.util.core.{FuncCode, RespCode}
import org.ebeijia.tools.{DateTime4J, Validate4J}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * AdminInfAddAction
 * @author zhicheng.xu
 *         15/8/7
 */


@Controller
@RequestMapping(value = Array("/sys/opr"))
class SysOperatorInfAddAction extends BaseValidRoleFunc{

  private val logger: Logger = LoggerFactory.getLogger(classOf[SysOperatorInfAddAction])
  @Autowired
  private val operatorService: SysOperatorService = null

  @RequestMapping(value = Array("add.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "操作员添加")
  @ResponseBody def add(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val tokenId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg:String=this.validRoleFunc(tokenId,token,request.getParameter("roleId"),FuncCode.OPR_FUNC_ADDITION)
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"")
    }
    val tblOperatorInf: TblSysOperatorInf = this.build(request)
    if (tblOperatorInf == null) {
      return  AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }

    try {
      //判断操作员用户名称唯一
      val operatorName:Int=operatorService.isOperatorName(tblOperatorInf.getId.getOprId,tblOperatorInf.getOprName)
      if(operatorName>0){
        return AjaxResp.getReturn(RespCode.OPR_ERROR_CODE, "")
      }
      if(tblOperatorInf.getRoleId == 1){
        return AjaxResp.getReturn(RespCode.OPER_ROLE_ERROR_UNIQ, "")
      }
      operatorService.save(tblOperatorInf)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        logger.info("新增操作员失败:"+e)
        AjaxResp.getReturn(RespCode.OPR_ERROR_ADD, "")
      }
    }
  }

  private def build(request: HttpServletRequest): TblSysOperatorInf = {
    val brhNo: String = request.getParameter("brhId")//机构编号默认写死
    val operatorName: String = request.getParameter("operatorName")
    val roleIdd: String = request.getParameter("roleIdd")
    val operatorPwd: String = request.getParameter("operatorPwd")
    val operatorDsc: String = request.getParameter("operatorDsc")

    val s: Array[Array[String]] = Array(Array(operatorName, "1", "32"),Array(roleIdd, "1", "6"),Array(operatorPwd, "1", "32"),
      Array(operatorDsc, "0", "128"))
    if (!Validate4J.checkStrArrLen(s)) {
      AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
      return null
    }

    val roleId:Int=roleIdd.toInt
    val id = operatorService.getMaxId()
    val tblOperatorInf: TblSysOperatorInf = new TblSysOperatorInf
    val tfId: TblSysOperatorInfId = new TblSysOperatorInfId
    tfId.setBrhNo(brhNo)
    tfId.setOprId(id)
    tblOperatorInf.setId(tfId)
    tblOperatorInf.setOprName(operatorName)
    tblOperatorInf.setRoleId(roleId)
    tblOperatorInf.setOprStat("0")
    tblOperatorInf.setOprPwd(EncryptMD5Util.encrypt(operatorPwd))
    tblOperatorInf.setOprDsc(operatorDsc)
    tblOperatorInf.setLastLogTime(" ")
    tblOperatorInf.setCrtTime(DateTime4J.getCurrentDateTime)
    tblOperatorInf
  }
}

