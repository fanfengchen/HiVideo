package com.ebeijia.module.wechat.service.media

import java.io.File
import java.util.Map

import com.ebeijia.entity.system.system.TblSysPicInf
import com.ebeijia.entity.vo.wechat.AccessToken
import com.ebeijia.entity.wechat.base._
import com.ebeijia.module.system.service.file.FileService
import com.ebeijia.module.system.service.picInf.SysPicInfService
import com.ebeijia.module.wechat.dao.base.{ArticlesDao, WechatFodderDao}
import com.ebeijia.module.wechat.service.core.{WechatMchtInfService, WechatTokenService}
import com.ebeijia.module.wechat.service.inter.MediaManager
import com.ebeijia.util.core.{SystemProperties, UpLoad}
import com.ebeijia.util.wechat.WechatUtil
import com.google.gson.{JsonArray, JsonObject, JsonParser}
import net.sf.json.JSONObject
import org.ebeijia.tools.DateTime4J
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

import scala.collection.immutable.HashMap

/**
  * WechatMediaServiceImpl
  *
  * @author zhicheng.xu
  *         15/8/14
  */


@Service("wechatMediaService")
final class WechatMediaServiceImpl extends WechatMediaService {
  @Autowired
  private val wechatMchtInfService: WechatMchtInfService = null
  @Autowired
  private val wechatTokenService: WechatTokenService = null
  @Autowired
  private val mediaManager: MediaManager = null
  @Autowired
  private val wechatFodderDao: WechatFodderDao = null
  @Autowired
  private val picInfService: SysPicInfService = null
  @Autowired
  private val articlesService: ArticlesService = null
  @Autowired
  private val fileService: FileService = null
  @Autowired
  private val articlesDao: ArticlesDao = null

  @Transactional
  @CacheEvict(value = Array("wechatFooderCache"), allEntries = true)
  def upLoadMedia(twm: TblWechatMchtInfId, `type`: String, f: MultipartFile, mediaType: String, ext: String, name: String, dsc: String): String = {
    //上传文件本地到服务器
    //    val file: File = new File("F:\\ConsoleMasterPIC\\head\\jpg\\a.jpg")
    val upload: UpLoad = new UpLoad
    val file: File = upload.getFile(f, SystemProperties.getProperties("image.massimg"),SystemProperties.getProperties("image.path1"), ext)
    val data: TblWechatMchtInf = wechatMchtInfService.getById(twm)
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    var jsonObject: JSONObject = null
    var result: String = null
    if (null != at) {
      jsonObject = mediaManager.upLoadMedia(at.getToken, `type`, file, ext)
      if (null != jsonObject) {
        if (jsonObject.toString.indexOf("errcode") != -1) {
          result = jsonObject.getString("errcode")
        } else {
          //保存图片
          val picData: TblSysPicInf = new TblSysPicInf
          val id: Int = picInfService.maxId
          picData.setPicId(id)
          picData.setPicName(f.getName)
          picData.setPicType("02")
          val picUrl: String = SystemProperties.getProperties("image.massimg") + SystemProperties.getProperties("image.path1")+ file.getName
          picData.setPicUrl(picUrl)
          picInfService.save(picData)
          // 保存meida
          val fodder: TblWechatFodder = new TblWechatFodder
          val fodderId: TblWechatFodderId = new TblWechatFodderId
          fodderId.setMchtId(twm.getMchtId)
          fodderId.setMedia(jsonObject.getString("media_id"))
          fodder.setCreateTime(DateTime4J.getCurrentDateTime)
          fodder.setId(fodderId)
          fodder.setMediaType(mediaType)
          val url: String = SystemProperties.getProperties("image.massimg.url") + file.getName
          fodder.setUrl(url)
          fodder.setType(`type`)
          fodder.setName(name)
          fodder.setDsc(dsc)
          fodder.setPicId(id.toString)
          wechatFodderDao.save(fodder)
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
  def dowLoadMedia(twm: TblWechatMchtInfId, mediaId: String, mediaType: String): String = {
    val data: TblWechatMchtInf = wechatMchtInfService.getById(twm)
    var result: String = null
    var jsonObject: String = null
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    if (null != at) {
      if ("0" == mediaType) {
        jsonObject = mediaManager.dowLoadTmpMedia(at.getToken, mediaId)
      } else {
        jsonObject = mediaManager.dowLoadMedia(at.getToken, mediaId)
      }
      if (null != jsonObject) {
        result = jsonObject.toString
      } else {
        result = "8888888"
      }
    } else {
      result = "9999999"
    }
    result
  }

  @Transactional
  @CacheEvict(value = Array("wechatFooderCache"), allEntries = true)
  def upGtMedia(id: TblWechatFodderId,picId:String,picUrl:String,mchtId: String, name: String, dsc: String, articles: String): Map[String,AnyRef] = {
    if(id != null){
      wechatFodderDao.deleteById(id)
    }
    val map:java.util.Map[String,AnyRef] = new java.util.HashMap[String,AnyRef]
    //过去图文提正文（数组）
    val contents:Array[String] = WechatUtil.forContents(articles)
    //提取正文上传服务器返回path
    val paths:Array[String] = new Array[String](contents.length)
    for (i <- 0 until contents.length) {
      val path: String = fileService.generateHTMLRetPath(contents(i))
      paths(i) = path
    }
    //替换图文体中所有正文path,返回最终图文
    val finalArticles:String = WechatUtil.forArticles(articles,paths)
    //获取token
    val data: TblWechatMchtInf = wechatMchtInfService.queryWechatMchtInf(mchtId)
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    var jsonObject: JSONObject = null
    var result: String = null
    if (null != at) {
      //上传图文
      jsonObject = mediaManager.upGtMedia(at.getToken, finalArticles)
      if (null != jsonObject) {
        if (jsonObject.toString.indexOf("errcode") != -1) {
          result = jsonObject.getString("errcode")
          map.put("error",result)
        } else {
          //保存meida
          val tmp: String = jsonObject.get("media_id").toString
          val fodder: TblWechatFodder = new TblWechatFodder
          val fodderid: TblWechatFodderId = new TblWechatFodderId
          fodderid.setMchtId(mchtId)
          fodderid.setMedia(tmp)
          fodder.setId(fodderid)
          fodder.setCreateTime(DateTime4J.getCurrentDateTime)
          fodder.setMediaType("2")
          fodder.setType("news")
          fodder.setName(name)
          fodder.setDsc(" ")
          fodder.setUrl((picUrl.split(","))(0))

          val taii: TblWechatArticlesInfId = new TblWechatArticlesInfId
          taii.setMchtId(mchtId)
          //将多图文提转换成图文实体taii.setId(articlesDao.getMaxSeq())
          val artList:java.util.List[TblWechatArticlesInf] = WechatUtil.forBeanList(finalArticles)
          //图文id
          var artId:String = ""
          //填充图文实体
          for (i <- 0 until artList.size()) {
            val twa:TblWechatArticlesInf = artList.get(i)
            twa.setId(taii)
            if(articlesDao.getMaxSeq().equals("1")){
              taii.setId("1")
              twa.setId(taii)
              val pid:Array[String] = picId.split(",")
              val purl:Array[String] = picUrl.split(",")
              twa.setPicUrl(purl(i))
              twa.setLocalPicId(pid(i))
              twa.setUrl(paths(i))
              twa.setUrl(paths(i))
              artId = artId+taii.getId+","
            }else{
              taii.setId((Integer.parseInt(articlesDao.getMaxSeq())+i+1).toString)
              twa.setId(taii)
              val pid:Array[String] = picId.split(",")
              val purl:Array[String] = picUrl.split(",")
              twa.setPicUrl(purl(i))
              twa.setLocalPicId(pid(i))
              twa.setUrl(paths(i))
              artId = artId+taii.getId+","
            }
            articlesService.Save(twa)
          }
          fodder.setArtId(artId.substring(0,artId.lastIndexOf(",")))
          wechatFodderDao.save(fodder)
          map.put("fodderInf",JSONObject.fromObject(fodder))
        }
      } else {
        result = "8888888"
        map.put("error",result)
      }
    } else {
      result = "9999999"
      map.put("error",result)
    }
    map
  }

  //	@Transactional
  //	@CacheEvict(value="wechatFooderCache",allEntries=true)
  //	public String upGtMedia(String mchtId,String name ,String dsc ,String articles) {
  //		// 调用接口获取access_token
  //		TblWechatMchtInf data =wechatMchtInfService.getById(mchtId);
  //		AccessToken at = wechatTokenService.getAccessToken(data.getAppid(), data.getAppsecret());
  //		String result = "0";
  //		if (null != at) {
  //			result = mediaManager.upGtMedia(at.getToken(), articles);
  //		}
  //		JsonObject resultJson = null;
  //		JSONObject resultJ;
  //		if (null != at) {
  //			//微信分页从0开始
  //			resultJ = mediaManager.newsGet(at.getToken(),"0","20");
  //			JsonParser jsonparer = new JsonParser();
  //			JsonObject json = jsonparer.parse(result.toString()).getAsJsonObject();
  //			JsonArray array =json.get("item").getAsJsonArray();
  //			for(int i =0;i<array.size();i++){
  //				JsonObject jsonElement =array.get(i).getAsJsonObject();
  //				String respMedia = jsonElement.get("media_id").getAsString();
  //				if(result.equals(respMedia)){
  //					resultJson =jsonElement.get("content").getAsJsonObject();
  //					System.out.println("==="+resultJson.toString());
  //				}
  //			}
  //		}
  //		//保存meida
  //		TblWechatFodder fodder = new TblWechatFodder();
  //		fodder.setCreateTime(DateTime4J.getCurrentDateTime());
  //		fodder.setMedia(result);
  //		fodder.setMediaType("1");
  //		fodder.setMchtId(mchtId);
  //		fodder.setUrl(" ");
  //		fodder.setType("news");
  //		fodder.setName(name);
  //		fodder.setDsc(dsc);
  //		wechatFodderDao.save(fodder);
  //		return result;
  //	}
  @Transactional
  @CacheEvict(value = Array("wechatFooderCache"), allEntries = true)
  def updateGtMedia(picId:String,picUrl:String,tid: TblWechatMchtInfId, id: TblWechatFodderId, name: String, dsc: String, media: String, articles: String): String = {
    //过去图文提正文（数组）
    val contents:Array[String] = WechatUtil.forContents(articles)
    //提取正文上传服务器返回path
    val paths:Array[String] = new Array[String](contents.length)
    for (i <- 0 until contents.length) {
      val path: String = "www.baidu.com"//fileService.generateHTMLRetPath(contents(i))
      paths(i) = path
    }
    //替换图文体中所有正文path,返回最终图文
    val finalArticles:String = WechatUtil.forArticle(articles,paths)
    val data: TblWechatMchtInf = wechatMchtInfService.getById(tid)
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    var jsonObject: JSONObject = null
    var result: String = null
    if (null != at) {
      jsonObject = mediaManager.updateMedia(at.getToken, media, finalArticles)
      if (null != jsonObject) {
        if (WechatUtil.getWechatCallBackStatus(jsonObject.toString) != 0) {
          result = jsonObject.getString("errcode")
        } else {
          //          val fodder: TblWechatFodder = wechatFodderDao.getById(media)
          //          fodder.setName(name)
          //          fodder.setDsc(dsc)
          //          wechatFodderDao.update(fodder)
          //删除系统已有图文重新构建保存
          val flist:TblWechatFodder = wechatFodderDao.getById(id)
          val hql:String = "FROM TblWechatArticlesInf WHERE id.id in (?)"
          articlesDao.find(hql,flist.getArtId)
          //保存meida
          //          val tmp: String = jsonObject.get("media_id").toString
          val fodder: TblWechatFodder = new TblWechatFodder
          val fodderid: TblWechatFodderId = new TblWechatFodderId
          fodderid.setMchtId(tid.getMchtId)
          fodderid.setMedia(media)
          fodder.setId(fodderid)
          fodder.setCreateTime(DateTime4J.getCurrentDateTime)
          fodder.setMediaType("2")
          fodder.setType("news")
          fodder.setName(name)
          fodder.setDsc(" ")
          fodder.setUrl((picUrl.split(","))(0))

          val taii: TblWechatArticlesInfId = new TblWechatArticlesInfId
          taii.setMchtId(tid.getMchtId)
          //将多图文提转换成图文实体taii.setId(articlesDao.getMaxSeq())
          val artList:java.util.List[TblWechatArticlesInf] = WechatUtil.forBeanList(finalArticles)
          //图文id
          var artId:String = ""
          //填充图文实体
          for (i <- 0 until artList.size()) {
            val twa:TblWechatArticlesInf = artList.get(i)
            twa.setId(taii)
            if(articlesDao.getMaxSeq().equals("1")){
              taii.setId("1")
              twa.setId(taii)
              val pid:Array[String] = picId.split(",")
              val purl:Array[String] = picUrl.split(",")
              twa.setPicUrl(purl(i))
              twa.setLocalPicId(pid(i))
              twa.setUrl(paths(i))
              twa.setUrl(paths(i))
              artId = artId+taii.getId+","
            }else{
              taii.setId((Integer.parseInt(articlesDao.getMaxSeq())+i+1).toString)
              twa.setId(taii)
              val pid:Array[String] = picId.split(",")
              val purl:Array[String] = picUrl.split(",")
              twa.setPicUrl(purl(i))
              twa.setLocalPicId(pid(i))
              twa.setUrl(paths(i))
              artId = artId+taii.getId+","
            }
            articlesService.Save(twa)
          }
          fodder.setArtId(artId.substring(0,artId.lastIndexOf(",")))
          wechatFodderDao.save(fodder)
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
  @Cacheable(value = Array("wechatFooderCache"), key = "#root.method.name+#mchtId")
  def mediaAllCount(tblWechatMchtInfId: TblWechatMchtInfId): Map[_, _] = {
    val data: TblWechatMchtInf = wechatMchtInfService.getById(tblWechatMchtInfId)
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    var result: JSONObject = null
    if (null != at) {
      result = mediaManager.mediaCountAll(at.getToken)
      WechatUtil.jsonToMap(result.toString)
    }
    null
  }

  @Transactional
  @Cacheable(value = Array("wechatFooderCache"))
  def newsGet(tblWechatMchtInfId: TblWechatMchtInfId, media: String): String = {
    val data: TblWechatMchtInf = wechatMchtInfService.getById(tblWechatMchtInfId)
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    var resultJson: JsonObject = null
    var result: JSONObject = null
    var resultMsg: String = null
    if (null != at) {
      //微信分页从0开始
      result = mediaManager.newsGet(at.getToken, "news", "0", "20")
      val jsonparer: JsonParser = new JsonParser
      val json: JsonObject = jsonparer.parse(result.toString).getAsJsonObject
      val array: JsonArray = json.get("item").getAsJsonArray
      for (i <- 0 until array.size()) {
        val jsonElement: JsonObject = array.get(i).getAsJsonObject();
        val respMedia: String = jsonElement.get("media_id").getAsString();
        if (media.equals(respMedia)) {
          resultJson = jsonElement.get("content").getAsJsonObject();
          resultJson.toString();
        }
      }
      if (resultJson == null) {
        resultMsg = "8888888"
      }
    } else {
      resultMsg = "9999999"
    }
    resultMsg
  }

  @Transactional
  @CacheEvict(value = Array("wechatFooderCache"), allEntries = true)
  def upLoadFImg(twm: TblWechatMchtInfId, `type`: String, f: MultipartFile, mediaType: String, ext: String, name: String, dsc: String): Map[String,AnyRef] = {
    val map:Map[String,AnyRef] = new java.util.HashMap[String,AnyRef]
    val upload: UpLoad = new UpLoad
    val file: File = upload.getFile(f, SystemProperties.getProperties("image.massimg"),SystemProperties.getProperties("image.path1"), ext)
    val data: TblWechatMchtInf = wechatMchtInfService.getById(twm)
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    var jsonObject: JSONObject = null
    var result: String = null
    if (null != at) {
      jsonObject = mediaManager.upLoadMedia(at.getToken, `type`, file, ext)
      if (null != jsonObject) {
        if (jsonObject.toString.indexOf("errcode") != -1) {
          result = jsonObject.getString("errcode")
        } else {
          //保存图片
          val picData: TblSysPicInf = new TblSysPicInf
          val id: Int = picInfService.maxId
          picData.setPicId(id)
          picData.setPicName(f.getName)
          picData.setPicType("02")
          val picUrl: String = SystemProperties.getProperties("image.massimg") + SystemProperties.getProperties("image.path1")+ file.getName
          picData.setPicUrl(picUrl)
          picInfService.save(picData)
          // 保存meida
          val fodder: TblWechatFodder = new TblWechatFodder
          val fodderId: TblWechatFodderId = new TblWechatFodderId
          fodderId.setMchtId(twm.getMchtId)
          fodderId.setMedia(jsonObject.getString("media_id"))
          fodder.setCreateTime(DateTime4J.getCurrentDateTime)
          fodder.setId(fodderId)
          fodder.setMediaType(mediaType)
          val url: String = SystemProperties.getProperties("image.massimg.url") + file.getName
          fodder.setUrl(url)
          fodder.setType(`type`)
          fodder.setName(name)
          fodder.setDsc(dsc)
          fodder.setPicId(id.toString)
          wechatFodderDao.save(fodder)
          map.put("imgInf",fodder)
          return map
        }
      } else {
        result = "8888888"
      }
    } else {
      result = "9999999"
    }
    map.put("errcode",result)
    map
  }
}
