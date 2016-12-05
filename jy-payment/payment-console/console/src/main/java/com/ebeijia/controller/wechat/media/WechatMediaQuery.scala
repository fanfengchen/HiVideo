package com.ebeijia.controller.wechat.media

import java.util.{HashMap, Map}
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.wechat.base.{TblWechatArticlesInf, TblWechatMchtInfId}
import com.ebeijia.module.wechat.service.media.{ArticlesService, WechatFodderService, WechatMediaService}
import com.ebeijia.util.core.{PatternUtil, RespCode}
import com.ebeijia.util.wechat.WechatError
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * 微信多媒体素材查询
 */


@Controller
@RequestMapping(value = Array("/wechat/media"))
class WechatMediaQuery extends BaseValidRoleFunc{
  @Autowired
  private val wechatFodderService: WechatFodderService = null
  @Autowired
  private val wechatMediaService: WechatMediaService = null
  @Autowired
  private val articlesService: ArticlesService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatMediaQuery])

  @RequestMapping(value = Array("query.html"), method = Array(RequestMethod.POST))
  @ResponseBody def query(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val mchtId: String = request.getParameter("mchtId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId,"200042")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE,"")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    val `type`: String = request.getParameter("type")
    val mediaType: String = request.getParameter("mediaType")
    val pageData: String = request.getParameter("aoData")
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: Map[String, AnyRef] = wechatFodderService.findBySql(mchtId, `type`, mediaType, pageData)
      map.put("mediaList", mapTmp.get("list"))
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)

    }
    catch {
      case e: Exception => {
        logger.info("微信多媒体查询失败")
        AjaxResp.getReturn(RespCode.WECHAT_MEDIA_UPMEDIAERROR, "")

      }
    }
  }

  @RequestMapping(value = Array("mediaAll.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "查询素材总数")
  @ResponseBody def mediaAll(tblWechatMchtInfId :TblWechatMchtInfId, request: HttpServletRequest): Map[_, _] = {
//    val mchtId: String = request.getParameter("mchtId")
    var map: Map[_,_] = new HashMap
    val s: Array[Array[String]] = Array(Array(tblWechatMchtInfId.getMchtId, "15", "15","1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    try {
      map = wechatMediaService.mediaAllCount(tblWechatMchtInfId)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)

    }
    catch {
      case e: Exception => {
        logger.info("查询素材总数失败")
        AjaxResp.getReturn(RespCode.WECHAT_MEDIA_FODDERCOUNTERROR, "")
      }
    }
  }

  @RequestMapping(value = Array("newsGet.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信图文查询")
  @ResponseBody def newsGet(tblWechatMchtInfId :TblWechatMchtInfId,media: String, request: HttpServletRequest): Map[String, AnyRef] = {
//    val media: String = request.getParameter("media")
//    val mchtId: String = request.getParameter("mchtId")
    val s: Array[Array[String]] = Array(Array(tblWechatMchtInfId.getMchtId, "15", "15","1"), Array(media, "1", "100","1"))
    if (ActionValidate.checkArray(s) != null) {
      AjaxResp.getReturn(RespCode.PARAM_ERROR,"")

    }
    val respMsg: String = wechatMediaService.newsGet(tblWechatMchtInfId,media)
    if (respMsg != null) {
      if (!PatternUtil.NUMBER_REGEX.matcher(respMsg).matches) {
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, respMsg)
      }
      else {
        AjaxResp.getReturn(RespCode.ERROR_CODE, WechatError.checkCode(respMsg),"")
      }
    }
    else {
      AjaxResp.getReturn(RespCode.ERROR_CODE, WechatError.checkCode(respMsg),"")
    }
  }

  @RequestMapping(value = Array("newsQuery.html"), method = Array(RequestMethod.POST))
  @ResponseBody def newsQuery(mchtId:String,session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val name: String = request.getParameter("name")
    val pageData: String = request.getParameter("aoData")
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: Map[String, AnyRef] = wechatFodderService.findBySqltoNews(mchtId,name, pageData)
      map.put("newsList", mapTmp.get("list"))
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("微信图文查询失败,请联系管理员或稍后再试")
        AjaxResp.getReturn(RespCode.WECHAT_MEDIA_SELFODDERCOUNTERROR, "")
      }
    }
  }

  @RequestMapping(value = Array("querydef.html"), method = Array(RequestMethod.POST))
  @ResponseBody def querydef(artId:String, request: HttpServletRequest): Map[String, AnyRef] = {
    try {
      if(artId.equals("") || artId == null){
        return AjaxResp.getReturn(RespCode.ERROR_CODE, "","该素材已被删除!!!")
      }
      val list:java.util.List[TblWechatArticlesInf] = articlesService.querydef(artId)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, list)
    }
    catch {
      case e: Exception => {
        logger.info("微信图文查询失败,请联系管理员或稍后再试")
        AjaxResp.getReturn(RespCode.WECHAT_MEDIA_SELFODDERCOUNTERROR, "")
      }
    }
  }
}

