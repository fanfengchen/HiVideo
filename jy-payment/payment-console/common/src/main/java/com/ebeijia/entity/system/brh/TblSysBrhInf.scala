package com.ebeijia.entity.system.brh

import java.io.Serializable
import javax.persistence._

@Entity
@Table(name = "TBL_SYS_BRH_INF")
@SerialVersionUID(1L)
class TblSysBrhInf extends Serializable{
  private var brhId: String = null
  private var brhName: String = null
  private var brhAddress: String = null
  private var brhLeavel: String = null
  private var upBrhId: String = null
  private var brhAreaCode: String = null
  private var postCode: String = null
  private var contactName: String = null
  private var telPhone: String = null

  @Id
  @Column(name = "BRH_ID", unique = true, nullable = false, length = 11)
  def getBrhId: String = {
    brhId
  }

  def setBrhId(brhId: String) {
    this.brhId = brhId
  }

  @Column(name = "BRH_NAME", nullable = false, length = 30)
  def getBrhName: String = {
    brhName
  }

  def setBrhName(brhName: String) {
    this.brhName = brhName
  }

  @Column(name = "BRH_ADDRESS", nullable = false, length = 50)
  def getBrhAddress: String = {
    brhAddress
  }

  def setBrhAddress(brhAddress: String) {
    this.brhAddress = brhAddress
  }

  @Column(name = "BRH_LEAVEL", nullable = false, length = 2)
  def getBrhLeavel: String = {
    brhLeavel
  }

  def setBrhLeavel(brhLeavel: String) {
    this.brhLeavel = brhLeavel
  }

  @Column(name = "UP_BRH_ID", nullable = false, length = 11)
  def getUpBrhId: String = {
    upBrhId
  }

  def setUpBrhId(upBrhId: String) {
    this.upBrhId = upBrhId
  }

  @Column(name = "BRH_AREA_CODE", nullable = false, length = 15)
  def getBrhAreaCode: String = {
    brhAreaCode
  }

  def setBrhAreaCode(brhAreaCode: String) {
    this.brhAreaCode = brhAreaCode
  }

  @Column(name = "POSE_CODE", nullable = false, length = 6)
  def getPostCode: String = {
    postCode
  }

  def setPostCode(postCode: String) {
    this.postCode = postCode
  }

  @Column(name = "CONTACT_NAME", nullable = false, length = 15)
  def getContactName: String = {
    contactName
  }

  def setContactName(contactName: String) {
    this.contactName = contactName
  }

  @Column(name = "TEL_PHONE", nullable = false, length = 15)
  def getTelPhone: String = {
    telPhone
  }
  def setTelPhone(telPhone: String) {
    this.telPhone = telPhone
  }
}
