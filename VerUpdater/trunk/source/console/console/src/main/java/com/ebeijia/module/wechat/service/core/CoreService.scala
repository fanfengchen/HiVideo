package com.ebeijia.module.wechat.service.core

import java.util.{ArrayList, Date, List, Map}
import javax.servlet.http.HttpServletRequest
import com.ebeijia.module.wechat.dao.base.WechatReqMsgDao
import com.ebeijia.util.wechat.MessageUtil
import com.ebeijia.entity.wechat.base.{TblWechatReqMsgId, TblWechatArticlesInf, TblWechatReqMsg, TblWechatRespMsg}
import com.ebeijia.module.wechat.service.media.ArticlesService
import com.ebeijia.module.wechat.service.msg.{WechatReqMsgService, WechatRespMsgService}
import net.sf.json.JSONObject
import org.ebeijia.tools.String4J
import org.slf4j.{Logger, LoggerFactory}
import com.ebeijia.entity.vo.wechat.resp._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
  * CoreService核心服务类
  *
  * @author zhicheng.xu
  *         15/8/13
  */

@Component
class CoreService {
  private val logger: Logger = LoggerFactory.getLogger(classOf[CoreService])
  @Autowired
  private val wechatReqMsgService: WechatReqMsgService = null
  @Autowired
  private val wechatRespMsgService: WechatRespMsgService = null
  @Autowired
  private val articlesService: ArticlesService = null
  @Autowired
  private val wechatReqMsgDao: WechatReqMsgDao = null

  @Transactional
  def processRequest(request: HttpServletRequest, mchtId: String): String = {
    var respMessage: String = null
    try {
      /**
        * @param requestMap  (xml请求解析)
        * @param fromUsrName (发送方帐号（open_id)
        * @param toUsrName   (公众帐号)
        * @param createTime  (发送时间)
        * @param msgType     (msgType text,image等)
        * @param content     (次为关键字回复，即keyworks)
        * @param event       (事件类型)
        * @param eventKey    (事件key值)
        */
      val requestMap: Map[String, String] = MessageUtil.parseXml(request)
      logger.info("微信公众平台返回消息：" + JSONObject.fromObject(requestMap).toString())
      val fromUsrName: String = requestMap.get("FromUserName")
      val toUsrName: String = requestMap.get("ToUserName")
      val createTime: String = requestMap.get("CreateTime")
      val msgType: String = requestMap.get("MsgType")
      val content: String = requestMap.get("Content")
      println("content" + content)
      val event: String = requestMap.get("Event")
      var eventKey: String = requestMap.get("EventKey")

      /**
        * 返回信息存入数据表
        * save TblWechatReqMsg
        */
      val tblWechatReqMsg: TblWechatReqMsg = new TblWechatReqMsg
      val tblWechatReqMsgId: TblWechatReqMsgId = new TblWechatReqMsgId
      tblWechatReqMsgId.setMchtId(mchtId)
      tblWechatReqMsgId.setReqMsgId(wechatReqMsgDao.getMaxSeq(mchtId))
      tblWechatReqMsg.setId(tblWechatReqMsgId)
      tblWechatReqMsg.setFromUsrName(fromUsrName)
      tblWechatReqMsg.setToUsrName(toUsrName)
      tblWechatReqMsg.setCreateTime(createTime)
      tblWechatReqMsg.setMsgType(msgType)
      //根据接收的消息类型入库
      this.saveReqMsg(tblWechatReqMsg, requestMap)

      /**
        * 事件类型设定
        * ①.menu 菜单事件推送事件
        * ②.keywords 关键字推送
        * ③。关注着推送
        * ④。地理位置推送
        * ⑤。高级图文群发推送
        */
      logger.info("微信公众平台返回消息：" + JSONObject.fromObject(requestMap).toString())
      //数据取值设定返回推送信息
      if (content != null) {
        eventKey = content
      }
      val findByMchtType: List[TblWechatRespMsg] = wechatRespMsgService.findByMchtType(mchtId, msgType, eventKey)
      println("findByMchtType" + findByMchtType)

      /**
        * ① 。事件自定义菜单推动
        * CLICK单机事件 ，VIEW跳转URL事件
        */
      if (event == MessageUtil.EVENT_TYPE_CLICK || content != null) {
        logger.info("事件消息---------------------")
        if (!findByMchtType.isEmpty) {
          val data: TblWechatRespMsg = findByMchtType.get(0)
          if (MessageUtil.RESP_MESSAGE_TYPE_TEXT == data.getMsgType) {
            logger.info("进入文本---------------------")
            val textMessage: TextMessage = new TextMessage
            textMessage.setToUserName(fromUsrName)
            textMessage.setFromUserName(toUsrName)
            textMessage.setCreateTime(new Date().getTime)
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT)
            textMessage.setFuncFlag(0)
            textMessage.setContent(data.getContent)
            respMessage = MessageUtil.textMessageToXml(textMessage)
          } else if (MessageUtil.RESP_MESSAGE_KF == data.getMsgType) {
            logger.info("进入接入客服---------------------")
            val textMessage: TextMessage = new TextMessage
            textMessage.setToUserName(fromUsrName)
            textMessage.setFromUserName(toUsrName)
            textMessage.setCreateTime(new Date().getTime)
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_KF)
            respMessage = MessageUtil.textMessageToXml(textMessage)
          } else if (MessageUtil.RESP_MESSAGE_TYPE_IMAGE == data.getMsgType) {
            logger.info("进入图片-------------------")
            val imageMessage: ImageMessage = new ImageMessage
            imageMessage.setToUserName(fromUsrName)
            imageMessage.setFromUserName(toUsrName)
            imageMessage.setCreateTime(new Date().getTime)
            imageMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_IMAGE)
            imageMessage.setFuncFlag(0)
            val image: Image = new Image
            image.setMediaId(data.getMediaId)
            imageMessage.setImage(image)
            respMessage = MessageUtil.imageMessageToXml(imageMessage)
          } else if (MessageUtil.RESP_MESSAGE_TYPE_VOICE == data.getMsgType) {
            logger.info("进入音频-------------------")
            val voiceMessage: VoiceMessage = new VoiceMessage
            voiceMessage.setToUserName(fromUsrName)
            voiceMessage.setFromUserName(toUsrName)
            voiceMessage.setCreateTime(new Date().getTime)
            voiceMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_VOICE)
            voiceMessage.setFuncFlag(0)
            val voice: Voice = new Voice
            voice.setMediaId(data.getMediaId)
            voiceMessage.setVoice(voice)
            respMessage = MessageUtil.voiceMessageToXml(voiceMessage)
          } else if (MessageUtil.RESP_MESSAGE_TYPE_VIDEO == data.getMsgType) {
            logger.info("进入视频-------------------")
            val videoMessage: VideoMessage = new VideoMessage
            videoMessage.setToUserName(fromUsrName)
            videoMessage.setFromUserName(toUsrName)
            videoMessage.setCreateTime(new Date().getTime)
            videoMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_VIDEO)
            videoMessage.setFuncFlag(0)
            val video: Video = new Video
            video.setMediaId(data.getMediaId)
            video.setTitle(data.getTitle)
            video.setDescription(data.getDescription)
            videoMessage.setVideo(video)
            respMessage = MessageUtil.videoMessageToXml(videoMessage)
          } else if (MessageUtil.RESP_MESSAGE_TYPE_MUSIC == data.getMsgType) {
            logger.info("进入音乐-------------------")
            val musicMessage: MusicMessage = new MusicMessage
            musicMessage.setToUserName(fromUsrName)
            musicMessage.setFromUserName(toUsrName)
            musicMessage.setCreateTime(new Date().getTime)
            musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC)
            musicMessage.setFuncFlag(0)
            val music: Music = new Music
            music.setTitle(data.getTitle)
            music.setDescription(data.getDescription)
            music.setMusicUrl(data.getMusicUrl)
            music.setHQMusicUrl(data.getHqMusicUrl)
            music.setThumbMediaId(data.getMediaId)
            musicMessage.setMusic(music)
            respMessage = MessageUtil.musicMessageToXml(musicMessage)
          } else if (MessageUtil.RESP_MESSAGE_TYPE_NEWS == data.getMsgType) {
            logger.info("进入图文-------------------")
            val articleList: List[Article] = new ArrayList[Article]
            for (articleId <- data.getArticleIds.split(",")) {
              val articlesTmp: TblWechatArticlesInf = articlesService.getByArtId(articleId)
              if (articlesTmp != null) {
                val article: Article = new Article
                article.setTitle(articlesTmp.getTitle)
                article.setDescription(articlesTmp.getDescription)
                article.setPicUrl(articlesTmp.getPicUrl)
                article.setUrl(articlesTmp.getUrl)
                articleList.add(article)
              }
            }
            val newsMessage: NewsMessage = new NewsMessage
            newsMessage.setToUserName(fromUsrName)
            newsMessage.setFromUserName(toUsrName)
            newsMessage.setCreateTime(new Date().getTime)
            newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS)
            newsMessage.setFuncFlag(0)
            newsMessage.setArticleCount(articleList.size)
            newsMessage.setArticles(articleList)
            respMessage = MessageUtil.newsMessageToXml(newsMessage)
          }
        } else {
          val textMessage: TextMessage = new TextMessage
          textMessage.setToUserName(fromUsrName)
          textMessage.setFromUserName(toUsrName)
          textMessage.setCreateTime(new Date().getTime)
          textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT)
          textMessage.setFuncFlag(0)
          textMessage.setContent("请输入正确的规则名！")
          respMessage = MessageUtil.textMessageToXml(textMessage)
        }
      } else if (msgType == MessageUtil.REQ_MESSAGE_TYPE_EVENT) {
        if (MessageUtil.REQ_MESSAGE_TYPE_LOCATION == requestMap.get("Event")) {
          logger.info("进入位置-------------------")
          val locationMessage: LocationMessage = new LocationMessage
          locationMessage.setToUserName(fromUsrName)
          locationMessage.setFromUserName(toUsrName)
          locationMessage.setCreateTime(new Date().getTime)
          locationMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)
          locationMessage.setLatitude(requestMap.get("Latitude"))
          locationMessage.setLongitude(requestMap.get("Longitude"))
          locationMessage.setPrecision(requestMap.get("Precision"))
          respMessage = MessageUtil.LocationMessageToXml(locationMessage)
        } else if (MessageUtil.EVENT_TYPE_SUBSCRIBE == requestMap.get("Event")) {
          val list: List[TblWechatRespMsg] = wechatRespMsgService.findBySubType(mchtId)
          if (list != null) {
            val twrm: TblWechatRespMsg = list.get(0)
            if (twrm.getMsgType.equals("text")) {
              val textMessage: TextMessage = new TextMessage
              textMessage.setToUserName(fromUsrName)
              textMessage.setFromUserName(toUsrName)
              textMessage.setCreateTime(new Date().getTime)
              textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT)
              textMessage.setFuncFlag(0)
              textMessage.setContent(twrm.getContent)
              respMessage = MessageUtil.textMessageToXml(textMessage)
            } else if (twrm.getMsgType.equals("image")) {
              val imageMessage: ImageMessage = new ImageMessage
              imageMessage.setToUserName(fromUsrName)
              imageMessage.setFromUserName(toUsrName)
              imageMessage.setCreateTime(new Date().getTime)
              imageMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_IMAGE)
              imageMessage.setFuncFlag(0)
              val image: Image = new Image
              image.setMediaId(twrm.getMediaId)
              imageMessage.setImage(image)
              respMessage = MessageUtil.imageMessageToXml(imageMessage)
            } else if (twrm.getMsgType.equals("news")) {
              val articleList: List[Article] = new ArrayList[Article]
              for (articleId <- twrm.getArticleIds.split(",")) {
                val articlesTmp: TblWechatArticlesInf = articlesService.getByArtId(articleId)
                if (articlesTmp != null) {
                  val article: Article = new Article
                  article.setTitle(articlesTmp.getTitle)
                  article.setDescription(articlesTmp.getDescription)
                  article.setPicUrl(articlesTmp.getPicUrl)
                  article.setUrl(articlesTmp.getUrl)
                  articleList.add(article)
                }
              }
              val newsMessage: NewsMessage = new NewsMessage
              newsMessage.setToUserName(fromUsrName)
              newsMessage.setFromUserName(toUsrName)
              newsMessage.setCreateTime(new Date().getTime)
              newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS)
              newsMessage.setFuncFlag(0)
              newsMessage.setArticleCount(articleList.size)
              newsMessage.setArticles(articleList)
              respMessage = MessageUtil.newsMessageToXml(newsMessage)
            }
          }
        }
      }
    }
    catch {
      case e: Exception => {
        //异常时通知微信不做处理
        respMessage = ""
        e.printStackTrace
      }
    }
    respMessage
  }

  private def saveReqMsg(tblWechatReqMsg: TblWechatReqMsg, requestMap: Map[String, String]) {
    // 消息类型
    val msgType: String = requestMap.get("MsgType")
    if (msgType == MessageUtil.REQ_MESSAGE_TYPE_TEXT) {
      // 接收用户发送的文本消息内容
      val content: String = requestMap.get("Content")
      val msgId: String = requestMap.get("MsgId")
      tblWechatReqMsg.setMsgId(msgId)
      tblWechatReqMsg.setContent(content)
    } else if (msgType == MessageUtil.REQ_MESSAGE_TYPE_IMAGE) {
      // 图片消息
      val picUrl: String = requestMap.get("PicUrl")
      val mediaId: String = requestMap.get("MediaId")
      val msgId: String = requestMap.get("MsgId")
      tblWechatReqMsg.setMsgId(msgId)
      tblWechatReqMsg.setPicUrl(picUrl)
      tblWechatReqMsg.setMediaId(mediaId)
    } else if (msgType == MessageUtil.REQ_MESSAGE_TYPE_LOCATION) {
      // 地理位置消息
      val locationX: String = requestMap.get("Location_X")
      val locationY: String = requestMap.get("Location_Y")
      val scale: String = requestMap.get("Scale")
      val label: String = requestMap.get("Label")
      val msgId: String = requestMap.get("MsgId")
      tblWechatReqMsg.setMsgId(msgId)
      tblWechatReqMsg.setLocationX(locationX)
      tblWechatReqMsg.setLocationY(locationY)
      tblWechatReqMsg.setScale(scale)
      tblWechatReqMsg.setLabel(label)
    } else if (msgType == MessageUtil.REQ_MESSAGE_TYPE_LINK) {
      // 链接消息
      val title: String = requestMap.get("Title")
      val description: String = requestMap.get("Description")
      val url: String = requestMap.get("Url")
      val msgId: String = requestMap.get("MsgId")
      tblWechatReqMsg.setMsgId(msgId)
      tblWechatReqMsg.setTitle(title)
      tblWechatReqMsg.setDescription(description)
      tblWechatReqMsg.setUrl(url)
    } else if (msgType == MessageUtil.REQ_MESSAGE_TYPE_VOICE) {
      // 音频消息
      val mediaId: String = requestMap.get("MediaId")
      val format: String = requestMap.get("Format")
      val msgId: String = requestMap.get("MsgId")
      tblWechatReqMsg.setMsgId(msgId)
      tblWechatReqMsg.setMediaId(mediaId)
      tblWechatReqMsg.setFormat(format)
    } else if (msgType == MessageUtil.REQ_MESSAGE_TYPE_EVENT) {
      // 事件推送
      // 事件类型
      val eventType: String = requestMap.get("Event")
      // 订阅
      if (eventType == MessageUtil.EVENT_TYPE_SUBSCRIBE) {
        tblWechatReqMsg.setEventType(MessageUtil.EVENT_TYPE_SUBSCRIBE)
        val eventKey: String = requestMap.get("EventKey")
        if (eventKey != null && eventKey.startsWith("qrscene_")) {
          tblWechatReqMsg.setEventKey(eventKey)
          tblWechatReqMsg.setTicket(requestMap.get("Ticket"))
        }
        // 取消订阅
      } else if (eventType == MessageUtil.EVENT_TYPE_UNSUBSCRIBE) {
        // 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
        tblWechatReqMsg.setEventType(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)
        // 自定义菜单点击事件
      } else if (eventType == MessageUtil.EVENT_TYPE_CLICK) {
        tblWechatReqMsg.setEventType(MessageUtil.EVENT_TYPE_CLICK)
        val eventKey: String = requestMap.get("EventKey")
        tblWechatReqMsg.setEventKey(eventKey)
        tblWechatReqMsg.setEventType(eventType)
      }
    }
    wechatReqMsgService.save(tblWechatReqMsg)
  }


}

