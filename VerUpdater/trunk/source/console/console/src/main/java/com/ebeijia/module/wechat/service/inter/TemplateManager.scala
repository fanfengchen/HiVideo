package com.ebeijia.module.wechat.service.inter

import net.sf.json.JSONObject
import com.ebeijia.entity.vo.wechat.button.Group
/**
 * TemplateManager分组管理器类
 * @author zhicheng.xu
 *         15/8/13
 */

trait TemplateManager {
  /**
   * 设置模板行业
   * @param id1 主行业
   * @param id2 副行业
   * @param accessToken 有效的access_token
   * @return JSONObject
   */
  def set(id1: String, id2: String, accessToken: String): JSONObject

  /**
   * 设置模板id
   * @param group 分组实例
   * @param accessToken 有效的access_token
   * @return JSONObject
   */
  def add(group: Group, accessToken: String): JSONObject

  /**
   * 发送模板消息
   * @param msg 消息内容
   * @param accessToken 有效的access_token
   * @return JSONObject
   */
  def send(msg: String, accessToken: String): JSONObject


}
