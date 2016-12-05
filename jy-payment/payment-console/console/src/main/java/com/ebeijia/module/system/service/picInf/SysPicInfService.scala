package com.ebeijia.module.system.service.picInf

/**
 * 图片
  * PicInfService
 */

import java.util.List

import com.ebeijia.entity.system.system.TblSysPicInf

trait SysPicInfService {
  //修改实体信息
  def updatePicInf(picInf: TblSysPicInf)
  //逻辑删除实体信息
  def deletePicInf(picInf: TblSysPicInf)
  //返回实体信息
  def queryPicInfList: List[_]
  //返回分页形式的实体信息
  def queryPageList(picInf: TblSysPicInf, page: Int, size: Int): List[TblSysPicInf]

  def countTotalNum(picInf: TblSysPicInf): Int

  def queryPicInfById(id: Int): TblSysPicInf
  //保存图片
  def save(picInf: TblSysPicInf)
  /**
   * 最大id+1
   * */
  def maxId:Int

}
