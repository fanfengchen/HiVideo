package com.ebeijia.module.wechat.service.media

import com.ebeijia.module.system.service.picInf.SysPicInfService
import com.ebeijia.module.wechat.service.core.{WechatMchtInfService, WechatTokenService}
import com.ebeijia.module.wechat.service.inter.MediaManager
import net.sf.json.JSONObject
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.ebeijia.entity.vo.wechat.media.UpMediaReq
/**
  * 通用素材上传方法
  * MediaCommonServiceImpl
  * @author xiong.wang
  *         2016/6/21
  */
@Service("WechatMediaUploadService")
class WechatMediaUploadServiceImpl extends WechatMediaUploadService {
  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatMediaUploadServiceImpl])
  @Autowired
  val mediaManager: MediaManager = null
  @Autowired
  private val wechatMchtInfService: WechatMchtInfService = null
  @Autowired
  private val wechatTokenService: WechatTokenService = null
  @Autowired
  private val picInfService: SysPicInfService = null

  def upLoadMediaUtil(upMediaReq: UpMediaReq): String = {
    var result: String = null
    var json: JSONObject = new JSONObject
    if (upMediaReq.getUpType.equals("video") || upMediaReq.getUpType.equals("image")) {
      if(upMediaReq.getUpType.equals("image")){

      }
      json = mediaManager.upLoadMedia(upMediaReq.getAccessToken, upMediaReq.getUpType, upMediaReq.getUpFile, null)
    } else if (upMediaReq.getUpType.equals("news")) {
      json = mediaManager.upGtMedia(upMediaReq.getAccessToken, upMediaReq.getArticles)
    }
    if (json.get("errcode") == null) {
      result = json.get("media_id").toString
      println(result)
    }
    result
  }
}
