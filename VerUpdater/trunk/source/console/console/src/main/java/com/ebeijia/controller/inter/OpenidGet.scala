package com.ebeijia.controller.inter

import java.util.Map
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.api.BaseValidate
import com.ebeijia.entity.vo.wechat.AccessToken
import com.ebeijia.entity.wechat.base.TblWechatMchtInf
import com.ebeijia.module.wechat.service.core.{WechatMchtInfService, WechatTokenService}
import com.ebeijia.util.core.RespCode
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

/**
  * OpenidGet
  * @author xiong.wang
  *         16/6/17
  */
@Controller
@RequestMapping(value = Array("/wechat/openid"))
class OpenidGet extends BaseValidate {
  @Autowired
  private val token: WechatTokenService = null
  @Autowired
  private val wechatMchtInfService: WechatMchtInfService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[OpenidGet])

  @RequestMapping(value = Array("get.html"))
  @ResponseBody
  def productList(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val code: String = request.getParameter("code")
    val mchtId: String = request.getParameter("mchtId")
    val s: Array[Array[String]] = Array(Array(mchtId, "1", "8","1"),Array(code, "1", "64","1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    val data: TblWechatMchtInf = wechatMchtInfService.queryWechatMchtInf(mchtId)
    val appid: String = data.getAppid
    val appsecret: String = data.getAppsecret
    val accessToken: AccessToken =
      token.getAccessTokenAndopenid(appid, appsecret, code)
    try {
      if (accessToken != null) {
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, accessToken.getOpenid)
      } else {
        AjaxResp.getReturn(RespCode.ERROR_CODE,"")
      }
    } catch {
      case e: Exception => {
        logger.info("获取账号失败，请重新再试" + e.printStackTrace())
        AjaxResp.getReturn(RespCode.OPENID_ERROR_GET,"")
      }
    }
  }
}