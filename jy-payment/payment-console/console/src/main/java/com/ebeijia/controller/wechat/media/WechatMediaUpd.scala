package com.ebeijia.controller.wechat.media

import java.util.Map
import javax.servlet.http.HttpServletRequest

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.wechat.base.{TblWechatFodderId, TblWechatMchtInfId}
import com.ebeijia.module.wechat.service.media.{WechatFodderService, WechatMediaService}
import com.ebeijia.util.core.RespCode
import com.ebeijia.util.wechat.WechatError
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * 微信多媒体素材修改
 */


@Controller
@RequestMapping(value = Array("/wechat/media"))
class WechatMediaUpd extends BaseValidRoleFunc{
  @Autowired
  private val wechatFodderService: WechatFodderService = null
  @Autowired
  private val wechatMediaService: WechatMediaService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatMediaUpd])

  @RequestMapping(value = Array("upd.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信多媒体修改")
  @ResponseBody def upd(id: TblWechatFodderId, request: HttpServletRequest): Map[String, AnyRef] = {
    id.setMedia(request.getParameter("media"))
    id.setMchtId(request.getParameter("mchtIdFlag"))
    val name: String = request.getParameter("name")
    val dsc: String = request.getParameter("dsc")
    val s: Array[Array[String]] = Array(Array(id.getMedia, "1", "100","1"), Array(name, "1", "30","1"), Array(dsc, "0", "64","1"))
    if (ActionValidate.checkArray(s) != null) {
      AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
    }
    if (wechatFodderService.getById(id) == null) {
      AjaxResp.getReturn(RespCode.WECHAT_MEDIA_UPDERROR_FILENOTEXIST, "")
    }
    try {
      wechatFodderService.upFodder(id, name, dsc)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        logger.info("微信多媒体修改失败")
        AjaxResp.getReturn(RespCode.WECHAT_MEDIA_UPDERROR, "")
      }
    }
  }

  @RequestMapping(value = Array("del.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信永久素材删除")
  @ResponseBody def del(fid:TblWechatFodderId, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId,"200042")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE,"")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    fid.setMedia(request.getParameter("media"))
    fid.setMchtId(request.getParameter("mchtIdFlag"))
    var respMsg: String = null
    val s: Array[Array[String]] = Array(Array(fid.getMedia, "1", "100","1"), Array(fid.getMchtId, "1", "15","1"))
    if (ActionValidate.checkArray(s) != null) {
      AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
    }
    if (wechatFodderService.getById(fid) == null) {
      AjaxResp.getReturn(RespCode.WECHAT_MEDIA_UPDERROR_FILENOTEXIST,"")
    }
    try {
      respMsg = wechatFodderService.delFodder(fid)
      if (respMsg == null) {
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
      }
      else {
        AjaxResp.getReturn(RespCode.ERROR_CODE, WechatError.checkCode(respMsg),"")
      }
    }
    catch {
      case e: Exception => {
        logger.info("微信永久素材删除失败")
        AjaxResp.getReturn(RespCode.WECHAT_MEDIA_DELERROR, "")
      }
    }
  }

  @RequestMapping(value = Array("updateNews.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信修改图文素材")
  @ResponseBody def updateGt(tid:TblWechatMchtInfId,id: TblWechatFodderId, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId,"200042")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE,"")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
//    val mchtId: String = request.getParameter("mchtId")
    id.setMedia(request.getParameter("media"))
    id.setMchtId(request.getParameter("mchtIdFlag"))
    val articles: String = request.getParameter("articles")
    val media: String = request.getParameter("media")
    val name: String = request.getParameter("name")
    val dsc: String = request.getParameter("dsc")
    val picId: String = request.getParameter("picId")
    val picUrl: String = request.getParameter("picUrl")
    val s: Array[Array[String]] = Array(Array(id.getMchtId, "1", "15","1"), Array(articles, "1", null,"1"), Array(name, "1", "30","1"), Array(dsc, "0", "64","1"), Array(media, "1", "100","1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    if (wechatFodderService.getById(id) == null) {
      AjaxResp.getReturn(RespCode.WECHAT_MEDIA_FODDERNOTEXITS, "")
    }
    var respMsg: String = null
    try {
      val map: Map[String, AnyRef] = wechatMediaService.upGtMedia(id,picId,picUrl,id.getMchtId, name, dsc, articles)
      if (map.get("error") == null) {
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
      } else {
        AjaxResp.getReturn(RespCode.ERROR_CODE, "",WechatError.checkCode(map.get("error").toString))
      }
    }
    catch {
      case e: Exception => {
        logger.info("微信修改图文失败")
        AjaxResp.getReturn(RespCode.WECHAT_MEDIA_UPFODDERDERROR,"")
      }
    }
  }
}

