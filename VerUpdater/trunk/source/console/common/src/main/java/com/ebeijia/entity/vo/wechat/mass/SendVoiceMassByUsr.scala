package com.ebeijia.entity.vo.wechat.mass

import java.util.List

/** 群发筛选用户
  * SendVoiceMassByUsr
  * @author zhicheng.xu
  *         15/8/19
  */


class SendVoiceMassByUsr extends BaseMass {
  private var voice: Voice = null
  private var touser: List[_] = null

  def getTouser: List[_] = {
    touser
  }

  def setTouser(touser: List[_]) {
    this.touser = touser
  }

  def getVoice: Voice = {
    voice
  }

  def setVoice(voice: Voice) {
    this.voice = voice
  }
}

