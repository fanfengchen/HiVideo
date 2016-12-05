package com.ebeijia.api

//import com.ebeijia.service.usr.UsrInfService

/**
 * BaseValidate
 * @author zhicheng.xu
 *         15/10/12
 */
class BaseValidate {
//  @Autowired
//  private val usrInfService:UsrInfService = null
//
//  def validate(txnDate: String, txnCd: String, txnChl: String, openid: String,usrId:String): String = {
//    //验证报文头 txnCd 5char txnDate 14 chl 1 txnUsr >0
//    val s: Array[Array[String]] = Array(Array(txnCd, "5", "5"),
//      Array(txnChl, "1", "1"), Array(openid, "1", "32"), Array(txnDate, "14", "14"))
//    if (Validate4J.checkStrArrLen(s)) {
//      //转成时间戳比较
//      val curTs =DateTime4J.getTimesByDate(DateTime4J.getCurrentDateTime)
//      val txnTs = DateTime4J.getTimesByDate(txnDate)
//      //客户端时间与服务器时间相差十分钟内
//      if ( curTs - txnTs > 600000 || txnTs - curTs > 600000) {
//        "交易日期或格式不正确"
//      } else if (!TxnCd.checkTxnCd(txnCd)) {
//        "交易代码不正确"
//      } else if (!"0".equals(txnChl) && !"1".equals(txnChl) && !"2".equals(txnChl)) {
//        "交易渠道不正确"
//      } else if(usrId != null && "0".equals(txnChl)) {
//        val result :String = null//usrInfService.findOpid(openid ,usrId)
//        if("ERROR".equals(result)) {
//          "该用户已在其他地方登录"
//        }else{
//          null
//        }
//      } else{
//        null
//      }
//    } else {
//      "报文格式不正确"
//    }
//  }

}
