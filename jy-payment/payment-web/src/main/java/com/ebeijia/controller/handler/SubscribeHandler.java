package com.ebeijia.controller.handler;

import com.ebeijia.entity.WxUser;
import com.ebeijia.service.WxUserService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * Created by zc on 2016/9/22.
 */
@Component
public class SubscribeHandler implements WxMpMessageHandler {

    @Autowired
    private WxUserService wxUserService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                    WxMpService wxMpService, WxSessionManager sessionManager)
            throws WxErrorException {

        String openid = wxMessage.getFromUserName();

        //判断是什么操作
        String code = (String)context.get("code");
        if("0".equals(code)){
            //关注
            WxMpUser wxMpUser = wxMpService.userInfo(openid,"zh_CN");
            WxUser wxUser = getWxUserByWxMpUser(wxMpUser);
            wxUserService.subscribe(wxUser);
        }
        if("1".equals(code)){
            //取消关注
            wxUserService.unsubscribe(openid);
        }
        return null;
    }

    private WxUser getWxUserByWxMpUser(WxMpUser wx){
        WxUser w = new WxUser();
        w.setOpenid(wx.getOpenId());
        w.setNickName(wx.getNickname());
        w.setSex(wx.getSexId());
        w.setCity(wx.getCity());
        w.setProvince(wx.getProvince());
        w.setCountry(wx.getCountry());
        w.setLang(wx.getLanguage());
        w.setHeadImgUrl(wx.getHeadImgUrl());
        w.setAttentionTime(new Date(wx.getSubscribeTime()));
        //w.setCancelAttentionTime(null);
        w.setAttentionStatus(wx.getSubscribe() == true ? "0" : "1");
        w.setUnionid(wx.getUnionId());
        //w.setPrivilege("");
        return w;
    }
}
