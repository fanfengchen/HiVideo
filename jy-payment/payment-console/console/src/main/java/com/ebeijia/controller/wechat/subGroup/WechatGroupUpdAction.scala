package com.ebeijia.controller.wechat.subGroup

import java.util.Map
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.entity.wechat.base.TblWechatSubGroupId
import com.ebeijia.module.wechat.service.group.WechatSubGroupService
import com.ebeijia.util.core.RespCode
import com.ebeijia.util.wechat.WechatError
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * 微信用户组别修改
 */


@Controller
@RequestMapping(value = Array("/wechat/subGroup"))
class WechatGroupUpdAction {

  @Autowired
  private val wechatSubGroupService: WechatSubGroupService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatGroupUpdAction])


  @RequestMapping(value = Array("upd.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "修改用户组别")
  @ResponseBody def upd(id:TblWechatSubGroupId, request: HttpServletRequest): Map[String, AnyRef] = {
    val mchtId: String = request.getParameter("mchtIdFlag")
    id.setMchtId(mchtId)
    val name: String = request.getParameter("name")
//    val groupId: String = request.getParameter("groupId")
    val s: Array[Array[String]] = Array(Array(mchtId, "1", "15","1"), Array(name, "1", "64","1"), Array(id.getId, "1", "8","1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    if(id.getId.equals("0") || id.getId.equals("1") || id.getId.equals("2")){
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"","该组别为系统初始化分组,不予修改!!!")
    }
    try {
      if (wechatSubGroupService.getById(id) == null) {
        AjaxResp.getReturn(RespCode.WECHAT_GUP_NOTEXITS_ERROR,"")
      }
      val respMsg: String = wechatSubGroupService.update(mchtId, id.getId, name)
      if (respMsg == null) {
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
      }
      else {
        AjaxResp.getReturn(RespCode.ERROR_CODE, WechatError.checkCode(respMsg), "")
      }
    }
    catch {
      case e: Exception => {
        logger.info("修改用户组别失败")
        AjaxResp.getReturn(RespCode.WECHAT_GUP_UPD_ERROR,  "")
      }
    }
  }

  @RequestMapping(value = Array("del.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "删除用户组别")
  @ResponseBody def del(id:TblWechatSubGroupId, request: HttpServletRequest): Map[String, AnyRef] = {
    val groupId: String = request.getParameter("id")
    val mchtId: String = request.getParameter("mchtIdFlag")
    val tid:TblWechatSubGroupId = new TblWechatSubGroupId
    tid.setId(groupId)
    tid.setMchtId(mchtId)
    val s: Array[Array[String]] = Array(Array(mchtId, "1", "15","1"), Array(groupId, "1", "8","1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    try {
      if (wechatSubGroupService.getById(tid) == null) {
        AjaxResp.getReturn(RespCode.WECHAT_GUP_NOTEXITS_ERROR, "")
      }
      val respMsg: String = wechatSubGroupService.del(mchtId, groupId)
      if (respMsg == null) {
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
      }
      else {
        AjaxResp.getReturn(RespCode.ERROR_CODE,"",WechatError.checkCode(respMsg))
      }
    }
    catch {
      case e: Exception => {
        logger.info("删除用户组别失败,请联系管理员或稍后再试")
        AjaxResp.getReturn(RespCode.WECHAT_GUP_DEL_ERROR, "")
      }
    }
  }
}

