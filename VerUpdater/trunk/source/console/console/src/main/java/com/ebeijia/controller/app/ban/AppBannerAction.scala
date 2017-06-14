package com.ebeijia.controller.app.ban

import java.util._
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.app.ban.{TblAppBannerInf, TblAppBannerInfId}
import com.ebeijia.entity.system.system.TblSysPicInf
import com.ebeijia.module.app.service.ban.AppBannerService
import com.ebeijia.module.system.service.picInf.SysPicInfService
import com.ebeijia.util.core.{FuncCode, RespCode}
import org.ebeijia.tools.{DateTime4J, Validate4J}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.springframework.web.multipart.{MultipartFile, MultipartHttpServletRequest}

/**
  * BannerAction
  * 广告管理
  * @author xiong.wang
  *  16/6/17
  */

@Controller
@RequestMapping(value = Array("/app/ban"))
class AppBannerAction extends BaseValidRoleFunc {

  private val logger: Logger = LoggerFactory.getLogger(classOf[AppBannerAction])
  @Autowired
  private val bannerService: AppBannerService = null
  @Autowired
  private val picInfService: SysPicInfService = null

  /**
   * 分页查询
   * @param session
   * @param request
   * @return map
   */
  @RequestMapping(value = Array("/queryList.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def queryList(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId, FuncCode.BAN_FUNC_MAINTAIN)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE,"")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    //查询入参
    val name: String = request.getParameter("name")
    val state: String = request.getParameter("state")
    val pageData: String = request.getParameter("aoData")
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: Map[String, AnyRef] = bannerService.findBySql(name, state, pageData)
      val list: List[AnyRef] = new LinkedList[AnyRef]
      val m: AnyRef = mapTmp.get("list")
      val lists: List[_] = m.asInstanceOf[List[_]]
      val it: Iterator[_] = lists.iterator
      while (it.hasNext) {
        val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
        val o: TblAppBannerInf = it.next.asInstanceOf[TblAppBannerInf]
        hashMap.put("banId", o.getId.getBanId.toString)
        hashMap.put("name", o.getName)
        hashMap.put("about", o.getAbout)
        hashMap.put("fileId", o.getFileId.toString)
        hashMap.put("linkType", o.getLinkType)
        hashMap.put("linkUrl", o.getLinkUrl)
        hashMap.put("state", o.getState)
        hashMap.put("seq", o.getSeq)
        hashMap.put("res1", o.getRes1)
        hashMap.put("res2", o.getRes2)
        hashMap.put("lastTime", o.getLastTime)
        val docId: Int = o.getFileId
        val tblPicInf: TblSysPicInf = picInfService.queryPicInfById(docId)
        if (tblPicInf != null) {
          hashMap.put("file", tblPicInf.getPicUrl)
        } else {
          hashMap.put("file", "")
        }
        list.add(hashMap)
      }
      //设置分页
      map.put("banList", list)
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("广告查询失败:" + e)
        AjaxResp.getReturn(RespCode.BAN_ERROR_SEL, "")
      }
    }
  }

  @RequestMapping(value = Array("/queryBan.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def queryDep(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val brhNo: String = request.getParameter("brhNo")
    val banId: String = request.getParameter("banId")
    val msg: String = this.validRoleFunc(usrId, token, roleId, FuncCode.BAN_FUNC_MAINTAIN)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    var copyId: Int = 0
    if (null != banId && !("".equals(banId))) {
      copyId = banId.toInt
    }
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val tbabId:TblAppBannerInfId = new TblAppBannerInfId
      tbabId.setBanId(copyId)
      tbabId.setBrhNo(brhNo)
      val tbt: TblAppBannerInf = bannerService.getById(tbabId)
      map.put("banList", tbt)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("广告详情查询失败:" + e)
        AjaxResp.getReturn(RespCode.BAN_ERROR_SEL,"")
      }
    }
  }


  @RequestMapping(value = Array("add.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "广告添加")
  @ResponseBody
  def add(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val banId: String = request.getParameter("banId")
    val msg: String = this.validRoleFunc(usrId, token, roleId, FuncCode.BAN_FUNC_ADDITION)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE,"")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    val state: Boolean = true
    val tblBannerInf: TblAppBannerInf = this.build(request, state)
    val fag: Boolean = bannerService.selSeq(tblBannerInf.getSeq)//查看是否存在该本版记录，没有返回true
    if (!fag) {
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"","该顺序已存在")
    }
    if (tblBannerInf == null) {
      return AjaxResp.getReturn(RespCode.OBJECT_ERROR_NULL, "")
    }
    try {
      val multipartRequest: MultipartHttpServletRequest = request.asInstanceOf[MultipartHttpServletRequest]
      val file: MultipartFile = multipartRequest.getFile("file")
      var ext: String = null
      if (file != null) {
        // 获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
        val fileName: String = file.getOriginalFilename//获取上传文件名字
        //验证时统一[]成大写getOriginalFilename
        ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)//获取后缀名
        //验证文件格式是否正确
        ext = ext.toLowerCase//转换为小写字母
        val respMsg: String = this.vali(ext)//判断图片格式
        if (!("".equals(respMsg))) {
          return AjaxResp.getReturn(RespCode.PIC_PNG_JPG_ERROR, "")
        }
      }
      if(tblBannerInf.getSeq.length>2){
        return AjaxResp.getReturn(RespCode.BAN_SEQ_LENGTH_ERROR, "")
      }
    bannerService.save(tblBannerInf, "jpg", file, ext)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        logger.info("新增广告失败:" + e)
        AjaxResp.getReturn(RespCode.BAN_ERROR_ADD, "")
      }
    }
  }

  //构建实体
  private def build(request: HttpServletRequest, state: Boolean): TblAppBannerInf = {
    val brhNo: String = request.getParameter("brhNo")
    val name: String = request.getParameter("name")
    val about: String = request.getParameter("about")
    val fileId: String = request.getParameter("fileId")
    //    val flag: String = request.getParameter("flag")
    val linkType: String = request.getParameter("linkType")
    val linkUrl: String = request.getParameter("linkUrl")
    //    val state: String = request.getParameter("state")
    val seq: String = request.getParameter("seq")
    val res1: String = request.getParameter("res1")
    val res2: String = request.getParameter("res2")
    val lastTime: String = DateTime4J.getCurrentDateTime

    val s: Array[Array[String]] = Array(Array(name, "1", "50"), Array(linkType, "1", "2"), Array(lastTime, "1", "14"))
    if (!Validate4J.checkStrArrLen(s)) {
      println("参数长度或格式不正确")
      return null
    }
    val tblBannerInf: TblAppBannerInf = new TblAppBannerInf
    if (!state) {
      val state1: String = request.getParameter("state")
      tblBannerInf.setState(state1)
    } else {
      tblBannerInf.setState("0")
    }
    tblBannerInf.setAbout(about)
    if (fileId != null) {
      tblBannerInf.setFileId(fileId.toInt)
    }
    val tblsId: TblAppBannerInfId=new TblAppBannerInfId
    tblsId.setBrhNo(brhNo)
    val banId: Int = bannerService.maxId
    tblsId.setBanId(banId)
    tblBannerInf.setId(tblsId)

    tblBannerInf.setLastTime(lastTime)
    tblBannerInf.setLinkType(linkType)
    tblBannerInf.setLinkUrl(linkUrl)
    tblBannerInf.setName(name)
    tblBannerInf.setRes1(res1)
    tblBannerInf.setRes2(res2)
    tblBannerInf.setSeq(seq)
    tblBannerInf
  }


  @RequestMapping(value = Array("upd.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "广告信息修改")
  def upd(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId, FuncCode.BAN_FUNC_MAINTAIN)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    val brhNo: String = request.getParameter("brhNo")
    val banId: String = request.getParameter("banId")
    val name: String = request.getParameter("name")
    val about: String = request.getParameter("about")
    val seq: String = request.getParameter("seq")
    val state: Boolean = false
    if(seq.length>2){
      return AjaxResp.getReturn(RespCode.BAN_SEQ_LENGTH_ERROR, "")
    }
//    if(name.length>50){
//      return AjaxResp.getReturn(RespCode.ERROR_CODE, "","名称字数长度应少于50")
//    }
//    if(about.length>250){
//      return AjaxResp.getReturn(RespCode.ERROR_CODE, "","中文简介的长度应少于250")
//    }

    val tabId:TblAppBannerInfId = new TblAppBannerInfId
    tabId.setBanId(banId.toInt)
    tabId.setBrhNo(brhNo)
    val tblBannerInf: TblAppBannerInf = bannerService.getById(tabId)
    if(!(tblBannerInf.getSeq.equals(seq))){
      val fag: Boolean = bannerService.selSeq(seq)//没有返回true，有返回false
      if (!fag) {
        return AjaxResp.getReturn(RespCode.ERROR_CODE,"","该顺序已存在")
      }
    }
    tblBannerInf.setName(name)
    tblBannerInf.setAbout(about)
    tblBannerInf.setSeq(seq)

    if (tblBannerInf == null) {
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    try {
      val multipartRequest: MultipartHttpServletRequest = request.asInstanceOf[MultipartHttpServletRequest]
      val file: MultipartFile = multipartRequest.getFile("file")
      var ext: String = null
      val fileName: String = file.getOriginalFilename
      if (!(fileName.equals(""))) {
        // 获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
        ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)
        //验证文件格式是否正确
        ext = ext.toLowerCase
        val respMsg: String = this.vali(ext)
        if (!("".equals(respMsg))) {
          return AjaxResp.getReturn(RespCode.PIC_PNG_JPG_ERROR, "")
        }
      }
      if (tblBannerInf == null) {
        return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
      }
      if(!(fileName.equals(""))){
        bannerService.update(tblBannerInf, "jpg", file, ext)
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
      }
      else{
        bannerService.update(tblBannerInf, null, null, null)
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
      }
    }
    catch {
      case e: Exception => {
        return AjaxResp.getReturn(RespCode.BAN_ERROR_UPD, "")
      }
    }
  }


  @RequestMapping(value = Array("delete.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "广告删除")
  def del(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId, FuncCode.BAN_FUNC_MAINTAIN)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    val brhNo: String = request.getParameter("brhNo")
    val banId: String = request.getParameter("banId")
    try {
      val tblbId:TblAppBannerInfId=new TblAppBannerInfId
      tblbId.setBanId(banId.toInt)
      tblbId.setBrhNo(brhNo)
      val tdd: TblAppBannerInf = bannerService.getById(tblbId)
      if (tdd.getState.equals("0")) {
        return AjaxResp.getReturn(RespCode.BAN_STATE_OPERATE_FALL, "")
      }
      bannerService.delById(tblbId)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        AjaxResp.getReturn(RespCode.BAN_ERROR_DEL, "")
      }
    }
  }

  @RequestMapping(value = Array("saveOrUpd.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "状态修改")
  def saveOrUpd(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId, FuncCode.BAN_FUNC_MAINTAIN)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    try {
      val brhNo: String = request.getParameter("brhNo")
      val banId: String = request.getParameter("banId")
      val state: String = request.getParameter("state")
      val tblbId:TblAppBannerInfId=new TblAppBannerInfId
      tblbId.setBanId(banId.toInt)
      tblbId.setBrhNo(brhNo)
      val tblBannerInf: TblAppBannerInf = bannerService.getById(tblbId)
      tblBannerInf.setState(state)
      bannerService.update(tblBannerInf, null, null, null)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        AjaxResp.getReturn(RespCode.STATUS_ERROR, "")
      }
    }
  }

  private def vali(ext: String): String = {
    var respMsg: String = ""
    if (("jpg" == ext) || ("png" == ext)) {
    }
    else {
      respMsg = "图片格式不正确"
    }
    respMsg
  }
}
