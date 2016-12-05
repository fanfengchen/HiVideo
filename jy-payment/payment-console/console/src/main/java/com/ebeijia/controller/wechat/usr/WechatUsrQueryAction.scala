package com.ebeijia.controller.wechat.usr

import java.util.{HashMap, List, Map}
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.entity.wechat.base.TblWechatSubscribe
import com.ebeijia.module.wechat.service.subscribe.WechatSubscribeService
import com.ebeijia.util.core.RespCode
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * WechatUsrQueryAction
 * @author zhicheng.xu
 *         15/8/10
 */



@Controller
@RequestMapping(value = Array("/wechat/usr"))
class WechatUsrQueryAction {
  @Autowired
  private val wechatSubscribeService: WechatSubscribeService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatUsrQueryAction])

  @RequestMapping(value = Array("query.html"), method = Array(RequestMethod.POST))
  @ResponseBody def query(mchtId:String,session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val subcribeId: String = request.getParameter("subcribeId")
    val openid: String = request.getParameter("openid")
    val nickname: String = request.getParameter("nickname")
    val groupId: String = request.getParameter("groupId")
    val pageData: String = request.getParameter("aoData")
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      //不在此用户组下面的关注者
      val mapTmp: Map[String, AnyRef] = wechatSubscribeService.findBySql(mchtId,groupId,subcribeId, openid,nickname, pageData)
      map.put("usrList", mapTmp.get("list"))
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("微信用户查询失败,请联系管理员或稍后再试")
        AjaxResp.getReturn(RespCode.WECHAT_USR_SEL_ERROR, "")
      }
    }
  }

  @RequestMapping(value = Array("queryBatch.html"), method = Array(RequestMethod.POST))
  @ResponseBody def queryByBatch(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val groupId: String = request.getParameter("groupId")
    val pageData: String = request.getParameter("aoData")
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    val s: Array[Array[String]] = Array(Array(groupId, "1", "6","1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    try {
      //不在此用户组下面的关注者
      val mapTmp: Map[String, AnyRef] = wechatSubscribeService.findByBatch(groupId, pageData)
      map.put("usrList", mapTmp.get("list"))
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      //在此分组下面的关注者
      val result: List[TblWechatSubscribe] = wechatSubscribeService.findByGroup(groupId)
      map.put("usrInList", result)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("微信用户查询失败,请联系管理员或稍后再试")
        AjaxResp.getReturn(RespCode.WECHAT_USR_SEL_ERROR, "")
      }
    }
  }
}

