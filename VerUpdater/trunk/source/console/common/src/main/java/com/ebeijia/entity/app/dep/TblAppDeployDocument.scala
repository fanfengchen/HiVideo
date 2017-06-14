package com.ebeijia.entity.app.dep

import java.io.Serializable
import javax.persistence._

import com.ebeijia.entity.app.ban.TblAppBannerInfId

@Entity
@Table(name = "TBL_APP_DEPLOY_DOCUMENT")
@SerialVersionUID(1L)
class TblAppDeployDocument() extends Serializable {
  private var id: TblAppDeployDocumentId = null
  private var deployAgent: String = null //发布应用APP/00001：web/00010：微信/00100
  private var deployTime: String = null  //发布时间
  private var visitCount: Int = 0  //访问总数
  private var state: String = null //0正常1关闭
  private var flag: String = null  //0新闻1通知2贴士3问题（有标题）4问题（无标题）5关于（有封面）6关于（无封面）
  private var docType: Int = 0  //文档类型0：html,1：text
  private var docTitle: String = null  //文章标题
  private var docContent: String = null  //文章内容
  private var docAuthor: String = null  //文章作者
  private var docDesc: String = null  //文章描述
  private var docImg: Int = 0  //文章封面
  private var res1: String = null  //预留1
  private var res2: String = null  //预留2
  private var docFlag: String = null  //1新用户安装指南2其他业务流程3燃气安装常识4燃气价格5公司简介6企业文化
  @EmbeddedId
  @AttributeOverrides(Array(new AttributeOverride(name = "brhNo", column = new Column(name = "BRH_NO", nullable = false, length = 50))
    , new AttributeOverride(name = "artId", column = new Column(name = "ID", nullable = false, length = 50))))
  def getId: TblAppDeployDocumentId = {
    id
  }
  def setId(id: TblAppDeployDocumentId) {
    this.id = id
  }
  @Column(name = "DEPLOY_AGENT", length = 11)
  def getDeployAgent: String = {
    deployAgent
  }
  def setDeployAgent(deployAgent: String) {
    this.deployAgent = deployAgent
  }
  @Column(name = "DEPLOY_TIME", length = 14)
  def getDeployTime: String = {
    deployTime
  }
  def setDeployTime(deployTime: String) {
    this.deployTime = deployTime
  }
  @Column(name = "VISIT_COUNT", length = 11)
  def getVisitCount: Int = {
    visitCount
  }
  def setVisitCount(visitCount: Int) {
    this.visitCount = visitCount
  }
  @Column(name = "STATE", length = 1)
  def getState: String = {
      state
  }
  def setState(state: String) {
    this.state = state
  }
  @Column(name = "FLAG", length = 1)
  def getFlag: String = {
    flag
  }
  def setFlag(flag: String) {
    this.flag = flag
  }
  @Column(name = "DOC_TYPE", length = 11)
  def getDocType: Int = {
    docType
  }
  def setDocType(docType: Int) {
    this.docType = docType
  }
  @Column(name = "DOC_TITLE", length = 45)
  def getDocTitle: String = {
    docTitle
  }
  def setDocTitle(docTitle: String) {
    this.docTitle = docTitle
  }
  @Column(name = "DOC_CONTENT", length = 4500)
  def getDocContent: String = {
    docContent
  }
  def setDocContent(docContent: String) {
    this.docContent = docContent
  }
  @Column(name = "DOC_AUTHOR", length = 45)
  def getDocAuthor: String = {
    docAuthor
  }
  def setDocAuthor(docAuthor: String) {
    this.docAuthor = docAuthor
  }
  @Column(name = "DOC_DESC", length = 512)
  def getDocDesc: String = {
    docDesc
  }
  def setDocDesc(docDesc: String) {
    this.docDesc = docDesc
  }
  @Column(name = "DOC_IMG", length = 11)
  def getDocImg: Int = {
    docImg
  }
  def setDocImg(docImg: Int) {
    this.docImg = docImg
  }
  @Column(name = "RES1", length = 200)
  def getRes1: String = {
    res1
  }
  def setRes1(res1: String) {
    this.res1 = res1
  }
  @Column(name = "RES2", length = 200)
  def getRes2: String = {
    res2
  }
  def setRes2(res2: String) {
    this.res2 = res2
  }
  @Column(name = "DOC_FLAG", length = 2)
  def getDocFlag: String = {
    docFlag
  }
  def setDocFlag(docFlag: String) {
    this.docFlag = docFlag
  }
}
