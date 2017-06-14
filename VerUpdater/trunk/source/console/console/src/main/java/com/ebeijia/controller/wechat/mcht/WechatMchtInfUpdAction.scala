package com.ebeijia.controller.wechat.mcht

import java.util.{List, Map}
import javax.servlet.http.HttpServletRequest

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.system.vo.RoleFuncParam
import com.ebeijia.entity.wechat.base.{TblWechatMchtInf, TblWechatMchtInfId}
import com.ebeijia.module.wechat.service.core.WechatMchtInfService
import com.ebeijia.util.core.{FuncCode, RespCode}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * 微信账号修改
 */


@Controller
@RequestMapping(value = Array("/wechat/mcht"))
class WechatMchtInfUpdAction  extends BaseValidRoleFunc{
  @Autowired
  private val wechatMchtInfService: WechatMchtInfService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatMchtInfUpdAction])

  @RequestMapping(value = Array("upd.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信账号修改")
  @ResponseBody def upd(roleFuncParam: RoleFuncParam, tblWechatMchtInf: TblWechatMchtInf,httpServletRequest: HttpServletRequest): Map[String, AnyRef] = {
//    判断是否有权限
    roleFuncParam.setFuncId("200002")
    val msg: String = this.validRoleFunc(roleFuncParam)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    if (tblWechatMchtInf == null) {
      return AjaxResp.getReturn(RespCode.OBJECT_ERROR_NULL, "")
    }
    try {
      val tblWechatMchtInfId:TblWechatMchtInfId = new TblWechatMchtInfId
      tblWechatMchtInfId.setMchtId(httpServletRequest.getParameter("mchtIdFlag"))
      tblWechatMchtInfId.setBrhNo(httpServletRequest.getParameter("brhNoFlag"))
      tblWechatMchtInf.setId(tblWechatMchtInfId)
      tblWechatMchtInf.setToken(httpServletRequest.getParameter("wxtoken"))
      if (wechatMchtInfService.getById(tblWechatMchtInfId) == null) {
        return AjaxResp.getReturn(RespCode.WECHAT_ACCOUNT_NOTEXIST,"")
      }
//      val twm: TblWechatMchtInf = wechatMchtInfService.queryWechatMchtInf(tblWechatMchtInf.getId.getMchtId)
//      if (tblWechatMchtInf == null) {
//      }
     val list:List[TblWechatMchtInf]= wechatMchtInfService.isListMchtInf(tblWechatMchtInf)
      if(list.size()>0){
        return AjaxResp.getReturn(RespCode.WECHAT_ACCOUNT_REBAND_ERROE, "")
      }
      if(tblWechatMchtInf.getWechatType.equals("订阅号")) tblWechatMchtInf.setWechatType("1") else  tblWechatMchtInf.setWechatType("2")
      wechatMchtInfService.updateWechatMchtInf(tblWechatMchtInf)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")

    }
    catch {
      case e: Exception => {
        logger.info("微信账户修改失败,请联系管理员或稍后再试:"+e)
        AjaxResp.getReturn(RespCode.WECHAT_ACCOUNT_UPDERROR, "")

      }
    }
  }

  @RequestMapping(value = Array("del.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信账户删除")
  @ResponseBody def del(roleFuncParam: RoleFuncParam, tblWechatMchtInfId: TblWechatMchtInfId,httpServletRequest: HttpServletRequest): Map[String, AnyRef] = {
    roleFuncParam.setFuncId("200002")
    val msg: String = this.validRoleFunc(roleFuncParam)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    try {
      tblWechatMchtInfId.setMchtId(httpServletRequest.getParameter("mchtIdFlag"))
      tblWechatMchtInfId.setBrhNo(httpServletRequest.getParameter("brhIdFlag"))
      val tblWechatMchtInf: TblWechatMchtInf = wechatMchtInfService.getById(tblWechatMchtInfId)
      if (tblWechatMchtInf == null) {
        return AjaxResp.getReturn(RespCode.WECHAT_MCHT_MOTFUND,  "")

      }
      else {
        wechatMchtInfService.deleteWechatMchtInf(tblWechatMchtInf)
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")

      }
    }
    catch {
      case e: Exception => {
        logger.info("微信账户删除失败,请联系管理员或稍后再试:"+e)
        AjaxResp.getReturn(RespCode.WECHAT_ACCOUNT_DELERROR,  "")
      }
    }
  }
}