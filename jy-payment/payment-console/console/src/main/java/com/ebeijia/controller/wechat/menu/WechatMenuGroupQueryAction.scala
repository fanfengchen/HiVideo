package com.ebeijia.controller.wechat.menu

import java.util.{HashMap, List, Map}
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.entity.wechat.base.TblWechatMenuGroup
import com.ebeijia.module.wechat.service.menu.WechatMenuGroupService
import com.ebeijia.util.core.RespCode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * 微信菜单查询
 */


@Controller
@RequestMapping(value = Array("/wechat/menu"))
class WechatMenuGroupQueryAction {
  @Autowired
  private val wechatMenuGroupService: WechatMenuGroupService = null

  @RequestMapping(value = Array("groupQuery.html"), method = Array(RequestMethod.POST))
  @ResponseBody def wechatMenuQuery(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val mchtId: String = request.getParameter("mchtIdFlag")
    val s: Array[Array[String]] = Array(Array(mchtId, "15", "15","1"))
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    if (ActionValidate.checkArray(s) != null) {
      AjaxResp.getReturn(RespCode.PARAM_ERROR,"",ActionValidate.checkArray(s))
    }
    val list: List[TblWechatMenuGroup] = wechatMenuGroupService.find(mchtId)
    map.put("info", list)
    AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)

  }
}
