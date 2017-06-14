package com.ebeijia.module.wechat.service.menu

import java.util.{List, Map}

import com.ebeijia.entity.vo.wechat.button.Menu
import com.ebeijia.entity.wechat.base.{TblWechatMenuId, TblWechatMenu}

/**
 * WechatMenuService
 * @author zhicheng.xu
 *         15/8/14
 */



trait WechatMenuService {
  //修改实体信息
  def updateWechatMenu(wechatMenu: TblWechatMenu)
  //保存菜单
  def save(wechatMenu: TblWechatMenu)

  def deleteMenuById(id:TblWechatMenuId): String
  //查询菜单是
  def findByHql(menuKey: String, mchtId: String): List[TblWechatMenu]
  //修改自定义菜单
  def modifyMenuById(tblWechatMenu: TblWechatMenu)
  //与微信服务器同步自定义菜单
  def SynchToMenu(mchtId: String, groupId: String): Menu
  //分页查询
  def findBySql(mchtId: String, groupId: String, aoData: String): Map[String, AnyRef]
  //下拉框查询
  def listFind(mchtId: String, groupId: String): Map[String, AnyRef]
  //根据id获取对象
  def getById(id: TblWechatMenuId): TblWechatMenu
  /***
    * 判断菜单名称唯一
    * */
  def isMenuNameCount(mchtId:String,menuId: String,menuName: String): Int
  def queryWechatMenuList: List[TblWechatMenu]
  /**
   *查询同一个组别 and parentId="-"
   *
   * */
  def groupParentList(mchtId:String,groupId: String):List[TblWechatMenu]
  /**查询二级菜单
   * */
  def queryParentList(mchtId:String,menuId:String): List[TblWechatMenu]
  /**
   * 判断排序号 唯一
   * */
  def getOrderNoCount(menuId: String,orderNo: String):Int
  def getMaxMenuId():Int
  def getMenuInf(mchtId:String,menuKey:String): TblWechatMenu

}
