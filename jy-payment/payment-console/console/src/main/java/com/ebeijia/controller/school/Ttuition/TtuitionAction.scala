package com.ebeijia.controller.school.Ttuition

import java.io.File
import java.util._
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.ttuition.Ttuition
import com.ebeijia.module.ttuition.service.TtuitionService
import com.ebeijia.util.core._
import org.ebeijia.tools.{DateTime4J, Validate4J}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}
import org.springframework.web.multipart.{MultipartFile, MultipartHttpServletRequest}

/**
  * TtuitionAction
  * 学生缴费管理
  * @author xiong.wang
  *         16/6/17
  */
@Controller
@RequestMapping(value = Array("/school/schc"))
class TtuitionAction extends BaseValidRoleFunc{
  private val logger: Logger = LoggerFactory.getLogger(classOf[TtuitionAction])
  @Autowired
  private val ttuitionService: TtuitionService = null

  @RequestMapping(value = Array("/queryList.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def queryList(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),FuncCode.STU_FUNC_MAINTAIN)
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    val studentName: String = request.getParameter("studentName")
    val payState: String = request.getParameter("payState")
    val pageData: String = request.getParameter("aoData")
    var map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: Map[String, AnyRef] = ttuitionService.findBySql(studentName,payState,pageData)
      //多条连接 添加key
      val list: List[AnyRef] = new LinkedList[AnyRef]
      val m:AnyRef=mapTmp.get("list")
      val lists:List[_]=m.asInstanceOf[List[_]]
      val it: Iterator[_] = lists.iterator
      while (it.hasNext) {
        val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
        val o: Ttuition = it.next.asInstanceOf[Ttuition]
        hashMap.put("id", o.getId.toString)
        hashMap.put("accommodation", o.getAccommodation.toString)
        hashMap.put("createAt", o.getCreateAt)
        hashMap.put("openId", o.getOpenId)
        hashMap.put("payStatus", o.getPayStatus.toString)
        hashMap.put("Reserve1", o.getReserve1)
        hashMap.put("Reserve2", o.getReserve2)
        hashMap.put("Reserve3", o.getReserve3)
        hashMap.put("studentId", o.getStudentId)
        hashMap.put("studentName", o.getStudentName)
        hashMap.put("tuition", o.getTuition.toString)
        list.add(hashMap)
      }
      map.put("schList", list)
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      map = AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
      map
    }
    catch {
      case e: Exception => {
        logger.info("学生信息查询失败:"+e)
        AjaxResp.getReturn(RespCode.STU_ERROR_SEL,"")
      }
    }
  }

  @RequestMapping(value = Array("/querySch.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def queryDep(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val id: String = request.getParameter("id")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),FuncCode.STU_FUNC_MAINTAIN)
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
//    var copyId :Int=0
//    if(null!=id && !("".equals(id))){
//      copyId= id.toInt
//    }
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val tbt: Ttuition = ttuitionService.getById(id.toInt)
      map.put("schList", tbt)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("学生详情查询失败:"+e)
        AjaxResp.getReturn(RespCode.STU_ERROR_SEL,"")
      }
    }
  }




  @RequestMapping(value = Array("add.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "学生添加")
  @ResponseBody def add(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    //判断是否有权限
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val id: String = request.getParameter("id")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),FuncCode.STU_FUNC_ADDITION)
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    val state:Boolean = true
    val tnurseryTuition: Ttuition = this.build(request,state)
    if (tnurseryTuition == null) {
      return  AjaxResp.getReturn(RespCode.ERROR_CODE,"")
    }
    try {
      ttuitionService.save(tnurseryTuition)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        logger.info("学生新增失败:"+e)
        AjaxResp.getReturn(RespCode.STU_ERROR_ADD,"")
      }
    }
  }

  private def build(request: HttpServletRequest,state:Boolean): Ttuition = {
    val accommodation: String = request.getParameter("accommodation")
    val openId: String = request.getParameter("openId")
    val payStatus: String = request.getParameter("payStatus")
    val reserve1: String = request.getParameter("reserve1")
    val reserve2: String = request.getParameter("reserve2")
    val reserve3: String = request.getParameter("reserve3")
    val studentId: String = request.getParameter("studentId")
    val studentName: String = request.getParameter("studentName")
    val tuition: String = request.getParameter("tuition")
    val s: Array[Array[String]] = Array(Array(studentId, "1", "5"),Array(studentName, "2", "8"),Array(tuition, "1", "6")
      ,Array(accommodation, "1", "5"),Array(reserve1, "1", "50"))
    if (!Validate4J.checkStrArrLen(s)) {
      println("参数长度或格式不正确")
      AjaxResp.getReturn(RespCode.PARAM_ERROR,"")
      return null
    }
    val tnurseryTuition:Ttuition = new Ttuition
    tnurseryTuition.setAccommodation(accommodation.toDouble)
    tnurseryTuition.setCreateAt(DateTime4J.getCurrentDateTime)
    tnurseryTuition.setOpenId(openId)
    tnurseryTuition.setPayStatus(payStatus.toInt)
    tnurseryTuition.setReserve1(reserve1)
    tnurseryTuition.setReserve2(reserve2)
    tnurseryTuition.setReserve3(reserve3)
    tnurseryTuition.setStudentId(studentId)
    tnurseryTuition.setStudentName(studentName)
    tnurseryTuition.setTuition(tuition.toDouble)
    tnurseryTuition
  }


  @RequestMapping(value = Array("upd.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "学生信息修改")
  def upd(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    //判断是否有权限
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),FuncCode.STU_FUNC_MAINTAIN)
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    val id: String = request.getParameter("id")
    val accommodation: String = request.getParameter("accommodation")
    val openId: String = request.getParameter("openId")
    val payStatus: String = request.getParameter("payStatus")
    val reserve1: String = request.getParameter("reserve1")
    val reserve2: String = request.getParameter("reserve2")
    val reserve3: String = request.getParameter("reserve3")
    val studentId: String = request.getParameter("studentId")
    val studentName: String = request.getParameter("studentName")
    val tuition: String = request.getParameter("tuition")
    val tnurseryTuition:Ttuition = ttuitionService.getById(id.toInt)
    tnurseryTuition.setAccommodation(accommodation.toDouble)
    tnurseryTuition.setCreateAt(DateTime4J.getCurrentDateTime)
    tnurseryTuition.setOpenId(openId)
    tnurseryTuition.setPayStatus(payStatus.toInt)
    tnurseryTuition.setReserve1(reserve1)
    tnurseryTuition.setReserve2(reserve2)
    tnurseryTuition.setReserve3(reserve3)
    tnurseryTuition.setStudentId(studentId)
    tnurseryTuition.setStudentName(studentName)
    tnurseryTuition.setTuition(tuition.toDouble)
    try {
      ttuitionService.update(tnurseryTuition)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        AjaxResp.getReturn(RespCode.STU_ERROR_UPD,"")
      }
    }
  }


  @RequestMapping(value = Array("delete.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "学生删除")
  def del(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    //判断是否有权限
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),FuncCode.STU_FUNC_MAINTAIN)
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    val id: String = request.getParameter("id")
    try {
      ttuitionService.delById(id.toInt)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        AjaxResp.getReturn(RespCode.STU_ERROR_DEL,"")
      }
    }
  }

  @RequestMapping(value = Array("saveOrUpd.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "状态修改")
  def saveOrUpd(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    //判断是否有权限
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),"100051")
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    try {
      val id:String = request.getParameter("id")
      val state:String = request.getParameter("payState")
      val tnurseryTuition:Ttuition = ttuitionService.getById(id.toInt)
      tnurseryTuition.setPayStatus(state.toInt)
      ttuitionService.update(tnurseryTuition)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        AjaxResp.getReturn(RespCode.STATUS_ERROR,"")
      }
    }
  }


  @RequestMapping(value = Array("export.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "学生信息导入")
  def export(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, request.getParameter("roleId"), "100051")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    try {
      val multipartRequest: MultipartHttpServletRequest = request.asInstanceOf[MultipartHttpServletRequest]
      val file: MultipartFile = multipartRequest.getFile("schExcelPath")
      var ext: String = null
      if (file == null) {
        return AjaxResp.getReturn(RespCode.ERROR_CODE,"")
      }
      // 获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
      val fileName: String = file.getOriginalFilename
      //验证时统一转成大写
      ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)
      //验证文件格式是否正确
      ext = ext.toLowerCase
      val respMsg: String = this.valiExcel(ext)
      if (!("".equals(respMsg))) {
        logger.info("验证表格文件格式：" + respMsg)
        return AjaxResp.getReturn(RespCode.FILE_TYPE_ERROR, "")
      }

      val upload: UpLoad = new UpLoad
      //上传文件本地到服务器
      val typeExcel = "excel"
      val fileExcel: File = upload.getFile(file, SystemProperties.getProperties("excel.page"), typeExcel, ext)
      val schExcelPath: String = SystemProperties.getProperties("excel.page.url") + SystemProperties.getProperties("excel.page") + "/" + typeExcel + "/" + fileExcel.getName
      logger.info("表格文件路径：" + schExcelPath)
      val execl: DowloadExecl = new DowloadExecl
      val lists: List[Ttuition] = execl.readXls1(schExcelPath)
      if (lists == null) {
        return AjaxResp.getReturn(RespCode.ERROR_CODE,"")
      }
      if (lists.size() == 0) {
        return AjaxResp.getReturn(RespCode.ERROR_CODE,"")
      }
//      for (sch <- lists {
//
//      }
      ttuitionService.saveExcel(lists)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    } catch {
      case e: Exception => {
        logger.info("学生信息导入失败:" + e)
        AjaxResp.getReturn(RespCode.STU_ERROR_EXP,"")
      }
    }
  }

  private def valiExcel(ext: String): String = {
    var respMsg: String = ""
    if (("xls" == ext) || ("xlsx" == ext)) {
    }
    else {
      respMsg = "Excel表格格式不正确"
    }
    respMsg
  }
}

