package com.ebeijia.module.system.service.operatorInf

import java.util
import java.util.{List, Map}

import com.ebeijia.module.system.dao.operatorInf.SysOperatorInfDao
import com.ebeijia.entity.system.system.{TblSysOperatorInf, TblSysOperatorInfId}
import com.ebeijia.module.system.service.picInf.SysPicInfService
import com.ebeijia.module.system.service.roleInf.SysRoleInfService
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
  * 操作员
  * OperatorService
  *
  * @author xiong.wang
  *         15/8/17
  */
@Service
final class SysOperatorServiceImpl extends SysOperatorService {
  private val logger: Logger = LoggerFactory.getLogger(classOf[SysOperatorServiceImpl])
  @Autowired
  private val operatorInfDao: SysOperatorInfDao = null
  @Autowired
  private val roleInfService: SysRoleInfService = null
  @Transactional
  @Cacheable(value = Array("operatorCache"), key = "#root.method.name+#id")
  def getById(id: TblSysOperatorInfId): TblSysOperatorInf = {
    operatorInfDao.getById(id)
  }

  @Transactional
  @Cacheable(value = Array("operatorCache"), key = "#root.method.name+#oprName+#oprName+#oprStat+#pageData")
  def findBySql(oprName: String, oprStat: String, pageData: String): Map[String, AnyRef] = {
    val query: StringBuilder = new StringBuilder
    query.append("select a.id.brhNo,a.id.oprId,a.oprName,a.roleId,a.oprDsc,a.oprStat,r.roleName ")
    query.append(" from TblSysOperatorInf a,TblSysRoleInf r ")
    query.append(" where a.roleId=r.id.ruleId ")
    if (oprName != null && !("" == oprName)) {
      query.append(" AND a.oprName like '%").append(oprName).append("%'")
    }
    if (oprStat != null && !("" == oprStat)) {
      query.append(" AND a.oprStat ='").append(oprStat).append("'")
    }
    query.append("  order by  a.id.oprId desc")
    val m: Map[String, AnyRef] = operatorInfDao.findByPage(query.toString(), pageData)
    m
  }

  @Transactional
  @Cacheable(value = Array("operatorCache"), key = "#root.method.name")
  def listOperatorByUpTime: List[TblSysOperatorInf] = {
    val sb: StringBuilder = new StringBuilder
    sb.append("select u.oprId,u.oprName,u.lastLogTime,p.picUrl from TblSysOperatorInf u, TblSysPicInf p")
    sb.append(" where u.oprHead = p.picId order by u.lastLogTime desc")
    val result: List[TblSysOperatorInf] = operatorInfDao.findByPage(sb.toString(), 1, 5)
    result
  }

  //查用户带头像信息,更新操作员登录时间 不加缓存
  def usrInfHead(usrId: Int): List[TblSysOperatorInf] = {
    val sb: StringBuilder = new StringBuilder
    sb.append("select u.oprId,u.oprName,p.picUrl from TblSysOperatorInf u, TblSysPicInf p")
    sb.append(" where u.oprHead = p.picId  and u.oprId ='").append(usrId).append("'")
    //去除更新最近登录时间
    //    val tblOperatorInf: TblOperatorInf = this.getById(usrId)
    //    tblOperatorInf.setLastLogTime(DateTime4J.getCurrentDateTime)
    //    operatorInfDao.update(tblOperatorInf)
    operatorInfDao.find(sb.toString())
  }

  @Transactional
  @Cacheable(value = Array("operatorCache"), key = "#root.method.name+#name")
  def login(name: String): List[TblSysOperatorInf] = {
    val sb: StringBuilder = new StringBuilder
    sb.append("FROM TblSysOperatorInf WHERE oprName = '").append(name).append("'")

    val tblOperatorInfs: List[TblSysOperatorInf] = operatorInfDao.find(sb.toString())
    tblOperatorInfs
  }

  @Transactional
  @Cacheable(value = Array("operatorCache"), key = "#root.method.name+#oprName+#pwd")
  def checkOperator(oprName: String, pwd: String): List[TblSysOperatorInf] = {
    val sb: StringBuilder = new StringBuilder
    sb.append("FROM TblSysOperatorInf WHERE oprName = '").append(oprName).append("'")
    sb.append(" and oprPwd ='").append(pwd).append("'")
    val operatorInfs: List[TblSysOperatorInf] = operatorInfDao.find(sb.toString())
    operatorInfs
  }

  @Transactional
  @Cacheable(value = Array("operatorCache"), key = "#root.method.name+#id+#pwd")
  def checkScalaAdmin(id: Int, pwd: String): util.List[TblSysOperatorInf] = {
    val sb: StringBuilder = new StringBuilder
    sb.append("FROM TblSysOperatorInf WHERE oprId = ").append(id).append("")
    sb.append(" and oprPwd ='").append(pwd).append("'")
    val operatorInf: List[TblSysOperatorInf] = operatorInfDao.find(sb.toString())
    operatorInf
  }

  @Transactional
  @CacheEvict(value = Array("operatorCache", "roleCache"), allEntries = true) def update(data: TblSysOperatorInf) {
    operatorInfDao.update(data)
  }

  @Transactional
  @CacheEvict(value = Array("operatorCache", "roleCache"), allEntries = true) def
  updateBySql(tbloprInf: TblSysOperatorInf,roleId:String) {
    val sb:StringBuilder = new StringBuilder
    sb.append(" UPDATE TBL_SYS_OPERATOR_INF SET BRH_NO = '").append(tbloprInf.getId.getBrhNo).append("', ")
    sb.append(" ROLE_ID = ").append(roleId).append(",")
    sb.append(" OPR_NAME = '").append(tbloprInf.getOprName).append("',")
    sb.append(" OPR_DSC = '").append(tbloprInf.getOprDsc).append("'")
    sb.append(" WHERE OPR_ID = '").append(tbloprInf.getId.getOprId).append("' ")
    sb.append(" AND ROLE_ID = ").append(tbloprInf.getRoleId).append("")
    operatorInfDao.executeSql(sb.toString())
    roleInfService.updateBySql(tbloprInf)
  }

  @CacheEvict(value = Array("operatorCache", "roleCache"), allEntries = true)
  @Transactional def save(data: TblSysOperatorInf) {
    operatorInfDao.save(data)
  }

  @CacheEvict(value = Array("operatorCache", "roleCache"), allEntries = true)
  @Transactional
  def delById(id: TblSysOperatorInfId) {
    operatorInfDao.deleteById(id)
  }


  //管理员修改密码  2015-10-21
  @CacheEvict(value = Array("operatorCache"), allEntries = true)
  @Transactional
  def updatePwd(oprName: String, pwd: String) {
    logger.info("管理员开始修改密码：oprName=" + oprName + "------pwd=" + pwd)
    val sqlUpdate: String = "update TblSysOperatorInf set oprPwd='" + pwd + "' WHERE oprName = '" + oprName + "'"
    operatorInfDao.updateAll(sqlUpdate)
  }


  /**
   * 判断管理员名称不能重复
   **/
  @Transactional
  @Cacheable(value = Array("operatorCache"), key = "#root.method.name+#oprId+#oprName")
  def isOperatorName(oprId: Int, oprName: String): Int = {
    operatorInfDao.isOperatorName(oprId, oprName)
  }

  /**
   * 根据角色判断是否有
   * 用户再使用
   **/
  @Transactional
  @Cacheable(value = Array("operatorCache"), key = "#root.method.name+#role")
  def getOperator(role: Int): List[TblSysOperatorInf] = {
    val sb: StringBuilder = new StringBuilder
    sb.append("FROM TblSysOperatorInf WHERE roleId ='").append(role).append("'")

    operatorInfDao.find(sb.toString())
  }


  /** 判断operator角色只能是一个
    * */
  @Transactional
  @Cacheable(value = Array("operatorCache"), key = "#root.method.name+#roleId+#oprId")
  def getOperatorRoleId(roleId: Int, oprId: Int): List[TblSysOperatorInf] = {
    val sb: StringBuilder = new StringBuilder
    sb.append(" FROM TblSysOperatorInf WHERE roleId ='1'")
    sb.append(" and id.oprId not in ('").append(oprId).append("')")
    operatorInfDao.find(sb.toString())
  }

  /**
   * 验证授权码
   */
  @Transactional
  @Cacheable(value = Array("operatorCache"), key = "#root.method.name+#code")
  def validCode(code: String): Boolean = {
    val hql: String = "from TblSysOperatorInf where code = '" + code + "' and roleId = '1'"
    val tblAdminInf: List[TblSysOperatorInf] = operatorInfDao.find(hql)
    if (tblAdminInf.size() <= 0) {
      return false
    } else {
      return true
    }
  }

  /**手动自增**/
  def getMaxId():Int ={
    val hql: String = "SELECT id.oprId from TblSysOperatorInf"
//    val tblAdminInf: List[TblSysOperatorInf] = operatorInfDao.find(hql)
//    return tblAdminInf.get(0).asInstanceOf[Int]+1
    val seqList: List[_] = operatorInfDao.find(hql)
    var maxDevation: Integer = 0
    val totalCount: Int = seqList.size()
    if (totalCount >= 1) {
      var max: Integer = Integer.parseInt(seqList.get(0).toString)
      for (i <- 0 until totalCount) {
        val temp: Integer = Integer.parseInt(seqList.get(i).toString)
        if (temp > max) {
          max = temp
        }
        maxDevation = max
      }
    }
    return maxDevation+1
  }

  @Transactional
  @Cacheable(value = Array("operatorCache"), key = "#root.method.name+#role")
  def vailRole(role:Int):Boolean = {
    val hql:String = "from TblSysOperatorInf where roleId = "+role+""
    val operList:List[TblSysOperatorInf]= operatorInfDao.find(hql)
    if(operList.isEmpty || operList.size() ==0){
      return true
    }
    return false
  }
}
