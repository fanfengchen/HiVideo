package com.ebeijia.module.app.service.soft

import java.io.File
import java.util.{List, Map}

import com.ebeijia.module.app.dao.soft.AppSoftVerInfDao
import com.ebeijia.entity.app.soft.{TblAppSoftVerInfId, TblAppSoftVerInf}
import com.ebeijia.module.system.service.picInf.SysPicInfService
import com.ebeijia.util.core.{SystemProperties, UpLoad}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

/**
  * SoftVerInfServiceImpl
  * 软件管理
  * @author xiong.wang
  */
@Service
final class SoftVerInfServiceImpl extends SoftVerInfService {
  private val logger: Logger = LoggerFactory.getLogger(classOf[SoftVerInfServiceImpl])
  @Autowired
  private val softVerInfDao: AppSoftVerInfDao = null
  @Autowired
  private val picInfService: SysPicInfService = null

  @Transactional
 // @Cacheable(value = Array("SoftVerCache"), key = "#root.method.name+#id")
  def getById(id: TblAppSoftVerInfId): TblAppSoftVerInf = {
    softVerInfDao.getById(id)
  }

  @Transactional
  @Cacheable(value = Array("SoftVerCache"), key = "#root.method.name+#verNo+#uTime+#pageData")
  def findBySql(verNo: String, uTime: String, pageData: String): Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("from TblAppSoftVerInf")
    query.append(" where 1 = 1 ")
    if (verNo != null && !("".equals(verNo))) {
      query.append(" AND verNo = '").append(verNo).append("'")
    }

    if (uTime != null && !("".equals(uTime))) {
      query.append(" AND isu_Time LIKE '%").append(uTime).append("%'")
    }
    query.append("  order by  id.verId desc")
    val m: Map[String, AnyRef] = softVerInfDao.findByPage(query.toString(), pageData)
    m
  }


  @Transactional
  @CacheEvict(value = Array("SoftVerCache"), allEntries = true)
  def update(data: TblAppSoftVerInf) {
    softVerInfDao.update(data)
  }

  @CacheEvict(value = Array("SoftVerCache"), allEntries = true)
  @Transactional
  def save(data: TblAppSoftVerInf, `type`: String, f: MultipartFile, ext: String) {
    val upload: UpLoad = new UpLoad
    if (f != null) {
      //上传文件本地到服务器
      val file: File = upload.getFile(f, SystemProperties.getProperties("soft.page"), `type`, ext)
      data.setLocalPath(SystemProperties.getProperties("soft.page") + "/" + data.getVerNo + "." + `type`)
      data.setUrlPath(SystemProperties.getProperties("soft.page.url") + "/" + data.getVerNo + "." + `type`)
    } else {
      data.setLocalPath(SystemProperties.getProperties("soft.page") + "/" + data.getVerNo + "." + `type`)
      data.setUrlPath(SystemProperties.getProperties("soft.page.url") + "/" + data.getVerNo + "." + `type`)
    }
    softVerInfDao.save(data)
  }

  @CacheEvict(value = Array("SoftVerCache"), allEntries = true)
  @Transactional
  def delById(id: TblAppSoftVerInfId) {
    softVerInfDao.deleteById(id)
  }

  def selSoft(verNo: String,_type:String): Boolean = {
    val hql: String = "from TblAppSoftVerInf where verNo = '" + verNo + "' AND TYPE ='"+ _type + "'"
    val list: List[TblAppSoftVerInf] = softVerInfDao.find(hql)
    if (list.isEmpty || list.size == 0) {
      return true
    }
    false
  }

  def maxId:Int={
    softVerInfDao.getMaxId()+1
  }
}
