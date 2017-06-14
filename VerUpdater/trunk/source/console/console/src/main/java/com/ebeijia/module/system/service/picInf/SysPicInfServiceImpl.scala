package com.ebeijia.module.system.service.picInf

import java.util.List

import com.ebeijia.module.system.dao.picInf.SysPicInfDao
import com.ebeijia.entity.system.system.TblSysPicInf
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * PicInfServiceImpl
 * @author zhicheng.xu
 *         15/8/13
 */


@Service final class SysPicInfServiceImpl extends SysPicInfService {
  @Autowired
  private val picInfDao: SysPicInfDao = null

  @Transactional
  @CacheEvict(value = Array("picCache"), allEntries = true)
  def updatePicInf(picInf: TblSysPicInf) {
    picInfDao.saveOrUpdate(picInf)
  }

  @Transactional
  @CacheEvict(value = Array("picCache"), allEntries = true)
  def deletePicInf(picInf: TblSysPicInf) {
    picInfDao.update(picInf)
  }

  @Transactional
  @CacheEvict(value = Array("picCache"), allEntries = true)
  def save(picInf: TblSysPicInf) {
    picInfDao.save(picInf)
  }

  @Transactional
  @Cacheable(value = Array("picCache"))
  def queryPicInfList: List[_] = {
    picInfDao.getPicInfList
  }

  @Transactional
  @Cacheable(value = Array("picCache"))
  def queryPageList(picInf: TblSysPicInf, page: Int, size: Int): List[TblSysPicInf] = {
    picInfDao.findByPage(picInf, page, size)
  }

  @Transactional
  @Cacheable(value = Array("picCache"), key = "#root.method.name+#picInf")
  def countTotalNum(picInf: TblSysPicInf): Int = {
    picInfDao.countTotalNum(picInf)
  }

  @Transactional
  @Cacheable(value = Array("picCache"), key = "#root.method.name+#id")
  def queryPicInfById(id: Int): TblSysPicInf = {
    picInfDao.getById(id)
  }
  /**
   * 最大id+1
   * */
  def maxId:Int={
    picInfDao.getMaxId()+1
  }

}
