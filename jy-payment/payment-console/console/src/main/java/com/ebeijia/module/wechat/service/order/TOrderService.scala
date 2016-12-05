package com.ebeijia.module.wechat.service.order

import java.util.Map

/**
 * Created by chen on 2016/7/12.
 */
trait TOrderService {
  //通过条件查询
  def findBySql(_type:String, startTime: String,endTime: String,payStatus:String,accountNo:String,pageData: String): Map[String, AnyRef]

}
