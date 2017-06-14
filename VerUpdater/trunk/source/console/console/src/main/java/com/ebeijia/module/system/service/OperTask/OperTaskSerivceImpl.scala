package com.ebeijia.module.system.service.OperTask

import com.ebeijia.entity.system.system.TblSysOperatorInf
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service

@Service
final class OperTaskSerivceImpl extends OperTaskSerivce {

  private var operName:String = null

  @Cacheable(value = Array("operToken"), key = "#root.method.name+#operName")
  def setOper(operName: String): Unit = {
    this.operName =  operName
  }

  def getOper(): String = {
    return this.operName
  }

  @CacheEvict(value = Array("operToken"), allEntries = true)
  def removeOperToken(): Unit = {

  }
}
