package com.ebeijia.module.wechat.service.group

import java.util
import java.util.{LinkedHashMap, LinkedList, List, Map}

import com.ebeijia.module.wechat.dao.base.{WechatMchtInfDao, WechatSubGroupDao}
import com.ebeijia.entity.wechat.base.{TblWechatSubscribe, TblWechatSubGroup, TblWechatSubGroupId, TblWechatMchtInf}
import com.ebeijia.module.wechat.service.core.{WechatMchtInfService, WechatTokenService}
import com.ebeijia.module.wechat.service.inter.GroupManager
import com.ebeijia.module.wechat.service.subscribe.WechatSubscribeService
import com.ebeijia.util.wechat.WechatUtil
import net.sf.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.ebeijia.entity.vo.wechat.AccessToken
import com.ebeijia.entity.vo.wechat.button.Group
/**
 * WechatSubGroupServiceImpl
  *
  * @author zhicheng.xu
 *         15/8/13
 *         关注者组别
 */


@Service final class WechatSubGroupServiceImpl extends WechatSubGroupService {
  @Autowired
  private val wechatSubGroupDao: WechatSubGroupDao = null
  @Autowired
  private val wechatSubscribeService: WechatSubscribeService = null
  @Autowired
  private val wechatMchtInfService: WechatMchtInfService = null
  @Autowired
  private val wechatTokenService: WechatTokenService = null
  @Autowired
  private val groupManager: GroupManager = null
  @Autowired
  private val wechatMchtInfDao: WechatMchtInfDao = null
  @Transactional
  @CacheEvict(value = Array("wechatSubGroupCache"), allEntries = true)
  def save(mchtId: String, name: String): Map[String, AnyRef] = {
    val map:Map[String, AnyRef] = new util.HashMap[String, AnyRef]()
    var result: String = null
    val gdata: TblWechatSubGroup = new TblWechatSubGroup
 /*   val tblWechatMchtInf: TblWechatMchtInf = wechatMchtInfService.getById(mchtId)
    val at: AccessToken = wechatTokenService.getAccessToken(tblWechatMchtInf.getAppid, tblWechatMchtInf.getAppsecret)*/
    //查询商户信息，商户信息只有一条
 val data: TblWechatMchtInf = wechatMchtInfService.queryWechatMchtInf("10001")
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    if (null != at) {
      val jsonObject: JSONObject = groupManager.createGroup(name, at.getToken)
      if (null != jsonObject) {
        if (WechatUtil.getWechatCallBackStatus(jsonObject.toString) != 0) {
          result = jsonObject.getString("errcode")
          return null
        } else {
          val group: String = jsonObject.getJSONObject("group").getString("id")
//          val data: TblWechatSubGroup = new TblWechatSubGroup
          val sid :TblWechatSubGroupId = new TblWechatSubGroupId
          sid.setId(group)
          sid.setMchtId(mchtId)
          gdata.setId(sid)
          gdata.setName(name)
          wechatSubGroupDao.save(gdata)
        }
      } else {
        result = "8888888"
      }
    } else {
      result = "9999999"
    }
    if(result != null){
      map.put("result",result)
    }else if(gdata != null){
      map.put("gdata",gdata)
    }
    map
  }

  @Transactional
  @CacheEvict(value = Array("wechatSubGroupCache","wechatUsrCache"), allEntries = true)
  def update(mchtId: String, groupId: String, name: String): String = {
    var result: String = null
   /* val tblWechatMchtInf: TblWechatMchtInf = wechatMchtInfService.getById(mchtId)
    val at: AccessToken = wechatTokenService.getAccessToken(tblWechatMchtInf.getAppid, tblWechatMchtInf.getAppsecret)*/
   //查询商户信息，商户信息只有一条
   val data: TblWechatMchtInf = wechatMchtInfService.queryWechatMchtInf(mchtId)
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    if (null != at) {
      val group: Group = new Group
      group.setId(groupId)
      group.setName(name)
      val jsonObject: JSONObject = groupManager.modGroup(group, at.getToken)
      if (null != jsonObject) {
        if (!("0" == jsonObject.getString("errcode"))) {
          result = jsonObject.getString("errcode")
        }
        else {
          val data: TblWechatSubGroup = new TblWechatSubGroup
          val sid:TblWechatSubGroupId = new TblWechatSubGroupId
          sid.setId(groupId)
          sid.setMchtId(mchtId)
          data.setId(sid)
          data.setName(name)
          wechatSubGroupDao.update(data)
          val sb:StringBuilder = new StringBuilder
          sb.append("UPDATE TBL_WECHAT_SUBSCRIBE SET GROUP_NAME ='").append(name).append("',")
          sb.append(" GROUP_ID= '").append(groupId).append("'")
          sb.append(" WHERE GROUP_ID ='").append(groupId).append("'")
          wechatSubGroupDao.executeSql(sb.toString())
        }
      }
    } else {
      result = "9999999"
    }
    result
  }

  @Transactional
  @CacheEvict(value = Array("wechatSubGroupCache"), allEntries = true)
  def del(mchtId: String, groupId: String): String = {
    var result: String = null
   /* val tblWechatMchtInf: TblWechatMchtInf = wechatMchtInfService.getById(mchtId)
    val at: AccessToken = wechatTokenService.getAccessToken(tblWechatMchtInf.getAppid, tblWechatMchtInf.getAppsecret)*/
   //查询商户信息，商户信息只有一条
   val data: TblWechatMchtInf = wechatMchtInfService.queryWechatMchtInf("10001")
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    if (null != at) {
      val jsonObject: JSONObject = groupManager.delGroup(groupId, at.getToken)
      if (null != jsonObject) {
        if (!("0" == jsonObject.getString("errcode"))) {
          result = jsonObject.getString("errcode")
        }
        else {
          val data: TblWechatSubGroup = new TblWechatSubGroup
          val sid: TblWechatSubGroupId = new TblWechatSubGroupId
          sid.setId(groupId)
          sid.setMchtId(mchtId)
          data.setId(sid)
          wechatSubGroupDao.delete(data)
          wechatSubscribeService.upGroup(groupId)
        }
      }
    } else {
      result = "9999999"
    }
    result
  }

  @Transactional
  @Cacheable(value = Array("wechatSubGroupCache"), key = "#root.method.name+#name+#pageData")
  def findBySql(mchtId: String,name: String, pageData: String): Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append(" from TblWechatSubGroup ")
    query.append(" where 1=1")
    query.append(" AND id.mchtId = '").append(mchtId).append("'")
    if (name != null && !("" .equals(name) )) {
      query.append(" AND name like '%").append(name).append("%'")
    }
//    query.append(" AND id.id <> '0' ")
    query.append(" order by id.id desc")
    val m: Map[String, AnyRef] = wechatSubGroupDao.findByPage(query.toString, pageData)
    m
  }

  @Transactional
  @Cacheable(value = Array("wechatSubGroupCache"), key = "#root.method.name+#mchtId")
  def listGroupByMchtId(mchtId: String): Map[String, AnyRef] = {
    val map: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
    val sb: StringBuilder = new StringBuilder
    //没有商户号
    //sb.append("FROM TblWechatSubGroup  where id.mchtId ='")
    sb.append("FROM TblWechatSubGroup  where 1=1")
    sb.append(" order by id.id desc")
    val list: List[TblWechatSubGroup] = wechatSubGroupDao.find(sb.toString)
    val result: List[AnyRef] = new LinkedList[AnyRef]
    import scala.collection.JavaConversions._
    for (data <- list) {
      val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
      hashMap.put("key", data.getId + "-" + data.getName)
      hashMap.put("value", data.getId)
      result.add(hashMap)
    }
    map.put("info", result)
    map
  }

  @Transactional
  @Cacheable(value = Array("wechatSubGroupCache"), key = "#root.method.name+#id")
  def getById(id: TblWechatSubGroupId): TblWechatSubGroup = {
    wechatSubGroupDao.getById(id)
  }

  @Transactional
  @Cacheable(value = Array("wechatSubGroupCache"), key = "#root.method.name+#mchtId")
  def findCount(mchtId: String): Int = {
    val sb: StringBuilder = new StringBuilder
   // sb.append("From TblWechatSubGroup where id.mchtId ='").append(mchtId).append("'")
    sb.append("From TblWechatSubGroup where 1=1")
    val total: Int = wechatSubGroupDao.getTotalRows(sb.toString)
    total
  }


  /**
   * 判断唯一
   *
   */
  @Transactional
  @Cacheable(value = Array("wechatSubGroupCache"), key = "#root.method.name+#id+#name")
  def isSubGroup(id:Int,name:String): Int={
    wechatSubGroupDao.isSubGroup(id,name)
  }

}