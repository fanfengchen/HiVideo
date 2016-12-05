package com.ebeijia.controller.wechat.mcht

import java.util.{HashMap, Map}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.system.vo.RoleFuncParam
import com.ebeijia.module.wechat.service.core.WechatMchtInfService
import com.ebeijia.util.core.{FuncCode, RespCode}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
  * 微信账户查询
 */


@Controller
@RequestMapping(value = Array("/wechat/mcht"))
class WechatMchtInfQueryAction extends BaseValidRoleFunc{
  @Autowired
  private val wechatMchtInfService: WechatMchtInfService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatMchtInfQueryAction])

  @RequestMapping(value = Array("query.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "微信账户查询")
  def query(roleFuncParam: RoleFuncParam,mchtId:String,aoData:String): Map[String, AnyRef] = {
    roleFuncParam.setFuncId("200002")
    val msg: String = this.validRoleFunc(roleFuncParam)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: Map[String, AnyRef] = wechatMchtInfService.findBySql(mchtId, aoData)
      map.put("mchtList", mapTmp.get("list"))
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("微信账号查询失败,请联系管理员或稍后再试")
        AjaxResp.getReturn(RespCode.WECHAT_ACCOUNT_QRY_ERROE,"")
      }
    }
  }
}
