package com.ebeijia.module.system.service.softver

import java.util
import java.util.Map

import com.ebeijia.module.system.dao.softver.SoftverDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
  * Created by xf on 2016/9/8.
  */
@Service("SoftverService")
class SoftverServiceImpl  extends SoftverService{

  @Autowired
  private val softverDao: SoftverDao = null

  //通过条件查询结果
//  @Cacheable(value = Array("SoftVerCache"), key = "#root.method.name+#verType+#pageData")
  @Transactional
  def findBySql( verType: String, pageData: String): util.Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("from Softver")
    query.append(" where 1 = 1 ")
    if(verType != null && !"".equals(verType)){
      query.append(" AND verType = '").append(verType).append("'")
    }
    query.append("  order by  id desc")
    val m: Map[String, AnyRef] = softverDao.findByPage(query.toString(), pageData)
    m
  }
}
