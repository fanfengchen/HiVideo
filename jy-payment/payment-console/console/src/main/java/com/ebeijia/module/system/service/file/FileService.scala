package com.ebeijia.module.system.service.file

/**
  * FileService
  * @author zhicheng.xu
  *         15/8/17
  */
trait FileService {
  @throws(classOf[Exception])
  def generateHTML(fileInfo: String)
  @throws(classOf[Exception])
  def generateHTMLRetPath(fileInfo: String):String
}
