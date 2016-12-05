package com.ebeijia.module.wechat.service.orderDetail

import java.util
import java.util.Map

import com.ebeijia.module.wechat.dao.orderDetail.TOrderDetailDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("TOrderDetailService")
class TOrderDetailServiceImpl extends TOrderDetailService{
  @Autowired
  private val tOrderDetailDao: TOrderDetailDao = null

  //通过条件查询结果
  //  @Cacheable(value = Array("TOrderDetailCache"), key = "#root.method.name+#orderId+#startTime+#endTime+#pageData")
  @Transactional
  def findBySql(orderId: String, startTime: String,endTime: String, pageData: String): util.Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("from TOrderDetailT")
    query.append(" where 1 = 1 ")
    if(orderId != null && !"".equals(orderId)){
      query.append(" AND orderId ='").append(orderId).append("'")
    }
    if(startTime != null && !"".equals(startTime)){
      query.append(" AND payTime >= ").append(startTime).append("")
    }
    if(endTime != null && !"".equals(endTime)){
      query.append(" AND payTime <= ").append(endTime).append("")
    }
    query.append("  order by  id desc")
    val m: Map[String, AnyRef] = tOrderDetailDao.findByPage(query.toString(), pageData)
    m
  }
}
