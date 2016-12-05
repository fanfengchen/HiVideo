package com.ebeijia.Validate

import java.util.regex.Matcher

import com.ebeijia.util.core.{PatternUtil, RespCode}
import org.ebeijia.tools.Validate4J

/**
 * ActionValidate
 * @author zhicheng.xu
 *         16/6/27
 */
object ActionValidate {


  /*统一验证 参数第三位
  *null，“”，1验证字符串
  *2验证数字，浮点
  *3正则验证
  */
  def checkArray(s: Array[Array[String]]): String = {
    var str: String =null
    for (i <- 0 until s.length) {
      str = s(i)(3) match {
        case "" =>this.checkStrArrLen(s(i))
        case null =>this.checkStrArrLen(s(i))
        case "1" =>this.checkStrArrLen(s(i))
        case "2" =>this.checkNumberLength(s(i)(0),s(i)(1).toInt,s(i)(2).toInt)
        case "3" =>this.checkRegex(s(i)(0), s(i)(1))
        case _ =>this.checkStrArrLen(s(i))
      }
      if(str !=null){
        return str
      }
    }
     str
  }

  /**
   * 判断字符串数组长度
   * 注:1.两者之间(字符串,参数1,参数2)
   * 2.小于等于某个值(字符串,1,参数2)
   * 3.等于某个值(字符串,参数1,参数1)
   * 4.没有限制的用0或null表示
   * @param s 字符串数组(二维)
   * @return 都满足条件, 返回true;反之false
   */
  def checkStrArrLen(s: Array[String]): String = {
      val str: String = s(0)
      val tmp:String ="验证不正确"
      if ("".equals(str) || str == null) {
        if (s(1) == null || "0".equals(s(1))) {
        }
        else {
          return str+RespCode.Msg(RespCode.PARAM_NULL_ERROR)
        }
      }
      else {
        //sql注入简单验证
        if (str.indexOf("'") != -1 || str.indexOf("’") != -1 || str.trim.indexOf("1=1") != -1) {
          return str+RespCode.Msg(RespCode.PARAM_SQL_ERROR)
        }
        //字符串长度验证
        var len1: Int = 0
        if ("".equals(s(1)) || null == s(1)) {
        } else {
          len1 = s(1).toInt
        }
        var len2: Int = 0
        if ("".equals(s(2)) || s(2) == null) {
        }
        else {
          len2 = s(2).toInt
        }
        if (!Validate4J.checkStringLength(str, len1, len2)) {
          return str+RespCode.Msg(RespCode.PARAM_STR_ERROR).concat(",长度限制为:").concat(s(2))
        }
    }
    null
  }

  /**
   * 判断Number的长度(注:小数点)
   *
   * @param str           String类型的Number
   * @param dataPrecision 指定数字数据的精度,精度是指数字的位数
   * @param dataScale     指定数字数据的小数位数,小数位数是指小数点后的位数
   * @return 符合条件返回true, 反之false
   */
  def checkNumberLength(str: String, dataPrecision: Int, dataScale: Int): String = {
    if (str.contains(".")) {
      val str1: String = str.replace(".", "&")
      val s: Array[String] = str1.split("&")
      val len: Int = dataPrecision - dataScale
      if(s(0).length <= len){
          return str+RespCode.Msg(RespCode.PARAM_NUM_ERROR)
      }
    }
    val len: Int = dataPrecision - dataScale
    if(str.length <= len){
       null
    }else{
      str+RespCode.Msg(RespCode.PARAM_NUM2_ERROR)
    }
  }

  /**
   * 正则验证
   *
   * @param str
   * @return
   */
  def checkRegex(str: String, paStr:String): String = {
    var flag: Boolean = false
    try {
      val matcher: Matcher = PatternUtil.mateReges(paStr).matcher(str)
      flag = matcher.matches
    }
    catch {
      case e: Exception => {
        e.printStackTrace
        flag = false
      }
    }
    if(flag){
      null
    }else{
      str+RespCode.Msg(RespCode.PARAM_REG_ERROR)
    }
  }

  def main(args: Array[String]) {
//    val s: Array[Array[String]] = Array(Array("", "1", "32","1"), Array("1", "1", "32","1"))
//val s: Array[Array[String]] = Array(Array("1006", "4", "0","2"),Array("abcsina.com", "5", "1","3"))
//    val tmp = this.checkArray(s)
//    System.out.print(tmp)
    val a:String = "8,9"
    var str:String = ""
    val b:Array[String] = a.split(",")
    for(c <- b){
      str = str + "'"+c+"',"
    }
    str = str.substring(0,str.length - 1)
    println(str)
  }
}
