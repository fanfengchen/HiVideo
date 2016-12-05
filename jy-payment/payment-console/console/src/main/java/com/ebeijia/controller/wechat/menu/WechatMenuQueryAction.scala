package com.ebeijia.controller.wechat.menu

import java.util
import java.util.{List, HashMap, Map}
import javax.servlet.http.{HttpServletRequest, HttpSession}
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.vo.wechat.button.Menu
import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.entity.wechat.base._
import com.ebeijia.module.wechat.service.media.WechatFodderService
import com.ebeijia.module.wechat.service.menu.WechatMenuService
import com.ebeijia.module.wechat.service.msg.WechatRespMsgService
import com.ebeijia.util.core.RespCode
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * 微信菜单查询
  * 同步
 */


@Controller
@RequestMapping(value = Array("/wechat/menu"))
class WechatMenuQueryAction extends BaseValidRoleFunc{
  @Autowired
  private val wechatMenuService: WechatMenuService = null
  @Autowired
  private val wechatRespMsgService:WechatRespMsgService = null
  @Autowired
  private val wechatFodderService:WechatFodderService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatMenuQueryAction])

  @RequestMapping(value = Array("query.html"), method = Array(RequestMethod.POST))
  @ResponseBody def query(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val mchtId: String = request.getParameter("mchtIdFlag")
    val groupId: String = request.getParameter("groupId")
    val pageData: String = request.getParameter("aoData")
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: Map[String, AnyRef] = wechatMenuService.findBySql(mchtId, groupId, pageData)
      map.put("menuList", mapTmp.get("list"))
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE,map)
    }
    catch {
      case e: Exception => {
        logger.info("微信菜单查询失败,请联系管理员或稍后再试")
        AjaxResp.getReturn(RespCode.WECHAT_MENU_SEL_ERROR,"")
      }
    }
  }

  @RequestMapping(value = Array("menuSyn.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信菜单同步")
  @ResponseBody def menuSyn(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val roleId: String = request.getParameter("roleId")
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg: String = this.validRoleFunc(usrId, token, roleId,"200031")
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE,"")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE,"",msg)
    }
    val mchtId: String = request.getParameter("mchtIdFlag")
    val groupId: String = request.getParameter("groupId")
    val s: Array[Array[String]] = Array(Array(mchtId, "1", "15","1"), Array(groupId, "1", "3","1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    var menu: Menu = null
    try {
      menu = wechatMenuService.SynchToMenu(mchtId, groupId)
      if (menu != null) {
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, menu)
      }
      else {
        //返回菜单特殊处理
        AjaxResp.getReturn(RespCode.WECHAT_MENU_SYS_ERROR, "")
      }
    }
    catch {
      case e: Exception => {
        logger.info("同步微信菜单失败")
        AjaxResp.getReturn(RespCode.WECHAT_MENU_SYS_ERROR, "")
      }
    }
  }

  @RequestMapping(value = Array("menuInf.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信菜单详情")
  @ResponseBody def menuInf(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val mchtId: String = request.getParameter("mchtId")
    val menuId: String = request.getParameter("menuId")
    val groupId: String = request.getParameter("groupId")
    val menuKey: String = request.getParameter("menuKey")
    val s: Array[Array[String]] = Array(Array(mchtId, "1", "15", "1"), Array(groupId, "1", "3", "1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "", ActionValidate.checkArray(s))
    }
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    var tblWechatMenu:TblWechatMenu = new TblWechatMenu
    val tmId:TblWechatMenuId = new TblWechatMenuId
    tmId.setMchtId(mchtId)
    tmId.setMenuId(menuId.toInt)
    try {
      tblWechatMenu  = wechatMenuService.getById(tmId)
      if(tblWechatMenu == null){
        return AjaxResp.getReturn(RespCode.ERROR_CODE, "","菜单已不存在")
      }
      val ls:List[TblWechatRespMsg] = new util.ArrayList[TblWechatRespMsg]()
      val tblWechatRespMsg:TblWechatRespMsg = wechatRespMsgService.getResp(mchtId,menuKey)
      if(tblWechatRespMsg == null){
        return AjaxResp.getReturn(RespCode.ERROR_CODE, "","菜单绑定的消息不存在")
      }
      if(tblWechatRespMsg.getMsgType.equals("news")){
        val tblWechatFodderId:TblWechatFodderId = new TblWechatFodderId
        tblWechatFodderId.setMchtId(tblWechatRespMsg.getId.getMchtId)
        tblWechatFodderId.setMedia(tblWechatRespMsg.getMediaId)
        val list:List[TblWechatFodder] = new util.ArrayList[TblWechatFodder]()
        val tblWechatFodder:TblWechatFodder = wechatFodderService.getById(tblWechatFodderId)
        list.add(tblWechatFodder)
        map.put("menuInf", list)
      }else{
        ls.add(tblWechatRespMsg)
        map.put("menuInf", ls)
      }
      return AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("微信菜单详情查询失败")
        AjaxResp.getReturn(RespCode.ERROR_CODE, "","微信菜单详情查询失败")
      }
    }
  }
}

