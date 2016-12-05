package com.ebeijia.module.app.service.dep

import java.io.File
import java.util.{List, Map}

import com.ebeijia.entity.app.ban.TblAppBannerInf
import com.ebeijia.module.app.dao.dep.AppDeployDocumentDao
import com.ebeijia.entity.app.dep.{TblAppDeployDocumentId, TblAppDeployDocument}
import com.ebeijia.entity.system.system.TblSysPicInf
import com.ebeijia.module.system.service.picInf.SysPicInfService
import com.ebeijia.util.core.{SystemProperties, UpLoad}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

/**
  * DeployDocumentServiceImpl
  * 文章管理
  * @author xiong.wang
  */
@Service
final class AppDeployDocumentServiceImpl extends AppDeployDocumentService {

  private val logger: Logger = LoggerFactory.getLogger(classOf[AppDeployDocumentServiceImpl])
  @Autowired
  private val deployDocumentDao: AppDeployDocumentDao = null
  @Autowired
  private val picInfService: SysPicInfService = null

  @Transactional
  @Cacheable(value = Array("DeployDocumentCache"), key = "#root.method.name+#id")
  def getById(id: TblAppDeployDocumentId): TblAppDeployDocument = {
    deployDocumentDao.getById(id)
  }

  @Transactional
  @Cacheable(value = Array("DeployDocumentCache"), key = "#root.method.name+#docTitle+#docType+#state+#pageData")
  def findBySql(docTitle: String, docType: String, state: String, pageData: String): Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("from TblAppDeployDocument")
    query.append(" where 1 = 1 ")
    if (docTitle != null && !("".equals(docTitle))) {
      query.append(" AND docTitle like '%").append(docTitle).append("%'")
    }
    if (docType != null && !("".equals(docType))) {
      query.append(" AND docType = ").append(docType).append("")
    }
    if (state != null && !("".equals(state))) {
      query.append(" AND state = '").append(state).append("'")
    }
    query.append(" order by id.artId desc")
    val m: Map[String, AnyRef] = deployDocumentDao.findByPage(query.toString(), pageData)
    m
  }


  @Transactional
  @CacheEvict(value = Array("DeployDocumentCache"), allEntries = true)
  def update(data: TblAppDeployDocument, `type`: String, f: MultipartFile, ext: String) {
    val upload: UpLoad = new UpLoad
    if (f != null) {
      //上传文件本地到服务器
      val file: File = upload.getFile(f, SystemProperties.getProperties("image.dep"), `type`, ext)
      val picData: TblSysPicInf = new TblSysPicInf
      val id: Int = picInfService.maxId
      picData.setPicId(id)
      picData.setPicName(data.getDocImg.toString)
      picData.setPicType("00")
      val picUrl: String = SystemProperties.getProperties("image.dep.url") + "/" + `type` + "/" + file.getName
      picData.setPicUrl(picUrl)
      picInfService.save(picData)
      data.setDocImg(id)
    } else {
      //文章封面
      //      data.setAdminHead(1)
    }
    deployDocumentDao.update(data)
  }

  @CacheEvict(value = Array("DeployDocumentCache"), allEntries = true)
  @Transactional
  def save(data: TblAppDeployDocument, `type`: String, f: MultipartFile, ext: String) {
    val upload: UpLoad = new UpLoad
    if (f != null) {
      //上传文件本地到服务器
      val file: File = upload.getFile(f, SystemProperties.getProperties("image.dep"), `type`, ext)
      val picData: TblSysPicInf = new TblSysPicInf
      val id: Int = picInfService.maxId
      picData.setPicId(id)
      picData.setPicName(data.getDocImg.toString)
      picData.setPicType("00")
      val picUrl: String = SystemProperties.getProperties("image.dep.url") + "/" + `type` + "/" + file.getName
      //图片压缩
      //      val imgCom: ImgCompress = new ImgCompress(picUrl)
      //      imgCom.resizeFix(300, 300, picUrl)
      picData.setPicUrl(picUrl)
      picInfService.save(picData)
      data.setDocImg(id)
    } else {
      //文章封面
      //      data.setAdminHead(1)
    }
    deployDocumentDao.save(data)
  }


  @CacheEvict(value = Array("DeployDocumentCache"), allEntries = true)
  @Transactional
  def delById(id: TblAppDeployDocumentId) {
    deployDocumentDao.deleteById(id)
  }

  def selByDocFlag(docFlag: String): TblAppDeployDocument = {
    val hql: String = "from TblAppDeployDocument where docFlag = '" + docFlag + "'"
    val list: List[TblAppDeployDocument] = deployDocumentDao.find(hql)
    if (list.isEmpty || list.size() == 0) {
      return null
    } else {
      list.get(0)
    }
  }
  def maxId:Int={
    deployDocumentDao.getMaxId()+1
  }
}
