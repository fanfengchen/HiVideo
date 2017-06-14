package com.ebeijia.controller.wechat.msg

import java.util
import java.util.{List, HashMap, Map}
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.wechat.base.{TblWechatFodder, TblWechatFodderId, TblWechatRespMsg}
import com.ebeijia.module.wechat.service.media.WechatFodderService
import com.ebeijia.module.wechat.service.msg.WechatRespMsgService
import com.ebeijia.util.core.RespCode
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * 自动回复消息查询
 */

@Controller
@RequestMapping(value = Array("/wechat/respMsg"))
class WechatRespMsgQueryAction extends BaseValidRoleFunc{
  @Autowired
  private val wechatRespMsgService: WechatRespMsgService = null
  @Autowired
  private val wechatFodderService:WechatFodderService = null

  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatRespMsgQueryAction])

  @RequestMapping(value = Array("query.html"), method = Array(RequestMethod.POST))
  @ResponseBody def query(keywords: String, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId,"200021")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE,"")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    val subcribe:String = request.getParameter("subcribe")
    val mchtId: String = request.getParameter("mchtIdFlag")
    val pageData: String = request.getParameter("aoData")
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: Map[String, AnyRef] = wechatRespMsgService.findBySql(keywords,mchtId, pageData,subcribe)
      map.put("respMsgList", mapTmp.get("list"))
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("微信自动回复查询失败,请联系管理员或稍后再试")
        AjaxResp.getReturn(RespCode.WECHAT_MSG_AUTORESSEL_ERROR, "")
      }
    }
  }

  @RequestMapping(value = Array("querySubcribe.html"), method = Array(RequestMethod.POST))
  @ResponseBody def query(msgType: String, keywords: String, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId,"200021")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE,"")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    val mchtId: String = request.getParameter("mchtId")
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: List[TblWechatRespMsg] = wechatRespMsgService.findBySubType(mchtId)
      if(mapTmp != null){
        val tblWechatRespMsg:TblWechatRespMsg = mapTmp.get(0)
        if(tblWechatRespMsg.getMsgType.equals("news")){
          val tblWechatFodderId:TblWechatFodderId = new TblWechatFodderId
          tblWechatFodderId.setMchtId(tblWechatRespMsg.getId.getMchtId)
          tblWechatFodderId.setMedia(tblWechatRespMsg.getMediaId)
          val list:List[TblWechatFodder] = new util.ArrayList[TblWechatFodder]()
          val tblWechatFodder:TblWechatFodder = wechatFodderService.getById(tblWechatFodderId)
          list.add(tblWechatFodder)
          map.put("fodderList", list)
        }
      }
      map.put("subcribeList", mapTmp)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("微信自动回复查询失败,请联系管理员或稍后再试")
        AjaxResp.getReturn(RespCode.WECHAT_MSG_AUTORESSEL_ERROR, "")
      }
    }
  }
}
