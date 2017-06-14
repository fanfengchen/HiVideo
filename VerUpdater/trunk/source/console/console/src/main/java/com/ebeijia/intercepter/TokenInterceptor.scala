package com.ebeijia.intercepter

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import com.ebeijia.module.system.service.systemToken.SysTokenSerivce
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter


/**
  * token验证
  * TokenInterceptor
  * @author xiong.wang
  *         2016/6/29
  */
class TokenInterceptor extends HandlerInterceptorAdapter {

  private val logger: Logger = LoggerFactory.getLogger(classOf[TokenInterceptor])

  var lowUrls: Array[String] = null

  def setLowUrls(lowUrls: Array[String]) {
    this.lowUrls = lowUrls
  }

  @Autowired
  val systeToken: SysTokenSerivce = null;

  override def preHandle(request: HttpServletRequest,
                         response: HttpServletResponse,
                         handler: Object): Boolean = {
    var flag:Boolean = false
    val requestUrl: String = request.getRequestURI.replace(request.getContextPath, "")
    if (null != lowUrls && lowUrls.length >= 1) for (url <- lowUrls) {
      if (requestUrl.contains(url)) {
        flag = true
      }
    }

    val token: String = request.getParameter("token")
    val newToken: String = systeToken.setToken(token)
    if (token != null || newToken.equals(token)) {
      flag = true
    }
    flag
  }

  override def postHandle(httpServletRequest: HttpServletRequest,
                          httpServletResponse: HttpServletResponse,
                          handler: Object,
                          modelAndView: ModelAndView): Unit = {
    logger.info("go into postHandle")
  }

  override def afterCompletion(httpServletRequest: HttpServletRequest,
                               httpServletResponse: HttpServletResponse,
                               handler: Object, e: Exception): Unit = {
    logger.info("go into afterCompletion")
  }

}
