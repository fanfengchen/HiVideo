package com.ebeijia.module.ttuition.service

import java.util.{List, Map}

import com.ebeijia.entity.ttuition.Ttuition
import com.ebeijia.module.ttuition.dao.TtuitionDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TtuitionServiceImpl extends TtuitionService{

  @Autowired
  private val ttuitionDao: TtuitionDao = null

  @Transactional
  @Cacheable(value = Array("TtuitionCache"), key = "#root.method.name+#id")
  def getById(id: Int): Ttuition = {
    ttuitionDao.getById(id)
  }

  @Transactional
  @Cacheable(value = Array("TtuitionCache"), key = "#root.method.name+#studentName+#payState+#pageData")
  def findBySql(studentName: String,payState:String,pageData: String): Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("from Ttuition")
    query.append(" where 1 = 1 ")
    if(studentName != null && !studentName.equals("")){
      query.append(" AND studentName like '%").append(studentName).append("%'")
    }
    if(payState != null && !payState.equals("")){
      query.append(" AND payStatus = ").append(payState).append("")
    }
    query.append("  order by  id desc")
    val m: Map[String, AnyRef] = ttuitionDao.findByPage(query.toString(), pageData)
    m
  }


  @Transactional
  @CacheEvict(value = Array("TtuitionCache"), allEntries = true)
  def update(data: Ttuition) {
    ttuitionDao.update(data)
  }

  @CacheEvict(value = Array("TtuitionCache"), allEntries = true)
  @Transactional
  def save(data: Ttuition){
    ttuitionDao.save(data)
  }

  @CacheEvict(value = Array("TtuitionCache"), allEntries = true)
  @Transactional
  def delById(id: Int) {
    ttuitionDao.deleteById(id)
  }

  @Transactional
  @CacheEvict(value = Array("TtuitionCache"), allEntries = true)
  def saveExcel(tt:List[Ttuition])={
    for (i <- 0 until tt.size()) {
      ttuitionDao.save(tt.get(i))
    }
  }
}
