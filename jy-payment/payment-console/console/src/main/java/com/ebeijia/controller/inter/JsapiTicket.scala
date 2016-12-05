package com.ebeijia.controller.inter

import java.util._
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.api.BaseValidate
import com.ebeijia.module.wechat.service.core.WechatJsService
import com.ebeijia.util.common.Sha1Util
import com.ebeijia.util.core.RespCode
import org.ebeijia.tools.RandomNum4J
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

/**
  * JsapiTicket
  * @author zhicheng.xu
  *         15/11/10
  */
@Controller
@RequestMapping(value = Array("/wechat/jsTicket"))
class JsapiTicket extends BaseValidate {

  @Autowired
  private val wechatJsServer: WechatJsService = null;

  @RequestMapping(value = Array("get.html"))
  @ResponseBody
  def getTicket(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val map: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
    val url = request.getParameter("url");
    val mchtId = request.getParameter("mchtId");
    val s: Array[Array[String]] = Array(Array(url, "1", null,"1"),Array(mchtId, "1", "8","1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    //签名
    val ticket = wechatJsServer.getJsTicket(url, mchtId)
    if (ticket == null || "8888888".equals(ticket) || "9999999".equals(ticket)) {
      AjaxResp.getReturn(RespCode.ERROR_CODE,"")
    } else {
      val noncestr: String = RandomNum4J.getRandomString(16)
      val timestamp: String = Sha1Util.getTimeStamp
      val signBuffer: StringBuilder = new StringBuilder
      signBuffer.append("jsapi_ticket=").append(ticket).append("&noncestr=").append(noncestr)
        .append("&timestamp=").append(timestamp).append("&url=").append(url)
      val sign: String = Sha1Util.encode(signBuffer.toString())
      map.put("noncestr", noncestr)
      map.put("timestamp", timestamp)
      map.put("sign", sign)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
  }
}
