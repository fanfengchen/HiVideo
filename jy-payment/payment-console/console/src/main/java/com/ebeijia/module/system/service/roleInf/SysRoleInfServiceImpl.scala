package com.ebeijia.module.system.service.roleInf

import java.util
import java.util._

import com.ebeijia.module.system.dao.role.{SysRoleInfDao, SysRoleMapDao}
import com.ebeijia.entity.system.system._
import com.ebeijia.module.system.service.brh.SysBrhInfService
import com.ebeijia.util.core.RoleButtonUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.{CacheEvict, Cacheable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
  * RoleInfServiceImpl
  *
  * @author zhicheng.xu
  *         15/8/13
  */


@Service
final class SysRoleInfServiceImpl extends SysRoleInfService {
  @Autowired
  private val roleInfDao: SysRoleInfDao = null
  @Autowired
  private val roleMapDao: SysRoleMapDao = null
  @Autowired
  private val sysBrhInfService: SysBrhInfService = null

  @Transactional
  @Cacheable(value = Array("roleCache"), key = "#root.method.name+#usrId")
  def funcFind(usrId: Int): String = {
    val sb: StringBuilder = new StringBuilder
    //修改返回值 2015-10-21
    sb.append("select r.id.funcId from TblSysOperatorInf a ,TblSysRoleMap r ")
    sb.append(" where a.id.oprId = ").append(usrId).append(" and a.roleId =r.id.roleId")
    val list: List[_] = roleInfDao.find(sb.toString)
    val result: StringBuilder = new StringBuilder
    if (list == null || list.size() == 0) {
      return null
    } else {
      for (i <- 0 until list.size()) {
        result.append(list.get(i)).append(",")
      }
    }
    result.toString.substring(0, result.length - 1)
  }

  //查询已有权限和未添加权限列表
  @Transactional
  @Cacheable(value = Array("roleCache"), key = "#root.method.name+#roleId")
  def findRoleDef(roleId: Int): Map[String, AnyRef] = {
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    val exis: StringBuilder = new StringBuilder
    exis.append("select f.funcId,f.funcName from TblSysFuncInf f , TblSysRoleMap r ")
    exis.append("where f.funcId = r.id.funcId and r.id.roleId = '").append(roleId).append("' ")
    exis.append(" order by f.funcId")
    val list: List[_] = roleInfDao.find(exis.toString)
    map.put("exis", list)
    //    val noexis: StringBuilder = new StringBuilder
    //    noexis.append("select funcId,funcName from TblFuncInf where funcId not in(")
    //    noexis.append("select f.funcId from TblRoleMap r,TblFuncInf f ")
    //    noexis.append("where r.id.funcId = f.funcId and r.id.roleId='").append(roleId).append("') and parentId <>'-'")
    //    noexis.append(" order by funcId")
    //    val noexisList: NewsInfoList[_] = roleInfDao.find(noexis.toString)
    //    map.put("noexis", noexisList)
    map
  }

  @Transactional
  @Cacheable(value = Array("funcCache"), key = "#root.method.name")
  def funcFindAll(): Map[String, AnyRef] = {
    val map: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
    val sb: StringBuilder = new StringBuilder
    //    sb.append("select f.funcId,f.funcName  from TblFuncInf f where f.parentId <> '-'")
    sb.append("SELECT f.funcId,f.funcName,f.parentId,f.funcType  FROM TblSysFuncInf f WHERE 1=1")
    sb.append(" ORDER BY f.funcId")
    val datas: List[_] = roleInfDao.find(sb.toString)
    val it: Iterator[_] = datas.iterator
    val list: List[AnyRef] = new LinkedList[AnyRef]
    while (it.hasNext) {
      val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
      val o: Array[AnyRef] = it.next.asInstanceOf[Array[AnyRef]]
      hashMap.put("key", o(1).toString)
      hashMap.put("value", o(0))
      hashMap.put("parent", o(2))
      hashMap.put("funcType", o(3))
      list.add(hashMap)
    }
    map.put("info", list)
    map
  }

  @Cacheable(value = Array("funcCache"), key = "#root.method.name+#roleId+#brhNo")
  def funcRolePer(roleId: String, brhNo: String): Map[String, AnyRef] = {
    val map: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
    val sb: StringBuilder = new StringBuilder
    //    sb.append("select f.funcId,f.funcName  from TblFuncInf f where f.parentId <> '-'")
    sb.append("SELECT f.funcId,f.funcName,f.parentId,f.funcType  FROM TblSysFuncInf f,TblSysRoleMap r WHERE 1=1")
    sb.append(" AND f.funcId = r.id.funcId")
    if (null != roleId && !"".equals(roleId)) {
      sb.append(" AND r.id.roleId = '" + roleId + "'")
    }
    if (null != brhNo && !"".equals(brhNo)) {
      sb.append(" AND r.id.brhNo = '" + brhNo + "'")
    }
    sb.append(" ORDER BY f.funcId")
    val datas: List[_] = roleInfDao.find(sb.toString)
    val it: Iterator[_] = datas.iterator
    val list: List[AnyRef] = new LinkedList[AnyRef]
    while (it.hasNext) {
      val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
      val o: Array[AnyRef] = it.next.asInstanceOf[Array[AnyRef]]
      hashMap.put("key", o(1).toString)
      hashMap.put("value", o(0))
      hashMap.put("parent", o(2))
      hashMap.put("funcType", o(3))
      list.add(hashMap)
    }
    map.put("info", list)
    map
  }

  @Transactional
  @CacheEvict(value = Array("operatorCache", "roleCache", "funcCache"), allEntries = true)
  def save(brhNo: String, name: String, dsc: String, roleList: String, roleType: String) {
    //roleId
    val roleId: Int = roleInfDao.getMaxId() + 1
    val data: TblSysRoleInf = new TblSysRoleInf
    val da1: TblSysRoleInfId = new TblSysRoleInfId
    da1.setRuleId(roleId)
    da1.setBrhNo(brhNo)
    data.setId(da1)
    data.setRoleName(name)
    data.setDsc(dsc)
    data.setRoleType(roleType)
    roleInfDao.save(data)
    val list: Array[String] = roleList.split(",")
    for (funcId <- 0 until list.size) {
      val roleMap: TblSysRoleMap = new TblSysRoleMap
      val id: TblSysRoleMapId = new TblSysRoleMapId
      id.setFuncId(list(funcId))
      id.setRoleId(data.getId.getRuleId.toString)
      id.setBrhNo(data.getId.getBrhNo)
      roleMap.setId(id)
      roleMapDao.save(roleMap)
    }
  }

  @Transactional
  @Cacheable(value = Array("roleCache"), key = "#root.method.name+#brhNo+#roleId+#roleName+#pageData")
  def findBySql(brhNo: String, roleId: Int, roleName: String, pageData: String): Map[String, AnyRef] = {
    val map: Map[String, AnyRef] = sysBrhInfService.getBrhBelowInf(brhNo)
    var brhStr: String = ""
    for (key <- map.keySet().toArray()) {
      brhStr = brhStr.concat("'").concat(key.toString).concat("',")
    }
    if (!brhStr.equals("")) {
      brhStr = brhStr.substring(0, brhStr.lastIndexOf(","))
    }
    val query: StringBuilder = new StringBuilder
    query.append(" from TblSysRoleInf where 1=1")
    if (roleId != 0) {
      query.append(" AND id.ruleId = ").append(roleId).append("")
    }
    if (roleName != null && !("" == roleName)) {
      query.append(" AND roleName like '").append(roleName).append("'")
    }
    query.append(" AND id.brhNo in(").append(brhStr).append(")")
    query.append(" order by id.ruleId desc")
    val m: Map[String, AnyRef] = roleInfDao.findByPage(query.toString, pageData)
    m
  }

  @Transactional
  @Cacheable(value = Array("roleCache"), key = "#root.method.name")
  def findRole: Map[String, AnyRef] = {
    val map: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
    val sb: StringBuilder = new StringBuilder
    sb.append("select r.id.ruleId,r.roleName from TblSysRoleInf r ")
    //    sb.append(" order by r.id.roleId desc")
    val datas: List[_] = roleInfDao.find(sb.toString)
    val it: Iterator[_] = datas.iterator
    val list: List[AnyRef] = new LinkedList[AnyRef]
    while (it.hasNext) {
      val hashMap: Map[String, AnyRef] = new LinkedHashMap[String, AnyRef]
      val o: Array[AnyRef] = it.next.asInstanceOf[Array[AnyRef]]
      hashMap.put("key", o(0).toString + "-" + o(1).toString)
      hashMap.put("value", o(0))
      list.add(hashMap)
    }
    map.put("info", list)
    map
  }

  @Transactional
  @Cacheable(value = Array("roleCache"), key = "#root.method.name+#id")
  def getById(id: TblSysRoleInfId): TblSysRoleInf = {
    roleInfDao.getById(id)
  }

  @Transactional
  @CacheEvict(value = Array("operatorCache", "roleCache", "funcCache"), allEntries = true)
  def delById(id: TblSysRoleInfId) {
    roleInfDao.deleteById(id)
    val sqlDelete: String = "delete from TblSysRoleMap where id.roleId = '" + id + "'"
    roleMapDao.updateAll(sqlDelete)
  }

  //更新角色
  @Transactional
  @CacheEvict(value = Array("operatorCache", "tblBrhCache", "roleCache", "funcCache"), allEntries = true)
  def update(tblRoleInf: TblSysRoleInf, roleList: String) {
    roleInfDao.update(tblRoleInf)
    //先删除 然后添加
    val sqlDelete: String = "delete from TblSysRoleMap where id.roleId = '" + tblRoleInf.getId.getRuleId + "'"
    roleMapDao.updateAll(sqlDelete)
    val list: Array[String] = roleList.split(",")
    for (funcId <- 0 until list.size) {
      val roleMap: TblSysRoleMap = new TblSysRoleMap
      val id: TblSysRoleMapId = new TblSysRoleMapId
      id.setFuncId(list(funcId))
      id.setRoleId(tblRoleInf.getId.getRuleId.toString)
      id.setBrhNo(tblRoleInf.getId.getBrhNo)
      roleMap.setId(id)
      roleMapDao.save(roleMap)
    }
  }

  /** 判断角色名称唯一
    * */
  @Transactional
  @Cacheable(value = Array("roleCache"), key = "#root.method.name+#roleId+#roleName")
  def isRoleName(roleId: Int, roleName: String): Int = {
    roleInfDao.isRoleName(roleId, roleName)
  }


  /**
    * 判断是否有权限
    *
    */
  @Transactional
  @Cacheable(value = Array("roleCache"), key = "#root.method.name+#roleId+#funcId")
  def roleFunCount(roleId: String, funcId: String): Int = {
    roleMapDao.roleFunCount(roleId, funcId)
  }

  //  @Transactional
  //  @Cacheable(value = Array("roleCache"), key = "#root.method.name")
  def findAllListRole(selbrhId:String): Map[String, AnyRef] = {
    val m: Map[String, AnyRef] = new HashMap[String, AnyRef]()
    val query: StringBuilder = new StringBuilder
    query.append(" from TblSysRoleInf where 1=1")
    query.append(" and id.brhNo = '").append(selbrhId).append("'")
    query.append(" order by id.ruleId desc")
    val list: List[TblSysRoleInf] = roleInfDao.find(query.toString())
    m.put("roleList", list)
    m
  }

  def vailDel(brhNo: String, roleId: Int): Boolean = {
    val map: Map[String, AnyRef] = sysBrhInfService.getBrhBelowInf(brhNo)
    var brhStr: String = ""
    for (key <- map.keySet().toArray()) {
      brhStr = brhStr.concat("'").concat(key.toString).concat("',")
    }
    if (!brhStr.equals("")) {
      brhStr = brhStr.substring(0, brhStr.lastIndexOf(","))
    }
    val query: StringBuilder = new StringBuilder
    query.append(" from TblSysRoleInf where 1=1")
    query.append(" AND id.ruleId = ").append(roleId).append("")
    query.append(" AND id.brhNo in(").append(brhStr).append(")")
    val list: util.List[TblSysRoleInf] = roleInfDao.find(query.toString())
    if (list.size() > 0) {
      return false
    }
    return true
  }

  @Transactional
  @CacheEvict(value = Array("operatorCache", "tblBrhCache", "roleCache", "funcCache"), allEntries = true)
  def updateBySql(tbloprInf: TblSysOperatorInf): Unit = {
    val hql1: String = "UPDATE TBL_SYS_ROLE_INF SET BRH_NO = '" + tbloprInf.getId.getBrhNo + "' WHERE RULE_ID = " + tbloprInf.getRoleId + " AND ROLE_NAME = '" + tbloprInf.getOprName + "'"
    roleInfDao.executeSql(hql1)
    val hql2: String = "from TblSysRoleMap where id.roleId = ? "
    val list: List[TblSysRoleMap] = roleMapDao.find(hql2, tbloprInf.getRoleId.toString)
    val sqlDelete: String = "delete from TblSysRoleMap where id.roleId = '" + tbloprInf.getRoleId + "'"
    roleMapDao.updateAll(sqlDelete)
    for (i <- 0 until list.size()) {
//      val roleMap: TblSysRoleMap = new TblSysRoleMap
//      val id: TblSysRoleMapId = new TblSysRoleMapId
//      id.setFuncId(list.get(i).getId.getFuncId)
//      id.setRoleId(list.get(i).getId.getRoleId)
//      id.setBrhNo(tbloprInf.getId.getBrhNo)
//      roleMap.setId(id)
//      roleMapDao.save(roleMap)
      val query:StringBuilder = new StringBuilder
      query.append("INSERT INTO TBL_SYS_ROLE_MAP VALUES ('").append(tbloprInf.getId.getBrhNo).append("',")
      query.append("'").append(list.get(i).getId.getRoleId).append("',")
      query.append("'").append(list.get(i).getId.getFuncId).append("')")
      roleMapDao.executeSql(query.toString())
    }
    //    val hql2:String = "UPDATE TBL_SYS_ROLE_MAP SET BRH_NO = '"+tbloprInf.getId.getBrhNo+"' WHERE ROLE_ID = "+tbloprInf.getRoleId+""
    //    roleMapDao.executeSql(hql2)
  }


  //  @Cacheable(value = Array("funcCache"), key = "#root.method.name+#roleId+#brhNo+#parentId")
  def funcRoleButton(roleId: String, brhNo: String, parentId: String): Map[String, util.List[util.Map[String, String]]] = {
    //菜单中启用按钮
    val sb: StringBuilder = new StringBuilder
    sb.append("SELECT f.funcId FROM TblSysFuncInf f,TblSysRoleMap r WHERE 1=1")
    sb.append(" AND f.funcId = r.id.funcId")
    sb.append(" AND f.parentId = '").append(parentId).append("'")
    sb.append(" AND r.id.roleId = '" + roleId + "'")
    sb.append(" AND r.id.brhNo = '" + brhNo + "'")
    sb.append(" AND f.funcType = 2")
    sb.append(" ORDER BY f.funcId")
    val list1: List[_] = roleInfDao.find(sb.toString)
    val size1:Int = list1.size()
    val lista: java.util.List[String] = new java.util.LinkedList[String]
    for(i <- 0 until size1){
      lista.add(list1.get(i).toString)
    }
    //菜单中禁用按钮
    val sb1: StringBuilder = new StringBuilder
    sb1.append("SELECT f.funcId FROM TblSysFuncInf f WHERE 1=1")
    sb1.append(" AND f.parentId = '").append(parentId).append("'")
    sb1.append(" AND f.funcType = 2")
    sb1.append(" ORDER BY f.funcId")
    val list2: List[_] = roleInfDao.find(sb1.toString)
    val size2:Int = list2.size()
    val listb: java.util.List[String] = new java.util.LinkedList[String]
    for(i <- 0 until size2){
      listb.add(list2.get(i).toString)
    }
    val map: Map[String, util.List[util.Map[String, String]]] = RoleButtonUtil.getRoleButton(lista, listb)
    map
  }

  def isRoleUniq(roleType:String): Int={
    val query:StringBuilder = new StringBuilder
    query.append("select count(*) from TblSysRoleInf where 1=1")
    query.append(" AND roleType = '").append(0).append("'")
    val list: util.List[_] = roleInfDao.find(query.toString())
    val count:Int = (list.get(0).asInstanceOf[Long]).intValue
    return count
  }
}
