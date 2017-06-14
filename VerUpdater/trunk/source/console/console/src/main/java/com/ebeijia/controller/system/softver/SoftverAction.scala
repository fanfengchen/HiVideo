package com.ebeijia.controller.system.softver

import java.util.{LinkedHashMap, _}
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.system.softver.Softver
import com.ebeijia.module.system.service.softver.SoftverService
import com.ebeijia.util.core.{FuncCode, RespCode}
import net.sf.json.JSONObject
import org.ebeijia.tools.DateTime4J
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
  * Created by xf on 2016/9/8.
  */
@Controller
@RequestMapping(value = Array("/sys/softver"))
class SoftverAction extends BaseValidRoleFunc {
  private val logger: Logger = LoggerFactory.getLogger(classOf[SoftverAction])

  @Autowired
  private val softverService: SoftverService = null
  private val token:String = "QYVD8W2JJW4KYDP2YMKWFX36"

  @RequestMapping(value = Array("/getVerInfo"), method = Array(RequestMethod.POST))
  @ResponseBody
  def query(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    //{"sign":"1717CA83AA9FC6C4308B429D69115D8F","data":"{}","authToken":"QYVD8W2JJW4KYDP2YMKWFX36",
// "sendTime":"1473760061324","appType":"11","signType":"01","version":"1.0-debug"}
    val jsonStr: String = request.getParameter("jsonStr")
    val artObj:JSONObject = JSONObject.fromObject(jsonStr)
    val sendTime=artObj.get("sendTime")
    val version=artObj.get("version")
    val appType=artObj.get("appType")
    val authToken=artObj.get("authToken").toString
    logger.info("============sendTime:"+sendTime+"||version:"+version+"||appType:"+appType+"||authToken:"+authToken)
    if (authToken == null || !token.equals(authToken)) {//判断token失效
      return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
    }
    val data=artObj.get("data")
    var verType:String = null
    if (data!=null && data!=""){
      val dataObj:JSONObject=JSONObject.fromObject(data)
      verType = dataObj.get("verType").toString
      logger.info("=====verType||"+verType)
    }
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: Map[String, AnyRef] = softverService.findBySql(verType, null)
      val list: List[AnyRef] = new LinkedList[AnyRef]
      val m:AnyRef=mapTmp.get("list")
      val lists:List[_]=m.asInstanceOf[List[_]]
      val it: Iterator[_] = lists.iterator
      var i:Int =0
      while (it.hasNext && i<1) {//多条时仅取第一条
        val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
        val o: Softver = it.next.asInstanceOf[Softver]
        hashMap.put("verNo", o.getVerNo)//verNo
        hashMap.put("url", o.getUrl)
        hashMap.put("user", o.getUser)
        hashMap.put("pwd", o.getPwd)
        hashMap.put("updateLog", o.getUpdateLog)
        hashMap.put("isuTime", DateTime4J.dateTimeFormat(o.getIsuTime))
        hashMap.put("isForce", o.getIsForce)
        list.add(hashMap)
        i = i+1
      }
      logger.info(list.toString)
      map.put("data", list)
//      map.put("current", mapTmp.get("current"))
//      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        e.printStackTrace()
        AjaxResp.getReturn(RespCode.ERROR_CODE,"")
      }
    }
  }
}
