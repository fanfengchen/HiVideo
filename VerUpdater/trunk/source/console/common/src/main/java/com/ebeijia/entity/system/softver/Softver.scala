package com.ebeijia.entity.system.softver

import java.io.Serializable
import java.util.Date
import javax.persistence.{Column, Entity, Id, Table}

/**
  * Created by xf on 2016/9/8.
  */
@Entity
@Table(name = "softver")
@SerialVersionUID(1L)
class Softver  extends Serializable{
  private var id: String = null
  private var name: String = null
  private var verType: String = null
  private var verNo: String = null
  private var url: String = null
  private var user: String = null
  private var pwd: String = null
  private var _isuTime: Date = null
  private var _isForce: String = null
  private var updateLog: String = null
  private var res1: String = null
  private var res2: String = null
  private var res3: String = null
  private var res4: String = null
  private var lastTime: Date = null

  @Id
  @Column(name = "ID", unique = true, nullable = false, length = 11)
  def getId: String = {
    id
  }
  def setId(id: String) {
    this.id = id
  }

  @Column(name = "NAME", length = 100)
  def getName: String = {
    name
  }
  def setName(name: String) {
    this.name = name
  }

  @Column(name = "VER_TYPE", length = 50)
  def getVerType: String = {
    verType
  }
  def setVerType(verType: String) {
    this.verType = verType
  }

  @Column(name = "VER_NO", length = 50)
  def getVerNo: String = {
    verNo
  }
  def setVerNo(verNo: String) {
    this.verNo = verNo
  }

  @Column(name = "URL", length = 100)
  def getUrl: String = {
    url
  }
  def setUrl(url: String) {
    this.url = url
  }

  @Column(name = "USER", length = 50)
  def getUser: String = {
    user
  }
  def setUser(user: String) {
    this.user = user
  }

  @Column(name = "PWD", length = 50)
  def getPwd: String = {
    pwd
  }
  def setPwd(pwd: String) {
    this.pwd = pwd
  }

  @Column(name = "ISU_TIME")
  def getIsuTime: Date = {
    _isuTime
  }
  def setIsuTime(_isuTime: Date) {
    this._isuTime = _isuTime
  }

  @Column(name = "IS_FORCE", length = 1)
  def getIsForce: String = {
    _isForce
  }
  def setIsForce(_isForce: String) {
    this._isForce = _isForce
  }

  @Column(name = "UPDATE_LOG")
  def getUpdateLog: String = {
    updateLog
  }
  def setUpdateLog(updateLog: String) {
    this.updateLog = updateLog
  }

  @Column(name = "RES1", length = 32)
  def getRes1: String = {
    res1
  }
  def setRes1(res1: String) {
    this.res1 = res1
  }

  @Column(name = "RES2", length = 32)
  def getRes2: String = {
    res2
  }
  def setRes2(res2: String) {
    this.res2 = res2
  }

  @Column(name = "RES3", length = 32)
  def getRes3: String = {
    res3
  }
  def setRes3(res3: String) {
    this.res3 = res3
  }

  @Column(name = "RES4", length = 32)
  def getRes4: String = {
    res4
  }
  def setRes4(res4: String) {
    this.res4 = res4
  }


  @Column(name = "LAST_TIME")
  def getLastTime: Date = {
    lastTime
  }
  def setLastTime(lastTime: Date) {
    this.lastTime = lastTime
  }


}
