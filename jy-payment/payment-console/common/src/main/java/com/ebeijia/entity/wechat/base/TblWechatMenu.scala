package com.ebeijia.entity.wechat.base

import javax.persistence._

/**
 * TblWechatMenu
 * @author zhicheng.xu
 *         15/8/12
 */


@Entity
@Table(name = "TBL_WECHAT_MENU")
@SerialVersionUID(1L)
class TblWechatMenu extends java.io.Serializable {
  private var id: TblWechatMenuId = null
  private var orderNo: String = null
  private var menuName: String = null
  private var parentId: String = null
  private var `type`: String = null
  private var menuKey: String = null
  private var url: String = null
  private var createTime: String = null
  private var updateTime: String = null
  private var groupId: String = null


  @EmbeddedId
  @AttributeOverrides(Array(new AttributeOverride(name = "menuId", column = new Column(name = "MENU_ID", nullable = false, length = 11)), new AttributeOverride(name = "mchtId", column = new Column(name = "MCHT_ID", nullable = false, length = 8)))) def getId: TblWechatMenuId = {
    id
  }

  def setId(id: TblWechatMenuId) {
    this.id = id
  }


  @Column(name = "ORDER_NO", length = 4)
  def getOrderNo: String = {
    orderNo
  }

  def setOrderNo(orderNo: String) {
    this.orderNo = orderNo
  }

  @Column(name = "MENU_NAME", nullable = false, length = 32)
  def getMenuName: String = {
    menuName
  }

  def setMenuName(menuName: String) {
    this.menuName = menuName
  }

  @Column(name = "PARENT_ID", nullable = false, length = 8)
  def getParentId: String = {
    parentId
  }

  def setParentId(parentId: String) {
    this.parentId = parentId
  }

  @Column(name = "TYPE", length = 1)
  def getType: String = {
    `type`
  }

  def setType(`type`: String) {
    this.`type` = `type`
  }

  @Column(name = "MENU_KEY", length = 12)
  def getMenuKey: String = {
    menuKey
  }

  def setMenuKey(menuKey: String) {
    this.menuKey = menuKey
  }

  @Column(name = "URL", length = 256)
  def getUrl: String = {
    url
  }

  def setUrl(url: String) {
    this.url = url
  }

  @Column(name = "CREATE_TIME", length = 14)
  def getCreateTime: String = {
    createTime
  }

  def setCreateTime(createTime: String) {
    this.createTime = createTime
  }

  @Column(name = "UPDATE_TIME", length = 14)
  def getUpdateTime: String = {
    updateTime
  }

  def setUpdateTime(updateTime: String) {
    this.updateTime = updateTime
  }

  @Column(name = "GROUP_ID", length = 3)
  def getGroupId: String = {
    groupId
  }

  def setGroupId(groupId: String) {
    this.groupId = groupId
  }
}

