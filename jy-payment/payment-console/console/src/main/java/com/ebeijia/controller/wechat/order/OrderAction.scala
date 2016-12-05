package com.ebeijia.controller.wechat.order

import java.util._
import javax.servlet.http.{HttpServletRequest, HttpSession}

import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.wechat.order.TOrder
import com.ebeijia.module.wechat.service.order.TOrderService
import com.ebeijia.util.core.{FuncCode, RespCode}
import com.ebeijia.util.wechat.SignUtil
import org.ebeijia.tools.DateTime4J
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

/**
 * Created by chen on 2016/7/14.
 */
@Controller
@RequestMapping(value = Array("/wechat/order"))
class OrderAction  extends BaseValidRoleFunc{
  private val logger: Logger = LoggerFactory.getLogger(classOf[OrderAction])
  @Autowired
  private val tOrderService: TOrderService = null

  /**
    * 查询订单详情
    * @param session
    * @param request
    * @return
    */
  @RequestMapping(value = Array("/qryOrderList.html"), method = Array(RequestMethod.POST))
  @ResponseBody
  def qryOrderList(session: HttpSession, request: HttpServletRequest): Map[String, AnyRef] ={
    val usrId: String = request.getParameter("usrId")
    val token: String = request.getParameter("token")
    val msg:String=this.validRoleFunc(usrId,token,request.getParameter("roleId"),FuncCode.WECHAT_FUNC_ORDER)
    if(msg!=null){
      if(msg.equals("tokenLose")){
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "token失效", "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, msg, "")
    }
    val _type: String = request.getParameter("_type")
    val accountNo: String = request.getParameter("accountNo")
    val startTime: String = request.getParameter("startTime")
    var startTime1: String = startTime
    val endTime: String = request.getParameter("endTime")
    var endTime1: String = endTime
    val payStatus: String = request.getParameter("payStatus")
    val aoData: String = request.getParameter("pageData")
    if(startTime!=null && !"".equals(startTime)){
      startTime1 = DateTime4J.formatParseDateString(startTime).getTime.toString
    }
    if (endTime!=null && !"".equals(endTime)){
      endTime1 = DateTime4J.formatParseDateString(DateTime4J.addDate(endTime,1)).getTime.toString
    }
    try{
      val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
      val mapTmp: Map[String, AnyRef] = tOrderService.findBySql(_type, startTime1,endTime1,payStatus,accountNo,aoData);
      val list: List[AnyRef] = new LinkedList[AnyRef]
      val m:AnyRef=mapTmp.get("list")
      val lists:List[_]=m.asInstanceOf[List[_]]
      val it: Iterator[_] = lists.iterator
      while (it.hasNext) {
        val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
        val o: TOrder = it.next.asInstanceOf[TOrder]
        hashMap.put("id", o.getId.toString)
        hashMap.put("userId", o.getUserId.toString)
        hashMap.put("accountNo", o.getAccountNo)
        hashMap.put("remarkName", o.getRemarkName)
        hashMap.put("type", o.getType)
        hashMap.put("accountDate", o.getAccountDate)
        hashMap.put("totalFee", SignUtil.intToString(o.getTotalFee))
        hashMap.put("payFee", SignUtil.intToString(o.getPayFee))
        if (o.getCreateTime!=null && !"".equals(o.getCreateTime.toString)){
          hashMap.put("createTime", DateTime4J.longFormatDateStr(o.getCreateTime))//支付时间
        }else{
          hashMap.put("createTime", o.getCreateTime.toString)//支付时间
        }
        if (o.getPayTime!=null && !"".equals(o.getPayTime.toString)){
          hashMap.put("payTime", DateTime4J.longFormatDateStr(o.getPayTime))//支付时间
        }else{
          hashMap.put("payTime", o.getPayTime.toString)//支付时间
        }
        hashMap.put("payStatus", o.getPayStatus)
        hashMap.put("outTradeNo", o.getOutTradeNo.toString)
        hashMap.put("goodsTag", o.getGoodsTag)

        hashMap.put("couponFee", SignUtil.intToString(o.getCouponFee))
        hashMap.put("feeType", o.getFeeType)
        hashMap.put("payInfo", o.getPayInfo)
        hashMap.put("attach", o.getAttach)
        hashMap.put("bankType", o.getBankType)
        hashMap.put("tradeType", o.getTradeType)
        hashMap.put("transactionId", o.getTransactionId)
        hashMap.put("bankBillNo", o.getBankBillNo)

        hashMap.put("outTransactionId", o.getOutTransactionId)
        hashMap.put("timeEnd", o.getTimeEnd)
        hashMap.put("lsResult", o.getLsResult)
        hashMap.put("lsInfo", o.getLsInfo)
        hashMap.put("lsSerial_no", o.getLsSerialNo)
        hashMap.put("lsTime", o.getLsTime)
        hashMap.put("city", o.getCity)
        hashMap.put("unit", o.getUnit)

        hashMap.put("refundId", o.getRefundId)
        hashMap.put("outRefund_no", o.getOutRefundNo)
        hashMap.put("refundChannel", o.getRefundChannel)
        hashMap.put("couponRefund_fee", SignUtil.intToString(o.getCouponRefundFee))
        hashMap.put("refundFee", SignUtil.intToString(o.getRefundFee))
        hashMap.put("isActive", o.getIsActive)
        hashMap.put("reserve1", o.getReserve1)
        hashMap.put("reserve2", o.getReserve2)
        hashMap.put("reserve3", o.getReserve3)
        hashMap.put("reserve4", o.getReserve4)
        list.add(hashMap)
      }
      logger.info("账单查询:"+list)
      map.put("banList", list)
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }catch {
      case e: Exception => {
        logger.info("账单查询失败:"+e.getStackTraceString)
        AjaxResp.getReturn(RespCode.ERROR_CODE, "")
      }
    }
  }
}
