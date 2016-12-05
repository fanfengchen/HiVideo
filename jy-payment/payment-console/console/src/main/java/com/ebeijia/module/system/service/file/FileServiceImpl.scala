package com.ebeijia.module.system.service.file

import java.io.{File, FileOutputStream, OutputStreamWriter}

import com.ebeijia.util.core.SystemProperties
import org.ebeijia.tools.DateTime4J
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.stereotype.Service

/**
  * FileServiceImpl
  *
  * @author zhicheng.xu
  *         15/8/17
  */

@Service("fileService")
class FileServiceImpl extends FileService {
  private val logger: Logger = LoggerFactory.getLogger(classOf[FileServiceImpl])

  @throws(classOf[Exception])
  def generateHTML(fileInfo: String) {
    val path: String = SystemProperties.getProperties("projectUrl") + SystemProperties.getProperties("htmlUrl")
    val f: File = new File(path + ".html")
    var opsw: OutputStreamWriter = null
    if (f.exists) {
      f.delete
    }
    if (f.createNewFile) {
      logger.info("文件创建成功！")
    }
    else {
      logger.info("文件创建失败！")
    }
    opsw = new OutputStreamWriter(new FileOutputStream(path + ".html"), "UTF-8")
    opsw.write(fileInfo)
    opsw.flush
    opsw.close
    logger.info("HTML写入完成！")
  }

  @throws(classOf[Exception])
  def generateHTMLRetPath(fileInfo: String):String ={
    val htmlName:String = DateTime4J.getCurrentDateTime
    val path: String = SystemProperties.getProperties("projectUrl") + SystemProperties.getProperties("htmlUrl")+htmlName
    val f: File = new File(path + ".html")
    var opsw: OutputStreamWriter = null
    if (f.exists) {
      f.delete
    }
    if (f.createNewFile) {
      logger.info("文件创建成功！")
    }
    else {
      logger.info("文件创建失败！")
    }
    opsw = new OutputStreamWriter(new FileOutputStream(path + ".html"), "UTF-8")
    opsw.write(fileInfo)
    opsw.flush
    opsw.close
    logger.info("HTML写入完成！")
    return SystemProperties.getProperties("htmlUrl.path")+htmlName+".html"
  }
}
