package com.ebeijia.module.wechat.service.menu

import java.util.{Iterator, LinkedHashMap, LinkedList, List, Map}

import com.ebeijia.module.wechat.dao.base.{WechatMchtInfDao, WechatMenuDao}
import com.ebeijia.entity.wechat.base.{TblWechatRespMsg, TblWechatMenuId, TblWechatMchtInf, TblWechatMenu}
import com.ebeijia.module.wechat.service.core.{WechatMchtInfService, WechatTokenService}
import com.ebeijia.module.wechat.service.inter.MenuManager
import com.ebeijia.entity.vo.wechat.AccessToken
import com.ebeijia.entity.vo.wechat.button.{Menu,CommonButton,Button,ViewButton,ComplexButton}
import com.ebeijia.module.wechat.service.msg.WechatRespMsgService
import com.ebeijia.util.wechat.WechatUtil
import net.sf.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * WechatMenuServiceImpl
  *
  * @author zhicheng.xu
 *         15/8/14
 */


@Service
final class WechatMenuServiceImpl extends WechatMenuService {
  @Autowired
  private val wechatMenuDao: WechatMenuDao = null
  @Autowired
  private val wechatMchtInfService: WechatMchtInfService = null
  @Autowired
  private val wechatTokenService: WechatTokenService = null
  @Autowired
  private val menuManager: MenuManager = null
  @Autowired
  private val wechatMchtInfDao: WechatMchtInfDao = null
  @Autowired
  private val wechatRespMsgService:WechatRespMsgService = null
  /**微信菜单修改
   * */
  @Transactional
  @CacheEvict(value = Array("wechatMenuCache"), allEntries = true)
  def updateWechatMenu(wechatMenu: TblWechatMenu) {
    wechatMenuDao.saveOrUpdate(wechatMenu)
  }

  @Transactional
  @CacheEvict(value = Array("wechatMenuCache"), allEntries = true)
  def save(wechatMenu: TblWechatMenu) {
    wechatMenuDao.save(wechatMenu)
  }

  @Transactional
  @CacheEvict(value = Array("wechatMenuCache"), allEntries = true)
  def deleteWechatMenu(wechatMenu: TblWechatMenu) {
    wechatMenuDao.update(wechatMenu)
  }

  @Transactional
  @CacheEvict(value = Array("wechatMenuCache"), allEntries = true)
  def addWechatMenu(wechatMenu: TblWechatMenu) {
    wechatMenuDao.saveOrUpdate(wechatMenu)
  }

  @Transactional
  @Cacheable(value = Array("wechatMenuCache"), key = "#root.method.name+#id")
  def getById(id: TblWechatMenuId): TblWechatMenu = {
    wechatMenuDao.getById(id)
  }

  @Transactional
  @Cacheable(value = Array("wechatMenuCache"), key = "#root.method.name")
  def queryWechatMenuList: List[TblWechatMenu] = {
    wechatMenuDao.getWechatMenuList
  }

  @Transactional
  @Cacheable(value = Array("wechatMenuCache"), key = "#root.method.name+#wechatMenu")
  def countTotalNum(wechatMenu: TblWechatMenu): Int = {
    wechatMenuDao.countTotalNum(wechatMenu)
  }

  @Transactional
  @Cacheable(value = Array("wechatMenuCache"), key = "#root.method.name+#id")
  def queryWechatMenuById(id: Int): TblWechatMenu = {
    wechatMenuDao.getById(id)
  }

  /**菜单同步
    *
   * */
  @Transactional
  @Cacheable(value = Array("wechatMenuCache"), key = "#root.method.name+#mchtId+#groupId")
  def SynchToMenu(mchtId: String, groupId: String): Menu = {
   // val tblWechatMchtInf: TblWechatMchtInf = wechatMchtInfService.getById(mchtId)
   //查询商户信息，商户信息只有一条
   val data: TblWechatMchtInf = wechatMchtInfService.queryWechatMchtInf("10001")
    val at: AccessToken = wechatTokenService.getAccessToken(data.getAppid, data.getAppsecret)
    var menu: Menu = new Menu
    var jsonObject: JSONObject = null
    var result: String = null
    if (null != at) {
      menu = getMenu(data.getId.getMchtId, groupId)
      jsonObject = menuManager.createMenu(menu, at.getToken)
      if (null != jsonObject) {
        if (WechatUtil.getWechatCallBackStatus(jsonObject.toString) != 0) {
          result = jsonObject.getString("errcode")
        }
        else {
          menu
        }
      }
      else {
        result = "8888888"
      }
    }
    else {
      result = "9999999"
    }
    menu
  }

  @Transactional
  @Cacheable(value = Array("wechatMenuCache"), key = "#root.method.name+#menuKey+#mchtId")
  def findByHql(menuKey: String, mchtId: String): List[TblWechatMenu] = {
    val hql: String = "FROM TblWechatMenu WHERE menuKey = '" + menuKey + "'"
    wechatMenuDao.find(hql)
  }

  /**
   * 从DB中查出的菜单转换为微信API格式
    *
    * @param groupId 菜单组
   * @param mchtId 商户号
   * @return
   */
  @Transactional
  @Cacheable(value = Array("wechatMenuCache"), key = "#root.method.name+#mchtId+#groupId")
  private def getMenu(mchtId: String, groupId: String): Menu = {
    val sb: StringBuilder = new StringBuilder
    //没有商户号
   // sb.append("from TblWechatMenu where mchtId = '").append(mchtId).append("' ")
    sb.append("from TblWechatMenu where 1=1 ")
    sb.append(" and id.mchtId = '").append(mchtId).append("'")
    sb.append(" and  groupId ='").append(groupId).append("' order by orderNo")
    val listMenu: List[TblWechatMenu] = wechatMenuDao.find(sb.toString)
    //一级菜单
    val mapFir: Map[String, TblWechatMenu] = this.tblWechatFirMenuFormate(listMenu)
    //二级菜单
    val mapSec: Map[String, TblWechatMenu] = this.tblWechatSecMenuFormate(listMenu)
    //记录是否有二级菜单
    var flag: Boolean = false
    val menu: Menu = new Menu
    // 转换菜单格式
    //一级菜单数组
    val button: Array[Button] = new Array[Button](mapFir.size)
    val interator: Iterator[String] = mapFir.keySet.iterator
    var i: Int = 0
    //记录二级菜单的数量
    var count: Int = 0
    while (interator.hasNext) {
      val key: String = interator.next
      val rootMenu: TblWechatMenu = mapFir.get(key)
      val interatorSec: Iterator[String] = mapSec.keySet.iterator
      while (interatorSec.hasNext) {
        val keySec: String = interatorSec.next
        if (mapSec.get(keySec).getParentId.equals(rootMenu.getId.getMenuId.toString)) {
          flag = true
          count += 1
        }
      }
      // 没有二级菜单
      if (!flag) {
        if ("1".equals(rootMenu.getType)) {
          // 事件菜单
          val btn: CommonButton = new CommonButton
          btn.setType("click")
          btn.setName(rootMenu.getMenuName)
          btn.setKey(rootMenu.getMenuKey)
          //一级菜单按钮放入一级菜单数组中
          button(i) = btn
          // 链接菜单
        } else if ("2".equals(rootMenu.getType)) {
          val btn: ViewButton = new ViewButton
          btn.setType("view")
          btn.setName(rootMenu.getMenuName)
          btn.setUrl(rootMenu.getUrl)
          //一级菜单按钮放入一级菜单数组中
          button(i) = btn
        }
        // 有二级菜单的一级菜单对象
      } else {
        // 有二级菜单的一级菜单对象
        val rootBtn: ComplexButton = new ComplexButton
        rootBtn.setName(rootMenu.getMenuName)
        // 二级菜单数组
        val subButton: Array[Button] = new Array[Button](count)
        val interatorMapSec: Iterator[String] = mapSec.keySet.iterator
        var j: Int = 0
        while (interatorMapSec.hasNext) {
          val keyMapSec: String = interatorMapSec.next
          if (mapSec.get(keyMapSec).getParentId.equals(rootMenu.getId.getMenuId.toString)) {
            val subMenu: TblWechatMenu = mapSec.get(keyMapSec)
            // 事件菜单
            if ("1".equals(subMenu.getType)) {
              val btn: CommonButton = new CommonButton
              btn.setType("click")
              btn.setName(subMenu.getMenuName)
              btn.setKey(subMenu.getMenuKey)
              //二级菜单按钮放入二级菜单数组中
              subButton(j) = btn
              // 链接菜单
            } else if ("2".equals(subMenu.getType)) {
              val btn: ViewButton = new ViewButton
              btn.setType("view")
              btn.setName(subMenu.getMenuName)
              btn.setUrl(subMenu.getUrl)
              //二级菜单按钮放入二级菜单数组中
              subButton(j) = btn
            }
            j += 1
          }
        }
        //二级菜单赋给一级菜单
        rootBtn.setSub_button(subButton)
        button(i) = rootBtn
      }
      i += 1
      flag = false
      count = 0
    }
    menu.setButton(button)
    menu
  }

  @Transactional
  @CacheEvict(value = Array("wechatMenuCache"), allEntries = true)
  def modifyMenuById(tblWechatMenu: TblWechatMenu) {
    wechatMenuDao.update(tblWechatMenu)
  }

  /**
   * 根据Id获取对象
   * 删除菜单
   * */
  @Transactional
  @CacheEvict(value = Array("wechatMenuCache"), allEntries = true)
  def deleteMenuById(id:TblWechatMenuId): String = {

    var result: String = null
    //如果有下级菜单不能删除
    val sql: String = "from TblWechatMenu where parentId = '" + id.getMenuId +  "'"
    val list: List[TblWechatMenu] = wechatMenuDao.find(sql)
    if (list != null && !list.isEmpty) {
      result = "此菜单存在下级菜单,请先删除子菜单"
    }else{
      //如果没有下级菜单能删除
      wechatMenuDao.deleteById(id)
    }
   /*
    val tblWechatMenu: TblWechatMenu = wechatMenuDao.getById(menuId)
   if (tblWechatMenu.getParentId == "-") {

      //如果是主菜单 删除其子菜单
      val sql: String = "from TblWechatMenu where parentId = '" + tblWechatMenu.getMenuId +  "'"
      val list: NewsInfoList[TblWechatMenu] = wechatMenuDao.find(sql)
      if (list != null && !list.isEmpty) {
        result = "此菜单存在下级菜单"
      }else{
        //删除所有的子菜单

        /*val sqlDeleteMenu: String = "delete from TblWechatMenu where parentId = '" + menuId + "'"
        wechatMenuDao.updateAll(sqlDeleteMenu)*/
        wechatMenuDao.delete(tblWechatMenu)
      }

    }else{
      wechatMenuDao.delete(tblWechatMenu)
    }*/

    result
  }

  /**
   * 根据菜单名称，菜单组查询微信菜单
   * */
//  @Transactional
//  @Cacheable(value = Array("wechatMenuCache"), key = "#root.method.name+#menuName+#groupId+#pageData")
  def findBySql(mchtId: String, groupId: String, pageData: String): Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("from TblWechatMenu")
    query.append(" where 1=1")
    //菜单名称
    query.append(" AND id.mchtId = '").append(mchtId).append("'")
//    if (mchtId != null && !("" == mchtId)) {
//      query.append(" AND id.mchtId = '").append(mchtId).append("'")
//    }
    //菜单组
    if (groupId != null && !("" == groupId)) {
      query.append(" AND groupId  = '").append(groupId).append("'")
    }
    query.append(" ORDER BY groupId,orderNo,parentId desc")
    val m: Map[String, AnyRef] = wechatMenuDao.findByPage(query.toString, pageData)
    m
  }

  /**
   * 根据组别查询父节点微信菜单（下拉框）
   * */
  @Transactional
  @Cacheable(value = Array("wechatMenuCache"), key = "#root.method.name+#mchtId+#groupId")
  def listFind(mchtId: String, groupId: String): Map[String, AnyRef] = {
    //没有商户号
    //val hql: String = "FROM TblWechatMenu where mchtId = ? and groupId = ? and parentId = '-' ORDER BY menuId"
   // val tblWechatMenu: NewsInfoList[TblWechatMenu] = wechatMenuDao.find(hql, mchtId, groupId)
    val hql: String = "FROM TblWechatMenu where  groupId = ? and parentId = '-' ORDER BY menuId desc"
    val tblWechatMenu: List[TblWechatMenu] = wechatMenuDao.find(hql,  groupId)
    val list: List[AnyRef] = new LinkedList[AnyRef]
    val map: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
    import scala.collection.JavaConversions._
    for (tblMenu <- tblWechatMenu) {
      val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
      hashMap.put("key", tblMenu.getId.getMenuId + "-" + tblMenu.getMenuName)
      hashMap.put("value", tblMenu.getId.getMenuId.toString)
      list.add(hashMap)
    }
    map.put("info", list)
    map
  }

  /**
   * 微信菜单格式化一级菜单
    *
    * @param menus 菜单
   * @return
   */
  private def tblWechatFirMenuFormate(menus: List[TblWechatMenu]): Map[String, TblWechatMenu] = {
    // 使用有序的map集合
    val map: Map[String, TblWechatMenu] = new LinkedHashMap[String, TblWechatMenu]
    // 组装从DB读取的菜单
    import scala.collection.JavaConversions._
    for (menu <- menus) {
      if (null == menu.getParentId || (menu.getParentId == "-")) {
        // 父菜单
        map.put(menu.getId.getMenuId.toString, menu)
      }
    }
    map
  }

  /**
   * 微信菜单格式化二级菜单
    *
    * @param menus
   * @return
   */
  private def tblWechatSecMenuFormate(menus: List[TblWechatMenu]): Map[String, TblWechatMenu] = {
    // 使用有序的map集合
    val map: Map[String, TblWechatMenu] = new LinkedHashMap[String, TblWechatMenu]
    // 组装从DB读取的菜单
    import scala.collection.JavaConversions._
    for (menu <- menus) {
      if (!(menu.getParentId == "-")) {
        // 子菜单
        map.put(menu.getId.getMenuId.toString, menu)
      }
    }
    map
  }


  /***
    * 判断菜单名称唯一
    * */
  @Transactional
  @Cacheable(value = Array("wechatMenuCache"), key = "#root.method.name+#menuId+#menuName")
  def isMenuNameCount(mchtId:String,menuId: String,menuName: String): Int={
    wechatMenuDao.isMenuNameCount(mchtId,menuId,menuName)
  }


  /**
   *查询同一个组别 and parentId="-"
   *
   * */
  @Transactional
  @Cacheable(value = Array("wechatMenuCache"), key = "#root.method.name+#mchtId+#groupId")
  def groupParentList(mchtId:String,groupId: String):List[TblWechatMenu]={
    val query: StringBuilder = new StringBuilder
    val hql: String = "FROM TblWechatMenu where id.mchtId = ? and  groupId = ? and parentId = '-' ORDER BY id.menuId desc"
    wechatMenuDao.find(hql,mchtId ,groupId)
  }


  /**查询 二级菜单
    *
   * */
  @Transactional
  @Cacheable(value = Array("wechatMenuCache"), key = "#root.method.name+#menuId")
  def queryParentList(mchtId:String,menuId:String): List[TblWechatMenu]={
    val sql: String = "from TblWechatMenu where id.mchtId = '"+mchtId+"' and parentId ='" + menuId +  "'"
     wechatMenuDao.find(sql)
  }
  /**
   * 判断排序号 唯一
   * */
  @Transactional
  @Cacheable(value = Array("wechatMenuCache"), key = "#root.method.name+#menuId+#orderNo")
  def getOrderNoCount(menuId: String,orderNo: String):Int={
    wechatMenuDao.getOrderNoCount(menuId,orderNo)
  }

  def getMaxMenuId():Int = {
    val hql:String = "SELECT MAX(id.menuId) FROM TblWechatMenu"
    (wechatMenuDao.find(hql).get(0).asInstanceOf[Int] + 1)
  }

  def getMenuInf(mchtId:String,menuKey:String): TblWechatMenu ={
    val hql:String = "from TblWechatMenu where menuKey = '"+menuKey+"'"
    val tblWechatMenu:List[TblWechatMenu] = wechatMenuDao.find(hql)
    if(tblWechatMenu.size() == 0){
      return null
    }
    tblWechatMenu.get(0)
  }
}
