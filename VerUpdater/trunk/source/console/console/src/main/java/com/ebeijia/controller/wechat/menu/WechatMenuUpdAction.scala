package com.ebeijia.controller.wechat.menu

import java.util.{HashMap, List, Map}
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.vo.wechat.button.Menu
import com.ebeijia.entity.wechat.base.{TblWechatRespMsg, TblWechatMenu, TblWechatMenuId}
import com.ebeijia.module.wechat.service.menu.WechatMenuService
import com.ebeijia.module.wechat.service.msg.WechatRespMsgService
import com.ebeijia.util.core.RespCode
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
  * 微信菜单修改
  */


@Controller
@RequestMapping(value = Array("/wechat/menu"))
class WechatMenuUpdAction extends BaseValidRoleFunc{
  @Autowired
  private val wechatMenuService: WechatMenuService = null
  @Autowired
  private val wechatRespMsgService:WechatRespMsgService = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatMenuUpdAction])

  @RequestMapping(value = Array("upd.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信菜单修改")
  @ResponseBody def upd(tblWechatMenu: TblWechatMenu, request: HttpServletRequest): Map[String, AnyRef] = {
    if (tblWechatMenu == null) {
      AjaxResp.getReturn(RespCode.PARAM_ERROR, "")
    }
    val s: Array[Array[String]] = Array(
      Array(tblWechatMenu.getMenuName, "1", "40", "1"),
      Array(tblWechatMenu.getParentId, "1", "10", "1"),
      Array(tblWechatMenu.getType, "1", "1", "1"),
      Array(tblWechatMenu.getGroupId, "1", "3", "1"),
      Array(tblWechatMenu.getUrl, "0", "256", "1"),
      Array(tblWechatMenu.getMenuKey, "0", "128", "1"))
    if (ActionValidate.checkArray(s) != null) {
      return null
    }
    val twni: TblWechatMenuId = new TblWechatMenuId
    twni.setMchtId(request.getParameter("mchtIdFlag"))
    twni.setMenuId(Integer.parseInt(request.getParameter("menuId")))
    tblWechatMenu.setId(twni)
    if (wechatMenuService.getById(twni) == null) {
      AjaxResp.getReturn(RespCode.WECHAT_MENU_NOTEXIST_ERROR, "")
    }
    try {
      //判断标题是否存在
      val nameCount: Int = wechatMenuService.isMenuNameCount(twni.getMchtId,tblWechatMenu.getId.getMenuId.toString, tblWechatMenu.getMenuName)
      if (nameCount > 0) {
        return AjaxResp.getReturn(RespCode.ERROR_CODE, "", "微信菜单标题已存在!")
      }
      //判断排序号是否存在
      val orderNo: Int = wechatMenuService.getOrderNoCount(tblWechatMenu.getId.getMenuId.toString, tblWechatMenu.getOrderNo)
      if (orderNo > 0) {
        return AjaxResp.getReturn(RespCode.ERROR_CODE, "", "微信菜单排序号已存在!")
      }
      //判断如果是第一级菜单时，同一组不能超过三个
      if (tblWechatMenu.getParentId.equals("-")) {
        val menuGroupList: List[TblWechatMenu] = wechatMenuService.groupParentList(twni.getMchtId,tblWechatMenu.getGroupId)
        if (menuGroupList.size() > 3) {
          return AjaxResp.getReturn(RespCode.ERROR_CODE, "", "同一组别一级菜单不能超过3个!")
        }
      } else {
        //判断二级菜单不能超过六个
        val menuList: List[TblWechatMenu] = wechatMenuService.queryParentList(twni.getMchtId,tblWechatMenu.getId.getMenuId.toString)
        if (menuList.size() >= 6) {
          return AjaxResp.getReturn(RespCode.ERROR_CODE, "", "每个一级菜单的二级菜单不能超过6个!")
        }
      }
      val tblWechatRespMsg:TblWechatRespMsg = wechatRespMsgService.getResp(tblWechatMenu.getId.getMchtId,tblWechatMenu.getMenuKey)
      if(request.getParameter("msgType") != null){
        tblWechatRespMsg.setMsgType(request.getParameter("msgType"))
      }
      if(request.getParameter("media") != null){
        tblWechatRespMsg.setMediaId(request.getParameter("media"))
      }
      if(request.getParameter("picId") != null){
        tblWechatRespMsg.setPicUrl(request.getParameter("picId"))
      }
      if(request.getParameter("artId") != null){
        tblWechatRespMsg.setArticleIds(request.getParameter("artId"))
      }
      if(request.getParameter("content") != null){
        tblWechatRespMsg.setContent(request.getParameter("content"))
      }
      if(request.getParameter("url") != null){
        tblWechatRespMsg.setUrl(request.getParameter("url"))
      }
      if(request.getParameter("menuKey") !=null){
        tblWechatRespMsg.setKeywords(request.getParameter("menuKey"))
      }
      wechatRespMsgService.update(tblWechatRespMsg,null)
      wechatMenuService.updateWechatMenu(tblWechatMenu)
//      val menu: Menu = wechatMenuService.SynchToMenu(request.getParameter("mchtIdFlag"), request.getParameter("groupId"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        logger.info("修改菜单失败,请联系管理员或稍后再试")
        AjaxResp.getReturn(RespCode.WECHAT_MENU_UPD_ERROR, "")
      }
    }
  }

  @RequestMapping(value = Array("del.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信菜单删除")
  @ResponseBody def del(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
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
    val menuId: String = request.getParameter("menuId")
    val mchtId: String = request.getParameter("mchtIdFlag")
    val id: TblWechatMenuId = new TblWechatMenuId
    id.setMchtId(mchtId)
    id.setMenuId(Integer.parseInt(menuId))
    var map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    if (wechatMenuService.getById(id) == null) {
      AjaxResp.getReturn(RespCode.WECHAT_MENU_NOTEXIST_ERROR, "")

    }
    try {
      val resp: String = wechatMenuService.deleteMenuById(id)
      if (resp == null) {
//        val menu: Menu = wechatMenuService.SynchToMenu(request.getParameter("mchtIdFlag"), request.getParameter("groupId"))
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
      }
      else {
        AjaxResp.getReturn(RespCode.ERROR_CODE, "", resp)
      }
    }
    catch {
      case e: Exception => {
        logger.info("删除菜单失败,请联系管理员或稍后再试")
        AjaxResp.getReturn(RespCode.WECHAT_MENU_DEL_ERROR, "")
      }
    }
  }
}

