package com.ebeijia.controller.wechat.media

import java.util.{HashMap, Map}
import javax.servlet.http.HttpServletRequest

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.wechat.base.TblWechatMchtInfId
import com.ebeijia.module.wechat.service.media.WechatMediaService
import com.ebeijia.util.core.RespCode
import com.ebeijia.util.wechat.WechatError
import net.sf.json.JSONObject
import org.ebeijia.tools.Validate4J
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}
import org.springframework.web.multipart.{MultipartFile, MultipartHttpServletRequest}

/**
 * 微信素材
 */

@Controller
@RequestMapping(value = Array("/wechat/media"))
class WechatMediaAddAction extends BaseValidRoleFunc{
  @Autowired
  private val wechatMediaService: WechatMediaService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatMediaAddAction])

  @RequestMapping(value = Array("upLoad.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信上传素材")
  @ResponseBody def upLoad(twm:TblWechatMchtInfId, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId,"200041")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE,"")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    twm.setMchtId(request.getParameter("mchtIdFlag"))
    val `type`: String = request.getParameter("type")
    val mediaType: String = request.getParameter("mediaType")
    val name: String = request.getParameter("name")
    val dsc: String = request.getParameter("dsc")
    val s: Array[Array[String]] = Array(Array(twm.getMchtId, "1", "15","1"), Array(`type`, "3", "6","1"), Array(mediaType, "1", "1","1"), Array(name, "1", "30","1"), Array(dsc, "0", "64","1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    val multipartRequest: MultipartHttpServletRequest = request.asInstanceOf[MultipartHttpServletRequest]
    val file: MultipartFile = multipartRequest.getFile("file")
    if (file == null) {
      AjaxResp.getReturn(RespCode.FILE_TYPE_ERROR,"")
    }
    // 获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
    val fileName: String = file.getOriginalFilename
    //验证时统一转成大写
    var ext: String = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)
    ext = ext.toLowerCase
    //验证文件格式是否正确
    var respMsg: String = this.vali(mediaType, `type`, ext)
    if (!("" == respMsg)) {
      AjaxResp.getReturn(RespCode.FILE_TYPE_ERROR, "")
    }
    try {
      respMsg = wechatMediaService.upLoadMedia(twm, `type`, file, mediaType, ext, name, dsc)
      if (respMsg == null) {
        AjaxResp.getReturn(RespCode.SUCCESS_CODE,"")
      } else {
        AjaxResp.getReturn(RespCode.ERROR_CODE, WechatError.checkCode(respMsg), "")
      }

    }
    catch {
      case e: Exception => {
        logger.info("上传素材失败")
        e.printStackTrace()
        AjaxResp.getReturn(RespCode.WECHAT_MEDIA_UPLOAD_MATERIAL_ERROR, "")

      }
    }
  }

  @RequestMapping(value = Array("dowLoad.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信下载素材")
  @ResponseBody def dowLoad(twm :TblWechatMchtInfId, request: HttpServletRequest): Map[String, AnyRef] = {
//    val mchtId: String = request.getParameter("mchtId")
    val mediaType: String = request.getParameter("mediaType")
    val media: String = request.getParameter("media")
    var resp: String = null
    val s: Array[Array[String]] = Array(Array(twm.getMchtId, "15", "15"), Array(media, "1", "100"), Array(mediaType, "1", "1"))
    if (!Validate4J.checkStrArrLen(s)) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))

    }
    try {
      resp = wechatMediaService.dowLoadMedia(twm, media, mediaType)
      //判断是否是下载链接
      if (resp.indexOf("http") == 0) {
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, resp)
      } else {
        val json: JSONObject = JSONObject.fromObject(resp)
        resp = json.getString("errcode")
        AjaxResp.getReturn(RespCode.ERROR_CODE, WechatError.checkCode(resp),"")
      }

    }
    catch {
      case e: Exception => {
        logger.info("下载多媒体失败")
        AjaxResp.getReturn(RespCode.WECHAT_MEDIA_DOWNLOAD_MATERIAL_ERROR, "")
      }
    }
  }

  @RequestMapping(value = Array("upNews.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信上传图文素材")
  @ResponseBody def upNews(picId:String,picUrl:String,twm :TblWechatMchtInfId, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val mchtId: String = request.getParameter("mchtId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId,"200051")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE,"")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }

    val articles: String = request.getParameter("articles")
    val name: String = request.getParameter("name")
    val dsc: String = request.getParameter("dsc")
    val s: Array[Array[String]] = Array(Array(mchtId, "1", "15","1"), Array(articles, "1", null,"1"), Array(name, "1", "30","1"), Array(dsc, "0", "64","1"))
    if (!Validate4J.checkStrArrLen(s)) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
    }
    try {
      val map: Map[String, AnyRef] = wechatMediaService.upGtMedia(null,picId,picUrl,mchtId, name, dsc, articles)
      if (map.get("error") == null) {
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
      } else {
        AjaxResp.getReturn(RespCode.ERROR_CODE, "",WechatError.checkCode(map.get("error").toString))
      }
    }
    catch {
      case e: Exception => {
        logger.info("上传图文失败,请联系管理员或稍后再试")
        AjaxResp.getReturn(RespCode.WECHAT_MEDIA_DOWNLOAD_FODDER_ERROR, "")
      }
    }
  }

  @RequestMapping(value = Array("upLoadFImg.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信上传图文图片")
  @ResponseBody def upLoadFImg(twm:TblWechatMchtInfId, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId,"200041")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE,"")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    twm.setMchtId(request.getParameter("mchtIdFlag"))
    val `type`: String = request.getParameter("type")
    val mediaType: String = request.getParameter("mediaType")
    val name: String = request.getParameter("name")
    val dsc: String = request.getParameter("dsc")
    val s: Array[Array[String]] = Array(Array(twm.getMchtId, "1", "15","1"), Array(`type`, "3", "6","1"), Array(mediaType, "1", "1","1"), Array(name, "1", "30","1"), Array(dsc, "0", "64","1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    val multipartRequest: MultipartHttpServletRequest = request.asInstanceOf[MultipartHttpServletRequest]
    val file: MultipartFile = multipartRequest.getFile("file")
    if (file == null) {
      AjaxResp.getReturn(RespCode.FILE_TYPE_ERROR,"")
    }
    // 获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
    val fileName: String = file.getOriginalFilename
    //验证时统一转成大写
    var ext: String = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)
    ext = ext.toLowerCase
    //验证文件格式是否正确
    val respMsg: String = this.vali(mediaType, `type`, ext)
    if (!("" == respMsg)) {
      AjaxResp.getReturn(RespCode.FILE_TYPE_ERROR, "")
    }
    try {
      val map:Map[String,AnyRef] = wechatMediaService.upLoadFImg(twm, `type`, file, mediaType, ext, name, dsc)
      if (map.get("errcode") == null) {
        AjaxResp.getReturn(RespCode.SUCCESS_CODE,map)
      } else {
        AjaxResp.getReturn(RespCode.ERROR_CODE, WechatError.checkCode(respMsg), "")
      }

    }
    catch {
      case e: Exception => {
        logger.info("上传素材失败")
        e.printStackTrace()
        AjaxResp.getReturn(RespCode.WECHAT_MEDIA_UPLOAD_MATERIAL_ERROR, "")

      }
    }
  }

  private def vali(`type`: String, mediaType: String, ext: String): String = {
    var respMsg: String = ""
    if ("0" == mediaType) {
      if (("image" == `type`) || ("thumb" == `type`)) {
        if (!("jpg" == ext)) {
          respMsg = "图片或缩略图格式不正确"
        }
      } else if ("voice" == `type`) {
        if (!("amr" == ext) || !("mp3" == ext)) {
          respMsg = "语音格式不正确"
        }
      } else if ("video" == `type`) {
        if (!("mp4" == ext)) {
          respMsg = "视频格式不正确"
        }
      }
    } else {
      if (("image" == `type`) || ("thumb" == `type`)) {
        if (!("jpg" == ext) || !("bmp" == ext) || !("png" == ext) || !("jpeg" == ext) || !("gif" == ext)) {
          respMsg = "图片或缩略图格式不正确"
        }
      } else if ("voice" == `type`) {
        if (!("amr" == ext) || !("mp3" == ext) || !("wma" == ext) || !("wav" == ext)) {
          respMsg = "语音格式不正确"
        }
      } else if ("video" == `type`) {
        if (!("mp4" == ext)) {
          respMsg = "视频格式不正确"
        }
      }
    }
    respMsg
  }
}

