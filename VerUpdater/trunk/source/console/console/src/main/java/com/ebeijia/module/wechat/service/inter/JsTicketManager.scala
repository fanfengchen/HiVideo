package com.ebeijia.module.wechat.service.inter

import net.sf.json.JSONObject

/**
 * JsTicketManager获取微信jsticket管理器类
 * @author zhicheng.xu
 *         15/11/10
 */

trait JsTicketManager {
  /**
   * 获取ticket
   * @param accessToken 有效的access_token
   * @return JSONObject
   */
  def getTicket(accessToken: String): JSONObject

}

