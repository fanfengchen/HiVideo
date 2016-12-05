package com.ebeijia.controller.wechat.notice

import java.util._
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.wechat.notice.TNoticeT
import com.ebeijia.module.wechat.service.notice.TNoticeService
import com.ebeijia.util.core.{FuncCode, RespCode}
import org.ebeijia.tools.{DateTime4J, Validate4J}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

@Controller
@RequestMapping(value = Array("/wechat/notice"))
class NoticeAction extends BaseValidRoleFunc{
  private val logger: Logger = LoggerFactory.getLogger(classOf[NoticeAction])
  @Autowired
  private val tNoticeService: TNoticeService=null

  /**
    * 查询通知列表
    * @param session HttpSession
    * @param request HttpServletRequest
    * @return
    */
  @RequestMapping(value = Array("/queryList.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def queryList(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),FuncCode.WECHAT_FUNC_NOTICE)
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "token失效", "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, msg, "")
    }
    val title: String = request.getParameter("title")
    val id: String = request.getParameter("id")
    val aoData: String = request.getParameter("aoData")
    logger.info("==title=="+title+"==id=="+id+"==aoData=="+aoData)
    try {
      var map: Map[String, AnyRef] = new HashMap[String, AnyRef]
      val mapTmp: Map[String, AnyRef] = tNoticeService.findBySql(title,id,aoData)
      //多条连接 添加key
      val list: List[AnyRef] = new LinkedList[AnyRef]
      val m:AnyRef=mapTmp.get("list")
      val lists:List[_]=m.asInstanceOf[List[_]]
      val it: Iterator[_] = lists.iterator
      while (it.hasNext) {
        val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
        val o: TNoticeT = it.next.asInstanceOf[TNoticeT]
        hashMap.put("id", o.getId.toString)
        hashMap.put("title", o.getTitle)
        hashMap.put("subTitle", o.getSubTitle)
        hashMap.put("content", o.getContent)
        hashMap.put("unit", o.getUnit)
        if (o.getBeginTime!=null && !"".equals(o.getBeginTime.toString)){
          hashMap.put("beginTime", DateTime4J.longFormatDate(o.getBeginTime))//开始时间
        }else{
          hashMap.put("beginTime", ""+o.getBeginTime)
        }
        if (o.getEndTime!=null && !"".equals(o.getEndTime.toString)){
          hashMap.put("endTime", DateTime4J.longFormatDate(o.getEndTime))//结束时间
        }else{
          hashMap.put("endTime", ""+o.getEndTime)
        }
        hashMap.put("createTime", DateTime4J.longFormatDate(o.getCreateTime))
        hashMap.put("_isActive", o.get_IsActive)
        list.add(hashMap)
      }
      logger.info("查询公告通知:"+list)
      map.put("banList", list)
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }catch {
      case e: Exception => {
        logger.info("公告通知查询失败:"+e)
        AjaxResp.getReturn(RespCode.WECHAT_NOTICE_SEL_ERROR, "")
      }
    }

  }

  /**
    * 根据id查询通知
    * @param session HttpSession
    * @param request HttpServletRequest
    * @return
    */
  @RequestMapping(value = Array("find.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "根据id查询通知")
  def find(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    //判断是否有权限
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),FuncCode.WECHAT_FUNC_NOTICE)
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "token失效", "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, msg, "")
    }
    try {
      val id: Int = Integer.parseInt(request.getParameter("id"))
      val tNotice:TNoticeT = tNoticeService.getById(id.toInt)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE,tNotice)
    }
    catch {
      case e: Exception => {
        AjaxResp.getReturn(RespCode.WECHAT_NOTICE_SEL_ERROR, "")
      }
    }
  }

  /**
    * 新增通知公告
    * @param session
    * @param request
    * @return
    */
  @RequestMapping(value = Array("add.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "通知添加")
  @ResponseBody
  def addNotice(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),FuncCode.WECHAT_FUNC_NOTICE)
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "token失效", "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, msg, "")
    }
    val tNotice: TNoticeT = this.build(request)
    if (tNotice == null) {
      return  AjaxResp.getReturn(RespCode.ERROR_CODE, "参数长度或格式不正确", "")
    }
    try {
      tNoticeService.save(tNotice)
      logger.info("新增公告通知成功")
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "", "通知公告新增成功")
    }catch {
      case e: Exception => {
        logger.info("新增公告通知失败:"+e)
        AjaxResp.getReturn(RespCode.WECHAT_NOTICE_ADD_ERROR, "")
      }
    }

  }

  private def build(request: HttpServletRequest): TNoticeT = {
    val title: String = request.getParameter("title")
    val subTitle: String = request.getParameter("subTitle")
    val content: String = request.getParameter("content")
    val unit: String = request.getParameter("unit")
    val beginTime: String = request.getParameter("beginTime")//yyyyMMdd
    val endTime: String = request.getParameter("endTime")
//    val _isActive: String = request.getParameter("_isActive")
    val s: Array[Array[String]] = Array(Array(title, "1", "50"),Array(subTitle, "0", "100"),Array(content, "0", "1000"),Array(unit, "0", "100"))
    if (!Validate4J.checkStrArrLen(s)) {
      println("参数长度或格式不正确")
      AjaxResp.getReturn(RespCode.ERROR_CODE, "参数长度或格式不正确", "")
      return null
    }
    val tNotice:TNoticeT = new TNoticeT
    tNotice.setTitle(title)
    tNotice.setSubTitle(subTitle)
    tNotice.setContent(content)
    tNotice.setUnit(unit)
    tNotice.setBeginTime(DateTime4J.formatParseDateString(beginTime).getTime)
    tNotice.setEndTime(DateTime4J.formatParseDateString(endTime).getTime)
    tNotice.setCreateTime(System.currentTimeMillis())
    tNotice.set_IsActive(""+0);
    tNotice
  }

  /**
    * 公告和通知删除
    * @param session
    * @param request
    * @return
    */
  @RequestMapping(value = Array("delete.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "通知公告删除")
  def del(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    //判断是否有权限
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),FuncCode.WECHAT_FUNC_NOTICE)
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "token失效", "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, msg, "")
    }
    val id: String = request.getParameter("id")
    try {
      tNoticeService.delById(id.toInt)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "", "通知公告删除成功")
    }
    catch {
      case e: Exception => {
        AjaxResp.getReturn(RespCode.WECHAT_NOTICE_DEL_ERROR, "")
      }
    }
  }

  /**
    * 修改公告通知
    * @param session
    * @param request
    * @return
    */
  @RequestMapping(value = Array("saveOrUpd.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "公告通知修改")
  def saveOrUpd(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    //判断是否有权限
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),FuncCode.WECHAT_FUNC_NOTICE)
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "token失效", "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, msg, "")
    }
    try {
      val id:String = request.getParameter("id")
      val title: String = request.getParameter("title")
      val subTitle: String = request.getParameter("subTitle")
      val content: String = request.getParameter("content")
      val unit: String = request.getParameter("unit")
      val beginTime: String = request.getParameter("beginTime")//yyyyMMdd
      val endTime: String = request.getParameter("endTime")
//      val _isActive: String = request.getParameter("_isActive")
      val s: Array[Array[String]] = Array(Array(title, "1", "50"),Array(subTitle, "0", "100"),Array(content, "0", "1000"),Array(unit, "0", "100"))
      if (!Validate4J.checkStrArrLen(s)) {
        println("参数长度或格式不正确")
        return AjaxResp.getReturn(RespCode.ERROR_CODE, "参数长度或格式不正确", "")
      }
      val tNotice:TNoticeT = tNoticeService.getById(id.toInt)
      tNotice.setTitle(title)
      tNotice.setSubTitle(subTitle)
      tNotice.setContent(content)
      tNotice.setUnit(unit)
      tNotice.setBeginTime(DateTime4J.formatParseDateString(beginTime).getTime)
      tNotice.setEndTime(DateTime4J.formatParseDateString(endTime).getTime)
      tNotice.set_IsActive(""+0)//默认
      tNoticeService.update(tNotice)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "", "通知公告内容修改成功")
    }
    catch {
      case e: Exception => {
        AjaxResp.getReturn(RespCode.WECHAT_NOTICE_UPD_ERROR, "")
      }
    }
  }
}
