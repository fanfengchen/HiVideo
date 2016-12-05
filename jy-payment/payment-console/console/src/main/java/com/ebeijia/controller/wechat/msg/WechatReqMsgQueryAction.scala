package com.ebeijia.controller.wechat.msg

import java.util.{HashMap, Map}
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.module.wechat.service.msg.WechatReqMsgService
import com.ebeijia.util.core.RespCode
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * 消息查询
 */

@Controller
@RequestMapping(value = Array("/wechat/reqMsg"))
class WechatReqMsgQueryAction extends BaseValidRoleFunc{
  @Autowired
  private val wechatReqMsgService: WechatReqMsgService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatReqMsgQueryAction])

  @RequestMapping(value = Array("query.html"), method = Array(RequestMethod.POST))
  @ResponseBody def query(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId,"200022")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE,"")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    val mchtId: String = request.getParameter("mchtId")
    val pageData: String = request.getParameter("aoData")
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: Map[String, AnyRef] = wechatReqMsgService.findBySql(mchtId, pageData)
      map.put("reqMsgList", mapTmp.get("list"))
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("微信收到消息查询失败,请联系管理员或稍后再试")
        AjaxResp.getReturn(RespCode.WECHAT_MSG_GET_ERROR, "")
      }
    }
  }
}
