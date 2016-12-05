package com.ebeijia.controller.system.mcht

import java.util
import java.util.{List, HashMap, Map}
import javax.servlet.http.HttpServletRequest

import com.ebeijia.Validate.ActionValidate
import com.ebeijia.ajax.resp.AjaxResp
import com.ebeijia.annotation.MyLog
import com.ebeijia.api.BaseValidRoleFunc
import com.ebeijia.entity.system.system.{TblSysMchtInf, TblSysMchtInfId}
import com.ebeijia.entity.system.vo.RoleFuncParam
import com.ebeijia.module.system.dao.brh.TblSysBrhInfDao
import com.ebeijia.module.system.service.mcht.MchtInfService
import com.ebeijia.util.core.{FuncCode, RespCode}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}


/**
  * 商户管理（非微信商户）
  * MchtManageAction
  *
  * @author xiong.wang
  * 2016/6/27
  */

/**
 * 商户管理（非微信商户）
 * MchtManageAction
  *
  * @author xiong.wang
 * 2016/6/27
 */
@Controller
@RequestMapping(value = Array("/sys/mcht"))
class SysMchtAction extends BaseValidRoleFunc {
  private val logger: Logger = LoggerFactory.getLogger(classOf[SysMchtAction])
  @Autowired
  private val mchtInfService: MchtInfService = null
  @Autowired
  private val tblBrhInfDao: TblSysBrhInfDao = null

  @RequestMapping(value = Array("query.html"), method = Array(RequestMethod.POST))
  @ResponseBody def query(brhNo:String,roleFuncParam: RoleFuncParam,aoData:String,request:HttpServletRequest): Map[String, AnyRef] = {
    roleFuncParam.setFuncId(FuncCode.MCHT_FUNC_MAINTAIN)
    val msg: String = this.validRoleFunc(roleFuncParam)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    val mchtId: String = request.getParameter("mchtId")
    val mchtName: String = request.getParameter("mchtName")
    val map: Map[String, AnyRef] = new HashMap[String, AnyRef]
    try {
      val mapTmp: Map[String, AnyRef] = mchtInfService.findBySql(brhNo,mchtId, mchtName, aoData)
      val smiList:List[TblSysMchtInf] = mapTmp.get("list").asInstanceOf[List[TblSysMchtInf]]
      val brhMap :util.Map[String,AnyRef] = new util.HashMap[String,AnyRef]()
      for ( i <- 0 until smiList.size()) {
        val brhHql: String = "SELECT brhId,brhName FROM TblSysBrhInf WHERE brhId = ?"
        val singData: List[_] = tblBrhInfDao.find(brhHql,smiList.get(i).getId.getBrhNo)
        if(singData.size() > 0){
          brhMap.put(singData.get(0).asInstanceOf[Array[AnyRef]](0).toString,singData.get(0).asInstanceOf[Array[AnyRef]](1))
        }
      }
      map.put("mchtList", mapTmp.get("list"))
      map.put("current", mapTmp.get("current"))
      map.put("total", mapTmp.get("total"))
      map.put("brhMap",brhMap)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, map)
    }
    catch {
      case e: Exception => {
        logger.info("商户查询失败:" + e)
        AjaxResp.getReturn(RespCode.MCHT_ERROR_SEL, "")
      }
    }
  }


  @RequestMapping(value = Array("add.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "商户添加")
  @ResponseBody def add(roleFuncParam: RoleFuncParam,tblSysMchtInf:TblSysMchtInf,request:HttpServletRequest): Map[String, AnyRef] = {
    roleFuncParam.setFuncId(FuncCode.BRH_FUNC_ADDITION)
    val msg: String = this.validRoleFunc(roleFuncParam)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    val brhNo :String = request.getParameter("brhId")
    val mchtId :String = request.getParameter("mchtId")
    val tmid:TblSysMchtInfId = new TblSysMchtInfId
    tmid.setBrhNo(brhNo)
    tmid.setMchtId(mchtId)
    tblSysMchtInf.setId(tmid)
    val s: Array[Array[String]] = Array(Array(mchtId, "1", "8", "1"),
      Array(tblSysMchtInf.getMchtName, "0", "128", "1"),
      Array(tblSysMchtInf.getMchtAddr, "0", "128", "1"),
      Array(tblSysMchtInf.getMchtPostCd, "0", "6", "1"),
      Array(tblSysMchtInf.getMchtTel, "0", "15", "1"))
    if (ActionValidate.checkArray(s) != null) {
      return AjaxResp.getReturn(RespCode.PARAM_ERROR, "",ActionValidate.checkArray(s))
    }
    if (tblSysMchtInf == null) {
      return AjaxResp.getReturn(RespCode.OBJECT_ERROR_NULL, "")
    }
    try {
      //判断机构号唯一
      val flag: Boolean = mchtInfService.vailMchtUniq(tblSysMchtInf.getId.getMchtId)
      if(!flag){
        return AjaxResp.getReturn(RespCode.MCHT_ERROR_UNIQ, "")
      }
      mchtInfService.save(tblSysMchtInf)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        logger.info("新增商户失败:" + e)
        AjaxResp.getReturn(RespCode.MCHT_ERROR_ADD, "")
      }
    }
  }

  @RequestMapping(value = Array("del.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "商户删除")
  @ResponseBody def del(request: HttpServletRequest,roleFuncParam:RoleFuncParam,tblMchtInf:TblSysMchtInfId): Map[String, AnyRef] = {
    roleFuncParam.setFuncId(FuncCode.BRH_FUNC_MAINTAIN)
    val msg: String = this.validRoleFunc(roleFuncParam)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    try {
      tblMchtInf.setBrhNo(request.getParameter("brhNoFlag"))
      mchtInfService.delById(tblMchtInf)
      AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
    }
    catch {
      case e: Exception => {
        logger.info("删除商户失败:" + e)
        AjaxResp.getReturn(RespCode.MCHT_ERROR_DEL, "")
      }
    }
  }

  @RequestMapping(value = Array("upd.html"), method = Array(RequestMethod.POST))
  @MyLog(remark = "商户修改")
  @ResponseBody def upd(roleFuncParam:RoleFuncParam,tblSysMchtInf:TblSysMchtInf,tblSysMchtInfId:TblSysMchtInfId): Map[String, AnyRef] = {
    tblSysMchtInf.setId(tblSysMchtInfId)
    roleFuncParam.setFuncId(FuncCode.BRH_FUNC_MAINTAIN)
    val msg: String = this.validRoleFunc(roleFuncParam)
    if (msg != null) {
      if (msg.equals("tokenLose")) {
        return AjaxResp.getReturn(RespCode.TOKEN_CODE, "")
      }
      return AjaxResp.getReturn(RespCode.ERROR_CODE, "")
    }
    try {
      //0成功 1为空 2失败
      val staNum:Int = mchtInfService.selByIdAndName(tblSysMchtInf)
      if(staNum == 0){
        AjaxResp.getReturn(RespCode.SUCCESS_CODE, "")
      }else if(staNum == 1){
        AjaxResp.getReturn(RespCode.ERROR_CODE, "","该商户不存在")
      }else{
        AjaxResp.getReturn(RespCode.ERROR_CODE, "")
      }
    }
    catch {
      case e: Exception => {
        logger.info("修改商户失败:" + e)
        AjaxResp.getReturn(RespCode.MCHT_ERROR_UPD, "")
      }
    }
  }
}
