package com.ebeijia.module.ttuition.service

import java.util.Map

import com.ebeijia.entity.ttuition.TPayItem
import com.ebeijia.module.ttuition.dao.TPayItemDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
  * TPayItemServiceImpl
  * 学校缴费项目管理
  * @author xiong.wang
  */
@Service
class TPayItemServiceImpl extends TPayItemService{
  @Autowired
  private val tPayItemDao: TPayItemDao = null

  @Transactional
  @Cacheable(value = Array("TPayItemCache"), key = "#root.method.name+#id")
  def getById(id: Int): TPayItem = {
    tPayItemDao.getById(id)
  }

  @Transactional
  @Cacheable(value = Array("TPayItemCache"), key = "#root.method.name+#name+#status+#pageData")
  def findBySql(name: String,status:String,pageData: String): Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("from TPayItem")
    query.append(" where 1 = 1 ")
    if(name != null && !name.equals("")){
      query.append(" AND name like '%").append(name).append("%'")
    }
    if(status != null && !status.equals("")){
      query.append(" AND status = ").append(status).append("")
    }
    query.append("  order by  id desc")
    val m: Map[String, AnyRef] = tPayItemDao.findByPage(query.toString(), pageData)
    m
  }


  @Transactional
  @CacheEvict(value = Array("TPayItemCache"), allEntries = true)
  def update(data: TPayItem) {
    tPayItemDao.update(data)
  }

  @CacheEvict(value = Array("TPayItemCache"), allEntries = true)
  @Transactional
  def save(data: TPayItem){
    tPayItemDao.save(data)
  }

  @CacheEvict(value = Array("TPayItemCache"), allEntries = true)
  @Transactional
  def delById(id: Int) {
    tPayItemDao.deleteById(id)
  }
}
