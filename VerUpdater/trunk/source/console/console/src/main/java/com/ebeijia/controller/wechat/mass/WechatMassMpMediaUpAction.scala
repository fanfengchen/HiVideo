package com.ebeijia.controller.wechat.mass

import java.io.File
import java.util.Map
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.system.system.TblSysPicInf
import com.ebeijia.module.system.service.picInf.SysPicInfService
import com.ebeijia.module.wechat.service.mass.WechatMassService
import com.ebeijia.util.core.{FuncCode, UpLoad, SystemProperties, RespCode}
import com.ebeijia.util.wechat.WechatError
import org.ebeijia.tools.Validate4J
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}
import org.springframework.web.multipart.{MultipartFile, MultipartHttpServletRequest}

/**
  * 微信上传群发素材
  */

@Controller
@RequestMapping(value = Array("/wechat/mass"))
class WechatMassMpMediaUpAction extends BaseValidRoleFunc{

  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatMassMpMediaUpAction])
  @Autowired
  private val wechatMassService: WechatMassService = null
  @Autowired
  private val sysPicInfService:SysPicInfService = null

  @RequestMapping(value = Array("mpnews.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信上传群发图文")
  @ResponseBody def mpnews(mchtIdFlag:String,articles: String, title: String, description: String,request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId,"200063")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE,"")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    val s: Array[Array[String]] = Array(Array(mchtIdFlag, "1", "10", "1"),Array(articles, "1", null, "1"), Array(title, "0", "100", "1"), Array(description, "0", "640", "1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    try {
      val respMsg: String = wechatMassService.upGtMedia(mchtIdFlag, title, description, articles)
      if (respMsg == null) {
        AjaxResp.getReturn(RespCode.SUCCESS_CODE,"")
      }
      else {
        AjaxResp.getReturn(RespCode.ERROR_CODE, WechatError.checkCode(respMsg), "")
      }
    }
    catch {
      case e: Exception => {
        logger.info("微信上传群发图文失败")
        AjaxResp.getReturn(RespCode.WECHAT_ERROR_UPLOAD_FODDER, "")
      }
    }
  }

  @RequestMapping(value = Array("mpvideo.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信上传群发视频")
  @ResponseBody def mpvideo(title: String, description: String,mediaId: String): Map[String, AnyRef] = {
    val s: Array[Array[String]] = Array(Array(mediaId, "1", null,"1"), Array(title, "1", "30","1"), Array(description, "1", "64","1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    try {
      val respMsg: String = wechatMassService.upVideoMedia("", title, description, mediaId)
      if (respMsg == null) {
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
      }
      else {
        AjaxResp.getReturn(RespCode.ERROR_CODE, WechatError.checkCode(respMsg))
      }
    }
    catch {
      case e: Exception => {
        logger.info("微信上传群发视频失败")
        AjaxResp.getReturn(RespCode.WECHAT_ERROR_UPLOAD_VIDEO,"")
      }
    }
  }
}
