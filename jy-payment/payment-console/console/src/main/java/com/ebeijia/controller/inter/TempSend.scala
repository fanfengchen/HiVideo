package com.ebeijia.controller.inter

import java.util._
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.api.BaseValidate
import com.ebeijia.module.wechat.service.template.WechatTemplateService
import com.ebeijia.util.core.RespCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
  * JsapiTicket
  * @author zhicheng.xu
  *         15/11/10
  */
@Controller
@RequestMapping(value = Array("/wechat/temp"))
class TempSend extends BaseValidate {

  @Autowired
  private val wechatTemplateService: WechatTemplateService = null;

  @RequestMapping(value = Array("send.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def getTicket(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val mchtId: String = request.getParameter("mchtId");
    val templateId: String = request.getParameter("templateId");
    val touser: String = request.getParameter("touser");
    val url: String = request.getParameter("url");
    val first: String = request.getParameter("first");
    val keyword: String = request.getParameter("keyword");
    val remark: String = request.getParameter("remark");
    val color: String = request.getParameter("color");
    val modDesc: StringBuilder = new StringBuilder
    val s: Array[Array[String]] = Array(Array(templateId, "1", null,"1"),
      Array(mchtId, "1", "8","1"),
      Array(touser, "1", "64","1"),
      Array(url, "1", "64","1"),
      Array(first, "1", "32","1"),
      Array(keyword, "1", null,"1"),
      Array(modDesc.toString(), "1", null,"1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }

    modDesc.append("{\"touser\":\"").append(touser)
      .append("\",\"template_id\":\"").append(templateId)
      .append("\",\"url\":\"").append(url)
      .append("\",\"data\":")
      .append("{\"first\":{\"value\":\"").append(first).append("\",\"color\":\"").append(color).append("\"}")
    val kwArr: Array[String] = keyword.split(",")
    if (templateId.equals("A9fi8I_R4G1JeWfkwvsu5eHDXV8hfSTCNr1xA6GGnus")) {
      modDesc.append(",\"").append("orderMoneySum").append("\":{\"value\":\"").append(kwArr(0)).append("\",\"color\":\"").append(color).append("\"}")
      modDesc.append(",\"").append("orderProductName").append("\":{\"value\":\"").append(kwArr(1)).append("\",\"color\":\"").append(color).append("\"}")
      modDesc.append(",\"Remark\":{\"value\":\"").append(remark).append("\",\"color\":\"").append(color).append("\"}}}")
    } else{
      for (i <- 0 until kwArr.length) {
        modDesc.append(",\"").append("keyword" + (i + 1)).append("\":{\"value\":\"").append(kwArr(i)).append("\",\"color\":\"").append(color).append("\"}")
      }
      modDesc.append(",\"remark\":{\"value\":\"").append(remark).append("\",\"color\":\"").append(color).append("\"}}}")
    }
    //    modDesc.append(",\"remark\":{\"value\":\"").append(remark).append("\",\"color\":\"").append(color).append("\"}}}")
    val result = wechatTemplateService.send(modDesc.toString(), mchtId)
    if (result != null || "8888888".equals(result) || "9999999".equals(result)) {
      AjaxResp.getReturn(RespCode.TEMP_ERROR_SEND, "")
    } else {
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
  }
}
