package com.ebeijia.controller

import java.util.Map

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.system.system.TblSysOperatorInf
import com.ebeijia.entity.system.vo.RoleFuncParam
import com.ebeijia.module.system.service.operatorInf.SysOperatorService
import com.ebeijia.util.core.{FuncCode, RespCode}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * Test
 * @author zhicheng.xu
 *         16/7/12
 */
@Controller
@RequestMapping(value = Array("/sys/test"))
class Test extends BaseValidRoleFunc {

  @Autowired
  private val operatorService: SysOperatorService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[Test])

  @RequestMapping(value = Array("test.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "请求模板")
  @ResponseBody def add(toi:TblSysOperatorInf,roleFuncParam: RoleFuncParam): Map[String, AnyRef] = {
    //验证权限
    roleFuncParam.setFuncId(FuncCode.OPR_FUNC_ADDITION)
    val msg: String = this.validRoleFunc(roleFuncParam)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    //验证请求参数
    val s: Array[Array[String]] = Array(Array(toi.getOprName, "1", "32", "1"),
      Array(toi.getOprName, "1", "8", "1"))

    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    //业务逻辑
    try {
      operatorService.save(toi)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    } catch {
      case e: Exception => {
        logger.info("新增操作员失败:" + e)
        AjaxResp.getReturn(RespCode.OPR_ERROR_ADD, "")
      }
    }
  }
}