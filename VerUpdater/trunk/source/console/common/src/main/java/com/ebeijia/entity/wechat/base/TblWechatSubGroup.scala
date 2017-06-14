package com.ebeijia.entity.wechat.base

import javax.persistence._

/**
 * TblWechatSubGroup
 * @author zhicheng.xu
 *         15/8/12
 */


@Entity
@Table(name = "TBL_WECHAT_SUB_GROUP")
@SerialVersionUID(1L)
class TblWechatSubGroup extends java.io.Serializable {
  private var id:TblWechatSubGroupId = null
  private var name: String = null

  @EmbeddedId
  @AttributeOverrides(Array(new AttributeOverride(name = "respMsgId", column = new Column(name = "ID", nullable = false, length = 10)), new AttributeOverride(name = "mchtId", column = new Column(name = "MCHT_ID", nullable = false, length = 11))))
  def getId: TblWechatSubGroupId = {
    id
  }

  def setId(id: TblWechatSubGroupId) {
    this.id = id
  }

  @Column(name = "NAME", length = 50)
  def getName: String = {
    name
  }

  def setName(name: String) {
    this.name = name
  }
}

