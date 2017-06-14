package com.ebeijia.controller.wechat.subGroup

/**
 * 用户组别查询
 */

import java.util.{HashMap, Map}
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.module.wechat.service.group.WechatSubGroupService
import com.ebeijia.util.core.RespCode
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

@Controller
@RequestMapping(value = Array("/wechat/subGroup"))
class WechatGroupQueryAction {

  @Autowired
  private val wechatSubGroupService: WechatSubGroupService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatGroupQueryAction])

  @RequestMapping(value = Array("query.html"), method = Array(RequestMethod.POST))
  @ResponseBody def query(mchtId:String,session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val name: String = request.getParameter("name")
    val pageData: String = request.getParameter("aoData")
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: Map[String, AnyRef] = wechatSubGroupService.findBySql(mchtId,name, pageData)
      map.put("groupList", mapTmp.get("list"))
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("关注者组别查询失败")
        AjaxResp.getReturn(RespCode.WECHAT_GUP_SEL_ERROR, "")
      }
    }
  }
}

