package com.ebeijia.module.wechat.service.media

import java.io.File
import java.util.{LinkedHashMap, LinkedList, List, Map}

import com.ebeijia.module.wechat.dao.base.{ArticlesDao, WechatFodderDao, WechatMchtInfDao}
import com.ebeijia.entity.system.system.TblSysPicInf
import com.ebeijia.entity.wechat.base.{TblWechatFodder, TblWechatFodderId, TblWechatMchtInf}
import com.ebeijia.module.system.service.picInf.SysPicInfService
import com.ebeijia.module.wechat.service.core.WechatTokenService
import com.ebeijia.module.wechat.service.inter.MediaManager
import com.ebeijia.util.core.{SystemProperties, UpLoad}
import com.ebeijia.util.wechat.WechatUtil
import com.ebeijia.entity.vo.wechat.AccessToken
import net.sf.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

/**
  * WechatFodderServiceImpl
  * @author zhicheng.xu
  *         15/8/14
  */


@Service
final class WechatFodderServiceImpl extends WechatFodderService {
  @Autowired
  private val wechatFodderDao: WechatFodderDao = null
  @Autowired
  private val wechatMchtInfDao: WechatMchtInfDao = null
  @Autowired
  private val wechatTokenService: WechatTokenService = null
  @Autowired
  private val mediaManager: MediaManager = null
  @Autowired
  private val picInfService: SysPicInfService = null
  @Autowired
  private val articlesService:ArticlesService = null

  @Transactional
//  @Cacheable(value = Array("wechatFooderCache"), key = "#root.method.name+#mchtId+#`type`+#mediaType+#aoData")
  def findBySql(mchtId: String, `type`: String, mediaType: String, aoData: String): Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("from TblWechatFodder")
//    query.append(" where type <> 'news' ")
    query.append(" where 1=1 ")
//    if (mchtId != null && !("" == mchtId)) {
      query.append(" AND id.mchtId = '").append(mchtId).append("'")
//    }
    if (`type` != null && !("" == `type`)) {
      query.append(" AND type ='").append(`type`).append("'")
    }
    if (mediaType != null && !("" == mediaType)) {
      query.append(" AND mediaType like '%").append(mediaType).append("%'")
    }
    query.append(" ORDER BY createTime desc")
    val m: Map[String, AnyRef] = wechatFodderDao.findByPage(query.toString, aoData)
    m
  }

  @Transactional
  @Cacheable(value = Array("wechatFooderCache"))
  def findBySqltoNews(mchtId:String,name: String, aoData: String): Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("from TblWechatFodder")
    query.append(" where type ='news' ")
    query.append(" AND id.mchtId = '").append(mchtId).append("'")
    if (name != null && !("" == name)) {
      query.append(" AND name like '%").append(name).append("%'")
    }
    val m: Map[String, AnyRef] = wechatFodderDao.findByPage(query.toString(), aoData)
    m
  }

  @Transactional
  @CacheEvict(value = Array("wechatFooderCache"), allEntries = true)
  def upFodder(id: TblWechatFodderId, name: String, dsc: String) {
    val data: TblWechatFodder = wechatFodderDao.getById(id)
    data.setName(name)
    data.setDsc(dsc)
    wechatFodderDao.update(data)
  }

  @Transactional
  @CacheEvict(value = Array("wechatFooderCache"), allEntries = true)
  def delFodder(fid:TblWechatFodderId): String = {
    val sb: StringBuilder = new StringBuilder
    sb.append("from TblWechatMchtInf where id.mchtId = '").append(fid.getMchtId).append("'")
    val tblWechatMchtInf: TblWechatMchtInf = wechatMchtInfDao.find(sb.toString).get(0)
    val at: AccessToken = wechatTokenService.getAccessToken(tblWechatMchtInf.getAppid, tblWechatMchtInf.getAppsecret)
    var jsonObject: JSONObject = null
    var result: String = null
    if (null != at) {
      jsonObject = mediaManager.mediaDel(at.getToken, fid.getMedia)
      if (null != jsonObject) {
        if (WechatUtil.getWechatCallBackStatus(jsonObject.toString) != 0) {
          result = jsonObject.getString("errcode")
        } else {
          val tblWechatFodder:TblWechatFodder = wechatFodderDao.getById(fid)
          if(tblWechatFodder.getMediaType.equals("2")){
            var str: String = ""
            val b: Array[String] = tblWechatFodder.getArtId.split(",")
            for (c <- b) {
              str = str + "'" + c + "',"
            }
            str = str.substring(0, str.length - 1)
            articlesService.delArt(str)
          }
          wechatFodderDao.deleteById(fid)
        }
      } else {
        result = "8888888"
      }
    } else {
      result = "9999999"
    }
    result
  }

  @Transactional
  @Cacheable(value = Array("wechatFooderCache"), key = "#root.method.name+#id")
  def getById(id: TblWechatFodderId): TblWechatFodder = {
    wechatFodderDao.getById(id)
  }

  @Transactional
  @Cacheable(value = Array("wechatFooderCache"))
  def mediaList(mchtId: String, msgType: String): Map[String, AnyRef] = {
    val map: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
    val hql: StringBuilder = new StringBuilder
    hql.append("FROM TblWechatFodder WHERE id.mchtId='").append(mchtId).append("'")
    hql.append(" and type ='").append(msgType).append("' and mediaType ='1'")
    hql.append("ORDER BY createTime desc")
    val dataList: List[TblWechatFodder] = wechatFodderDao.find(hql.toString)
    val list: List[AnyRef] = new LinkedList[AnyRef]
    import scala.collection.JavaConversions._
    for (data <- dataList) {
      val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
      hashMap.put("key", data.getId.getMedia + "-" + data.getName)
      hashMap.put("value", data.getId.getMedia)
      hashMap.put("url", data.getUrl)
      list.add(hashMap)
    }
    map.put("info", list)
    map
  }

  @Transactional
  @CacheEvict(value = Array("wechatFooderCache"), allEntries = true)
  def save(data: TblWechatFodder) {
    wechatFodderDao.save(data)
  }

  @Transactional
  @CacheEvict(value = Array("wechatFooderCache"), allEntries = true)
  def saveFodder(data: TblWechatFodder, `type`: String, f: MultipartFile, ext: String) {
    val upload: UpLoad = new UpLoad
    if (f != null) {
      //上传文件本地到服务器
      val file: File = upload.getFile(f, SystemProperties.getProperties("image.fodder"), `type`, ext)
      val picData: TblSysPicInf = new TblSysPicInf
      val id: Int = picInfService.maxId
      picData.setPicId(id)
      picData.setPicName(data.getName.toString)
      picData.setPicType("01")
      val picUrl: String = SystemProperties.getProperties("image.fodder.url") + "/" + `type` + "/" + file.getName
      //图片压缩
      //      val imgCom: ImgCompress = new ImgCompress(picUrl)
      //      imgCom.resizeFix(300, 300, picUrl)
      picData.setPicUrl(picUrl)
      picInfService.save(picData)
    } else {
      //文章封面
      //      data.setAdminHead(1)
    }
    wechatFodderDao.save(data)
  }

  @Transactional
  @CacheEvict(value = Array("wechatFooderCache"), allEntries = true)
  def getByPic(picId: String): TblWechatFodder = {
    val hql: String = "from TblWechatFodder where picId = '" + picId + "'"
    val list: List[TblWechatFodder] = wechatFodderDao.find(hql)
    if (list.isEmpty || list.size() == 0) {
      return null
    }
    list.get(0)
  }
}
