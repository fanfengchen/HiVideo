package com.ebeijia.controller.servlet.wechat

import java.io.IOException
import java.io.PrintWriter
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import com.ebeijia.module.wechat.service.core.CoreService
import com.ebeijia.util.wechat.SignUtil
/**
 * CoreServlet
 * 核心请求处理类
 * @author zhicheng.xu
 *         15/8/11
 */

@Controller
class CoreServlet {
  @Autowired
  private val coreService: CoreService = null

  /**
   * 确认请求来自微信服务器
   */
  @RequestMapping(value = Array("/coreServlet/{mchtId}"), method = Array(RequestMethod.GET))
  @throws(classOf[ServletException])
  @throws(classOf[IOException])
  def doGet(@PathVariable mchtId: String, request: HttpServletRequest, response: HttpServletResponse) {
    // 微信加密签名
    val signature: String = request.getParameter("signature")
    // 时间戳
    val timestamp: String = request.getParameter("timestamp")
    // 随机数
    val nonce: String = request.getParameter("nonce")
    // 随机字符串
    val echostr: String = request.getParameter("echostr")
    var out: PrintWriter = response.getWriter
    // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
    if (SignUtil.checkSignature(signature, timestamp, nonce)) {
      out.print(echostr)
    }
    out.close
    out = null
  }

  /**
   * 处理微信服务器发来的消息
   */
  @RequestMapping(value = Array("/coreServlet/{mchtId}"), method = Array(RequestMethod.POST))
  @throws(classOf[ServletException])
  @throws(classOf[IOException])
  def doPost(@PathVariable mchtId: String, request: HttpServletRequest, response: HttpServletResponse) {
    // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
    request.setCharacterEncoding("UTF-8")
    response.setCharacterEncoding("UTF-8")
    // 调用核心业务类接收消息、处理消息
    println("调用核心业务类接收消息、处理消息==================")
    val respMessage: String = coreService.processRequest(request, mchtId)
    // 响应消息
    val out: PrintWriter = response.getWriter
    out.print(respMessage)
    out.close
  }
}
