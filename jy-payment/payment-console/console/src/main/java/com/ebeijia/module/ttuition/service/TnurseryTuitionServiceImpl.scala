package com.ebeijia.module.ttuition.service

import java.util.{List, Map}

import com.ebeijia.entity.ttuition.TnurseryTuition
import com.ebeijia.module.ttuition.dao.TnurseryTuitionDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
  * TnurseryTuitionServiceImpl
  * 学生缴费管理
  * @author xiong.wang
  */
@Service
class TnurseryTuitionServiceImpl extends TnurseryTuitionService {

  @Autowired
  private val tnurseryTuitionDao: TnurseryTuitionDao = null

  @Transactional
  @Cacheable(value = Array("TnurseryTuitionCache"), key = "#root.method.name+#id")
  def getById(id: Int): TnurseryTuition = {
    tnurseryTuitionDao.getById(id)
  }

  @Transactional
  @Cacheable(value = Array("TnurseryTuitionCache"), key = "#root.method.name+#studentName+#payState+#pageData")
  def findBySql(studentName: String, payState: String, pageData: String): Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("from TnurseryTuition")
    query.append(" where 1 = 1 ")
    if (studentName != null && !studentName.equals("")) {
      query.append(" AND studentName like '%").append(studentName).append("%'")
    }
    if (payState != null && !payState.equals("")) {
      query.append(" AND payStatus = ").append(payState).append("")
    }
    query.append("  order by  id desc")
    val m: Map[String, AnyRef] = tnurseryTuitionDao.findByPage(query.toString(), pageData)
    m
  }


  @Transactional
  @CacheEvict(value = Array("TnurseryTuitionCache"), allEntries = true)
  def update(data: TnurseryTuition) {
    tnurseryTuitionDao.update(data)
  }

  @CacheEvict(value = Array("TnurseryTuitionCache"), allEntries = true)
  @Transactional
  def save(data: TnurseryTuition) {
    tnurseryTuitionDao.save(data)
  }

  @CacheEvict(value = Array("TnurseryTuitionCache"), allEntries = true)
  @Transactional
  def delById(id: Int) {
    tnurseryTuitionDao.deleteById(id)
  }

  @Transactional
  @CacheEvict(value = Array("TnurseryTuitionCache"), allEntries = true)
  def saveExcel(tt: List[TnurseryTuition]) = {
    for (i <- 0 until tt.size()) {
      tnurseryTuitionDao.save(tt.get(i))
    }
  }
}
