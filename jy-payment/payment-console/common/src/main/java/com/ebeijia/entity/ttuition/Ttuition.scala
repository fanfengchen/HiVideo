package com.ebeijia.entity.ttuition

import javax.persistence._

@Entity
@Table(name = "T_tuition")
@SerialVersionUID(1L)
class Ttuition extends Serializable{
  private var id:Int = 0
  private var openId:String = null
  private var studentId:String = null
  private var studentName:String = null
  private var tuition:Double = .0
  private var accommodation:Double = .0
  private var payStatus:Int = 0
  private var createAt:String = null
  private var reserve1:String = null
  private var reserve2:String = null
  private var reserve3:String = null
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false, length = 20)
  def getId: Int = {
    id
  }
  def setId(id: Int) {
    this.id = id
  }

  @Column(name = "open_id", length = 50)
  def getOpenId: String = {
    openId
  }

  def setOpenId(openId: String) {
    this.openId = openId
  }

  @Column(name = "student_id", length = 50)
  def getStudentId: String = {
    studentId
  }

  def setStudentId(studentId: String) {
    this.studentId = studentId
  }

  @Column(name = "student_name", length = 50)
  def getStudentName: String = {
    studentName
  }

  def setStudentName(studentName: String) {
    this.studentName = studentName
  }

  @Column(name = "tuition", length = 10)
  def getTuition: Double = {
    tuition
  }

  def setTuition(tuition: Double) {
    this.tuition = tuition
  }


  @Column(name = "accommodation", length = 10)
  def getAccommodation: Double = {
    accommodation
  }

  def setAccommodation(accommodation: Double) {
    this.accommodation = accommodation
  }

  @Column(name = "pay_status", length = 1)
  def getPayStatus: Int = {
    payStatus
  }

  def setPayStatus(payStatus: Int) {
    this.payStatus = payStatus
  }

  @Column(name = "create_at", length = 10)
  def getCreateAt: String = {
    createAt
  }

  def setCreateAt(createAt: String) {
    this.createAt = createAt
  }

  @Column(name = "reserve1", length = 200)
  def getReserve1: String = {
    reserve1
  }

  def setReserve1(reserve1: String) {
    this.reserve1 = reserve1
  }

  @Column(name = "reserve2", length = 200)
  def getReserve2: String = {
    reserve2
  }

  def setReserve2(reserve2: String) {
    this.reserve2 = reserve2
  }

  @Column(name = "reserve3", length = 200)
  def getReserve3: String = {
    reserve3
  }

  def setReserve3(reserve3: String) {
    this.reserve3 = reserve3
  }
}
