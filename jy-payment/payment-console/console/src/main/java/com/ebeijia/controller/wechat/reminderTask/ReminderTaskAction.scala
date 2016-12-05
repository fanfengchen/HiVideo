package com.ebeijia.controller.wechat.reminderTask

import java.util._
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.wechat.reminderTask.TReminderTask
import com.ebeijia.module.wechat.service.reminderTask.TReminderTaskService
import com.ebeijia.util.core.{FuncCode, RespCode}
import org.ebeijia.tools.DateTime4J
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * Created by chen on 2016/7/25.
 */
@Controller
@RequestMapping(value = Array("/wechat/reminder"))
class ReminderTaskAction extends BaseValidRoleFunc{
  private val logger: Logger = LoggerFactory.getLogger(classOf[ReminderTaskAction])

  @Autowired
  private val tReminderTaskService:TReminderTaskService = null

  /**
   * 缴费提醒查询
   * @param session
   * @param request
   * @return
   */
  @RequestMapping(value = Array("/queryList.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def queryList(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),FuncCode.WECHAT_FUNC_REMINDER)
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "token失效", "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, msg, "")
    }
    val id: String = request.getParameter("id")
    val aoData: String = request.getParameter("aoData")
    try{
      var map: Map[String, AnyRef] = new HashMap[String, AnyRef]
      val mapTmp: Map[String, AnyRef] = tReminderTaskService.findBySql(id,aoData)
      //多条连接 添加key
      val list: List[AnyRef] = new LinkedList[AnyRef]
      val m:AnyRef=mapTmp.get("list")
      val lists:List[_]=m.asInstanceOf[List[_]]
      val it: Iterator[_] = lists.iterator
      while (it.hasNext){
        val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
        val o: TReminderTask = it.next.asInstanceOf[TReminderTask]
        hashMap.put("id", o.getId.toString)
        hashMap.put("taskName", o.getTaskName)
        hashMap.put("excuteDay", o.getExcuteDay.toString)
        hashMap.put("startTime", o.getStartTime)
        hashMap.put("endTime", o.getEndTime)
        hashMap.put("frequency", o.getFrequency.toString)
        hashMap.put("targetPeriod", o.getTargetPeriod.toString)
        hashMap.put("createAt", DateTime4J.dtDateFormat(o.getCreateAt))
        hashMap.put("status", o.getStatus.toString)
        hashMap.put("reserve1", o.getReserve1)
        hashMap.put("reserve2", o.getReserve2)
        hashMap.put("reserve3", o.getReserve3)
        list.add(hashMap)
      }
      logger.info("查询缴费提醒:"+list)
      map.put("banList", list)
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }catch {
      case e: Exception => {
        logger.info("缴费提醒查询失败:"+e)
        AjaxResp.getReturn(RespCode.ERROR_CODE, "")
      }
    }
  }

  @RequestMapping(value = Array("saveReminder.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  @MyLog(remark = "缴费提醒修改")
  def saveOrUpd(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] ={
    //判断是否有权限
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),FuncCode.WECHAT_FUNC_REMINDER)
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "token失效", "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, msg, "")
    }
    try{
      val id:String = request.getParameter("id")
      val taskName:String = request.getParameter("taskName")
      val excuteDay:String = request.getParameter("excuteDay")
      val startTime:String = request.getParameter("startTime")
      val endTime:String = request.getParameter("endTime")
      val frequency:String = request.getParameter("frequency")
      val targetPeriod:String = request.getParameter("targetPeriod")

//      val createAt:String = request.getParameter("createAt")
      val status:String = request.getParameter("status")
      val reserve1:String = request.getParameter("reserve1")
      val reserve2:String = request.getParameter("reserve2")
      val reserve3:String = request.getParameter("reserve3")
//      val format:SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//      val str:Date = format.parse(createAt)

      val tReminderTask:TReminderTask = tReminderTaskService.getById(Integer.valueOf(id))
      tReminderTask.setTaskName(taskName)
      if(excuteDay == null || "".equals(excuteDay)){
        tReminderTask.setExcuteDay(0)
      }else{
        tReminderTask.setExcuteDay(Integer.parseInt(excuteDay))
      }
      tReminderTask.setStartTime(startTime)
      tReminderTask.setEndTime(endTime)
      if(frequency == null || "".equals(frequency)){
        tReminderTask.setFrequency(0)
      }else{
        tReminderTask.setFrequency(Integer.parseInt(frequency))
      }
      if(targetPeriod == null || "".equals(targetPeriod)){
        tReminderTask.setTargetPeriod(0)
      }else{
        tReminderTask.setTargetPeriod(Integer.parseInt(targetPeriod))
      }
//      tReminderTask.setCreateAt(str)
      if(status == null || "".equals(status)){
        tReminderTask.setStatus(0)
      }else{
        tReminderTask.setStatus(Integer.parseInt(status))
      }
      tReminderTask.setReserve1(reserve1)
      tReminderTask.setReserve2(reserve2)
      tReminderTask.setReserve3(reserve3)
      tReminderTaskService.update(tReminderTask)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE,"","缴费提醒修改成功")
    }catch {
      case e: Exception => {
        e.printStackTrace()
        AjaxResp.getReturn(RespCode.ERROR_CODE, "")
      }
    }
  }

}
