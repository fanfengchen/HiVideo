package com.ebeijia.module.system.service.systemToken

import com.ebeijia.util.common.EncryptMD5Util
import org.ebeijia.tools.UUID4J
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

/**
 * Token
 * @author zhicheng.xu
 *         16/5/24
 */
@Service
final class SysTokenServiceImpl extends SysTokenSerivce{

  /**
   * 设置token
   * @param id
   * @return
   */
  @Cacheable(value = Array("sysToken"), key = "#root.method.name+#id")
  def setToken( id :String) :String={
    val uuid: String = UUID4J.getUUID + id
    val token: String = EncryptMD5Util.encrypt(uuid)
    token
  }

//  def getToken(id: String, token: String): Boolean = {
//    val newToken: String = this.setToken(id)
//    var flag: Boolean = false
//    if (newToken.equals(token)) {
//      flag = true
//    }
//    flag
//  }
}
