package com.ebeijia.module.wechat.service.orderDetail

import java.util.Map

/**
 * Created by xf on 2016/6/16.
 */
trait TOrderDetailService {
  //通过条件查询结果
  def findBySql(orderId: String , startTime: String,endTime: String, pageData: String): Map[String, AnyRef]

}
