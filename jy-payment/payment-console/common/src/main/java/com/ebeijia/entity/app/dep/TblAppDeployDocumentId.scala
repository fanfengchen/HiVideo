package com.ebeijia.entity.app.dep

import javax.persistence._


/**
 *  文章管理TblAppDeployDocument主键
 */
@Embeddable
@SerialVersionUID(1L)
class TblAppDeployDocumentId extends java.io.Serializable{
  private var artId: Int = 0
  private var brhNo:String=null
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false, length = 10)
  def getArtId:Int={
    artId
  }
  def setArtId(artId:Int): Unit ={
    this.artId=artId
  }

  @Column(name = "BRH_NO", nullable = false, length = 10)
  def getBrhNo:String={
    brhNo
  }
  def setBrhNo(brhNo:String): Unit ={
    this.brhNo = brhNo
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[TblAppDeployDocumentId]

  override def equals(other: Any): Boolean = other match {
    case that: TblAppDeployDocumentId =>
      (that canEqual this) &&
        artId == that.artId &&
        brhNo == that.brhNo
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(artId, brhNo)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
