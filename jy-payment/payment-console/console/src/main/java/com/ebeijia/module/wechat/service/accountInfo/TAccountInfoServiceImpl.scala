package com.ebeijia.module.wechat.service.accountInfo

import java.util
import java.util.Map

import com.ebeijia.module.wechat.dao.accountInfo.TAccountInfoDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by xf on 2016/7/5.
 */
@Service("TAccountInfoService")
class TAccountInfoServiceImpl extends TAccountInfoService{
  @Autowired
  private val tAccountInfoDao: TAccountInfoDao = null

  //通过条件查询结果
  @Transactional
  @Cacheable(value = Array("TAccountInfoCache"), key = "#root.method.name+#remiderDate+#startTime+#endTime+#pageData")
  def findBySql(remiderDate: String,startTime: String,endTime: String, pageData: String): util.Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("from TAccountInfo")
    query.append(" where 1 = 1 ")
    if(remiderDate != null && !remiderDate.equals("")){
      query.append(" AND remiderDate = ").append(remiderDate).append("")
    }
    if(startTime != null && !startTime.equals("")){
      query.append(" AND createTime >= ").append(startTime).append("")
    }
    if(endTime != null && !endTime.equals("")){
      query.append(" AND createTime <= ").append(endTime).append("")
    }
    query.append("  AND isActive = 0 order by createTime desc")
    val m: Map[String, AnyRef] = tAccountInfoDao.findByPage(query.toString(), pageData)
    m
  }
}
