package com.ebeijia.module.wechat.service.core

import java.util
import java.util.{LinkedHashMap, LinkedList, List, Map}

import com.ebeijia.entity.system.system.TblSysOperatorInf
import com.ebeijia.module.system.dao.operatorInf.SysOperatorInfDao
import com.ebeijia.module.system.service.brh.SysBrhInfService
import com.ebeijia.module.system.service.operatorInf.SysOperatorService
import com.ebeijia.module.wechat.dao.base.WechatMchtInfDao
import com.ebeijia.module.wechat.dao.mcht.MchtInfDao
import com.ebeijia.entity.wechat.base.{TblWechatMchtInf, TblWechatMchtInfId}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * WechatMchtInfServiceImpl
  *
  * @author zhicheng.xu
 *         15/8/13
 */


@Service
final class WechatMchtInfServiceImpl extends WechatMchtInfService {
  @Autowired
  private val wechatMchtInfDao: WechatMchtInfDao = null
  @Autowired
  private val mchtInfDao: MchtInfDao = null
  @Autowired
  private val sysBrhInfService:SysBrhInfService = null
  @Autowired
  private val operatorInfDao: SysOperatorInfDao = null

  @Transactional
  @CacheEvict(value = Array("wechatMchtCache"), allEntries = true)
  def updateWechatMchtInf(wechatMchtInf: TblWechatMchtInf) {
    wechatMchtInfDao.saveOrUpdate(wechatMchtInf)
  }

  @Transactional
  @CacheEvict(value = Array("wechatMchtCache"), allEntries = true)
  def deleteWechatMchtInf(wechatMchtInf: TblWechatMchtInf) {
    wechatMchtInfDao.delete(wechatMchtInf)
  }

  @Transactional
  @CacheEvict(value = Array("wechatMchtCache"), allEntries = true)
  def addWechatMchtInf(wechatMchtInf: TblWechatMchtInf): String = {
    //检查是否已经绑定
    val hql: StringBuilder = new StringBuilder
    hql.append("from TblWechatMchtInf where id.mchtId ='").append(wechatMchtInf.getId.getMchtId).append("'")
    hql.append(" or  nickName ='").append(wechatMchtInf.getNickname).append("'")
    hql.append(" or  appId ='").append(wechatMchtInf.getAppid).append("'")
    hql.append(" or  appsecret ='").append(wechatMchtInf.getAppsecret).append("'")
    val list: List[TblWechatMchtInf] = wechatMchtInfDao.find(hql.toString())
    var respMsg: String = ""
    if (list.isEmpty) {
      wechatMchtInfDao.saveOrUpdate(wechatMchtInf)
    }
    else {
      respMsg = "0"
    }
    respMsg
  }

  @Transactional
  @Cacheable(value = Array("wechatMchtCache"), key = "#root.method.name")
  def queryWechatMchtInf(mchtId:String): TblWechatMchtInf = {
    val tblWechatMchtInfId :TblWechatMchtInfId = new TblWechatMchtInfId
    val mchtHql:String = "from TblWechatMchtInf where id.mchtId = '"+mchtId+"'"
    val lst:List[TblWechatMchtInf] = wechatMchtInfDao.find(mchtHql)
    if(lst.isEmpty || lst.size() == 0){
      return null
    }
    tblWechatMchtInfId.setBrhNo(lst.get(0).getId.getBrhNo)
    tblWechatMchtInfId.setMchtId(mchtId)
    val tmp :TblWechatMchtInf =wechatMchtInfDao.getById(tblWechatMchtInfId)
    println("tmp.getAppid = " + tmp.getAppid)
    tmp
  }

  @Transactional
  @Cacheable(value = Array("wechatMchtCache"), key = "#root.method.name+#id")
  def getById(id: TblWechatMchtInfId): TblWechatMchtInf = {
    wechatMchtInfDao.getById(id)
  }

  @Transactional
  @Cacheable(value = Array("wechatMchtCache"), key = "#root.method.name")
  def findMchtList: Map[String, AnyRef] = {
    val map: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
    val mchtList: List[Array[AnyRef]] = mchtInfDao.findMchtList
    val list: List[AnyRef] = new LinkedList[AnyRef]
    import scala.collection.JavaConversions._
    for (obj <- mchtList) {
      val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
      hashMap.put("key", obj(0) + "-" + obj(1))
      hashMap.put("value", obj(0))
      list.add(hashMap)
    }
    map.put("info", list)
    map
  }

  @Transactional
  @Cacheable(value = Array("wechatMchtCache"), key = "#root.method.name+#mchtId+#pagaData")
  def findBySql(mchtId: String, pagaData: String): Map[String, AnyRef] = {
    val query: StringBuffer = new StringBuffer
    query.append("from TblWechatMchtInf")
    query.append(" where 1=1")
    if (mchtId != null && !("" == mchtId)) {
      query.append(" AND id.mchtId like '%").append(mchtId).append("%'")
    }
    query.append(" ORDER BY id.mchtId")
    val m: Map[String, AnyRef] = wechatMchtInfDao.findByPage(query.toString, pagaData)
    m
  }

  @Transactional
  @Cacheable(value = Array("wechatMchtCache"), key = "#root.method.name+#nickName")
  def findByNikeName(nickName: String): TblWechatMchtInf = {
    val sb: StringBuilder = new StringBuilder
    sb.append("from TblWechatMchtInf where nickName = '").append(nickName).append("'")
    val list: List[TblWechatMchtInf] = wechatMchtInfDao.find(sb.toString)
    if (list.isEmpty) {
      null
    }
    else {
      list.get(0)
    }
  }

  @Transactional
  @Cacheable(value = Array("wechatMchtCache"), key = "#root.method.name+#appid")
  def getByAppid(appid: String): TblWechatMchtInf = {
    val sb: StringBuilder = new StringBuilder
    sb.append("from TblWechatMchtInf where appid = '").append(appid).append("'")
    val list: List[TblWechatMchtInf] = wechatMchtInfDao.find(sb.toString)
    if (list.isEmpty) {
      null
    }
    else {
      list.get(0)
    }
  }

  /**检查是否绑定
   * */
  @Transactional
  @Cacheable(value = Array("wechatMchtCache"), key = "#root.method.name+#wechatMchtInf")
  def isListMchtInf(wechatMchtInf:TblWechatMchtInf): List[TblWechatMchtInf] ={
    //检查是否已经绑定
    val hql: StringBuilder = new StringBuilder
    hql.append("from TblWechatMchtInf where (id.mchtId ='").append(wechatMchtInf.getId.getMchtId).append("'")
    hql.append(" or  nickName ='").append(wechatMchtInf.getNickname).append("'")
    hql.append(" or  appId ='").append(wechatMchtInf.getAppid).append("'")
    hql.append(" or  appsecret ='").append(wechatMchtInf.getAppsecret).append("')")
    hql.append(" and  id.mchtId not in('").append(wechatMchtInf.getId.getMchtId).append("')")
      wechatMchtInfDao.find(hql.toString())
  }
  @Transactional
  @Cacheable(value = Array("wechatMchtCache"), key = "#root.method.name+#brhNo")
  def selbrhMchtInf(brhNo:String): List[TblWechatMchtInf] ={
//    val map:Map[String,AnyRef] = sysBrhInfService.getBrhBelowInf(brhNo)
//    var brhStr:String = ""
//    for (key <- map.keySet().toArray()) {
//      brhStr = brhStr.concat("'").concat(key.toString).concat("',")
//    }
//    if(!brhStr.equals("")){
//      brhStr = brhStr.substring(0,brhStr.lastIndexOf(","))
//    }
    val hql:String = "from TblWechatMchtInf where id.brhNo = '"+brhNo+"'"
    wechatMchtInfDao.find(hql)
  }

  def selbrhdel(brhNo:String): Int ={
    val hql1:String = "from TblWechatMchtInf where id.brhNo = '"+brhNo+"'"
    val list1:List[TblWechatMchtInf] = wechatMchtInfDao.find(hql1)
    if(list1.size() != 0){
      return 1
    }
    val hql2:String = "from TblSysOperatorInf where id.brhNo = '"+brhNo+"'"
    val list2:List[TblSysOperatorInf] = operatorInfDao.find(hql2)
    if(list2.size() != 0){
      return 2
    }
    val hql3:String = "from TblSysRoleInf where id.brhNo = '"+brhNo+"'"
    val list3:List[TblSysOperatorInf] = operatorInfDao.find(hql3)
    if(list3.size() != 0){
      return 3
    }
    return 0
  }

  @Transactional
  @Cacheable(value = Array("wechatMchtCache"), key = "#root.method.name+#id")
  def selMchtList(id:String):TblWechatMchtInf ={
    val hql:String = "from TblWechatMchtInf where token = '"+id+"'"
    wechatMchtInfDao.find(hql).get(0)
  }
}
