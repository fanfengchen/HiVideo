package com.ebeijia.controller.wechat.mass

import java.util.Map
import javax.servlet.http.HttpServletRequest

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.wechat.base.TblWechatSubscribe
import com.ebeijia.module.wechat.service.mass.WechatMassService
import com.ebeijia.module.wechat.service.subscribe.WechatSubscribeService
import com.ebeijia.util.core.RespCode
import com.ebeijia.util.wechat.WechatError
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
  * 微信群发消息
  */


@Controller
@RequestMapping(value = Array("/wechat/mass"))
class WechatMassSendAction extends BaseValidRoleFunc{
  @Autowired
  private val wechatMassService: WechatMassService = null
  @Autowired
  private val wechatSubscribeService:WechatSubscribeService = null

  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatMassSendAction])

  @RequestMapping(value = Array("sendAll.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信群发消息")
  @ResponseBody def sendAll(mchtId:String,content: String, groupId: String, media: String, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId,"200061")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE,"")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    val `type`: String = request.getParameter("msgType")
    val s: Array[Array[String]] = Array(Array(content, "0", "3000", "1"), Array(`type`, "4", "6", "1"), Array(groupId, "1", "6", "1"), Array(media, "0", "100", "1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    try {
      val list:java.util.List[TblWechatSubscribe] = wechatSubscribeService.findByGroup(groupId)
      if(list.size() == 0 || list.isEmpty){
        return AjaxResp.getReturn(RespCode.ERROR_CODE, "","该分组成员为空请选择其他分组")
      }
      val respMsg: String = wechatMassService.sendByGroup(mchtId, content, `type`, groupId, media)
      if (respMsg == null) {
        AjaxResp.getReturn(RespCode.WECHAT_MASS_SUCCESS, "")
      }
      else {
        AjaxResp.getReturn(RespCode.ERROR_CODE,"", WechatError.checkCode(respMsg))
      }
    }
    catch {
      case e: Exception => {
        request.setAttribute("message", "群发消息失败")
        logger.info("群发消息失败:" + e)
        AjaxResp.getReturn(RespCode.WECHAT_MASS_ERROR, "")
      }
    }
  }

  @RequestMapping(value = Array("sendUsr.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信筛选群发消息")
  @ResponseBody def sendUsr(mchtId:String,content: String, msgType: String, toUsr: String, media: String, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId,"200061")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE,"")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    val s: Array[Array[String]] = Array(Array(content, "0", "3000", "1"), Array(msgType, "4", "6", "1"), Array(toUsr, "1", null, "1"), Array(media, "0", "100", "1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    try {
      val respMsg: String = wechatMassService.sendByUsr(mchtId, content, msgType, toUsr, media)
      if (respMsg == null) {
        AjaxResp.getReturn(RespCode.WECHAT_MASS_SUCCESS, "")
      }
      else {
        AjaxResp.getReturn(RespCode.ERROR_CODE,"", WechatError.checkCode(respMsg))
      }
    }
    catch {
      case e: Exception => {
        request.setAttribute("message", "筛选群发消息失败")
        logger.info("筛选群发消息失败:" + e)
        AjaxResp.getReturn(RespCode.WECHAT_MASS_FILTERFODDER_ERROE, "")
      }
    }
  }
}

