package com.ebeijia.module.system.service.txnLog

import java.util.{List, Map}

import com.ebeijia.entity.system.log.TblSysTxnLog

/**
 * TxnLogService
 * @author zhicheng.xu
 *         15/8/13
 */



trait SysTxnLogService {
  def updateTxnLog(txnLog: TblSysTxnLog)

  def deleteTxnLog(txnLog: TblSysTxnLog)

  def addTxnLog(txnLog: TblSysTxnLog)

  def queryTxnLogList: List[_]

  def countTotalNum(txnLog: TblSysTxnLog): Int

  def queryTxnLogById(id: Int): TblSysTxnLog
  //分页查询
  def findBySql(date: String,endDate:String, txnChl: String, status: String, pageData: String): Map[String, AnyRef]
}
