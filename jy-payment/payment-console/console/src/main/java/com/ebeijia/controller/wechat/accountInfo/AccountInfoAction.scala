package com.ebeijia.controller.wechat.accountInfo

import java.util._
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.wechat.accountInfo.TAccountInfo
import com.ebeijia.module.wechat.service.accountInfo.TAccountInfoService
import com.ebeijia.util.core.{FuncCode, RespCode}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * Created by xf on 2016/7/5.
 */
@Controller
@RequestMapping(value = Array("/wechat/accountInfo"))
class AccountInfoAction extends BaseValidRoleFunc{
  private val logger: Logger = LoggerFactory.getLogger(classOf[AccountInfoAction])
  @Autowired
  private val tAccountInfoService: TAccountInfoService=null

  /**
   * 查询订单详情列表
   * @return
   */
  @RequestMapping(value = Array("/queryList.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def queryList(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),FuncCode.OPR_FUNC_ADDITION)
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "token失效", "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, msg, "")
    }
    val remiderDate: String = request.getParameter("remiderDate")//提醒时间
    val startTime: String = request.getParameter("startTime")//创建时间区间
    val endTime: String = request.getParameter("endTime")
    val aoData: String = request.getParameter("aoData")
    try {
      var map: Map[String, AnyRef] = new HashMap[String, AnyRef];
      val mapTmp: Map[String, AnyRef] = tAccountInfoService.findBySql(remiderDate,startTime,endTime,aoData);
      //多条连接 添加key
      val list: List[AnyRef] = new LinkedList[AnyRef]
      val m:AnyRef=mapTmp.get("list")
      val lists:List[_]=m.asInstanceOf[List[_]]
      val it: Iterator[_] = lists.iterator
      while (it.hasNext) {
        val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
        val o: TAccountInfo = it.next.asInstanceOf[TAccountInfo]
        hashMap.put("accountNo", o.getId.getAccountNo)//账户编号
        hashMap.put("remarkName", o.getRemarkName)//备注名
        hashMap.put("status", o.getStatus)//状态
        hashMap.put("remiderDate", o.getRemiderDate)//提醒时间
        hashMap.put("createTime", o.getCreateTime)//创建时间
        list.add(hashMap)
      }
      logger.info("綁定賬戶信息查询成功:"+list)
      map.put("banList", list)
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }catch {
      case e: Exception => {
        logger.info("绑定账户信息查询失败:"+e)
        AjaxResp.getReturn(RespCode.WECHAT_ACCOUNTINFO_SEL_ERROR, "")
      }
    }

  }
}
