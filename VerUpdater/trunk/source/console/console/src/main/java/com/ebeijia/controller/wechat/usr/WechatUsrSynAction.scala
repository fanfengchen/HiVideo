package com.ebeijia.controller.wechat.usr

import java.util.Map
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.entity.wechat.base.{TblWechatSubscribeId, TblWechatSubscribe}
import com.ebeijia.module.wechat.service.subscribe.WechatSubscribeService
import com.ebeijia.util.core.RespCode
import com.ebeijia.util.wechat.WechatError
import org.ebeijia.tools.Validate4J
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * WechatUsrSynAction
 * @author zhicheng.xu
 *         15/8/10
 */

@Controller
@RequestMapping(value = Array("/wechat/usr")) class WechatUsrSynAction {
  @Autowired
  private val wechatSubscribeService: WechatSubscribeService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatUsrSynAction])

  @RequestMapping(value = Array("syn.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信用户同步")
  @ResponseBody def syn(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val mchtId:String = request.getParameter("mchtId")
    try {
      val respMsg: String = wechatSubscribeService.synUsr(mchtId)
      if (respMsg == null) {
        AjaxResp.getReturn(RespCode.SUCCESS_CODE,"")
      } else {
        AjaxResp.getReturn(RespCode.ERROR_CODE, respMsg, "")
      }
    }
    catch {
      case e: Exception => {
        logger.info("微信用户同步失败")
        AjaxResp.getReturn(RespCode.WECHAT_USR_SYS_ERROR, "")
      }
    }
  }

  @RequestMapping(value = Array("upRemark.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信用户修改备注")
  @ResponseBody def upRemark(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val subcribe: String = request.getParameter("subcribeId")
    val remark: String = request.getParameter("remark")
    val mchtId: String = request.getParameter("mchtIdFlag")
    val s: Array[Array[String]] = Array(Array(subcribe, "1", "20","1"), Array(remark, "1", "16","1"))
    if (ActionValidate.checkArray(s) != null) {
      AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
    }
    val ts :TblWechatSubscribeId = new TblWechatSubscribeId
    ts.setMchtId(mchtId)
    ts.setSubcribeId(subcribe)
    val data: TblWechatSubscribe = wechatSubscribeService.getById(ts)
    if (data == null) {
      AjaxResp.getReturn(RespCode.WECHAT_USR_REMARK_ERROR,"")
    }
    data.setRemarks(remark)
    try {
      val respMsg: String = wechatSubscribeService.upRemark(data)
      if (respMsg == null) {
        AjaxResp.getReturn(RespCode.SUCCESS_CODE,"")
      }
      else {
        AjaxResp.getReturn(RespCode.ERROR_CODE, WechatError.checkCode(respMsg), "")
      }
    }
    catch {
      case e: Exception => {
        logger.info("微信用户修改备注失败")
        AjaxResp.getReturn(RespCode.WECHAT_USR_UPREMARK_ERROR, "")
      }
    }
  }

  @RequestMapping(value = Array("mv.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信移动用户分组")
  @ResponseBody def mv(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val subcribe: String = request.getParameter("subcribeId")
    val groupId: String = request.getParameter("groupId")
    val mchtId: String = request.getParameter("mchtIdFlag")
    val s: Array[Array[String]] = Array(Array(subcribe, "1", "20","1"), Array(groupId, "1", "6","1"))
    if (!Validate4J.checkStrArrLen(s)) {
      AjaxResp.getReturn(RespCode.PARAM_ERROR,  "")
    }
    try {
      val ts :TblWechatSubscribeId = new TblWechatSubscribeId
      ts.setMchtId(mchtId)
      ts.setSubcribeId(subcribe)
      val data: TblWechatSubscribe = wechatSubscribeService.getById(ts)
      if (data == null) {
        AjaxResp.getReturn(RespCode.WECHAT_USR_REMARK_ERROR, "")
      }
      data.setGroupId(groupId)
      val respMsg: String = wechatSubscribeService.upGroup(data)
      if (respMsg == null) {
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
      } else {
        AjaxResp.getReturn(RespCode.ERROR_CODE, WechatError.checkCode(respMsg), "")
      }
    }
    catch {
      case e: Exception => {
        logger.info("微信移动用户分组失败")
        AjaxResp.getReturn(RespCode.WECHAT_USR_MOVE_ERROR,  "")
      }
    }
  }

  @RequestMapping(value = Array("batchMv.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信移动用户分组")
  @ResponseBody def batchMv(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val openList: String = request.getParameter("openList")
    val outList: String = request.getParameter("outList")
    val groupId: String = request.getParameter("groupId")
    val mchtId: String = request.getParameter("mchtId")
    val s: Array[Array[String]] = Array(Array(openList, "1", "1500","1"), Array(groupId, "1", "6","1"))
    if (!Validate4J.checkStrArrLen(s)) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    try {
      var respMsg: String = null
      var respMsg2: String = null
      //移动分组
      if (openList != null) {
        respMsg = wechatSubscribeService.batchUpGroup(openList, groupId)
      }
      //移出分组
      if (outList != null) {
        respMsg2 = wechatSubscribeService.batchUpGroup(openList, groupId)
      }
      if (respMsg == null && respMsg2 == null) {
        AjaxResp.getReturn(RespCode.SUCCESS_CODE,"")
      } else {
        if (respMsg == null) {
          AjaxResp.getReturn(RespCode.ERROR_CODE,"",WechatError.checkCode(respMsg2))
        }
        else {
          AjaxResp.getReturn(RespCode.ERROR_CODE,"",WechatError.checkCode(respMsg))
        }
      }
    }
    catch {
      case e: Exception => {
        logger.info("微信批量移动用户分组失败")
        AjaxResp.getReturn(RespCode.WECHAT_USR_MOVE_ERROR, "")
      }
    }
  }
}

