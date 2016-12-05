package com.ebeijia.entity.app.soft

import java.io.Serializable
import javax.persistence._

import com.ebeijia.entity.app.dep.TblAppDeployDocumentId
/**
 * 软件TblAppSoftVerInf
 */
@Entity
@Table(name = "TBL_APP_SOFTVER_INF")
@SerialVersionUID(1L)
class TblAppSoftVerInf extends Serializable{
  private var id:TblAppSoftVerInfId=null
  private var _type :String = null
  private var verNo :String = null
  private var localPath :String = null
  private var urlPath :String  = null
  private var uTime : String = null
  private var force :String = null
  private var res1 :String = null
  private var res2:String = null
  private var lastTime :String = null

  @EmbeddedId
  @AttributeOverrides(Array(new AttributeOverride(name = "brhNo", column = new Column(name = "BRH_NO", nullable = false, length = 11))
    , new AttributeOverride(name = "verId", column = new Column(name = "VER_ID", nullable = false, length = 5))))
  def getId: TblAppSoftVerInfId = {
    id
  }
  def setId(id: TblAppSoftVerInfId) {
    this.id = id
  }
  @Column(name = "TYPE", length = 2)
  def getType: String = {
    _type
  }

  def setType(_type: String) {
    this._type = _type
  }
  @Column(name = "VER_NO", length = 20)
  def getVerNo: String = {
    verNo
  }

  def setVerNo(verNo: String) {
    this.verNo = verNo
  }
  @Column(name = "LOCAL_PATH", length = 200)
  def getLocalPath: String = {
    localPath
  }

  def setLocalPath(localPath: String) {
    this.localPath = localPath
  }
  @Column(name = "URL_PATH", length = 200)
  def getUrlPath: String = {
    urlPath
  }

  def setUrlPath(urlPath: String) {
    this.urlPath = urlPath
  }
  @Column(name = "ISU_TIME", length = 14)
  def getUTime: String = {
    uTime
  }

  def setUTime(uTime: String) {
    this.uTime = uTime
  }
  @Column(name = "IS_FORCE", length = 1)
  def getForce: String = {
    force
  }

  def setForce(force: String) {
    this.force = force
  }
  @Column(name = "RES1", length = 200)
  def getRes1: String = {
    res1
  }
  def setRes1(res1: String) {
    this.res1 = res1
  }
  @Column(name = "RES2", length = 200)
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
