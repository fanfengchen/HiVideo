
package com.ebeijia.module.wechat.service.core

import java.io._
import java.net.{ConnectException, URL}
import java.security.{KeyStore, SecureRandom}
import java.util.{HashMap, Map}
import javax.net.ssl.{HttpsURLConnection, SSLContext, SSLSocketFactory, TrustManager}

import com.ebeijia.entity.vo.wechat.AccessToken
import com.ebeijia.util.core.Constant
import com.ebeijia.util.wechat.{CustomizedHostnameVerifier, MyX509TrustManager, WechatUtil}
import net.sf.json.{JSONException, JSONObject}
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.multipart.{FilePart, MultipartRequestEntity, Part, StringPart}
import org.apache.commons.httpclient.methods.{GetMethod, PostMethod}
import org.apache.commons.httpclient.params.HttpMethodParams
import org.apache.http.client.methods.{CloseableHttpResponse, HttpPost}
import org.apache.http.client.params.CookiePolicy
import org.apache.http.conn.ssl.{SSLConnectionSocketFactory, SSLContexts}
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.{CloseableHttpClient, DefaultHttpClient, HttpClients}
import org.apache.http.util.EntityUtils
import org.apache.http.{HttpResponse, HttpStatus}
import org.ebeijia.tools.DateTime4J
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
/**
 * WechatTokenServiceImpl
 * @author zhicheng.xu
 *         15/8/13
 */

@Service
final class WechatTokenServiceImpl extends WechatTokenService {
  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatTokenServiceImpl])

  /**
   * 发起https请求并获取结果
   * @param requestUrl 请求地址
   * @param requestMethod  请求方式（GET、POST）
   * @param outputStr  提交的数据
   * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
   */
  def httpRequest(requestUrl: String, requestMethod: String, outputStr: String): JSONObject = {
    var jsonObject: JSONObject = null
    val buffer: StringBuffer = new StringBuffer
    try {
      val tm: Array[TrustManager] = Array(new MyX509TrustManager)
      // 创建SSLContext对象，并使用我们指定的信任管理器初始化
      val sslContext: SSLContext = SSLContext.getInstance("SSL", "SunJSSE")
      sslContext.init(null, tm, new SecureRandom)
      // 从上述SSLContext对象中得到SSLSocketFactory对象
      val ssf: SSLSocketFactory = sslContext.getSocketFactory
      val url: URL = new URL(requestUrl)
      val httpUrlConn: HttpsURLConnection = url.openConnection.asInstanceOf[HttpsURLConnection]
      httpUrlConn.setSSLSocketFactory(ssf)
      httpUrlConn.setDoOutput(true)
      httpUrlConn.setDoInput(true)
      httpUrlConn.setUseCaches(false)
      httpUrlConn.setHostnameVerifier(new CustomizedHostnameVerifier)
      // 设置请求方式（GET/POST）
      httpUrlConn.setRequestMethod(requestMethod)
      System.setProperty("jsse.enableSNIExtension", "false")
      if ("GET".equalsIgnoreCase(requestMethod))
        httpUrlConn.connect()
      // 当有数据需要提交时
      if (null != outputStr) {
        val outputStream: OutputStream = httpUrlConn.getOutputStream
        // 注意编码格式，防止中文乱码
        outputStream.write(outputStr.getBytes("UTF-8"))
        outputStream.close()
      }
      // 将返回的输入流转换成字符串
      var inputStream: InputStream = httpUrlConn.getInputStream
      val inputStreamReader: InputStreamReader = new InputStreamReader(inputStream, "utf-8")
      val bufferedReader: BufferedReader = new BufferedReader(inputStreamReader)
      for (str <- bufferedReader.readLine()) {
        buffer.append(str)
      }
      bufferedReader.close()
      inputStreamReader.close()
      // 释放资源
      inputStream.close()
      inputStream = null
      httpUrlConn.disconnect()
      jsonObject = JSONObject.fromObject(buffer.toString)
    }
    catch {
      case ce: ConnectException =>
        logger.info("Weixin server connection timed out.")
      case e: Exception =>
        logger.info("https request error:{}" + e)
    }
    jsonObject
  }

  /**
   * 获取access_token
   * @param appid 凭证
   * @param appsecret 密钥
   * @return AccessToken
   */
//  @Cacheable(value = Array("wechatTokenCache"), key = "#root.method.name+#appid+#appsecret")
  def getAccessToken(appid: String, appsecret: String): AccessToken = {
    var accessToken: AccessToken = null
    val requestUrl: String = WechatUtil.ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret)
    val jsonObject: JSONObject = httpRequest(requestUrl, "GET", null)
    // 如果请求成功
    if (null != jsonObject) {
      try {
        accessToken = new AccessToken
        accessToken.setToken(jsonObject.getString("access_token"))
        accessToken.setExpiresIn(jsonObject.getInt("expires_in"))
      }
      catch {
        case e: JSONException => {
          accessToken = null
        }
      }
    }
    accessToken
  }

  /**
   * 网页获取access_token和openid
   * @param appid 凭证
   * @param appsecret 密钥
   * @param code 参数
   * @return AccessToken
   */
  @Cacheable(value = Array("wechatTokenCache"), key = "#root.method.name+#appid+#appsecret+#code")
  def getAccessTokenAndopenid(appid: String, appsecret: String, code: String): AccessToken = {
    var accessToken: AccessToken = null
    val requestUrl: String = WechatUtil.ACCESS_TOKEN_H5_URL.
      replace("APPID", appid).replace("APPSECRET", appsecret).replace("CODE", code)
    val jsonObject: JSONObject = httpRequest(requestUrl, "GET", null)
    // 如果请求成功
    if (null != jsonObject) {
      try {
        accessToken = new AccessToken
        accessToken.setToken(jsonObject.getString("access_token"))
        accessToken.setExpiresIn(jsonObject.getInt("expires_in"))
        accessToken.setRefreshToken(jsonObject.getString("refresh_token"))
        accessToken.setOpenid(jsonObject.getString("openid"))
        accessToken.setScope(jsonObject.getString("scope"))
        //        accessToken.setUnionid(jsonObject.getString("unionid"))
      }
      catch {
        case e: JSONException => {
          accessToken = null
        }
      }
    }
    accessToken
  }

  /**
   * 上传多媒体
   * @param url 地址
   * @param access_token token
   * @param file 文件
   * @return result
   */
  def upload(url: String, access_token: String, file: File, `type`: String, ext: String, flag: String): JSONObject = {
    val client: HttpClient = new HttpClient
    val post: PostMethod = new PostMethod(url)
    try {
      if (file != null && file.exists) {
        val filepart: FilePart = new FilePart("media", file, `type` + "/" + ext, "UTF-8")
        var parts: Array[Part] = null
        if (flag != null) {
          //如果为永久视频则多提交一个表单
          val tmp: String = "{\"title\":\"VIDEO_TITLE\",\"introduction\":\"INTRODUCTION\"}"
          val sp: StringPart = new StringPart("description", tmp)
          parts = Array[Part](filepart, sp)
        }
        else {
          parts = Array[Part](filepart)
        }
        val entity: MultipartRequestEntity = new MultipartRequestEntity(parts, post.getParams)
        post.setRequestEntity(entity)
        val status: Int = client.executeMethod(post)
        if (status == HttpStatus.SC_OK) {
          val responseContent: String = post.getResponseBodyAsString
          val json:JSONObject = JSONObject.fromObject(responseContent)
          if (json.get("errcode") == null) {
            return json
          }
        }
      }
    }
    catch {
      case e: Exception => e.printStackTrace
    }
    null
  }

  /**
   * 下载多媒体
   *
   * @param url 地址
   * @param method 方法
   * @param mediaId 媒体id
   * @return String
   */
  def dowload(url: String, method: String, mediaId: String): String = {
    val httpClient: HttpClient = new HttpClient
    httpClient.getParams.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8")
    var br: BufferedReader = null
    var response: HttpResponse = null
    httpClient.getParams.setCookiePolicy(CookiePolicy.IGNORE_COOKIES)
    var result: String = null
    var statusCode: Int = 0
    try {
      val getMethod: GetMethod = new GetMethod(url)
      val request: HttpPost = new HttpPost(url)
      if (method == "GET") {
        statusCode = httpClient.executeMethod(getMethod)
      }
      else {
        val se: StringEntity = new StringEntity("{\"media_id\":\"" + mediaId + "\"}")
        request.setEntity(se)
        response = new DefaultHttpClient().execute(request)
        statusCode = response.getStatusLine.getStatusCode
      }
      if (statusCode == 200) {
        var in: InputStream = null
        if (method == "GET") {
          in = getMethod.getResponseBodyAsStream
        }
        else {
          in = response.getEntity.getContent
        }
        br = new BufferedReader(new InputStreamReader(in, "UTF-8"))
        val line: String = br.readLine
        if (line != null && line.indexOf("errcode") == -1) {
          result = url
        }
        else {
          line
        }
        if (br != null) br.close
      }
    }
    catch {
      case ex: Exception => ex.printStackTrace
    } finally {
      if (br != null) {
        try {
          br.close
        }
        catch {
          case e: IOException => e.printStackTrace
        }
      }
    }
    result
  }

  /**
   * 带证书的ssl请求
   *
   * @param url  地址
   * @param xml  参数
   * @param file  证书文件
   * @return
   */
  def customSSL(url: String, xml: String,file :File) {
    val doXMLtoMap: Map[AnyRef,AnyRef] = new HashMap[AnyRef,AnyRef];
    val keyStore: KeyStore = KeyStore.getInstance("PKCS12");
    val instream: FileInputStream = new FileInputStream(file);
    try {
      keyStore.load(instream, Constant.MCHT_ID.toCharArray());
    } finally {
      instream.close()
    }
    val sslcontext: SSLContext = SSLContexts.custom()
      .loadKeyMaterial(keyStore, Constant.MCHT_ID.toCharArray())
      .build()
    // Allow TLSv1 protocol only
    val sslsf: SSLConnectionSocketFactory = new SSLConnectionSocketFactory(
      sslcontext, Array("TLSv1"), null,
      SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    val httpclient: CloseableHttpClient = HttpClients.custom()
      .setSSLSocketFactory(sslsf)
      .build();
    try {
      val httpPost: HttpPost = new HttpPost(url);
      httpPost.setEntity(new StringEntity(xml, "UTF-8"));

//      println(httpPost.getURI());

//      println("executing request" + httpPost.getRequestLine());

      val response: CloseableHttpResponse = httpclient.execute(httpPost);

      val jsonStr: String = EntityUtils.toString(response.getEntity(), "UTF-8");
      response.close();

    } finally {

      httpclient.close();
    }
  }


//  /**
//   * 向指定 URL 发送POST方法的请求
//   *
//   * @param url
//     * 发送请求的 URL
//   * @param param
//     * 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
//   * @return 所代表远程资源的响应结果
//   */
  //  def sendPost(url: String, param: String): String = {
  //    var out: PrintWriter = null
  //    var in: BufferedReader = null
  //    var result: String = ""
  //    try {
  //      val realUrl: URL = new URL(url)
  //      val conn: URLConnection = realUrl.openConnection
  //      conn.setRequestProperty("accept", "*/*")
  //      conn.setRequestProperty("connection", "Keep-Alive")
  //      conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)")
  //      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
  //      conn.setDoOutput(true)
  //      conn.setDoInput(true)
  //      out = new PrintWriter(conn.getOutputStream)
  //      out.print(param)
  //      out.flush
  //      in = new BufferedReader(new InputStreamReader(conn.getInputStream))
  //      var line: String = null
  //      while ((({
  //        line = in.readLine;
  //        line
  //      })) != null) {
  //        result += line
  //      }
  //    }
  //    catch {
  //      case e: Exception => {
  //        println("发送 POST 请求出现异常！" + e)
  //        e.printStackTrace
  //      }
  //    } finally {
  //      try {
  //        if (out != null) {
  //          out.close
  //        }
  //        if (in != null) {
  //          in.close
  //        }
  //      }
  //      catch {
  //        case ex: IOException => {
  //          ex.printStackTrace
  //        }
  //      }
  //    }
  //     result
  //  }

  /**
   * 向指定 URL 发送POST方法的请求
   *
   * @param url
     * 发送请求的 URL
   * @param msg
     * 请求参数
   * @return 所代表远程资源的响应结果
   */
  def sendPost(url: String, mobile: String, msg: String): String = {
    var response: String = null
    val postMethod: PostMethod = new PostMethod(url)
    postMethod.getParams().setContentCharset("utf-8")
    postMethod.addParameter("account", Constant.SMS_ACCOUT)
    postMethod.addParameter("password", Constant.SMS_PWD)
    postMethod.addParameter("destmobile", mobile)
    postMethod.addParameter("sendDateTime", DateTime4J.getCurrentDateTime)
    postMethod.addParameter("msgText", msg)
    val httpClient: HttpClient = new HttpClient()
    var statusCode: Int = 0;
    try {
      statusCode = httpClient.executeMethod(postMethod)
      if (statusCode == HttpStatus.SC_OK) {
        response = postMethod.getResponseBodyAsString()
      } else {
        logger.info("发送statusCode错误:statusCode=" + statusCode)
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      postMethod.releaseConnection()
    }
    response
  }



  /**
   * 获取剩余短信次数
   * */
  def getPostSmsCount(url: String): String={
    var response: String = null
    val postMethod: PostMethod = new PostMethod(url)
    postMethod.getParams().setContentCharset("utf-8")
    postMethod.addParameter("account", Constant.SMS_ACCOUT)
    postMethod.addParameter("password", Constant.SMS_PWD)

    val httpClient: HttpClient = new HttpClient()
    var statusCode: Int = 0;
    try {
      //执行方法
      statusCode = httpClient.executeMethod(postMethod)
        //返回信息
        response = postMethod.getResponseBodyAsString()

    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      postMethod.releaseConnection()
    }
    response
  }

}
