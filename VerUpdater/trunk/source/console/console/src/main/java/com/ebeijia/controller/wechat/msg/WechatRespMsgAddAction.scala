package com.ebeijia.controller.wechat.msg

import java.util.Map
import javax.servlet.http.HttpServletRequest

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.wechat.base.{TblWechatRespMsgId, TblWechatRespMsg}
import com.ebeijia.module.wechat.service.msg.WechatRespMsgService
import com.ebeijia.util.core.RespCode
import org.ebeijia.tools.DateTime4J
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 *消息新增
 */

@Controller
@RequestMapping(value = Array("/wechat/respMsg"))
class WechatRespMsgAddAction extends BaseValidRoleFunc{
  @Autowired
  private val wechatRespMsgService: WechatRespMsgService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatRespMsgAddAction])

  @RequestMapping(value = Array("add.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信回复消息新增")
  @ResponseBody def add(tsm:TblWechatRespMsg, request: HttpServletRequest): Map[String, AnyRef] = {
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
    val tsmid:TblWechatRespMsgId = new TblWechatRespMsgId
    tsmid.setMchtId(request.getParameter("mchtIdFlag"))
    tsmid.setRespMsgId(wechatRespMsgService.getMaxRespId(tsmid.getMchtId))
    tsm.setId(tsmid)
    val s: Array[Array[String]] = Array(Array(tsmid.getMchtId, "1", "15","1"),
      Array(tsm.getKeywords, "0", "32","1"),
//      Array(tsm.getKeywordType, "1", "1","1"),
//      Array(tsm.getRespType, "4", "6","1"),
//      Array(tsm.getMsgType, "4", "25","1"),
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
    val articlesJson: String = request.getParameter("articles")
    val mchtId: String = request.getParameter("mchtIdFlag")
    val keywords: String = request.getParameter("keywords")
    val msgType: String = request.getParameter("msgType")
    val articleId: String = request.getParameter("artId")
    val url: String = request.getParameter("url")
    if ("news" == msgType) {
      if (("" == articlesJson) || articlesJson == null) {
        AjaxResp.getReturn(RespCode.WECHAT_MSG_FODDER_ERROR, "")
      }
    }
    tsm.setMediaId(request.getParameter("media"))
    val subscribe: String = request.getParameter("subscribe")
    if(subscribe != null){
      if(!subscribe.equals("text")){
        tsm.setMediaId(request.getParameter("media"))
      }
      tsm.setCreateTime(DateTime4J.getCurrentDateTime)
      tsm.setKeywordType("2")
      tsm.setRespType("subscribe")
      tsm.setMsgType(subscribe)
    }
    tsm.setArticleIds(articleId)
    tsm.setUrl(url)
    try {
      //验证关键字限制
      val total: Int = wechatRespMsgService.check(null, mchtId, keywords)
      if (total > 0) {
        return AjaxResp.getReturn(RespCode.WECHAT_MSG_KEY_EXIST, "")
      }
      wechatRespMsgService.save(tsm, articlesJson)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE,"")
    }
    catch {
      case e: Exception => {
        logger.info("微信回复消息新增失败")
        AjaxResp.getReturn(RespCode.WECHAT_MSG_ADD_ERROR, "")
      }
    }
  }
}

