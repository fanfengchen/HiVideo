package com.ebeijia.controller.app.dep

import java.util._
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.app.ban.{TblAppBannerInf, TblAppBannerInfId}
import com.ebeijia.entity.app.dep.{TblAppDeployDocumentId, TblAppDeployDocument}
import com.ebeijia.entity.system.system.TblSysPicInf
import com.ebeijia.module.app.service.dep.AppDeployDocumentService
import com.ebeijia.module.system.service.picInf.SysPicInfService
import com.ebeijia.util.core.{FuncCode, RespCode}
import org.ebeijia.tools.{DateTime4J, Validate4J}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}
import org.springframework.web.multipart.{MultipartFile, MultipartHttpServletRequest}

/**
  * DeployDocumentAction
  * 文章管理
  * @author xiong.wang
  *  16/6/17
  */
@Controller
@RequestMapping(value = Array("/app/dep"))
class AppDeployDocumentAction extends BaseValidRoleFunc {

  private val logger: Logger = LoggerFactory.getLogger(classOf[AppDeployDocumentAction])
  @Autowired
  private val deployDocumentService: AppDeployDocumentService = null
  @Autowired
  private val picInfService: SysPicInfService = null

  @RequestMapping(value = Array("/queryList.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def queryList(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId,FuncCode.DEP_FUNC_MAINTAIN)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    //查询入参
    val docTitle: String = request.getParameter("docTitle")
    val docType: String = request.getParameter("docType")
    val state: String = request.getParameter("state")
    val pageData: String = request.getParameter("aoData")
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: Map[String, AnyRef] = deployDocumentService.findBySql(docTitle, docType, state, pageData)
      //多条连接 添加key
      val list: List[AnyRef] = new LinkedList[AnyRef]
      val m: AnyRef = mapTmp.get("list")
      val lists: List[_] = m.asInstanceOf[List[_]]
      val it: Iterator[_] = lists.iterator
      while (it.hasNext) {
        val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
        val o: TblAppDeployDocument = it.next.asInstanceOf[TblAppDeployDocument]
        hashMap.put("artId", o.getId.getArtId.toString)
        hashMap.put("deployAgent", o.getDeployAgent.toString)
        hashMap.put("deployTime", o.getDeployTime)
        hashMap.put("visitCount", o.getVisitCount.toString)
        hashMap.put("state", o.getState)
        hashMap.put("flag", o.getFlag)
        hashMap.put("docType", o.getDocType.toString)
        hashMap.put("docTitle", o.getDocTitle)
        hashMap.put("docContent", o.getDocContent)
        hashMap.put("docDesc", o.getDocDesc)
        hashMap.put("res1", o.getRes1)
        hashMap.put("res2", o.getRes2)
        hashMap.put("docImgId", o.getDocImg.toString)
        val docId: Int = o.getDocImg
        val tblPicInf: TblSysPicInf = picInfService.queryPicInfById(docId)
        if (tblPicInf != null) {
          if("6".equals(o.getFlag))
          {
            hashMap.put("docImg","")
          }
          else{
             hashMap.put("docImg", tblPicInf.getPicUrl)

          }
        } else {
          hashMap.put("docImg", "")
        }

        hashMap.put("docFlag", o.getDocFlag)
        hashMap.put("docAuthor", o.getDocAuthor)
        list.add(hashMap)
      }
      //设置分页
      map.put("depList", list)
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("文章查询失败:" + e)
        AjaxResp.getReturn(RespCode.DEP_ERROR_SEL, "")
      }
    }
  }

  @RequestMapping(value = Array("/queryDep.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def queryDep(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId, FuncCode.DEP_FUNC_MAINTAIN)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    val artId: String = request.getParameter("artId")
    val brhNo: String = request.getParameter("brhNo")
    var copyId: Int = 0
    if (null != artId && !("".equals(artId))) {
      copyId = artId.toInt
    }
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val tblaId:TblAppDeployDocumentId=new TblAppDeployDocumentId
      tblaId.setArtId(copyId)
      tblaId.setBrhNo(brhNo)
      val tdd: TblAppDeployDocument = deployDocumentService.getById(tblaId)
      map.put("depList", tdd)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("文章详情查询失败:" + e)
        AjaxResp.getReturn(RespCode.DEP_ERROR_SEL, "")
      }
    }
  }


  @RequestMapping(value = Array("add.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "文章添加")
  @ResponseBody def add(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    //  val id: String = request.getParameter("id")
    val msg: String = this.validRoleFunc(usrId, token, roleId, FuncCode.DEP_FUNC_ADDITION)
    if (msg != null) {
      if (msg.equals("tokenlose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    val state: Boolean = true
    val tblDeployDocument: TblAppDeployDocument = this.build(request, state)
//    if(tblDeployDocument.getDocContent.length>3000){
//      return AjaxResp.getReturn(RespCode.ERROR_CODE,"","超过字数上限，应控制在3000以内")
//    }
    if (tblDeployDocument == null) {
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"")
    }
    try {
      val multipartRequest: MultipartHttpServletRequest = request.asInstanceOf[MultipartHttpServletRequest]
      val file: MultipartFile = multipartRequest.getFile("file")
      var ext: String = null
      val fileName: String = file.getOriginalFilename
      if (!(fileName.equals(""))) {
        // 获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
        val fileName: String = file.getOriginalFilename
        //验证时统一转成大写
        ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)
        //验证文件格式是否正确
        ext = ext.toLowerCase
        val respMsg: String = this.vali(ext)
        if (!("".equals(respMsg))) {
          return AjaxResp.getReturn(RespCode.PIC_PNG_JPG_ERROR, "")
        }
      }
      if (tblDeployDocument.getDocFlag.equals("0") || tblDeployDocument.getDocFlag.isEmpty || tblDeployDocument.getDocFlag == null) {
        deployDocumentService.save(tblDeployDocument, "jpg", file, ext)

      } else {
        val ls: TblAppDeployDocument = deployDocumentService.selByDocFlag(tblDeployDocument.getDocFlag)//没有返回null
        if (ls == null) {
          deployDocumentService.save(tblDeployDocument, "jpg", file, ext)

        } else {
          tblDeployDocument.setId(ls.getId)
          deployDocumentService.update(tblDeployDocument, "jpg", file, ext)

        }
      }
      AjaxResp.getReturn(RespCode.SUCCESS_CODE,"")
    }
    catch {
      case e: Exception => {
        logger.info("新增文章失败:" + e)
        AjaxResp.getReturn(RespCode.DEP_ERROR_ADD, "")
      }
    }
  }

  //构建实体
  private def build(request: HttpServletRequest, state: Boolean): TblAppDeployDocument = {
    val brhNo: String = request.getParameter("brhNo")
    val deployAgent: String = request.getParameter("deployAgent")
    val deployTime: String = DateTime4J.getCurrentDateTime
    val visitCount: String = request.getParameter("visitCount")
    val docAuthor: String = request.getParameter("docAuthor")
    val flag: String = request.getParameter("flag")
    val docType: String = request.getParameter("docType")
    val docTitle: String = request.getParameter("docTitle")
    val docContent: String = request.getParameter("docContent")
    val docDesc: String = request.getParameter("docDesc")
    val res1: String = request.getParameter("res1")
    val res2: String = request.getParameter("res2")
    val docFlag: String = request.getParameter("docFlag")
    val s: Array[Array[String]] = Array(Array(deployAgent, "1", "5"),
      Array(flag, "1", "1"), Array(docType, "1", "11"))
    if (!Validate4J.checkStrArrLen(s)) {
      println("参数长度或格式不正确")
      AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
      return null
    }
    val tblDeployDocument: TblAppDeployDocument = new TblAppDeployDocument
    if (!state) {
      val state1: String = request.getParameter("state")
      tblDeployDocument.setState(state1)
    } else {
      tblDeployDocument.setState("0")
    }
    val tblsId: TblAppDeployDocumentId=new TblAppDeployDocumentId
    tblsId.setBrhNo(brhNo)
    val artId: Int = deployDocumentService.maxId
    tblsId.setArtId(artId)
    tblDeployDocument.setId(tblsId)
    tblDeployDocument.setDeployAgent(deployAgent)
    tblDeployDocument.setDeployTime(deployTime)
    tblDeployDocument.setDocAuthor(docAuthor)
    tblDeployDocument.setDocContent(docContent)
    tblDeployDocument.setDocDesc(docDesc)
    tblDeployDocument.setDocType(docType.toInt)

    if((docFlag !=null) && !("".equals(docFlag))){
      tblDeployDocument.setDocTitle("")
    }
    else{
      tblDeployDocument.setDocTitle(docTitle)
    }

    if (visitCount != null && !visitCount.equals("")) {
      tblDeployDocument.setVisitCount(visitCount.toInt)
    }
    tblDeployDocument.setFlag(flag)
    tblDeployDocument.setRes1(res1)
    tblDeployDocument.setRes2(res2)
    tblDeployDocument.setDocFlag(docFlag)
    tblDeployDocument
  }


  @RequestMapping(value = Array("upd.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "文章信息修改")
  def upd(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId, FuncCode.DEP_FUNC_MAINTAIN)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    val state: Boolean = true
    val artId: String = request.getParameter("artId")
    val brhNo: String = request.getParameter("brhNo")
    val tabId:TblAppDeployDocumentId = new TblAppDeployDocumentId
    tabId.setArtId(artId.toInt)
    tabId.setBrhNo(brhNo)
    val tblDeployDocument: TblAppDeployDocument = deployDocumentService.getById(tabId)
    val deployAgent: String = request.getParameter("deployAgent")
    val deployTime: String = DateTime4J.getCurrentDateTime
    val visitCount: String = request.getParameter("visitCount")
    val docAuthor: String = request.getParameter("docAuthor")
    val state1: String = request.getParameter("state")
    val flag: String = request.getParameter("flag")
    val docType: String = request.getParameter("docType")
    val docTitle: String = request.getParameter("docTitle")
    val docContent: String = request.getParameter("docContent")
    val docDesc: String = request.getParameter("docDesc")
    val res1: String = request.getParameter("res1")
    val res2: String = request.getParameter("res2")
    val docFlag: String = request.getParameter("docFlag")
    if(("".equals(docContent)) && (docContent == null)){
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"", "文章内容不能为空")
    }
//    if(docContent.length>3000){
//      return AjaxResp.getReturn(RespCode.ERROR_CODE,"","超过字数上限，应控制在3000以内")
//    }
    tblDeployDocument.setDeployAgent(deployAgent)
    tblDeployDocument.setDeployTime(deployTime)
    tblDeployDocument.setDocAuthor(docAuthor)
    tblDeployDocument.setDocContent(docContent)
    tblDeployDocument.setDocDesc(docDesc)
    //    tblDeployDocument.setDocImg(docImg.asInstanceOf[Integer])
    tblDeployDocument.setDocType(docType.toInt)
    tblDeployDocument.setDocTitle(docTitle)
    if (visitCount != null && !visitCount.equals("")) {
      tblDeployDocument.setVisitCount(visitCount.toInt)
    }
    tblDeployDocument.setState(state1)
    tblDeployDocument.setFlag(flag)
    tblDeployDocument.setRes1(res1)
    tblDeployDocument.setRes2(res2)
    tblDeployDocument.setDocFlag(docFlag)

    // 转型为MultipartHttpRequest
    val multipartRequest: MultipartHttpServletRequest = request.asInstanceOf[MultipartHttpServletRequest]
    val file: MultipartFile = multipartRequest.getFile("file") // 获得文件
    var ext: String = null
    if ( file.getOriginalFilename!="") {
      // 获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
      val fileName: String = file.getOriginalFilename // 获得文件名
      //验证时统一转成大写
      ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)//获得扩展名
      //验证文件格式是否正确
      ext = ext.toLowerCase
      val respMsg: String = this.vali(ext)
      if(fileName!=null &&(!"".equals(fileName))) {
        if (!("".equals(respMsg))) {
          return AjaxResp.getReturn(RespCode.PIC_PNG_JPG_ERROR, "")
        }
      }
    }
    if (tblDeployDocument == null) {
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }

    try {
      val ls: TblAppDeployDocument = deployDocumentService.selByDocFlag(tblDeployDocument.getDocFlag)
      if (ls != null && (docFlag != null && !docFlag.equals(""))) {//数据库有flag，再传进flag，
         if( file.getOriginalFilename!="") {
           deployDocumentService.delById(tabId)
           tblDeployDocument.setId(ls.getId)
           deployDocumentService.update(tblDeployDocument, "jpg", file, ext)
           AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
         }
        else{
           deployDocumentService.delById(tabId)
           tblDeployDocument.setId(ls.getId)
           deployDocumentService.update(tblDeployDocument, null, null, null)
           AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
         }
      }
      else {
        //有,再传进来更新
        if ( file.getOriginalFilename != "") {
          deployDocumentService.update(tblDeployDocument, "jpg", file, ext)
          AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
        }
        else {
          deployDocumentService.update(tblDeployDocument, null, null, null)
          AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
        }
      }
    }
    catch {
      case e: Exception => {
        AjaxResp.getReturn(RespCode.DEP_ERROR_UPD, "")
      }
    }
  }

  @RequestMapping(value = Array("del.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "文章删除")
  def del(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token,roleId, FuncCode.DEP_FUNC_MAINTAIN)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
   val artId: String = request.getParameter("artId")
    val brhNo: String = request.getParameter("brhNo")
    try {
      val tabId:TblAppDeployDocumentId = new TblAppDeployDocumentId
      tabId.setArtId(artId.toInt)
      tabId.setBrhNo(brhNo)
     val tdd: TblAppDeployDocument = deployDocumentService.getById(tabId)
      if (tdd.getState.equals("0")) {
        return AjaxResp.getReturn(RespCode.DEP_STATE_OPERATE_FAIL,"")
      }
      deployDocumentService.delById(tabId)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        AjaxResp.getReturn(RespCode.DEP_ERROR_DEL,"")
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
      val artId: String = request.getParameter("artId")
      val state: String = request.getParameter("state")
      val tblbId:TblAppDeployDocumentId=new TblAppDeployDocumentId
      tblbId.setArtId(artId.toInt)
      tblbId.setBrhNo(brhNo)
      val tblAppDeployDocument: TblAppDeployDocument = deployDocumentService.getById(tblbId)
      tblAppDeployDocument.setState(state)
      deployDocumentService.update(tblAppDeployDocument, null, null, null)
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
