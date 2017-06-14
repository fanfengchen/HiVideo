package com.ebeijia.util.common

import javax.servlet.http.HttpServletRequest

/**
 * IpUtil
 * @author zhicheng.xu
 *         15/11/3
 */
object IpUtil {

   def getRemoteHost(request: HttpServletRequest): String = {
    var ip: String = request.getHeader("x-forwarded-for")
    if (ip == null || ip.length == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP")
    }
    if (ip == null || ip.length == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP")
    }
    if (ip == null || ip.length == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr
    }
    return if ((ip == "0:0:0:0:0:0:0:1")) "127.0.0.1" else ip
  }
}
