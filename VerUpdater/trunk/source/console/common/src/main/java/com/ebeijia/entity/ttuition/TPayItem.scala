package com.ebeijia.entity.ttuition

import javax.persistence._

@Entity
@Table(name = "t_pay_item")
@SerialVersionUID(1L)
class TPayItem extends Serializable{
  private var id:Int = 0
  private var name:String = null
  private var amount:Double = .0
  private var target:String = null
  private var status:Int = 0
  private var remark:String = null
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

  @Column(name = "name", length = 50)
  def getName: String = {
    name
  }

  def setName(name: String) {
    this.name = name
  }

  @Column(name = "amount", length = 15)
  def getAmount: Double = {
    amount
  }

  def setAmount(amount:Double) {
    this.amount = amount
  }

  @Column(name = "target", length = 20)
  def getTarget: String = {
    target
  }

  def setTarget(target: String) {
    this.target = target
  }

  @Column(name = "status", length = 4)
  def getStatus: Int = {
    status
  }

  def setStatus(status: Int) {
    this.status = status
  }

  @Column(name = "remark", length = 10)
  def getRemark: String = {
    remark
  }

  def setRemark(remark: String) {
    this.remark = remark
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
