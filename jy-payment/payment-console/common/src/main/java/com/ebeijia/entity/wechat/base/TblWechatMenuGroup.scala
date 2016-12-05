package com.ebeijia.entity.wechat.base

import javax.persistence.{Column, Entity, Id, Table}

/**
 * TblWechatGroup
 * @author zhicheng.xu
 *         15/8/12
 */


@Entity
@Table(name = "TBL_WECHAT_MENU_GROUP")
@SerialVersionUID(1L)
class TblWechatMenuGroup extends java.io.Serializable {
  private var id: String = null
  private var name: String = null

  @Id
  @Column(name = "ID", unique = true, nullable = false, length = 3)
  def getId: String = {
    id
  }

  def setId(id: String) {
    this.id = id
  }

  @Column(name = "NAME", length = 10)
  def getName: String = {
    name
  }

  def setName(name: String) {
    this.name = name
  }


}

