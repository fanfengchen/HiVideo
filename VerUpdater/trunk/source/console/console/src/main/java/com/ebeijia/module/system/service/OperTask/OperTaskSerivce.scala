package com.ebeijia.module.system.service.OperTask


trait OperTaskSerivce {
  def setOper(operName: String): Unit

  def getOper(): String

  def removeOperToken(): Unit
}
