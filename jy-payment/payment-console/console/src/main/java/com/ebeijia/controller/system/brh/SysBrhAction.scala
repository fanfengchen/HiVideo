package com.ebeijia.controller.system.brh

import java.util._
import javax.servlet.http.HttpServletRequest

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.system.brh.TblSysBrhInf
import com.ebeijia.entity.system.system.{TblSysRoleInfId, TblSysRoleInf, TblSysOperatorInf}
import com.ebeijia.entity.system.vo.RoleFuncParam
import com.ebeijia.entity.wechat.base.{TblWechatMchtInf, TblWechatMchtInfId}
import com.ebeijia.module.system.service.brh.SysBrhInfService
import com.ebeijia.module.system.service.operatorInf.SysOperatorService
import com.ebeijia.module.system.service.roleInf.SysRoleInfService
import com.ebeijia.module.wechat.service.core.WechatMchtInfService
import com.ebeijia.util.core.{FuncCode, RespCode}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation._

/**
  * 机构管理
  * BrhManageAction
  *
  * @author xiong.wang
  *         2016/6/23
  */
@Controller
@RequestMapping(value = Array("/sys/brh"))
class SysBrhAction extends BaseValidRoleFunc{
  private val logger: Logger = LoggerFactory.getLogger(classOf[SysBrhAction])
  @Autowired
  private val tblBrhInfService: SysBrhInfService = null
  @Autowired
  private val wechatMchtInfService: WechatMchtInfService = null
  @Autowired
  private val sysOperatorService:SysOperatorService = null
  @Autowired
  private val sysRoleInfService:SysRoleInfService = null

//  @InitBinder
//  def initBinder(binder:WebDataBinder) {
//    binder.registerCustomEditor(classOf[TblSysBrhInf], new CustomTblSysBrhInfEditor());
//  }

  @RequestMapping(value = Array("query.html"), method = Array(RequestMethod.POST))
  @ResponseBody def query(roleFuncParam: RoleFuncParam, brhNo: String, brhName: String, aoData: String,request: HttpServletRequest): Map[String, AnyRef] = {
    val brhId:String = request.getParameter("brhId")
    roleFuncParam.setFuncId(FuncCode.BRH_FUNC_MAINTAIN)
    val msg: String = this.validRoleFunc(roleFuncParam)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    var map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: Map[String, AnyRef] = tblBrhInfService.findBySql(roleFuncParam.getRoleId,brhId,brhNo, brhName, aoData)
      map.put("brhList", mapTmp.get("list"))
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      map = AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
      map
    }
    catch {
      case e: Exception => {
        logger.info("机构查询失败:" + e)
        AjaxResp.getReturn(RespCode.BRH_ERROR_SEL, "")
      }
    }
  }


  @RequestMapping(value = Array("add.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "机构添加")
  @ResponseBody def add(brhNo: String,roleFuncParam: RoleFuncParam,tblSysBrhInf: TblSysBrhInf): Map[String, AnyRef] = {
    roleFuncParam.setFuncId(FuncCode.BRH_FUNC_ADDITION)
    val msg: String = this.validRoleFunc(roleFuncParam)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    if(!roleFuncParam.getRoleId.equals("1")){
      if(tblSysBrhInf.getBrhLeavel.equals("0") ){
        return AjaxResp.getReturn(RespCode.ERROR_CODE, "", "非admin超级管理员不得添加母机构")
      }
    }
    if (tblSysBrhInf == null) {
      return AjaxResp.getReturn(RespCode.OBJECT_ERROR_NULL, "")
    }
    val s: Array[Array[String]] = Array(Array(tblSysBrhInf.getBrhId, "1", "32","1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    try {
      //判断机构号唯一
      val flag: Boolean = tblBrhInfService.vailBrhUniq(tblSysBrhInf.getBrhId)
      if (!flag) {
        return AjaxResp.getReturn(RespCode.BRH_ERROR_UNIQ, "")
      }
      val str:String = tblBrhInfService.save(brhNo,tblSysBrhInf)
      if(str != null){
        return AjaxResp.getReturn(RespCode.ERROR_CODE, "",str)
      }
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        logger.info("新增机构失败:" + e)
        AjaxResp.getReturn(RespCode.BRH_ERROR_ADD, "")
      }
    }
  }

  @RequestMapping(value = Array("del.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "机构删除")
  @ResponseBody def del(roleFuncParam: RoleFuncParam, brhId: String): Map[String, AnyRef] = {
    roleFuncParam.setFuncId(FuncCode.BRH_FUNC_MAINTAIN)
    val msg: String = this.validRoleFunc(roleFuncParam)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "")
    }
    try {
      //绑定微信账户的机构不予删除
      val count:Int = wechatMchtInfService.selbrhdel(brhId)
      if(count == 1){
        return AjaxResp.getReturn(RespCode.ERROR_CODE, "","微信账号绑定的机构不可以删除")
      }else if(count == 2){
        return AjaxResp.getReturn(RespCode.ERROR_CODE, "","用户绑定的机构不可以删除")
      }else if(count == 3){
        return AjaxResp.getReturn(RespCode.ERROR_CODE, "","角色绑定的机构不可以删除")
      }
      val repMsg:String = tblBrhInfService.delById(brhId)
      if(repMsg != null){
        return AjaxResp.getReturn(RespCode.ERROR_CODE, "",repMsg)
      }
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: IllegalArgumentException => {
        logger.info("删除机构失败:" + e)
        AjaxResp.getReturn(RespCode.ERROR_CODE, "","机构号不存在，刷新后重试")
      }
      case e: Exception => {
        logger.info("删除机构失败:" + e)
        AjaxResp.getReturn(RespCode.BRH_ERROR_DEL, "")
      }
    }
  }

  @RequestMapping(value = Array("upd.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "机构修改")
  @ResponseBody def upd(roleFuncParam: RoleFuncParam, tblSysBrhInf: TblSysBrhInf): Map[String, AnyRef] = {
    roleFuncParam.setFuncId(FuncCode.BRH_FUNC_MAINTAIN)
    val msg: String = this.validRoleFunc(roleFuncParam)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    try {
      tblBrhInfService.saveOrUpdate(tblSysBrhInf)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        logger.info("修改机构失败:" + e)
        AjaxResp.getReturn(RespCode.BRH_ERROR_UPD, "")
      }
    }
  }
}
