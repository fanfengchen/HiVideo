package com.ebeijia.module.wechat.service.menu

import java.util.{LinkedHashMap, LinkedList, List, Map}

import com.ebeijia.module.wechat.dao.base.WechatGroupDao
import com.ebeijia.entity.wechat.base.TblWechatMenuGroup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * WechatMenuGroupServiceImpl
 * @author zhicheng.xu
 *         15/8/14
 */


@Service
final class WechatMenuGroupServiceImpl extends WechatMenuGroupService {
  @Autowired
  private val wechatGroupDao: WechatGroupDao = null

  @Transactional
  @Cacheable(value = Array("wechatMenuCache"), key = "#root.method.name+#mchtId")
  def find(mchtId: String): List[TblWechatMenuGroup] = {
    val sb: StringBuilder = new StringBuilder
    //没有商户号
   /* sb.append("from TblWechatMenuGroup where mchtId ='").append(mchtId).append("'")
    sb.append(" and id in (select distinct groupId  from TblWechatMenu where mchtId ='").append(mchtId)
    sb.append("')")*/
    sb.append("from TblWechatMenuGroup where 1=1 ")
    sb.append(" and id in (select distinct groupId  from TblWechatMenu where 1=1)")
    sb.append(" ")
    val list: List[TblWechatMenuGroup] = wechatGroupDao.find(sb.toString)
    list
  }

  /**
   * 组别下拉框
   * mchtId 商户号
   * */
  @Transactional
  @Cacheable(value = Array("wechatMenuCache"), key = "#root.method.name+#mchtId")
  def listFind(mchtId: String): Map[String, AnyRef] = {
    val sb: StringBuilder = new StringBuilder
    sb.append("FROM TblWechatMenuGroup where 1=1 ")
   // sb.append(" where mchtId='").append(mchtId).append("'")
    sb.append(" ORDER BY id")
    val grouplist: List[TblWechatMenuGroup] = wechatGroupDao.find(sb.toString)
    val list: List[AnyRef] = new LinkedList[AnyRef]
    val map: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
    import scala.collection.JavaConversions._
    for (tblWechatMenuGroup <- grouplist) {
      val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
      hashMap.put("key", tblWechatMenuGroup.getId + "-" + tblWechatMenuGroup.getName)
      hashMap.put("value", tblWechatMenuGroup.getId)
      list.add(hashMap)
    }
    map.put("info", list)
    map
  }
}
