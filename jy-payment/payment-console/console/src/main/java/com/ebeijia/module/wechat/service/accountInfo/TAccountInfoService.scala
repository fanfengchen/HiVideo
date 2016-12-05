package com.ebeijia.module.wechat.service.accountInfo

import java.util.Map

/**
 * Created by xf on 2016/7/5.
 */
trait TAccountInfoService {
  //通过条件查询结果
  def findBySql(remiderDate: String, startTime: String,endTime: String, pageData: String): Map[String, AnyRef]

}
