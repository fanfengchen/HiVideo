package com.ebeijia.controller.wechat.msg

/**
 * WechatRespMsgUpdAction
  *
  * @author zhicheng.xu
 *         15/8/10
 */

import java.util.{HashMap, Map}
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.wechat.base.{TblWechatMenu, TblWechatRespMsgId, TblWechatRespMsg}
import com.ebeijia.module.wechat.service.menu.WechatMenuService
import com.ebeijia.module.wechat.service.msg.WechatRespMsgService
import com.ebeijia.util.core.RespCode
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

@Controller
@RequestMapping(value = Array("/wechat/respMsg"))
class WechatRespMsgUpdAction extends BaseValidRoleFunc{
  @Autowired
  private val wechatRespMsgService: WechatRespMsgService = null
  @Autowired
  private val wechatMenuService:WechatMenuService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatRespMsgUpdAction])

  @RequestMapping(value = Array("upd.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信回复消息修改")
  @ResponseBody def upd(tsm: TblWechatRespMsg, request: HttpServletRequest): Map[String, AnyRef] = {
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
    if (tsm == null) {
      AjaxResp.getReturn(RespCode.OBJECT_ERROR_NULL,"")
    }
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    val tsmid:TblWechatRespMsgId = new TblWechatRespMsgId
    tsmid.setMchtId(request.getParameter("mchtIdFlag"))
    tsmid.setRespMsgId(request.getParameter("respMsgId"))
    tsm.setId(tsmid)
    if (tsm == null) {
      AjaxResp.getReturn(RespCode.OBJECT_ERROR_NULL, "")
    }
    val s: Array[Array[String]] = Array(Array(tsmid.getMchtId, "15", "15","1"),
      Array(tsm.getKeywords, "0", "32","1"),
      Array(tsm.getKeywordType, "1", "1","1"),
      Array(tsm.getRespType, "4", "6","1"),
      Array(tsm.getMsgType, "4", "25","1"),
      Array(tsm.getContent, "0", "1000","1"),
      Array(tsm.getPicUrl, "0", "256","1"),
      Array(tsm.getTitle, "0", "64","1"),
      Array(tsm.getDescription, "0", "1024","1"),
      Array(tsm.getUrl, "0", "256","1"),
      Array(tsm.getMediaId, "0", "256","1"),
      Array(tsm.getMusicUrl, "0", "256","1"),
      Array(tsm.getHqMusicUrl, "0", "256","1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    if (wechatRespMsgService.getById(tsmid) == null) {
      AjaxResp.getReturn(RespCode.WECHAT_MSG_REP_NOTEXITS_ERROR, "")
    }
    val articlesJson: String = request.getParameter("articlesJson")
    val msgType: String = request.getParameter("msgType")
    if ("news" == msgType) {
      if (("" == articlesJson) || articlesJson == null) {
        AjaxResp.getReturn(RespCode.WECHAT_MSG_FODDER_ERROR, "")
      }
    }
    try {
      //验证关键字限制
      val total: Int = wechatRespMsgService.check(tsmid.getRespMsgId, tsmid.getMchtId, tsm.getKeywords)
      if (total > 0) {
        AjaxResp.getReturn(RespCode.WECHAT_MSG_KEY_EXIST, "")
      }
      wechatRespMsgService.update(tsm, articlesJson)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("微信回复消息修改失败")
        AjaxResp.getReturn(RespCode.WECHAT_MSG_REP_SEL_ERROR, "")
      }
    }
  }

  @RequestMapping(value = Array("del.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信回复消息删除")
  @ResponseBody def del(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val tsmid:TblWechatRespMsgId = new TblWechatRespMsgId
    tsmid.setMchtId(request.getParameter("mchtId"))
    tsmid.setRespMsgId(request.getParameter("respMsgId"))
    try {
      val tblWechatRespMsg: TblWechatRespMsg = wechatRespMsgService.getById(tsmid)
      if (tblWechatRespMsg == null) {
        return AjaxResp.getReturn(RespCode.ERROR_CODE, "","消息不存在")
      }else {
        val tblWechatMenu:TblWechatMenu = wechatMenuService.getMenuInf(tblWechatRespMsg.getId.getMchtId,tblWechatRespMsg.getKeywords)
        if(tblWechatMenu != null){
          return AjaxResp.getReturn(RespCode.ERROR_CODE, "","该关键字已配置菜单不予删除")
        }
        wechatRespMsgService.delete(tsmid)
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
      }
    }
    catch {
      case e: Exception => {
        logger.info("删除回复消息失败")
        AjaxResp.getReturn(RespCode.WECHAT_MSG_REP_DEL_ERROR, "")
      }
    }
  }
}

