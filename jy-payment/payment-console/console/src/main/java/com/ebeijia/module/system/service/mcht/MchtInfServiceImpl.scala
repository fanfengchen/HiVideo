package com.ebeijia.module.system.service.mcht

import java.util
import java.util.{LinkedList, LinkedHashMap, List, Map}

import com.ebeijia.entity.system.brh.TblSysBrhInf
import com.ebeijia.module.system.dao.brh.TblSysBrhInfDao
import com.ebeijia.module.system.service.brh.SysBrhInfService
import com.ebeijia.module.wechat.dao.base.WechatMchtInfDao
import com.ebeijia.module.wechat.dao.mcht.MchtInfDao
import com.ebeijia.entity.system.system.{TblSysMchtInf, TblSysMchtInfId}
import com.ebeijia.entity.wechat.base.TblWechatMchtInf
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
  * 商户管理
  * MchtInfServiceImpl
  *
  * @author xiong.wang
  *         2016/6/23
  */
@Service
class MchtInfServiceImpl extends MchtInfService {
  @Autowired
  private val mchtInfDao: MchtInfDao = null
  @Autowired
  private val wechatMchtInfDao: WechatMchtInfDao = null
  @Autowired
  private val tblBrhInfDao: TblSysBrhInfDao = null
  @Autowired
  private val sysBrhInfService:SysBrhInfService = null

  @Transactional
  @Cacheable(value = Array("mchtCache"), key = "#root.method.name+#id")
  def getById(id: TblSysMchtInfId): TblSysMchtInf = {
    mchtInfDao.getById(id)
  }

  @Transactional
  @Cacheable(value = Array("mchtCache"), key = "#root.method.name+#mchtId+#brhNo+#mchtName+#pageData")
  def findBySql(brhNo:String,mchtId: String, mchtName: String, pageData: String): Map[String, AnyRef] = {
    val map:Map[String,AnyRef] = sysBrhInfService.getBrhBelowInf(brhNo)
    var brhStr:String = ""
    for (key <- map.keySet().toArray()) {
      brhStr = brhStr.concat("'").concat(key.toString).concat("',")
    }
    if(!brhStr.equals("")){
      brhStr = brhStr.substring(0,brhStr.lastIndexOf(","))
    }
    val query: StringBuilder = new StringBuilder
    query.append(" from TblSysMchtInf ")
    query.append(" where 1 = 1")
    if (mchtId != null && !("" == mchtId)) {
      query.append(" AND id.mchtId = '").append(mchtId).append("'")
    }
    if (mchtName != null && !("" == mchtName)) {
      query.append(" AND mchtName like'%").append(mchtName).append("%'")
    }
    query.append(" AND id.brhNo in (").append(brhStr).append(")")
    val m: Map[String, AnyRef] = mchtInfDao.findByPage(query.toString(), pageData)
    m
  }

  @Transactional
  @CacheEvict(value = Array("mchtCache"), allEntries = true) def update(data: TblSysMchtInf) {
    mchtInfDao.update(data)
  }

  @CacheEvict(value = Array("mchtCache"), allEntries = true)
  @Transactional def save(data: TblSysMchtInf) {
    mchtInfDao.save(data)
  }

  @CacheEvict(value = Array("mchtCache"), allEntries = true)
  @Transactional def saveOrUpdate(data: TblSysMchtInf) {
    mchtInfDao.saveOrUpdate(data)
  }

  @CacheEvict(value = Array("mchtCache"), allEntries = true)
  @Transactional
  def delById(id: TblSysMchtInfId) {
    mchtInfDao.deleteById(id)
  }

  def vailMchtUniq(mchtId: String): Boolean = {
    var flag: Boolean = false
    val sql: String = "SELECT id.brhNo FROM TblSysMchtInf WHERE id.mchtId = '" + mchtId + "'"
    val dataList: List[TblSysMchtInf] = mchtInfDao.find(sql)
    if (dataList.isEmpty || dataList.size() == 0) {
      flag = true
    }
    flag
  }

//  @Cacheable(value = Array("mchtCache"), key = "#root.method.name")
  def ListMchtId(brhNo:String): Map[String, AnyRef] = {
    val map: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
    val hql: String = "FROM TblSysMchtInf WHERE id.brhNo = '"+brhNo+"' ORDER BY id.mchtId "
    val tblMchtInfs: List[TblSysMchtInf] = mchtInfDao.find(hql)
    val list: List[AnyRef] = new LinkedList[AnyRef]
    import scala.collection.JavaConversions._
    for (tblMchtInf <- tblMchtInfs) {
      val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
      hashMap.put("key", tblMchtInf.getId.getMchtId + "-" + tblMchtInf.getId.getBrhNo)
      hashMap.put("value", tblMchtInf.getId.getMchtId)
      list.add(hashMap)
    }
    map.put("info", list)
    map
  }

  @Transactional
  @CacheEvict(value = Array("mchtCache"), allEntries = true)
  def selByIdAndName(tblSysMchtInf:TblSysMchtInf):Int = {
    val mchtId:String = tblSysMchtInf.getId.getMchtId
    val mchtName:String = tblSysMchtInf.getMchtName
    val mchtAddr:String = tblSysMchtInf.getMchtAddr
    val mchtPostCode:String = tblSysMchtInf.getMchtPostCd
    val mchtTel:String = tblSysMchtInf.getMchtTel
    val query: StringBuilder = new StringBuilder
    query.append(" from TblSysMchtInf ")
    query.append(" where 1 = 1")
    if (mchtId != null && !("" == mchtId)) {
      query.append(" AND id.mchtId = '").append(mchtId).append("'")
    }
    val smList: List[TblSysMchtInf] = mchtInfDao.find(query.toString())
    //0成功 1为空 2失败
    if (smList.size() == 0) {
      return 1
    } else {
      val sql: StringBuilder = new StringBuilder
      sql.append("UPDATE TBL_SYS_MCHT_INF SET MCHT_NAME ='").append(mchtName).append("',")
      sql.append(" MCHT_ADDR = '").append(mchtAddr).append("',")
      sql.append(" MCHT_POST_CD = '").append(mchtPostCode).append("',")
      sql.append(" MCHT_TEL = '").append(mchtTel).append("'")
      sql.append(" WHERE MCHT_ID = '").append(mchtId).append("'")
      try {
        mchtInfDao.executeSql(sql.toString())
        return 0
      } catch {
        case e: Exception => {
          return 2
        }
      }
    }
  }
}
