package com.ebeijia.controller.wechat.menu

import java.util
import java.util.{LinkedHashMap, List, Map}
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.vo.wechat.button.Menu
import com.ebeijia.entity.wechat.base.{TblWechatRespMsg, TblWechatRespMsgId, TblWechatMenu, TblWechatMenuId}
import com.ebeijia.module.wechat.dao.base.WechatRespMsgDao
import com.ebeijia.module.wechat.service.menu.WechatMenuService
import com.ebeijia.module.wechat.service.msg.WechatRespMsgService
import com.ebeijia.util.core.RespCode
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * 微信菜单新增
 */


@Controller
@RequestMapping(value = Array("/wechat/menu"))
class WechatMenuAddAction extends BaseValidRoleFunc{
  @Autowired
  private val wechatMenuService: WechatMenuService = null
  @Autowired
  private val wechatRespMsgService: WechatRespMsgService = null
  @Autowired
  private val wechatRespMsgDao: WechatRespMsgDao = null
  private val logger: Logger = LoggerFactory.getLogger(classOf[WechatMenuAddAction])

  @RequestMapping(value = Array("add.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "微信菜单新增")
  @ResponseBody def add(twm: TblWechatMenu, request: HttpServletRequest): Map[String, AnyRef] = {
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
    if (twm == null) {
      AjaxResp.getReturn(RespCode.OBJECT_ERROR_NULL,"")
    }
    val twmi: TblWechatMenuId = new TblWechatMenuId
    twmi.setMchtId(request.getParameter("mchtIdFlag"))
    twmi.setMenuId(wechatMenuService.getMaxMenuId())
    val s: Array[Array[String]] = Array(
      Array(twm.getMenuName, "1", "40","1"),
      Array(twm.getOrderNo, "1", "4","1"),
      Array(twm.getParentId, "1", "10","1"),
      Array(twm.getType, "1", "1","1"),
      Array(twm.getGroupId, "1", "3","1"),
      Array(twm.getUrl, "0", "256","1"),
      Array(twm.getMenuKey, "0", "128","1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    try {
      twm.setId(twmi)
      twm.setMenuKey(twm.getOrderNo)
      //判断标题是否存在
      val nameCount:Int=wechatMenuService.isMenuNameCount(twm.getId.getMchtId,twm.getId.getMenuId.toString,twm.getMenuName)
      if(nameCount>0){
        return AjaxResp.getReturn(RespCode.ERROR_CODE, "", "微信菜单标题已存在!")
      }
      //判断排序号是否存在
      val orderNo:Int=wechatMenuService.getOrderNoCount(twm.getId.getMenuId.toString,twm.getOrderNo)
      if(orderNo>0){
        return AjaxResp.getReturn(RespCode.ERROR_CODE,"","微信菜单排序号已存在!")
      }
      //判断如果是第一级菜单时，同一组不能超过三个
      if(twm.getParentId.equals("-")){
        val menuGroupList:List[TblWechatMenu]=wechatMenuService.groupParentList(twm.getId.getMchtId,twm.getGroupId)
        if(menuGroupList.size()>=3){
          return AjaxResp.getReturn(RespCode.ERROR_CODE, "", "同一组别一级菜单不能超过3个!")
        }
      }else{
        //判断二级菜单不能超过六个
        val menuList:List[TblWechatMenu]=wechatMenuService.queryParentList(twm.getId.getMchtId,twm.getId.getMenuId.toString)
        if(menuList.size()>=6){
          return AjaxResp.getReturn(RespCode.ERROR_CODE, "", "每个一级菜单的二级菜单不能超过6个!")
        }
      }
      val tsmid:TblWechatRespMsgId = new TblWechatRespMsgId
      val tsm:TblWechatRespMsg = new TblWechatRespMsg
      tsmid.setMchtId(request.getParameter("mchtIdFlag"))
      tsmid.setRespMsgId(wechatRespMsgService.getMaxRespId(tsmid.getMchtId))
      tsm.setId(tsmid)
      tsm.setKeywords(twm.getOrderNo)
      //微信菜单返回信息
      //文本正文
      val msgType1:String = request.getParameter("msgType")
      val content :String = request.getParameter("content")
      //图片
      val picUrl :String = request.getParameter("picUrl")
      val mediaId:String = request.getParameter("media")
      //图文
      val artId :String = request.getParameter("artId")
      //链接
      val url :String = request.getParameter("url")
      tsm.setKeywords(twm.getOrderNo)
      tsm.setMsgType(msgType1)
      tsm.setUrl(url)
      tsm.setContent(content)
      tsm.setPicUrl(picUrl)
      tsm.setMediaId(mediaId)
      tsm.setRespType("CLICK")
      val total: Int = wechatRespMsgService.check(null, twm.getId.getMchtId, twm.getMenuKey)
      if (total > 0) {
        AjaxResp.getReturn(RespCode.WECHAT_MSG_KEY_EXIST, "")
      }
      tsm.setArticleIds(artId)
      tsm.setKeywordType("1")
      wechatRespMsgDao.save(tsm)
      wechatMenuService.save(twm)
//      val menu: Menu = wechatMenuService.SynchToMenu(request.getParameter("mchtIdFlag"),request.getParameter("groupId"))
//      val map:Map[String,AnyRef] = new util.HashMap[String,AnyRef]
//      map.put("menu",menu)
//      map.put("menuId",twmi.getMenuId.toString)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        logger.info("新增微信菜单失败")
        AjaxResp.getReturn(RespCode.WECHAT_MENU_ADD_ERROR,  "")
      }
    }
  }

  //验证菜单key值不能重复
  @RequestMapping(value = Array("wechatMenuAddCheck.html"), method = Array(RequestMethod.POST))
  @ResponseBody def wechatMenuAddCheck(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    val map: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
    val menuKey: String = request.getParameter("menuKey")
    val mchtId: String = request.getParameter("mchtIdFlag")
    val tblWechatMenu: List[TblWechatMenu] = wechatMenuService.findByHql(menuKey, mchtId)
    if (!tblWechatMenu.isEmpty) {
      map.put("info", "yes")
    }
    else {
      map.put("info", "no")
    }
    map
  }
}

