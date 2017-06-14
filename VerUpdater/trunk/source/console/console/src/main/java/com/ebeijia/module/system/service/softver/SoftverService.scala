package com.ebeijia.module.system.service.softver

import java.util.Map

/**
  * Created by xf on 2016/9/8.
  */
trait SoftverService {
  //通过条件查询结果
  def findBySql(verType: String, pageData: String): Map[String, AnyRef]
}
