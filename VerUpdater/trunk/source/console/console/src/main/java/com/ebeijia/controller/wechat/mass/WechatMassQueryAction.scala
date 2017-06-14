package com.ebeijia.controller.wechat.mass

import java.util.{Map, List, HashMap}
import javax.servlet.http.HttpServletRequest

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.wechat.base.TblWechatMass
import com.ebeijia.module.wechat.service.mass.WechatMassService
import com.ebeijia.util.core.RespCode
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * 微信群发消息查询
 */

@Controller
@RequestMapping(value = Array("/wechat/mass"))
class WechatMassQueryAction extends BaseValidRoleFunc{

  @Autowired
  private val wechatMassService: WechatMassService = null

  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatMassQueryAction])

  @RequestMapping(value = Array("query.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "微信群发消息查询")
  def query(beginDate: String,endDate: String, msgType: String,aoData: String,request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId,"200064")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE,"")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: Map[String, AnyRef] = wechatMassService.findBySql(beginDate,endDate,msgType, aoData)
      map.put("massList", mapTmp.get("list"))
      val mapMedia:Map[String, AnyRef] = wechatMassService.queryMedia(mapTmp.get("list").asInstanceOf[List[TblWechatMass]])
      map.put("mediaList",mapMedia)
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("微信群发查询失败,请联系管理员或稍后再试")
        AjaxResp.getReturn(RespCode.WECHAT_ERROR_MASS_QUERY, "")
      }
    }
  }
}

