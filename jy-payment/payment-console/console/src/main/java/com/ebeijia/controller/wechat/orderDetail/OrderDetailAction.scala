package com.ebeijia.controller.wechat.orderDetail

import java.util._
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.wechat.orderDetail.TOrderDetailT
import com.ebeijia.module.wechat.service.orderDetail.TOrderDetailService
import com.ebeijia.util.core.RespCode
import com.ebeijia.util.wechat.SignUtil
import org.ebeijia.tools.DateTime4J
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

@Controller
@RequestMapping(value = Array("/wechat/orderDetail"))
class OrderDetailAction extends BaseValidRoleFunc{
  private val logger: Logger = LoggerFactory.getLogger(classOf[OrderDetailAction])
  @Autowired
  private val tOrderDetailService: TOrderDetailService=null

  /**
    * 查询订单详情列表
    * @return
    */
  @RequestMapping(value = Array("/queryList.html"), method = Array(RequestMethod.GET))
  @ResponseBody
  def queryList(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] = {
    //    val usrId: String = request.getParameter("usrId")
    //    val token: String = request.getParameter("token")
    //    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),FuncCode.WECHAT_FUNC_ORDER)
    //    if(msg!=null){
    //      if(msg.equals("tokenLose")){
    //        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "token失效", "")
    //      }
    //      return AjaxResp.getReturn(RespCode.ERROR_CODE, msg, "")
    //    }
    val orderId: String = request.getParameter("orderId")
    val startTime: String = request.getParameter("startTime")
    var startTime1: String = startTime
    val endTime: String = request.getParameter("endTime")
    var endTime1: String = endTime
    val aoData: String = request.getParameter("aoData")
    if(startTime!=null && !"".equals(startTime)){
      startTime1 = DateTime4J.formatParseDateString(startTime).getTime.toString
    }
    if (endTime!=null && !"".equals(endTime)){
      endTime1 = DateTime4J.formatParseDateString(DateTime4J.addDate(endTime,1)).getTime.toString
    }
    logger.info("==orderId=="+orderId+"==startTime=="+startTime1+"==endTime=="+endTime1+"==aoData=="+aoData)
    try {
      var map: Map[String, AnyRef] = new HashMap[String, AnyRef]
      val mapTmp: Map[String, AnyRef] = tOrderDetailService.findBySql(orderId,startTime1,endTime1,aoData)
      //多条连接 添加key
      val list: List[AnyRef] = new LinkedList[AnyRef]
      val m:AnyRef=mapTmp.get("list")
      val lists:List[_]=m.asInstanceOf[List[_]]
      val it: Iterator[_] = lists.iterator
      while (it.hasNext) {
        val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
        val o: TOrderDetailT = it.next.asInstanceOf[TOrderDetailT]
        hashMap.put("id", o.getId.toString)
        hashMap.put("orderId", o.getOrderId)
        //        hashMap.put("accountNo", o.getAccountNo)//账号
        //        hashMap.put("accountName", o.getAccountName)//账户名
        //        hashMap.put("accountDate", o.getAccountDate)//缴费期数
        hashMap.put("itemType", SignUtil.intToString(o.getItemType))
        hashMap.put("itemDesc", o.getItemDesc)
        hashMap.put("itemSum", SignUtil.intToString(o.getItemSum))
        hashMap.put("preStatus", o.getPreStatus)
        if (o.getSufStatus!=null && !"".equals(o.getSufStatus)){
          hashMap.put("sufStatus", o.getSufStatus)//支付状态
        }else{
          hashMap.put("sufStatus", "0")//支付状态
        }
        if (o.getPayTime!=null && !"".equals(o.getPayTime.toString)){
          hashMap.put("payTime", DateTime4J.longFormatDateStr(o.getPayTime.toLong))//支付时间
        }else{
          hashMap.put("payTime", "")//支付时间
        }
        hashMap.put("heatNo", o.getHeatNo)
        hashMap.put("heatArea", o.getHeatArea)
        hashMap.put("heatAddress", o.getHeatAddress)
        hashMap.put("remark", o.getRemark)
        hashMap.put("unpaidSum", o.getUnpaidSum)
        hashMap.put("reserve1", o.getReserve1)
        hashMap.put("reserve2", o.getReserve2)
        hashMap.put("reserve3", o.getReserve3)
        hashMap.put("reserve4", o.getReserve4)
        list.add(hashMap)
      }
      logger.info("订单详情查询成功:"+list)
      map.put("banList", list)
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }catch {
      case e: Exception => {
        logger.info("订单详情查询失败:"+e)
        e.printStackTrace()
        AjaxResp.getReturn(RespCode.WECHAT_ORDERDETAIL_SEL_ERROR, "")
      }
    }

  }
}
