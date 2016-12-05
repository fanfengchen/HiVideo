package com.ebeijia.module.wechat.service.template

import com.ebeijia.module.wechat.dao.base.WechatMchtInfDao
import com.ebeijia.entity.wechat.base.TblWechatMchtInf
import com.ebeijia.module.wechat.service.core.{WechatMchtInfService, WechatTokenService}
import com.ebeijia.module.wechat.service.inter.TemplateManager
import com.ebeijia.util.wechat.WechatUtil
import net.sf.json.JSONObject
import com.ebeijia.entity.vo.wechat.AccessToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * WechatSubGroupServiceImpl
 * @author zhicheng.xu
 *         15/8/13
 *         关注者组别
 */


@Service
final class WechatTemplateServiceImpl extends WechatTemplateService {

  @Autowired
  private val wechatTokenService: WechatTokenService = null
  @Autowired
  private val templateManager: TemplateManager = null
  @Autowired
  private val wechatMchtInfDao: WechatMchtInfDao = null
  @Autowired
  private val wechatMchtInfService: WechatMchtInfService = null

  @Transactional
  def send(msg: String,mchtId:String): String = {
    var result: String = null
    val data: TblWechatMchtInf = wechatMchtInfService.queryWechatMchtInf("10001")
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    if (null != at) {
      val jsonObject: JSONObject = templateManager.send(msg, at.getToken)
      println("模板消息微信返回 = " + jsonObject)
      if (null != jsonObject) {
        if (WechatUtil.getWechatCallBackStatus(jsonObject.toString) != 0) {
          result = jsonObject.getString("errcode")
        } else {
          val msg: String = jsonObject.getString("msgid")
          println("msg = " + msg)
        }
      } else {
        result = "8888888"
      }
    } else {
      result = "9999999"
    }
    result
  }

  //重置token的空方法
  @CacheEvict(value = Array("wechatTokenCache"), allEntries = true)
  def restToken(): Unit ={

  }
}