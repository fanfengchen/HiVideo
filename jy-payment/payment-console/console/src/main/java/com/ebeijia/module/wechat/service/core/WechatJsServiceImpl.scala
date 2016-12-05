package com.ebeijia.module.wechat.service.core

import com.ebeijia.module.wechat.dao.base.WechatMchtInfDao
import com.ebeijia.entity.wechat.base.TblWechatMchtInf
import com.ebeijia.module.wechat.service.inter.JsTicketManager
import com.ebeijia.util.wechat.WechatUtil
import net.sf.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import com.ebeijia.entity.vo.wechat.AccessToken
/**
 * WechatMchtInfServiceImpl
 * @author zhicheng.xu
 *         15/8/13
 */


@Service
final class WechatJsServiceImpl extends WechatJsService {

  @Autowired
  private val wechatTokenService: WechatTokenService = null
  @Autowired
  private val wechatMchtInfDao: WechatMchtInfDao = null
  @Autowired
  private val wechatMchtInfService: WechatMchtInfService = null
  @Autowired
  private val jsTicketManager: JsTicketManager = null

  @Cacheable(value = Array("wechatSignCache"), key = "#root.method.name+#url")
  def getJsTicket(url: String,mchtId:String): String = {
    var result: String = null
    val data: TblWechatMchtInf = wechatMchtInfService.queryWechatMchtInf(mchtId)
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    if (null != at) {
      val jsonObject: JSONObject = jsTicketManager.getTicket(at.getToken)
      if (null != jsonObject) {
        if (WechatUtil.getWechatCallBackStatus(jsonObject.toString) != 0) {
          result = jsonObject.getString("errcode")
        } else {
          result = jsonObject.getString("ticket")
          return result
        }
      } else {
        result = "8888888"
      }
    } else {
      result = "9999999"
    }
    result
  }
}
