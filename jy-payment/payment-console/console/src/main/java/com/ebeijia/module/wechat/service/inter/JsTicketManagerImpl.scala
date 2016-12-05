package com.ebeijia.module.wechat.service.inter

import com.ebeijia.module.wechat.service.core.WechatTokenService
import com.ebeijia.util.wechat.WechatUtil
import net.sf.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * JsTicketManager获取微信jsticket管理器类
 * @author zhicheng.xu
 */

@Service("JsTicketManager")
class JsTicketManagerImpl extends JsTicketManager {
  @Autowired
  private val wechatTokenService: WechatTokenService = null

  /**
   * 获取ticket
   * @param accessToken 有效的access_token
   * @return JSONObject
   */
  def getTicket(accessToken: String): JSONObject = {
    val url: String = WechatUtil.JSTICKET_GET.replace("ACCESS_TOKEN", accessToken)
    val jsonObject: JSONObject = wechatTokenService.httpRequest(url, "POST", null)
    jsonObject
  }
}

