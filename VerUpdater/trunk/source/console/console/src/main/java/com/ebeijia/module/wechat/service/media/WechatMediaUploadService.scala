package com.ebeijia.module.wechat.service.media
import com.ebeijia.entity.vo.wechat.media.UpMediaReq
/**
  * 通用素材上传方法
  * MediaCommonService
  * @author xiong.wang
  *         2016/6/21
  */
trait WechatMediaUploadService {
  //通用素材上传方法
  def upLoadMediaUtil(upMediaReq: UpMediaReq): String
}
