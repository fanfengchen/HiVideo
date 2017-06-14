package com.ebeijia.ajax.resp

import java.util.{HashMap, Map}

import com.ebeijia.util.core.RespCode

/**
 * AjaxResp
 * @author zhicheng.xu
 *         15/8/7
 */
object AjaxResp {
  def getReturn(respCode: String, o: AnyRef): Map[String, AnyRef] = {
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    map.put("respCode", respCode)
    map.put("errorInfo", RespCode.Msg(respCode))
    map.put("content", o)
    map
  }

  def getReturn(respCode: String, o: AnyRef,msg:String): Map[String, AnyRef] = {
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    map.put("respCode", respCode)
    map.put("errorInfo", msg)
    map.put("content", o)
    map
  }
}

