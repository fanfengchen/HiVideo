package com.ebeijia.module.system.service.txnLog

import java.util.{List, Map}

import com.ebeijia.module.system.dao.txnLog.SysTxnLogDao
import com.ebeijia.entity.system.log.TblSysTxnLog
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * TxnLogServiceImpl
 * @author zhicheng.xu
 *         15/8/13
 */



@Service
final class SysTxnLogServiceImpl extends SysTxnLogService {
  @Autowired
  private val txnLogDao: SysTxnLogDao = null

  @Transactional
  def updateTxnLog(txnLog: TblSysTxnLog) {
    txnLogDao.saveOrUpdate(txnLog)
  }

  @Transactional
  def deleteTxnLog(txnLog: TblSysTxnLog) {
    txnLogDao.update(txnLog)
  }

  @Transactional
  def addTxnLog(txnLog: TblSysTxnLog) {
    txnLogDao.saveOrUpdate(txnLog)
  }

  @Transactional
  def queryTxnLogList: List[_] = {
    txnLogDao.getTxnLogList
  }

  @Transactional
  def countTotalNum(txnLog: TblSysTxnLog): Int = {
    txnLogDao.countTotalNum(txnLog)
  }

  @Transactional
  def queryTxnLogById(id: Int): TblSysTxnLog = {
    txnLogDao.getById(id)
  }

  @Transactional
  def findBySql(date: String,endDate:String, txnChl: String, status: String, pageData: String): Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("from TblSysTxnLog ")
    query.append(" where 1=1")

    if (date != null && !("".equals(date))) {
      query.append(" AND txnDate >='").append(date).append("000000' ")

    }
    if (endDate != null && !("".equals( endDate))) {

      query.append(" AND txnDate <='").append(endDate).append("235959' ")
    }
    if (txnChl != null && !("" .equals(txnChl) )) {
      query.append(" AND txnChl like '%").append(txnChl).append("%' ")
    }
    if (status != null && !("" .equals(status) )) {
      query.append(" AND txnStatus ='").append(status).append("' ")
    }
    query.append(" order by txnNo desc")
    txnLogDao.findByPage(query.toString, pageData)
  }
}
