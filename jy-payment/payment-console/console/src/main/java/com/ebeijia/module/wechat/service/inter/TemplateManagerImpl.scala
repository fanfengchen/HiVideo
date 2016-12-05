package com.ebeijia.module.wechat.service.inter

import com.ebeijia.entity.vo.wechat.button.Group
import com.ebeijia.module.wechat.service.core.WechatTokenService
import com.ebeijia.util.wechat.WechatUtil
import net.sf.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


/**
 * TemplateManagerImpl分组管理器类
 * @author zhicheng.xu
 *         15/8/13
 */


@Service("TemplateManager")
class TemplateManagerImpl extends TemplateManager {
  @Autowired
  private val wechatTokenService: WechatTokenService = null

  /**
   * 创建分组
   * @param accessToken 有效的access_token
   * @return JSONObject
   */
  def set(id1: String, id2: String, accessToken: String): JSONObject = {
    val url: String = WechatUtil.TEMPLATE_SET.replace("ACCESS_TOKEN", accessToken)
    val jsonId: String = "{\"industry_id1\":\"" + id1 + "\",\"industry_id\":\"" + id2 + "\"}"
    wechatTokenService.httpRequest(url, "POST", jsonId)
  }

  /**
   * 修改分组
   * @param group 分组实例
   * @param accessToken 有效的access_token
   * @return JSONObject
   */
  def add(group: Group, accessToken: String): JSONObject = {
    val url: String = WechatUtil.TEMPLATE_ADD.replace("ACCESS_TOKEN", accessToken)
    val jsonGroup: String = "{\"group\":" + JSONObject.fromObject(group).toString + "}"
    wechatTokenService.httpRequest(url, "POST", jsonGroup)
  }


  /**
   * 发送模板消息
   * @param msg 内容
   * @param accessToken 有效的access_token
   * @return JSONObject
   */
  def send(msg: String, accessToken: String): JSONObject = {
    val url: String = WechatUtil.TEMPLATE_SEND.replace("ACCESS_TOKEN", accessToken)
    //amt以分计算
//    val amtTmp = (amt.toDouble/100).toString
//    model.append("{\"touser\":\"").append(openid).append("\",\"template_id\":").append("\"Jj_LVcCvggIhEuEJBTFwlAMI4sPPYp_U1JhN0yXmyqc\"")
//      .append(",\"url\":\"http://weixin.qq.com/download\",").append("\"data\":{\"first\":{\"value\":\"壹倍加咖啡收到一笔付款，信息如下：\",\"color\":\"#17377\"},\"keyword1\":{\"value\":\"")
//      .append(DateTime4J.getCurrentDateZN(date)).append("\",\"color\":\"#17377\"},\"keyword2\":{\"value\":\"壹倍加咖啡消费\",\"color\":\"#17377\"},\"keyword3\":{\"value\":\"")
//      .append(amtTmp).append("\",\"color\":\"#17377\"},\"keyword4\":{\"value\":\"壹倍加咖啡\",\"color\":\"#17377\"},\"keyword5\":{\"value\":\"")
//      .append(seqNo).append("\",\"color\":\"#17377\"},\"remark\":{\"value\":\"欢迎再度光临\",\"color\":\"#17377\"}}}")
    wechatTokenService.httpRequest(url, "POST", msg)
  }
}
