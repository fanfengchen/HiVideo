package com.ebeijia.controller.wechat.subGroup

import java.util.Map
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.wechat.base.TblWechatSubGroup
import com.ebeijia.module.wechat.service.group.WechatSubGroupService
import com.ebeijia.util.core.RespCode
import com.ebeijia.util.wechat.WechatError
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * 用户组别新增
 */

@Controller
@RequestMapping(value = Array("/wechat/subGroup"))
class WechatGroupAddAction extends BaseValidRoleFunc{
  @Autowired
  private val wechatSubGroupService: WechatSubGroupService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatGroupAddAction])

  @RequestMapping(value = Array("add.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "新增用户组别")
  @ResponseBody def add(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val mchtId: String = request.getParameter("mchtIdFlag")
    val name: String = request.getParameter("name")
    val s: Array[Array[String]] = Array(Array(mchtId, "1", "15","1"), Array(name, "1", "64","1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
    }
    try {
      //验证分组限制
      val total: Int = wechatSubGroupService.findCount(mchtId)
      if (total >= 100) {
        AjaxResp.getReturn(RespCode.WECHAT_GUP_KEY_EXITS, "")
      }
      val map: Map[String,AnyRef] = wechatSubGroupService.save(mchtId, name)
      if (map.get("gdata") != null) {
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, map.get("gdata"))
      }else {
        AjaxResp.getReturn(RespCode.ERROR_CODE, WechatError.checkCode(map.get("result").toString), "")
      }
    }
    catch {
      case e: Exception => {
        logger.info("新增用户组别失败")
        AjaxResp.getReturn(RespCode.WECHAT_GUP_ADD_ERROR, "")
      }
    }
  }
}

