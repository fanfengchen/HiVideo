package com.ebeijia.module.wechat.service.notice

import java.util
import java.util.Map

import com.ebeijia.entity.wechat.notice.TNoticeT
import com.ebeijia.module.wechat.dao.notice.TNoticeDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TNoticeServiceImpl extends TNoticeService{

  @Autowired
  private val tNoticeDao: TNoticeDao = null

  //根据id查找通知
  @Transactional
  @Cacheable(value = Array("TNoticeTCache"), key = "#root.method.name+#id")
  def getById(id: Int): TNoticeT = {
    tNoticeDao.getById(id)
  }

  //通过条件查询结果
  @Transactional
  @Cacheable(value = Array("TNoticeTCache"), key = "#root.method.name+#title+#id+#pageData")
  def findBySql(title: String, id: String, pageData: String): util.Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("from TNoticeT")
    query.append(" where 1 = 1 ")
    if(title != null && !"".equals(title)){
      query.append(" AND title like '%").append(title).append("%'")
    }
    if(id != null && !"".equals(id) ){
      query.append(" AND id = '").append(id).append("'")
    }
    query.append("  order by  id desc")
    val m: Map[String, AnyRef] = tNoticeDao.findByPage(query.toString(), pageData)
    m
  }

  //通知更新
  @Transactional
  @CacheEvict(value = Array("TNoticeTCache"), allEntries = true)
  def update(data: TNoticeT){
    tNoticeDao.update(data)
  }

  //删除通知
  @Transactional
  @CacheEvict(value = Array("TNoticeTCache"), allEntries = true)
  def delById(id: Int){
    tNoticeDao.deleteById(id)
  }

  //添加通知
  @Transactional
  @CacheEvict(value = Array("TNoticeTCache"), allEntries = true)
  def save(data: TNoticeT){
    tNoticeDao.save(data)
  }
}
