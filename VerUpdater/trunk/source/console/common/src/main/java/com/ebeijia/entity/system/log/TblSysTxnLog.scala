package com.ebeijia.entity.system.log

import java.io.Serializable
import javax.persistence._

/**
 * TblTxnLog
 * @author zhicheng.xu
 *         15/8/11
 */


@Entity
@Table(name = "TBL_SYS_TXN_LOG")
@SerialVersionUID(1L)
class TblSysTxnLog extends Serializable {
  private var txnNo: Int = 0
  private var txnDate: String = null
  private var operator: String = null
  private var remoteAddr: String = null
  private var operaTime: String = null
  private var txnStatus: String = null
  private var txnName: String = null
  private var txnChl: String = null
  private var txnError: String = null
  private var tlMisc: String = null


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "TXN_NO", unique = true, nullable = false, length = 15)
  def getTxnNo: Int = {
    txnNo
  }

  def setTxnNo(txnNo: Int) {
    this.txnNo = txnNo
  }

  @Column(name = "TXN_DATE", nullable = false, length = 14)
  def getTxnDate: String = {
    txnDate
  }

  def setTxnDate(txnDate: String) {
    this.txnDate = txnDate
  }

  @Column(name = "OPERATOR", length = 8)
  def getOperator: String = {
    operator
  }

  def setOperator(operator: String) {
    this.operator = operator
  }

  @Column(name = "REMOTE_ADDR", length = 15)
  def getRemoteAddr: String = {
    remoteAddr
  }

  def setRemoteAddr(remoteAddr: String) {
    this.remoteAddr = remoteAddr
  }

  @Column(name = "OPERA_TIME", length = 7)
  def getOperaTime: String = {
    operaTime
  }

  def setOperaTime(operaTime: String) {
    this.operaTime = operaTime
  }

  @Column(name = "TXN_STATUS", length = 1)
  def getTxnStatus: String = {
    txnStatus
  }

  def setTxnStatus(txnStatus: String) {
    this.txnStatus = txnStatus
  }

  @Column(name = "TXN_NAME", length = 36)
  def getTxnName: String = {
    txnName
  }

  def setTxnName(txnName: String) {
    this.txnName = txnName
  }

  @Column(name = "TXN_CHL", length = 1)
  def getTxnChl: String = {
    txnChl
  }

  def setTxnChl(txnChl: String) {
    this.txnChl = txnChl
  }

  @Column(name = "TXN_ERROR", length = 128)
  def getTxnError: String = {
    txnError
  }

  def setTxnError(txnError: String) {
    this.txnError = txnError
  }

  @Column(name = "TL_MISC", length = 128)
  def getTlMisc: String = {
    tlMisc
  }

  def setTlMisc(tlMisc: String) {
    this.tlMisc = tlMisc
  }
}

