package com.ebeijia.controller.wechat.mcht

import java.util.Map
import javax.servlet.http.HttpServletRequest

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.system.vo.RoleFuncParam
import com.ebeijia.entity.vo.wechat.AccessToken
import com.ebeijia.entity.wechat.base.{TblWechatMchtInfId, TblWechatMchtInf}
import com.ebeijia.module.wechat.service.core.{WechatTokenService, WechatMchtInfService}
import com.ebeijia.util.core.{FuncCode, RespCode}
import net.sf.json.JSONObject
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
  * 微信账户绑定
  */

@Controller
@RequestMapping(value = Array("/wechat/mcht"))
class WechatMchtInfAddAction extends BaseValidRoleFunc {
  @Autowired
  private val wechatMchtInfService: WechatMchtInfService = null
  @Autowired
  private val wechatTokenService: WechatTokenService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatMchtInfAddAction])

  @RequestMapping(value = Array("add.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "绑定微信账户")
  @ResponseBody def add(roleFuncParam: RoleFuncParam,tblWechatMchtInf:TblWechatMchtInf,request: HttpServletRequest): Map[String, AnyRef] = {
    roleFuncParam.setFuncId("200001")
    val msg: String = this.validRoleFunc(roleFuncParam)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    val twi:TblWechatMchtInfId = new TblWechatMchtInfId
    twi.setMchtId(request.getParameter("mchtIdFlag"))
    twi.setBrhNo(request.getParameter("brhNoFlag"))
    tblWechatMchtInf.setToken(request.getParameter("wxtoken"))
    if (tblWechatMchtInf == null) {
      AjaxResp.getReturn(RespCode.OBJECT_ERROR_NULL, "")
    }
    val s: Array[Array[String]] = Array(Array(twi.getBrhNo, "1", "11","1"), Array(twi.getMchtId, "1", "8","1"))
    val verifyParam: String = ActionValidate.checkArray(s)
    if (verifyParam != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",verifyParam)
    }
    try {
      tblWechatMchtInf.setId(twi)
      //验证加入商户appid和密钥是否正确
      val at: AccessToken = wechatTokenService.getAccessToken(tblWechatMchtInf.getAppid, tblWechatMchtInf.getAppsecret)
      if(at == null){
        return AjaxResp.getReturn(RespCode.ERROR_CODE, "","不合法的APPID,请登陆微信公众平台查证")
      }
      val respMsg: String = wechatMchtInfService.addWechatMchtInf(tblWechatMchtInf)
      if ("0" == respMsg) {
        return AjaxResp.getReturn(RespCode.WECHAT_ACCOUNT_REBAND_ERROE, "")
      }
      AjaxResp.getReturn(RespCode.WECHAT_ACCOUNT_BAND_SUCCESS, "")
    }
    catch {
      case e: Exception => {
        logger.info("绑定失败,请联系管理员或稍后再试:" + e)
        AjaxResp.getReturn(RespCode.WECHAT_ACCOUNT_BAND_ERROE, "")
      }
    }
  }
}

