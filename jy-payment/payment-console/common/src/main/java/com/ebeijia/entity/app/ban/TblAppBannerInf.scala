package com.ebeijia.entity.app.ban

import java.io.Serializable
import javax.persistence._

/**
 * 广告TblAppBannerInf
 */
@Entity
@Table(name = "TBL_APP_BANNER_INF")
@SerialVersionUID(1L)
class TblAppBannerInf extends Serializable {
  private var id: TblAppBannerInfId = null  //广告ID
  private var name: String = null  //名称
  private var about: String = null  //中文简介
  private var fileId:Int = 0 //图片ID
  private var linkType :String = null  //关联类型（无链接：01、网页地址：02）
  private var linkUrl:String = null  //关联连接
  private var state:String = null//1关闭0正常
  private var seq:String = null//順序
  private var res1:String = null//预留1
  private var res2:String = null//预留2
  private var lastTime:String = null  //最后修改时间
  @EmbeddedId
  @AttributeOverrides(Array(new AttributeOverride(name = "brhNo", column = new Column(name = "BRH_NO", nullable = false, length = 50))
    , new AttributeOverride(name = "banId", column = new Column(name = "BAN_ID", nullable = false, length = 5))))
  def getId: TblAppBannerInfId = {
    id
  }

  def setId(id: TblAppBannerInfId) {
    this.id = id
  }
  @Column(name = "NAME", length = 50)
  def getName: String = {
    name
  }
  def setName(name: String) {
    this.name = name
  }
  @Column(name = "ABOUT", length = 150)
  def getAbout: String = {
    about
  }
  def setAbout(about: String) {
    this.about = about
  }
  @Column(name = "FILE_ID", length = 11)
  def getFileId: Int = {
    fileId
  }
  def setFileId(fileId: Int) {
    this.fileId = fileId
  }
  @Column(name = "LINK_TYPE", length = 2)
  def getLinkType: String = {
    linkType
  }
  def setLinkType(linkType: String) {
    this.linkType = linkType
  }
  @Column(name = "LINK_URL", length = 255)
  def getLinkUrl: String = {
    linkUrl
  }
  def setLinkUrl(linkUrl: String) {
    this.linkUrl = linkUrl
  }
  @Column(name = "STATE", length = 1)
  def getState: String = {
    state
  }
  def setState(state: String) {
    this.state = state
  }
  @Column(name = "SEQ", length = 2)
  def getSeq: String = {
    seq
  }
  def setSeq(seq: String) {
    this.seq = seq
  }
  @Column(name = "RES1", length = 2)
  def getRes1: String = {
    res1
  }
  def setRes1(res1: String) {
    this.res1 = res1
  }
  @Column(name = "RES2", length = 2)
  def getRes2: String = {
    res2
  }
  def setRes2(res2: String) {
    this.res2 = res2
  }
  @Column(name = "LAST_TIME", length = 14)
  def getLastTime: String = {
    lastTime
  }
  def setLastTime(lastTime: String) {
    this.lastTime = lastTime
  }
}
