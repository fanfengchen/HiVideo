package com.ebeijia.module.wechat.service.mass

import java.util
import java.util.{ArrayList, List, Map}

import com.ebeijia.module.wechat.dao.base.{WechatSubscribeDao, WechatMassDao}
import com.ebeijia.entity.vo.wechat.AccessToken
import com.ebeijia.entity.vo.wechat.mass.{Filter, Image, Mpnews, Mpvideo, SendImageMass, SendImageMassByUsr, SendMpnewsMass, SendMpnewsMassByUsr, SendMpvideoMass, SendMpvideoMassByUsr, SendTextMass, SendTextMassByUsr, SendVoiceMass, SendVoiceMassByUsr, Text, ToUser, Voice}
import com.ebeijia.entity.wechat.base._
import com.ebeijia.module.system.service.file.FileService
import com.ebeijia.module.wechat.service.core.{WechatMchtInfService, WechatTokenService}
import com.ebeijia.module.wechat.service.inter.{MassManager, MediaManager}
import com.ebeijia.module.wechat.service.media.{ArticlesService, WechatFodderService}
import com.ebeijia.util.wechat.{MessageUtil, WechatUtil}
import net.sf.json.JSONObject
import org.ebeijia.tools.DateTime4J
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
  * WechatMassServiceImpl
  *
  * @author zhicheng.xu
  *         15/8/14
  */


@Service("wechatMassService")
final class WechatMassServiceImpl extends WechatMassService {
  @Autowired
  private val wechatMchtInfService: WechatMchtInfService = null
  @Autowired
  private val wechatTokenService: WechatTokenService = null
  @Autowired
  private val mediaManager: MediaManager = null
  @Autowired
  private val massManager: MassManager = null
  @Autowired
  private val wechatFodderService: WechatFodderService = null
  @Autowired
  private val wechatMassDao: WechatMassDao = null
  @Autowired
  private val articlesService: ArticlesService = null
  @Autowired
  private val fileService: FileService = null
  @Autowired
  private val wechatSubscribeDao: WechatSubscribeDao = null

  @Transactional
  @CacheEvict(value = Array("wechatFooderCache"), allEntries = true)
  @throws(classOf[Exception])
  def upGtMedia(mchtId: String, title: String, dsc: String, articles: String): String = {
    // val data: TblWechatMchtInf = wechatMchtInfService.getById(mchtId)
    //查询商户信息，商户信息只有一条
    val data: TblWechatMchtInf = wechatMchtInfService.queryWechatMchtInf("10001")
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    var result: String = null
    if (null != at) {
      val jsonObject: JSONObject = mediaManager.massUpNews(at.getToken, articles)
      if (null != jsonObject) {
        if (WechatUtil.getWechatCallBackStatus(jsonObject.toString) != 0) {
          result = jsonObject.getString("errcode")
        } else {
          // 保存meida
          val fodder: TblWechatFodder = new TblWechatFodder
          fodder.setCreateTime(DateTime4J.getCurrentDateTime)
          fodder.getId.setMedia(jsonObject.getString("media_id"))
          fodder.setMediaType("1")
          fodder.getId.setMchtId(data.getId.getMchtId)
          fodder.setUrl(" ")
          fodder.setType("mpnews")
          fodder.setName(title)
          fodder.setDsc(dsc)
          wechatFodderService.save(fodder)
          val tai: TblWechatArticlesInf = new TblWechatArticlesInf
          val taii: TblWechatArticlesInfId = new TblWechatArticlesInfId
          val json: JSONObject = JSONObject.fromObject(articles)
          tai.setAuthor(json.getString("author"))
          tai.setContent(json.getString("content"))
          tai.setDescription(json.getString("description"))
          tai.setDigest(json.getString("digest"))
          taii.setId(articlesService.getMaxSeq().toString)
          taii.setMchtId(mchtId)
          tai.setId(taii)
          //          tai.setLocalPicId()
          tai.setThumbMediaId(json.getString("thumb_media_id"))
          tai.setTitle(json.getString("title"))
          val path: String = fileService.generateHTMLRetPath(json.getString("content"))
          tai.setUrl(path)
          articlesService.Save(tai)
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
  @CacheEvict(value = Array("wechatFooderCache"), allEntries = true)
  def upVideoMedia(mchtId: String, title: String, dsc: String, mediaId: String): String = {
    // val data: TblWechatMchtInf = wechatMchtInfService.getById(mchtId)
    //查询商户信息，商户信息只有一条
    val data: TblWechatMchtInf = wechatMchtInfService.queryWechatMchtInf("10001")
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    var result: String = null
    if (null != at) {
      val jsonObject: JSONObject = mediaManager.massUpVideo(at.getToken, mediaId, title, dsc)
      if (null != jsonObject) {
        if (WechatUtil.getWechatCallBackStatus(jsonObject.toString) != 0) {
          result = jsonObject.getString("errcode")
        } else {
          // 保存meida
          val fodder: TblWechatFodder = new TblWechatFodder
          fodder.setCreateTime(DateTime4J.getCurrentDateTime)
          fodder.getId.setMchtId(result)
          fodder.setMediaType("1")
          fodder.getId.setMchtId(data.getId.getMchtId)
          fodder.setUrl(" ")
          fodder.setType("mpvideo")
          fodder.setName(title)
          fodder.setDsc(dsc)
          wechatFodderService.save(fodder)
        }
      } else {
        result = "8888888"
      }
    } else {
      result = "9999999"
    }
    result
  }

  //群发所有用户
  @Transactional
  @CacheEvict(value = Array("wechatMassCache"), allEntries = true)
  def sendByGroup(mchtId: String, content: String, `type`: String, groupId: String, media: String): String = {
    //根据group和type生产对象
    val filter: Filter = new Filter
    if (groupId == null || ("" == groupId)) {
      filter.setIs_to_all(true)
    }
    else {
      filter.setIs_to_all(false)
      filter.setGroup_id(groupId)
    }
    val sendJson: String = this.build(content, filter, `type`, media)
    // val data: TblWechatMchtInf = wechatMchtInfService.getById(mchtId)
    //查询商户信息，商户信息只有一条
    val data: TblWechatMchtInf = wechatMchtInfService.queryWechatMchtInf(mchtId)
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    var result: String = null
    if (null != at) {
      val jsonObject: JSONObject = massManager.massAll(at.getToken, sendJson)
      if (null != jsonObject) {
        if (WechatUtil.getWechatCallBackStatus(jsonObject.toString) != 0) {
          result = jsonObject.getString("errcode")
        } else {
          // 保存群发消息
          val tblWechatMass: TblWechatMass = new TblWechatMass
          val tblWechatMassId: TblWechatMassId = new TblWechatMassId
          tblWechatMassId.setMsgId(jsonObject.getString("msg_id"))
          tblWechatMassId.setMchtId(mchtId)
          tblWechatMass.setId(tblWechatMassId)
          tblWechatMass.setContent(content)
          tblWechatMass.setCreateTime(DateTime4J.getCurrentDateTime)
          tblWechatMass.setMedia(media)
          tblWechatMass.setMsgType(`type`)
          tblWechatMass.setToUsr(sendUsrCount(groupId, 0))
          //根据组别发送
          tblWechatMass.setType("0")
          wechatMassDao.save(tblWechatMass)
        }
      } else {
        result = "8888888"
      }
    } else {
      result = "9999999"
    }
    result
  }

  //群发筛选所有用户
  @Transactional
  @CacheEvict(value = Array("wechatMassCache"), allEntries = true)
  def sendByUsr(mchtId: String, content: String, msgType: String, toUsr: String, media: String): String = {
    //根据group和type生产对象
    val toUser: ToUser = new ToUser
    val toUserList: List[Any] = new ArrayList[Any]
    for (usr <- toUsr.split(",")) {
      toUserList.add(usr)
    }
    toUser.setToUser(toUserList)
    val sendJson: String = this.buildUsr(content, toUserList, msgType, media)
    //val data: TblWechatMchtInf = wechatMchtInfService.getById(mchtId)
    //查询商户信息，商户信息只有一条
    val data: TblWechatMchtInf = wechatMchtInfService.queryWechatMchtInf(mchtId)
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    var result: String = null
    if (null != at) {
      val jsonObject: JSONObject = massManager.massUsr(at.getToken, sendJson)
      if (null != jsonObject) {
        if (WechatUtil.getWechatCallBackStatus(jsonObject.toString) != 0) {
          result = jsonObject.getString("errcode")
        } else {
          // 保存群发消息
          val tblWechatMass: TblWechatMass = new TblWechatMass
          val tblWechatMassId: TblWechatMassId = new TblWechatMassId
          tblWechatMassId.setMsgId(jsonObject.getString("msg_id"))
          tblWechatMassId.setMchtId(mchtId)
          tblWechatMass.setId(tblWechatMassId)
          tblWechatMass.setContent(content)
          tblWechatMass.setCreateTime(DateTime4J.getCurrentDateTime)
          tblWechatMass.setMedia(media)
          tblWechatMass.setMsgType(msgType)
          tblWechatMass.setToUsr(sendUsrCount(null, (toUsr.split(",")).length))
          //根据组别发送
          tblWechatMass.setType("1")
          tblWechatMass.setToUsr(toUserList.size)
          wechatMassDao.save(tblWechatMass)
        }
      } else {
        result = "8888888"
      }
    } else {
      result = "9999999"
    }
    result
  }

  //群发删除
  @Transactional
  @CacheEvict(value = Array("wechatMassCache"), allEntries = true)
  def sendDel(mchtId: String, mediaId: String): String = {
    // val data: TblWechatMchtInf = wechatMchtInfService.getById(mchtId)
    //查询商户信息，商户信息只有一条
    val data: TblWechatMchtInf = wechatMchtInfService.queryWechatMchtInf("10001")
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    var result: String = "0"
    if (null != at) {
      val jsonObject: JSONObject = massManager.massDel(at.getToken, mediaId)
      if (null != jsonObject) {
        if (WechatUtil.getWechatCallBackStatus(jsonObject.toString) != 0) {
          result = jsonObject.getString("errcode")
        } else {
          // 数据库操作？
        }
      } else {
        result = "8888888"
      }
    } else {
      result = "9999999"
    }
    result
  }

  //	//群发预览
  //	@Transactional
  //	public String sendPreview(String mchtId,ToGroupMass touserTextMass){
  //		// 调用接口获取access_token
  //		TblWechatMchtInf data = wechatMchtInfService.getById(mchtId);
  //		AccessToken at = wechatTokenService.getAccessToken(data.getAppid(),data.getAppsecret());
  //		String result = "0";
  //		String sendJson =JSONObject.fromObject(touserTextMass).toString();
  //		if (null != at) {
  //			JSONObject jsonObject = massManager.massAll(at.getToken(), sendJson);
  //			if (null != jsonObject) {
  //				if (WechatUtil.getWechatCallBackStatus(jsonObject.toString()) != 0) {
  //					result = jsonObject.getString("errcode");
  //				} else {
  //					// 数据库操作？
  //				}
  //			}else{
  //				result = "8888888";
  //			}
  //		} else {
  //			result = "9999999";
  //		}
  //		return result;
  //	}

  //群发状态查询
  @Transactional
  @Cacheable(value = Array("wechatMassCache"), key = "#root.method.name+#mchtId+#msgId")
  def sendStatusFind(mchtId: String, msgId: String): String = {
    // val data: TblWechatMchtInf = wechatMchtInfService.getById(mchtId)
    //查询商户信息，商户信息只有一条
    val data: TblWechatMchtInf = wechatMchtInfService.queryWechatMchtInf("10001")
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    var result: String = "0"
    if (null != at) {
      result = massManager.massStatus(at.getToken, msgId)
    }
    result
  }

  @Transactional
  @Cacheable(value = Array("wechatMassCache"), key = "#root.method.name+#beginDate+#endDate+#msgType+#pagaData")
  def findBySql(beginDate: String, endDate: String, msgType: String, pagaData: String): Map[String, AnyRef] = {
    val query: StringBuffer = new StringBuffer
    query.append("from TblWechatMass")
    query.append(" where 1=1")
    if (msgType != null && !("" == msgType)) {
      query.append(" AND msgType ='").append(msgType).append("'")
    }
    if (beginDate != null && !("".equals(beginDate))) {
      query.append(" AND createTime >='").append(beginDate).append("000000' ")

    }
    if (endDate != null && !("".equals(endDate))) {

      query.append(" AND createTime <='").append(endDate).append("235959' ")
    }
    query.append(" ORDER BY id.msgId  desc")
    val m: Map[String, AnyRef] = wechatMassDao.findByPage(query.toString, pagaData)
    m
  }

  //构建发送群发消息对象
  private def build(content: String, filter: Filter, `type`: String, media: String): String = {
    var sendJson: String = null
    if (MessageUtil.RESP_MESSAGE_TYPE_TEXT == `type`) {
      val text: Text = new Text
      text.setContent(content)
      val sendTextMass: SendTextMass = new SendTextMass
      sendTextMass.setFilter(filter)
      sendTextMass.setText(text)
      sendTextMass.setMsgtype(`type`)
      sendJson = JSONObject.fromObject(sendTextMass).toString
    } else if (MessageUtil.RESP_MESSAGE_TYPE_IMAGE == `type`) {
      val image: Image = new Image
      image.setMedia_id(media)
      val sendImageMass: SendImageMass = new SendImageMass
      sendImageMass.setFilter(filter)
      sendImageMass.setImage(image)
      sendImageMass.setMsgtype(`type`)
      sendJson = JSONObject.fromObject(sendImageMass).toString
    } else if (MessageUtil.RESP_MESSAGE_TYPE_VOICE == `type`) {
      val voice: Voice = new Voice
      voice.setMedia_id(media)
      val sendVoiceMass: SendVoiceMass = new SendVoiceMass
      sendVoiceMass.setFilter(filter)
      sendVoiceMass.setVoice(voice)
      sendVoiceMass.setMsgtype(`type`)
      sendJson = JSONObject.fromObject(sendVoiceMass).toString
    } else if (MessageUtil.RESP_MESSAGE_TYPE_VIDEO == `type`) {
      val video: Mpvideo = new Mpvideo
      video.setMedia_id(media)
      val sendVideoMass: SendMpvideoMass = new SendMpvideoMass
      sendVideoMass.setFilter(filter)
      sendVideoMass.setMpvideo(video)
      sendVideoMass.setMsgtype(`type`)
      sendJson = JSONObject.fromObject(sendVideoMass).toString
    } else if (MessageUtil.RESP_MESSAGE_TYPE_MPNEWS == `type`) {
      val news: Mpnews = new Mpnews
      news.setMedia_id(media)
      val sendMpnewsMass: SendMpnewsMass = new SendMpnewsMass
      sendMpnewsMass.setFilter(filter)
      sendMpnewsMass.setMpnews(news)
      sendMpnewsMass.setMsgtype(`type`)
      sendJson = JSONObject.fromObject(sendMpnewsMass).toString
    }
    sendJson
  }

  //构建发送群发消息筛选用户对象
  private def buildUsr(content: String, toUser: List[_], `type`: String, media: String): String = {
    var sendJson: String = null
    if (MessageUtil.RESP_MESSAGE_TYPE_TEXT == `type`) {
      val text: Text = new Text
      text.setContent(content)
      val sendTextMass: SendTextMassByUsr = new SendTextMassByUsr
      sendTextMass.setTouser(toUser)
      sendTextMass.setText(text)
      sendTextMass.setMsgtype(`type`)
      sendJson = JSONObject.fromObject(sendTextMass).toString
    }
    else if (MessageUtil.RESP_MESSAGE_TYPE_IMAGE == `type`) {
      val image: Image = new Image
      image.setMedia_id(media)
      val sendImageMass: SendImageMassByUsr = new SendImageMassByUsr
      sendImageMass.setTouser(toUser)
      sendImageMass.setImage(image)
      sendImageMass.setMsgtype(`type`)
      sendJson = JSONObject.fromObject(sendImageMass).toString
    }
    else if (MessageUtil.RESP_MESSAGE_TYPE_VOICE == `type`) {
      val voice: Voice = new Voice
      voice.setMedia_id(media)
      val sendVoiceMass: SendVoiceMassByUsr = new SendVoiceMassByUsr
      sendVoiceMass.setTouser(toUser)
      sendVoiceMass.setVoice(voice)
      sendVoiceMass.setMsgtype(`type`)
      sendJson = JSONObject.fromObject(sendVoiceMass).toString
    }
    else if (MessageUtil.RESP_MESSAGE_TYPE_VIDEO == `type`) {
      val video: Mpvideo = new Mpvideo
      video.setMedia_id(media)
      val sendVideoMass: SendMpvideoMassByUsr = new SendMpvideoMassByUsr
      sendVideoMass.setTouser(toUser)
      sendVideoMass.setMpvideo(video)
      sendVideoMass.setMsgtype(`type`)
      sendJson = JSONObject.fromObject(sendVideoMass).toString
    }
    else if (MessageUtil.RESP_MESSAGE_TYPE_MPNEWS == `type` || `type`.equals("news")) {
      val news: Mpnews = new Mpnews
      news.setMedia_id(media)
      val sendMpnewsMass: SendMpnewsMassByUsr = new SendMpnewsMassByUsr
      sendMpnewsMass.setTouser(toUser)
      sendMpnewsMass.setMpnews(news)
      sendMpnewsMass.setMsgtype(MessageUtil.RESP_MESSAGE_TYPE_MPNEWS)
      sendJson = JSONObject.fromObject(sendMpnewsMass).toString
    }
    sendJson
  }

  def sendUsrCount(groupId: String, count: Int): Int = {
    if (count > 0) return count
    else {
      val hql: String = "SELECT COUNT(*) FROM TblWechatSubscribe WHERE groupId = ?"
      Integer.parseInt((wechatSubscribeDao.find(hql, groupId).get(0)).toString)
    }
  }

  def queryMedia(list: List[TblWechatMass]): Map[String, AnyRef] = {
    val map:Map[String, AnyRef] = new util.LinkedHashMap[String, AnyRef]
    for ( i <- 0 until list.size()) {
      val tblWechatFodder:TblWechatFodderId = new TblWechatFodderId
      tblWechatFodder.setMchtId(list.get(i).getId.getMchtId)
      tblWechatFodder.setMedia(list.get(i).getMedia)
      if(!list.get(i).getMsgType.equals("text")){
        val li: TblWechatFodder = wechatFodderService.getById(tblWechatFodder)
        if(li != null){
          map.put(li.getId.getMedia,li)
        }
      }
    }
    map
  }
}
