package com.ebeijia.module.system.service.brh

import java.util
import java.util._

import com.ebeijia.module.system.dao.brh.TblSysBrhInfDao
import com.ebeijia.entity.system.brh.TblSysBrhInf
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


/**
  * 机构
  * TblBrhInfService
  * @author xiong.wang
  *         15/8/17
  */
@Service
class SysBrhInfServiceImpl extends SysBrhInfService {
  private val logger: Logger = LoggerFactory.getLogger(classOf[SysBrhInfService])
  @Autowired
  private val tblBrhInfDao: TblSysBrhInfDao = null
//  /**机构信息*/
//  private val brhMap :Map[String, String]= new util.HashMap[String, String]()

  @Transactional
  @Cacheable(value = Array("tblBrhCache"), key = "#root.method.name+#id")
  def getById(brhId: String): TblSysBrhInf = {
    tblBrhInfDao.getById(brhId)
  }

  @Transactional
  @Cacheable(value = Array("tblBrhCache"), key = "#root.method.name+#roleId+#brhNo+#brhId+#brhName+#pageData")
  def findBySql(roleId:String,brhNo:String,brhId: String, brhName: String, pageData: String): Map[String, AnyRef] = {
    val tblSysBrhInf:TblSysBrhInf = tblBrhInfDao.getById(brhId)
    val map:Map[String,AnyRef] = this.getBrhBelowInf(brhId)
    var brhStr:String = ""
    for (key <- map.keySet().toArray()) {
      brhStr = brhStr.concat("'").concat(key.toString).concat("',")
    }
    if(!brhStr.equals("")){
      brhStr = brhStr.substring(0,brhStr.lastIndexOf(","))
    }
//    val hql:String = "from TblWechatMchtInf where id.brhNo in ("+brhStr+")"
//    wechatMchtInfDao.find(hql)
    val query: StringBuilder = new StringBuilder
    query.append(" from TblSysBrhInf ")
    query.append(" where 1 = 1")
    if(!roleId.equals("1")){
      if(brhNo.equals("") || brhNo == null){
        if (brhId != null && !("" == brhId)) {
          query.append(" AND brhId in(").append(brhStr).append(")")
        }
        if (brhName != null && !("" == brhName)) {
          query.append(" AND brhName ='").append(brhName).append("'")
        }
        query.append(" and brhLeavel >= "+tblSysBrhInf.getBrhLeavel+" ")
      }else{
        query.append(" AND brhId = '").append(brhNo).append("'")
        if (brhName != null && !("" == brhName)) {
          query.append(" AND brhName ='").append(brhName).append("'")
        }
      }
    }else{
      if(brhNo.equals("") || brhNo == null){
//        if (brhId != null && !("" == brhId)) {
//          query.append(" AND brhId  = '").append(brhStr).append("'")
//        }
        if (brhName != null && !("" == brhName)) {
          query.append(" AND brhName ='").append(brhName).append("'")
        }
      }else{
        query.append(" AND brhId = '").append(brhNo).append("'")
        if (brhName != null && !("" == brhName)) {
          query.append(" AND brhName ='").append(brhName).append("'")
        }
        query.append(" and brhLeavel >= "+tblSysBrhInf.getBrhLeavel+" ")
      }
    }
    val m: Map[String, AnyRef] = tblBrhInfDao.findByPage(query.toString(), pageData)
    m
  }

  @Transactional
  @CacheEvict(value = Array("tblBrhCache"), allEntries = true) def update(data: TblSysBrhInf) {
    tblBrhInfDao.update(data)
  }

  @CacheEvict(value = Array("tblBrhCache"), allEntries = true)
  @Transactional def save(brhId: String,data: TblSysBrhInf):String = {
    val map:Map[String,AnyRef] = this.getBrhBelowInf(brhId)
    var brhStr:String = ""
    for (key <- map.keySet().toArray()) {
      brhStr = brhStr.concat("'").concat(key.toString).concat("',")
    }
    if(!brhStr.equals("")){
      brhStr = brhStr.substring(0,brhStr.lastIndexOf(","))
    }
    val hql:String = "select max(upBrhId) from TblSysBrhInf where brhId in ("+brhStr+")"
    val minBrh:String = tblBrhInfDao.find(hql).get(0).toString
    var repStr:String = null
    if(!minBrh.equals("0")){
        if(minBrh.equals(data.getBrhLeavel)){
          repStr = "该机构下属机构已存在，请选择级别为"+(Integer.parseInt(minBrh)+1)+"的机构"
        }
    }
    tblBrhInfDao.save(data)
    repStr
  }
  @CacheEvict(value = Array("tblBrhCache"), allEntries = true)
  @Transactional def saveOrUpdate(data: TblSysBrhInf){
    tblBrhInfDao.saveOrUpdate(data)
  }

  @CacheEvict(value = Array("tblBrhCache"), allEntries = true)
  @Transactional
  def delById(brhId: String) :String={
    //是否有下级机构
    val sb:StringBuilder = new StringBuilder
    sb.append("FROM TblSysBrhInf WHERE upBrhId = '").append(brhId).append("' ")
    val list:List[TblSysBrhInf] = tblBrhInfDao.find(sb.toString())
    if(list.size() > 0){
      return "该机构下有子机构，请先删除子机构"
    }
    tblBrhInfDao.deleteById(brhId)
    return null
  }

  /**获取本机构与下属机构信息*/
  def getBrhBelowInf(brhId:String) :Map[String,AnyRef]={
    getBelowBrhMapCopy(brhId,brhId,null)
  }

  /*获取本行和下属机构*/
  def getBrhBelowId(brhId:String) :String={
    this.getBrhBelowBrhInf(
      this.getBelowBrhMap(brhId,brhId, null));
  }

  def getBrhBelowBrhInf(brhMap :Map[String,String]):String = {
    val belowBrhInfo :StringBuilder = new StringBuilder
    belowBrhInfo.append("(")
    val iter :util.Iterator[String]  = brhMap.keySet().iterator()
    while(iter.hasNext()) {
      val brhId :String = iter.next();
      belowBrhInfo.append("'").append(brhId).append("'")
      if(iter.hasNext()) {
        belowBrhInfo.append(",")
      }
    }
    belowBrhInfo.append(")");
     belowBrhInfo.toString()
  }

  def getBelowBrhMap(brhId:String ,id:String ,brhMap :Map[String,String]) : Map[String,String] ={
    val sql :String = "SELECT brhId,brhName FROM TblSysBrhInf WHERE upBrhId = '" + id +"'"
    val dataList :List[TblSysBrhInf] = tblBrhInfDao.find(sql)
    var tmp: Map[String, String] = null
    if(brhMap != null){
      tmp = brhMap
    }else{
      tmp = new util.LinkedHashMap[String,String]
    }
    if(!"".equals(brhId)){
      tmp.put(brhId,brhId)
    }
    val it: Iterator[_] = dataList.iterator
    while (it.hasNext) {
      val o: Array[AnyRef] = it.next.asInstanceOf[Array[AnyRef]]
      tmp.put(o(0).toString,o(1).toString)
      tmp = getBelowBrhMap("",o(0).toString,tmp)
    }
    tmp
  }


  def getBelowBrhMapCopy(brhId:String ,id:String ,brhMap :Map[String,AnyRef]) : Map[String,AnyRef] ={
    val sql :String = "SELECT brhId,brhName FROM TblSysBrhInf WHERE upBrhId = '" + id +"'"
    val dataList :List[TblSysBrhInf] = tblBrhInfDao.find(sql)
    var tmp: Map[String, AnyRef] = null
    if(brhMap != null){
      tmp = brhMap
    }else{
      tmp = new util.LinkedHashMap[String,AnyRef]
    }
    if(!"".equals(brhId)){
      val sql :String = "FROM TblSysBrhInf WHERE brhId = '" + id +"'"
      val dList :List[TblSysBrhInf] = tblBrhInfDao.find(sql)
      tmp.put(brhId,dList.get(0).getBrhName)
    }
    val it: Iterator[_] = dataList.iterator
    while (it.hasNext) {
      val o: Array[AnyRef] = it.next.asInstanceOf[Array[AnyRef]]
      tmp.put(o(0).toString,o(1).toString)
      tmp = getBelowBrhMapCopy("",o(0).toString,tmp)
    }
    tmp
  }


  def vailBrhUniq(brhId: String): Boolean = {
    var flag:Boolean = false
    val sql :String = "SELECT brhId FROM TblSysBrhInf WHERE brhId = '" + brhId +"'"
    val dataList :List[TblSysBrhInf] = tblBrhInfDao.find(sql)
    if(dataList.isEmpty || dataList.size() == 0){
      flag = true
    }
    flag
  }

  def getBrhList(brhNo:String):Map[String, AnyRef] ={
    val tmp: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]()
    val sql :String = "SELECT brhId,brhName FROM TblSysBrhInf WHERE brhId ='".concat(brhNo).concat("'")
    val dataList :List[_] = tblBrhInfDao.find(sql)
    tmp.put("brhList",dataList)
    tmp
  }

  def getAllBrhList(brhNo:String,brhLeavel:String):Map[String, AnyRef] = {
    val map:Map[String,AnyRef] = this.getBrhBelowInf(brhNo)
    var brhStr:String = ""
    for (key <- map.keySet().toArray()) {
      brhStr = brhStr.concat("'").concat(key.toString).concat("',")
    }
    if(!brhStr.equals("")){
      brhStr = brhStr.substring(0,brhStr.lastIndexOf(","))
    }
    val tmp: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]()
    val sql :String = "SELECT brhId,brhName,upBrhId FROM TblSysBrhInf where brhId in ("+brhStr+") and brhLeavel <= "+brhLeavel+" "
    val dataList :List[_] = tblBrhInfDao.find(sql)
    tmp.put("brhInfoList",dataList)
    tmp
  }

  //获取当前机构下最小级别机构等级
  def selUpBrh(brhId:String): String = {
    val sql :String = "SELECT MAX(brhLeavel)FROM TblSysBrhInf where upBrhId = ?"
    tblBrhInfDao.find(sql,brhId).get(0).toString
  }
}
