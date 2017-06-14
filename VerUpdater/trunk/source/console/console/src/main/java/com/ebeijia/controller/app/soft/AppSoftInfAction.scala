package com.ebeijia.controller.app.soft

import java.util._
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.app.soft.{TblAppSoftVerInfId, TblAppSoftVerInf}
import com.ebeijia.module.app.service.soft.SoftVerInfService
import com.ebeijia.util.core.{FuncCode, RespCode}
import org.ebeijia.tools.{DateTime4J, Validate4J}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}
import org.springframework.web.multipart.{MultipartFile, MultipartHttpServletRequest}

/**
  * SoftInfAction
  * 软件管理
  * @author xiong.wang
  *  16/6/17
 */
@Controller
@RequestMapping(value = Array("/app/soft"))
class AppSoftInfAction extends BaseValidRoleFunc {

  private val logger: Logger = LoggerFactory.getLogger(classOf[AppSoftInfAction])
  @Autowired
  private val softVerInfService: SoftVerInfService = null

  @RequestMapping(value = Array("/queryList.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def queryList(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId, FuncCode.SOFT_FUNC_MAINTAIN)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    //查询入参
    val verNo: String = request.getParameter("verNo")
    val isuTime: String = request.getParameter("isuTime")
    val pageData: String = request.getParameter("aoData")
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: Map[String, AnyRef] = softVerInfService.findBySql(verNo, isuTime, pageData)
      //多条连接 添加key
      val list: List[AnyRef] = new LinkedList[AnyRef]
      val m: AnyRef = mapTmp.get("list")
      val lists: List[_] = m.asInstanceOf[List[_]]
      val it: Iterator[_] = lists.iterator
      while (it.hasNext) {
        val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
        val o: TblAppSoftVerInf = it.next.asInstanceOf[TblAppSoftVerInf]
        hashMap.put("verId", o.getId.getVerId.toString)
        hashMap.put("type", o.getType)
        hashMap.put("verNo", o.getVerNo)
        hashMap.put("localPath", o.getLocalPath)
        hashMap.put("urlPath", o.getUrlPath)
        hashMap.put("isuTime", o.getUTime)
        hashMap.put("isForce", o.getForce)
        hashMap.put("res1", o.getRes1)
        hashMap.put("res2", o.getRes2)
        hashMap.put("lastTime", o.getLastTime)
        list.add(hashMap)
      }
      //设置分页
      map.put("softList", list)
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE,map)
    }
    catch {
      case e: Exception => {
        logger.info("软件查询失败:" + e)
        AjaxResp.getReturn(RespCode.SOFT_ERROR_SEL,"")
      }
    }
  }

  @RequestMapping(value = Array("/querySoft.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def queryDep(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val verId:String=request.getParameter("verId")
    val brhNo:String =request.getParameter("brhNo")
    var copyId: Int = 0
    if (null != verId && !("".equals(verId))) {
      copyId = verId.toInt
    }
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val tblsId: TblAppSoftVerInfId=new TblAppSoftVerInfId
      tblsId.setBrhNo(brhNo)
      tblsId.setVerId(copyId)
      val tsv: TblAppSoftVerInf = softVerInfService.getById(tblsId)
      map.put("softList", tsv)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("软件详情查询失败:" + e)
        AjaxResp.getReturn(RespCode.ERROR_CODE,"")
      }
    }
  }


  @RequestMapping(value = Array("add.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "软件添加")
  @ResponseBody def add(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId, FuncCode.SOFT_FUNC_ADDITION)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    val tblSoftVerInf: TblAppSoftVerInf = this.build(request)
    val fag: Boolean = softVerInfService.selSoft(tblSoftVerInf.getVerNo,tblSoftVerInf.getType)//查看是否存在该本版记录，没有返回true
    if (!fag) {
      return AjaxResp.getReturn(RespCode.SOFT_VERNO_OPERATE_ADD,"")
    }
    if (tblSoftVerInf == null) {
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"")
    }
    try {
      val flag: String = request.getParameter("flag")
      var ext: String = null
      if (flag.equals("0")) {
        val multipartRequest: MultipartHttpServletRequest = request.asInstanceOf[MultipartHttpServletRequest]
        val file: MultipartFile = multipartRequest.getFile("file")
        if (file != null) {
          val fileName: String = file.getOriginalFilename
          //验证时统一转成大写
          ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)
          //验证文件格式是否正确
          ext = ext.toLowerCase
          val respMsg: String = this.vali(ext)
          if (!("".equals(respMsg))) {
            return AjaxResp.getReturn(RespCode.FILE_TYPE_ERROR, "")
          }
        }
        var ty: String = null
        if (tblSoftVerInf.getType.equals("1")) {
          ty = "apk"
        } else if (tblSoftVerInf.getType.equals("2")) {
          ty = "ipa"
        }
        softVerInfService.save(tblSoftVerInf, ty, file, ty)
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
      } else {
        softVerInfService.save(tblSoftVerInf, null, null, null)
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
      }

    }
    catch {
      case e: Exception => {
        logger.info("新增软件失败:" + e)
        AjaxResp.getReturn(RespCode.SOFT_ERROR_ADD,"")
      }
    }
  }

  private def build(request: HttpServletRequest): TblAppSoftVerInf = {
    val tblSoftVerInf: TblAppSoftVerInf = new TblAppSoftVerInf
    val _type: String = request.getParameter("type")
    if (_type != null) {
      tblSoftVerInf.setType(_type)
    }
    val brhNo:String =request.getParameter("brhNo")
    val verNo: String = request.getParameter("verNo")
    val isuTime: String = DateTime4J.getCurrentDateTime
    val isForce: String = request.getParameter("isForce")
    val res1: String = request.getParameter("res1")
    val res2: String = request.getParameter("res2")
    val lastTime: String = DateTime4J.getCurrentDateTime

    val s: Array[Array[String]] = Array(Array(verNo, "1", "15"), Array(_type, "1", "2")
      , Array(isuTime, "1", "14"), Array(isForce, "1", "2"))
    if (!Validate4J.checkStrArrLen(s)) {
      println("参数长度或格式不正确")
      AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
      return null
    }
    val tblsId: TblAppSoftVerInfId=new TblAppSoftVerInfId
    tblsId.setBrhNo(brhNo)
    val artId: Int = softVerInfService.maxId
    tblSoftVerInf.setId(tblsId)
    tblSoftVerInf.setVerNo(verNo)

    tblSoftVerInf.setUTime(isuTime)
    tblSoftVerInf.setForce(isForce)
    tblSoftVerInf.setRes1(res1)
    tblSoftVerInf.setRes2(res2)
    tblSoftVerInf.setLastTime(lastTime)
    tblSoftVerInf
  }


  @RequestMapping(value = Array("upd.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "软件信息修改")
  def upd(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId, FuncCode.SOFT_FUNC_MAINTAIN)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    val brhNo:String =request.getParameter("brhNo")
    val verId: String = request.getParameter("verId")
    val verNo: String = request.getParameter("verNo")
    val isForce: String = request.getParameter("isForce")
    val _type:String = request.getParameter("type")
    val fag: Boolean = softVerInfService.selSoft(verNo,_type)//查看是否存在该本版记录，没有返回true
    if (!fag) {
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"","版本号重复")
    }
    val tblsId: TblAppSoftVerInfId=new TblAppSoftVerInfId
    tblsId.setBrhNo(brhNo)
    tblsId.setVerId(verId.toInt)
    val tblSoftVerInf: TblAppSoftVerInf = softVerInfService.getById(tblsId)
    try {
      tblSoftVerInf.setVerNo(verNo)
      tblSoftVerInf.setForce(isForce)
      tblSoftVerInf.setType(_type)
      softVerInfService.update(tblSoftVerInf)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    } catch {
      case e: Exception => {
        AjaxResp.getReturn(RespCode.SOFT_ERROR_UPD,"")
      }
    }
  }

  @RequestMapping(value = Array("del.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "软件删除")
  def del(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId, FuncCode.SOFT_FUNC_MAINTAIN)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    val brhNo:String =request.getParameter("brhNo")
    val verId: String = request.getParameter("verId")
    try {
      val tblsId: TblAppSoftVerInfId=new TblAppSoftVerInfId
      tblsId.setBrhNo(brhNo)
      tblsId.setVerId(verId.toInt)
      softVerInfService.delById(tblsId)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        AjaxResp.getReturn(RespCode.SOFT_ERROR_DEL,"")
      }
    }
  }

  private def vali(ext: String): String = {
    var respMsg: String = ""
    if (("apk" == ext) || ("ipa" == ext)) {
    }
    else {
      respMsg = "文件格式不正确"
    }
    respMsg
  }
}
