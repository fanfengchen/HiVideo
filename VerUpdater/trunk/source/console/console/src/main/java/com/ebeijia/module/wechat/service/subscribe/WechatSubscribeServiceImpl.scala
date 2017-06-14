package com.ebeijia.module.wechat.service.subscribe

import java.util
import java.util.{ArrayList, List, Map}

import com.ebeijia.module.wechat.dao.base.{WechatSubGroupDao, WechatMchtInfDao, WechatSubscribeDao}
import com.ebeijia.entity.vo.wechat.button.{UsrToGroup, UsrsToGroup, ModUsr}
import com.ebeijia.entity.wechat.base._
import com.ebeijia.module.wechat.service.CoreUtfm
import com.ebeijia.module.wechat.service.core.{WechatMchtInfService, WechatTokenService}
import com.ebeijia.module.wechat.service.inter.{GroupManager, UsrManager}
import com.ebeijia.util.wechat.WechatUtil
import net.sf.json.{JSONArray, JSONObject}
import org.ebeijia.tools.DateTime4J
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.ebeijia.entity.vo.wechat.AccessToken
/**
 * WechatSubscribeServiceImpl
  *
  * @author zhicheng.xu
 *         15/8/14
 *    微信关注者信息
 */

@Service("wechatSubscribeService")
final class WechatSubscribeServiceImpl extends WechatSubscribeService {
  @Autowired
  private val wechatMchtInfDao: WechatMchtInfDao = null
  @Autowired
  private val wechatMchtInfService: WechatMchtInfService = null
  @Autowired
  private val wechatTokenService: WechatTokenService = null
  @Autowired
  private val usrManager: UsrManager = null
  @Autowired
  private val wechatSubscribeDao: WechatSubscribeDao = null
  @Autowired
  private val wechatSubGroupDao:WechatSubGroupDao = null
  @Autowired
  private val groupManager:GroupManager = null

  @Transactional
  @Cacheable(value = Array("wechatUsrCache"), key = "#root.method.name+#mchtId+#groupId+#subcribeId+#openid+#nickname+#pageData")
  def findBySql(mchtId:String,groupId:String,subcribeId: String, openid: String,nickname:String, pageData: String): Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("from TblWechatSubscribe")
    query.append(" where 1=1")
    query.append(" AND id.mchtId = '").append(mchtId).append("'")
    if (subcribeId != null && !("" .equals(subcribeId) )) {
      //query.append(" AND mchtId like '%").append(mchtId).append("%'")
      query.append(" AND id.subcribeId like '%").append(subcribeId).append("%'")
    }
    if (openid != null && !("" .equals(openid) )) {
      query.append(" AND openid like '%").append(openid).append("%'")
    }
    if (nickname != null && !("" .equals(nickname) )) {
      query.append(" AND nickname like '%").append(nickname).append("%'")
    }
    if (groupId != null && !("" .equals(groupId) )) {
      query.append(" AND groupId = '").append(groupId).append("'")
    }
    query.append(" AND subscribeTiny <> 0 ")
    query.append(" ORDER BY subscribeTime desc")
    wechatSubscribeDao.findByPage(query.toString, pageData)
  }

  @Transactional
  @Cacheable(value = Array("wechatUsrCache"), key = "#root.method.name+#groupId+#pageData")
  def findByBatch(groupId: String, pageData: String): Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("from TblWechatSubscribe")
   // query.append(" where mchtId ='").append(mchtId).append("'")
    //query.append(" where subcribeId ='").append(subcribeId).append("'")
    query.append(" where 1=1 and  subscribeTiny =1")
    if ("0" == groupId) {
      query.append(" AND groupId is not null and groupId not in('')")
    } else {
      query.append(" AND (groupId <>'").append(groupId).append("' or groupId is null or groupId ='')")
    }
    query.append(" ORDER BY subscribeTime desc")
    wechatSubscribeDao.findByPage(query.toString, pageData)
  }

  @Transactional
  @Cacheable(value = Array("wechatUsrCache"), key = "#root.method.name+#groupId")
  def findByGroup(groupId: String): List[TblWechatSubscribe] = {
    val query: StringBuilder = new StringBuilder
    query.append("from TblWechatSubscribe")
    //query.append(" where mchtId ='").append(mchtId).append("'")
   // query.append(" where subcribeId ='").append(subcribeId).append("'")
    query.append(" where 1=1 and  subscribeTiny =1")
    if ("0" == groupId) {
      query.append(" AND groupId is null or groupId =''")
    }
    else {
      query.append(" AND groupId ='").append(groupId).append("'")
    }
    query.append(" ORDER BY subscribeTime desc")
    wechatSubscribeDao.find(query.toString)
  }
  @Transactional
  @CacheEvict(value = Array("wechatUsrCache"), allEntries = true)
  def synUsr(mchtId:String): String = {
    val sb: StringBuilder = new StringBuilder
    sb.append("from TblWechatMchtInf where id.mchtId = '"+mchtId+"'")
    val list: List[TblWechatMchtInf] = wechatMchtInfDao.find(sb.toString)
    var resp: String = null
    import scala.collection.JavaConversions._
    for (data <- list) {
      resp = this.SynchUsrIdList(data.getAppid, data.getAppsecret, data.getId.getMchtId)
      if (resp != null) {
        resp
      }
    }
    resp
  }

  @Transactional
  @CacheEvict(value = Array("wechatUsrCache"), allEntries = true)
  def upRemark(tblWechatSubscribe: TblWechatSubscribe): String = {
    //TBL_WECHAT_MCHT_INF 直接查询 微信绑定商户表只有一条数据
    //val data: TblWechatMchtInf = wechatMchtInfService.getById("00008")//bug
    val data: TblWechatMchtInf = wechatMchtInfService.queryWechatMchtInf("10001")
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    var result: String = null
    if (null != at) {
      val modUsr: ModUsr = new ModUsr
      modUsr.setOpenid(tblWechatSubscribe.getOpenid)
      modUsr.setRemark(tblWechatSubscribe.getRemarks)
      val jsonObject: JSONObject = usrManager.upRemark(modUsr, at.getToken)
      if (null != jsonObject) {
        if (WechatUtil.getWechatCallBackStatus(jsonObject.toString) != 0) {
          result = jsonObject.getString("errcode")
        } else {
          wechatSubscribeDao.update(tblWechatSubscribe)
        }
      }
    } else {
      result = "9999999"
    }
    result
  }

  private def SynchUsrIdList(appId: String, appSecret: String, mchtId: String): String = {
    val at: AccessToken = wechatTokenService.getAccessToken(appId, appSecret)
    var openid: String = ""
    var jsonObject: JSONObject = null
    var result: String = null
    val groupMap: util.Map[String,AnyRef] = new util.HashMap[String,AnyRef]
    if (null != at) {
      jsonObject = usrManager.getUsr(at.getToken)
      if (null != jsonObject) {
        if (WechatUtil.getWechatCallBackStatus(jsonObject.toString) == 0) {
          val usrId: JSONObject = jsonObject.get("data").asInstanceOf[JSONObject]
          if (usrId != null) {
            val jsonArr: JSONArray = usrId.get("openid").asInstanceOf[JSONArray]
            for (i <- 0 until jsonArr.size()) {
              var usrDef: JSONObject = null
              if (null != at) {
                usrDef = usrManager.getUsrInf(at.getToken, jsonArr.get(i).toString)
                //println("usrDef="+usrDef+"--jsonArr.get(i).toString-"+jsonArr.get(i).toString+"---i="+i)
                if(i == 0){
                  var usrTagGroup: JSONObject = new JSONObject
                  usrTagGroup = groupManager.getGroup(at.getToken)
                  val usrTagArr: JSONArray = usrTagGroup.get("groups").asInstanceOf[JSONArray]
                  var groupStr:String = null
                  val groupSb:StringBuilder = new StringBuilder
                  for (i <- 0 until usrTagArr.size()) {
                    //类似于同步分组
                    val groupID:String = (usrTagArr.get(i).asInstanceOf[JSONObject]).get("id").toString
                    groupSb.append("'").append(groupID).append("',")
                    //设置分组
                    val twgi:TblWechatSubGroupId = new TblWechatSubGroupId
                    val twg:TblWechatSubGroup = new TblWechatSubGroup
                    twgi.setId((usrTagArr.get(i).asInstanceOf[JSONObject]).get("id").toString)
                    twgi.setMchtId(mchtId)
                    twg.setId(twgi)
                    twg.setName((usrTagArr.get(i).asInstanceOf[JSONObject]).get("name").toString)
                    groupMap.put((usrTagArr.get(i).asInstanceOf[JSONObject]).get("id").toString,(usrTagArr.get(i).asInstanceOf[JSONObject]).get("name").toString)
                    wechatSubGroupDao.saveOrUpdate(twg)
                  }
                  groupStr = groupSb.toString().substring(0,groupSb.toString().lastIndexOf(","))
                  //删除不存在的同步分组
                  val sql:String = "DELETE FROM TBL_WECHAT_SUB_GROUP WHERE ID NOT IN ("+groupStr+")"
                  wechatSubGroupDao.executeSql(sql)
                }
                //用户列表
                this.saveWechatSubscribe(usrDef, mchtId,groupMap)
                //将所有关注用户加入列表
                openid += ("'" + usrDef.get("openid") + "',")
              }
            }
          }
          //查询到取消关注的用户修改状态
          if (!("" == openid)) {
            this.updateWechatSubscribe(openid, mchtId)
          }
        }
      } else {
        result = "8888888"
      }
    } else {
      result = "9999999"
    }
    result
  }

  @Transactional
  @CacheEvict(value = Array("wechatUsrCache"), allEntries = true)
  def updateWechatSubscribe(openid: String, mchtId: String) {
    val openidTmp = openid.substring(0, openid.length - 1)
    val sb: StringBuilder = new StringBuilder
    sb.append("update TBL_WECHAT_SUBSCRIBE set SUBSCRIBE_TINY ='0' where openid in (")
    sb.append("select t.opId from(select openid as opId from TBL_WECHAT_SUBSCRIBE where openid not in (").append(openidTmp).append("")
    sb.append(" )) t) ")
   // sb.append(" )) and mchtId ='").append(mchtId).append("'")

    wechatSubscribeDao.executeSql(sb.toString)
  }

  private def saveWechatSubscribe(`def`: JSONObject, mchtId: String,map:util.Map[String,AnyRef]) {
    val subscribe: TblWechatSubscribe = new TblWechatSubscribe()
    val ts:TblWechatSubscribeId = new TblWechatSubscribeId
    ts.setMchtId(mchtId)
    ts.setSubcribeId(wechatSubscribeDao.getMaxSeq())
    subscribe.setId(ts)
    subscribe.setOpenid(`def`.get("openid").toString)
    subscribe.setSubscribeTiny(`def`.get("subscribe").toString.toInt)
    //处理特殊昵称
     val c :CoreUtfm =new CoreUtfm
     val nick:String= c.filterOffUtf8Mb4(`def`.get("nickname").toString)
    // println("nickname---:"+nick)
    subscribe.setNickname(nick)
    subscribe.setSex(`def`.get("sex").toString.toInt)
    subscribe.setCity(`def`.get("city").toString)
    subscribe.setCountry(`def`.get("country").toString)
    subscribe.setProvince(`def`.get("province").toString)
    subscribe.setLanguage(`def`.get("language").toString)
    subscribe.setHeadimgurl(`def`.get("headimgurl").toString)
    val subTime: Long = `def`.get("subscribe_time").toString.toLong
   // println("subTime = " + subTime+"--DateTime4J.timestampFormat(subTime):"+DateTime4J.timestampFormat(subTime))
    subscribe.setSubscribeTime(DateTime4J.timestampFormat(subTime))
    subscribe.setCreateTime(DateTime4J.getCurrentDateTime)
    subscribe.setGroupId(`def`.get("groupid").toString)
    subscribe.setGroupName((map.get(`def`.get("groupid").toString)).toString)
    this.addWechatSubscribe(subscribe)
  }

  @Transactional
  @CacheEvict(value = Array("wechatUsrCache"), allEntries = true)
  def addWechatSubscribe(wechatSubscribe: TblWechatSubscribe) {
    val hql: StringBuilder = new StringBuilder
   // 去掉商户Id hql.append("from TblWechatSubscribe where mchtId ='").append("00008").append("' and")
    hql.append("from TblWechatSubscribe where 1=1 ")
    hql.append(" and id.mchtId ='").append(wechatSubscribe.getId.getMchtId).append("'")
    hql.append(" and openid ='").append(wechatSubscribe.getOpenid).append("'")
    hql.append(" and groupId ='").append(wechatSubscribe.getGroupId).append("'")
    hql.append(" and groupName = '").append(wechatSubscribe.getGroupName).append("'")
//    hql.append(" and subscribeTiny <> 0 ")
    val list: List[TblWechatSubscribe] = wechatSubscribeDao.find(hql.toString)
    if (list.size()>0) {
      if(list.get(0).getSubscribeTiny == 0 && wechatSubscribe.getSubscribeTiny == 1){
        val tblWechatSubscribe:TblWechatSubscribe = list.get(0)
        tblWechatSubscribe.setSubscribeTiny(1)
        wechatSubscribeDao.update(tblWechatSubscribe)
      }
      //暂时不更新 2015-11-13
      /*val data: TblWechatSubscribe = list.get(0)
      println("update --wechatSubscribe.getSubcribeId----"+data.getSubcribeId)
      data.setSubcribeId(data.getSubcribeId)
      data.setSubscribeTiny(wechatSubscribe.getSubscribeTiny)
      data.setOpenid(wechatSubscribe.getOpenid)
      data.setNickname(wechatSubscribe.getNickname)
      data.setSex(wechatSubscribe.getSex)
      data.setCity(wechatSubscribe.getCity)
      data.setCountry(wechatSubscribe.getCountry)
      data.setProvince(wechatSubscribe.getProvince)
      data.setLanguage(wechatSubscribe.getLanguage)
      data.setHeadimgurl(wechatSubscribe.getHeadimgurl)
      data.setSubscribeTime(wechatSubscribe.getSubscribeTime)
      data.setUpdateTime(DateTime4J.getCurrentDateTime)
      data.setGroupId(wechatSubscribe.getGroupId)
      data.setGroupName(wechatSubscribe.getGroupName)
      data.setRemarks(wechatSubscribe.getRemarks)
      wechatSubscribeDao.update(data)*/
    } else {
     // println("save ------")
//      val tsid:TblWechatSubGroupId = new TblWechatSubGroupId
//      val ts:TblWechatSubGroup = new TblWechatSubGroup
//      tsid.setId(wechatSubscribe.getGroupId)
//      tsid.setMchtId("10001")
//      ts.setId(tsid)
//      wechatSubGroupDao.saveOrUpdate(ts)
      wechatSubscribeDao.save(wechatSubscribe)
    }
  }



  @Transactional
  @CacheEvict(value = Array("wechatUsrCache"), allEntries = true)
  def upGroup(tblWechatSubscribe: TblWechatSubscribe): String = {
    //val data: TblWechatMchtInf = wechatMchtInfService.getById("00008")
    //商户
    val data: TblWechatMchtInf = wechatMchtInfService.queryWechatMchtInf("10001")
    val tsg:TblWechatSubGroupId = new TblWechatSubGroupId
    tsg.setId(tblWechatSubscribe.getGroupId)
    tsg.setMchtId(tblWechatSubscribe.getId.getMchtId)
    val ts:TblWechatSubGroup = wechatSubGroupDao.getById(tsg)
    tblWechatSubscribe.setGroupName(ts.getName)
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    var result: String = null
    if (null != at) {
      val utg: UsrToGroup = new UsrToGroup
      utg.setOpenid(tblWechatSubscribe.getOpenid)
      utg.setTo_groupid(tblWechatSubscribe.getGroupId)
      val jsonObject: JSONObject = usrManager.mvUsr(utg, at.getToken)
      if (null != jsonObject) {
        if (WechatUtil.getWechatCallBackStatus(jsonObject.toString) != 0) {
          result = jsonObject.getString("errcode")
        } else {
          if (tblWechatSubscribe.getGroupId == "0") {
            tblWechatSubscribe.setGroupId("")
          }
          wechatSubscribeDao.update(tblWechatSubscribe)
        }
      }
    } else {
      result = "9999999"
    }
    result
  }

  @Transactional
  @CacheEvict(value = Array("wechatUsrCache"), allEntries = true)
  def batchUpGroup(subList: String, groupId: String): String = {
    //val data: TblWechatMchtInf = wechatMchtInfService.getById("")
    val data: TblWechatMchtInf = wechatMchtInfService.queryWechatMchtInf("10001")
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    var result: String = null
    if (null != at) {
      val utg: UsrsToGroup = new UsrsToGroup
      val tmp: List[String] = new ArrayList[String]
      for (str <- subList.split(",")) {
        tmp.add(str)
      }
      utg.setOpenid_list(tmp)
      utg.setTo_groupid(groupId)
      val groupNameHql:String = "from TblWechatSubGroup where id.id = '"+groupId+"'"
      val ts:List[TblWechatSubGroup] = wechatSubGroupDao.find(groupNameHql)
      val jsonObject: JSONObject = usrManager.mvUsrs(utg, at.getToken)
      if (null != jsonObject) {
        if (WechatUtil.getWechatCallBackStatus(jsonObject.toString) != 0) {
          result = jsonObject.getString("errcode")
        }
        else {
          //批量更新组别
          val sb: StringBuilder = new StringBuilder();
          var subTmp: String = "";
          for (i <- 0 until tmp.size()) {
            subTmp += "'" + tmp.get(i) + "',";
          }
          var groupIdTmp: String = ""
          if (groupId == "0") {
            groupIdTmp = ""
          } else {
            groupIdTmp = groupId
          }
          sb.append("update TblWechatSubscribe set groupId = '").append(groupIdTmp).append("' ,groupName = '").append(ts.get(0).getName).append("'")
            .append(" where ")
          sb.append("openid in (").append(subTmp.substring(0, subTmp.length - 1)).append(")")
          wechatSubscribeDao.updateAll(sb.toString)
        }
      }
    } else {
      result = "9999999"
    }
    result
  }

  @Transactional
  @CacheEvict(value = Array("wechatUsrCache"), allEntries = true)
  def upGroup(groupId: String) {
    val sb: StringBuilder = new StringBuilder
    sb.append("update TblWechatSubscribe set groupId = '0',groupName='未分组' WHERE groupId ='").append(groupId).append("'")
    wechatSubscribeDao.updateAll(sb.toString)
  }

  @Transactional
  @Cacheable(value = Array("wechatUsrCache"), key = "#root.method.name+#id")
  def getById(id: TblWechatSubscribeId): TblWechatSubscribe = {
    val data: TblWechatSubscribe = wechatSubscribeDao.getById(id)
    if (data.getSubscribeTiny == 0) {
      return null
    }
    wechatSubscribeDao.getById(id)
  }
}
