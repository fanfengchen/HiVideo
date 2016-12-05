package com.ebeijia.controller.list.wechat

import java.util.Map
import javax.servlet.http.HttpServletRequest

import com.ebeijia.module.wechat.service.menu.WechatMenuGroupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

/**
 * ListGroupId
 * @author zhicheng.xu
 *         15/8/11
 */



@Controller
@RequestMapping(value = Array("/list"))
class ListGroupId {
  @Autowired
  private val wechatMenuGroupService: WechatMenuGroupService = null

  //获取商户级别
  @RequestMapping(value = Array("groupId.html"))
  @ResponseBody def listGroupId(request: HttpServletRequest): Map[String, AnyRef] = {
    val mchtId: String = request.getParameter("mchtId")
    wechatMenuGroupService.listFind(mchtId)
  }
}
