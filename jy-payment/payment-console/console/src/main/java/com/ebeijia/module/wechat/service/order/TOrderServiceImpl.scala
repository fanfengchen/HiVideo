package com.ebeijia.module.wechat.service.order

import java.util
import java.util.Map

import com.ebeijia.module.wechat.dao.order.TOrderDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
 * Created by chen on 2016/7/12.
 */
@Service("TOrderService")
class TOrderServiceImpl extends TOrderService{
  @Autowired
  private val tOrderDao:TOrderDao = null

  //通过条件查询
//  @Cacheable(value = Array("TOrderCache"), key = "#root.method.name+#_type+#accountNo+#startTime+#endTime+#payStatus+#pageData")
  @Transactional
  def  findBySql(_type: String, startTime: String, endTime: String, payStatus: String, accountNo: String, pageData: String): util.Map[String,AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("from TOrder ")
    query.append(" where 1=1 ")
    if (_type != null && !_type.equals("")){
      query.append(" AND type ='").append(_type).append("'")
    }
    if (payStatus != null && !payStatus.equals("")){
      query.append(" AND payStatus ='").append(payStatus).append("'")
    }
    if (accountNo != null && !accountNo.equals("")){
      query.append(" AND accountNo = '").append(accountNo).append("'")
    }
    if(startTime != null && !startTime.equals("")){
      query.append(" AND createTime >= ").append(startTime).append("")
    }
    if(endTime != null && !endTime.equals("")){
      query.append(" AND createTime <= ").append(endTime).append("")
    }
    query.append(" order by createTime desc")
    val m: Map[String, AnyRef] = tOrderDao.findByPage(query.toString(), pageData)
    m
  }
}
