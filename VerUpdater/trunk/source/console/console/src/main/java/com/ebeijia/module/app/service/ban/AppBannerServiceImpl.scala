package com.ebeijia.module.app.service.ban

import java.io.File
import java.util.{List, Map}

import com.ebeijia.entity.app.soft.TblAppSoftVerInf
import com.ebeijia.module.app.dao.ban.AppBannerInfDao
import com.ebeijia.entity.app.ban.{TblAppBannerInfId, TblAppBannerInf}
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
  * BannerServiceImpl
  * 广告管理
  * @author xiong.wang
  */
@Service
final class AppBannerServiceImpl extends AppBannerService {
  private val logger: Logger = LoggerFactory.getLogger(classOf[AppBannerServiceImpl])
  @Autowired
  private val bannerInfDao: AppBannerInfDao = null
  @Autowired
  private val picInfService: SysPicInfService = null

  @Transactional
  @Cacheable(value = Array("BannerCache"), key = "#root.method.name+#id")
  def getById(id: TblAppBannerInfId): TblAppBannerInf = {
    bannerInfDao.getById(id)
  }

  @Transactional
  @Cacheable(value = Array("BannerCache"), key = "#root.method.name+#name+#state+#pageData")
  def findBySql(name: String, state: String, pageData: String): Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("from TblAppBannerInf")
    query.append(" where 1 = 1 ")
    if (name != null && !("".equals(name))) {
      query.append(" AND name like '%").append(name).append("%'")
    }
    if (state != null && !("".equals(state))) {
      query.append(" AND state = '").append(state).append("'")
    }
    query.append("  order by  id.banId desc")
    val m: Map[String, AnyRef] = bannerInfDao.findByPage(query.toString(), pageData)
    m
  }


  @Transactional
  @CacheEvict(value = Array("BannerCache"), allEntries = true)
  def update(data: TblAppBannerInf, `type`: String, f: MultipartFile, ext: String) {
    val upload: UpLoad = new UpLoad
    if (f != null) {
      //上传文件本地到服务器
      val file: File = upload.getFile(f, SystemProperties.getProperties("image.ban"), `type`, ext)
      val picData: TblSysPicInf = new TblSysPicInf
      val id: Int = picInfService.maxId
      picData.setPicId(id)
      picData.setPicName(data.getName.toString)
      picData.setPicType("01")
      val picUrl: String = SystemProperties.getProperties("image.ban.url") + "/" + `type` + "/" + file.getName
      picData.setPicUrl(picUrl)
      picInfService.save(picData)
      data.setFileId(id)
    } else {
      //广告封面
      //      data.setAdminHead(1)
    }
    bannerInfDao.update(data)
  }

  @CacheEvict(value = Array("BannerCache"), allEntries = true)
  @Transactional
  def save(data: TblAppBannerInf, `type`: String, f: MultipartFile, ext: String) {
    val upload: UpLoad = new UpLoad
    if (f != null) {
      //上传文件本地到服务器
      val file: File = upload.getFile(f, SystemProperties.getProperties("image.ban"), `type`, ext)
      val picData: TblSysPicInf = new TblSysPicInf
      val id: Int = picInfService.maxId
      picData.setPicId(id)
      picData.setPicName(data.getName.toString)
      picData.setPicType("01")
      val picUrl: String = SystemProperties.getProperties("image.ban.url") + "/" + `type` + "/" + file.getName
      //图片压缩
      //      val imgCom: ImgCompress = new ImgCompress(picUrl)
      //      imgCom.resizeFix(300, 300, picUrl)
      picData.setPicUrl(picUrl)
      data.setFileId(id)
      picInfService.save(picData)
    } else {
      //广告封面
      //      data.setAdminHead(1)
    }
    bannerInfDao.save(data)
  }

  @CacheEvict(value = Array("BannerCache"), allEntries = true)
  @Transactional
  def delById(id: TblAppBannerInfId) {
    bannerInfDao.deleteById(id)
  }

  def selSeq(Seq: String): Boolean = {
    val hql: String = "from TblAppBannerInf where seq = '" + Seq + "'"
    val list: List[TblAppBannerInf] = bannerInfDao.find(hql)
    if (list.isEmpty || list.size == 0) {
      return true
    }
    false
  }
  /**
   * 最大id+1
   * */
  def maxId:Int={
    bannerInfDao.getMaxId()+1
  }

}
