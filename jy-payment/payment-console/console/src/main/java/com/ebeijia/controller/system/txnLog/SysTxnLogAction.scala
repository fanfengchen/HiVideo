package com.ebeijia.controller.system.txnLog

import java.util.{HashMap, Map}
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.module.system.service.txnLog.SysTxnLogService
import com.ebeijia.util.core.{FuncCode, RespCode}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
  * TxnLogAction
  * @author zhicheng.xu
  *         15/8/10
  */

@Controller
@RequestMapping(value = Array("/sys/txnLog"))
class SysTxnLogAction extends BaseValidRoleFunc {
  @Autowired
  private val txnLogService: SysTxnLogService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[SysTxnLogAction])

  @RequestMapping(value = Array("/query.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def query(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, request.getParameter("roleId"), FuncCode.ACC_FUNC_OPRLOG)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    val date: String = request.getParameter("beginDate")
    //结束日期
    val endDate: String = request.getParameter("endDate")
    val txnChl: String = request.getParameter("txnChl")
    val status: String = request.getParameter("status")
    val pageData: String = request.getParameter("aoData")
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: Map[String, AnyRef] = txnLogService.findBySql(date, endDate, txnChl, status, pageData)
      map.put("txnLogList", mapTmp.get("list"))
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("操作流水查询失败")
        AjaxResp.getReturn(RespCode.TXN_ERROR_SEL,"")
      }
    }
  }
}
