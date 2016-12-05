package com.ebeijia.module.wechat.service.core

/**
 * WechatTokenService
 * @author zhicheng.xu
 *         15/8/13
 */

import java.io.File
import com.ebeijia.entity.vo.wechat.AccessToken
import net.sf.json.JSONObject

trait WechatTokenService {
  /**
   * 获取access_token
   *
   * @param appid 凭证
   * @param appsecret 密钥
   * @return
   */
  def getAccessToken(appid: String, appsecret: String): AccessToken

  /**
   * 网页获取access_token和openid
   *
   * @param appid 凭证
   * @param appsecret 密钥
   * @param code 参数
   * @return
   */
  def getAccessTokenAndopenid(appid: String, appsecret: String,code:String): AccessToken

  /**
   * 带证书的ssl请求
   *
   * @param url  地址
   * @param xml  参数
   * @param file 证书文件
   * @return
   */
  def customSSL(url :String ,xml :String ,file:File)
  /**
   * 发起https请求并获取结果
   *
   * @param requestUrl
	 * 请求地址
   * @param requestMethod
	 * 请求方式（GET、POST）
   * @param outputStr
	 * 提交的数据
   * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
   */
  def httpRequest(requestUrl: String, requestMethod: String, outputStr: String): JSONObject

  /**
   * 上传多媒体
   * @param url
   * @param access_token
   * @param file
   * @param ext
   * @return result
   */
  def upload(url: String, access_token: String, file: File, `type`: String, ext: String, flag: String): JSONObject

  /**
   * 下载临时多媒体
   * @param url
   * @param method
   * @param mediaId
   * @return JSONObject
   */
  def dowload(url: String, method: String, mediaId: String): String

  /**
   * 向指定 URL 发送POST方法的请求
   *
   * @param url
     * 发送请求的 URL
   * @param param
     * 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
   * @return 所代表远程资源的响应结果
   */
  def sendPost(url: String, mobile:String,param: String): String

  /**
   * 获取剩余短信次数
   * */
  def getPostSmsCount(url: String): String
}
