package com.ebeijia.controller.web;

import com.ebeijia.controller.handler.SubscribeHandler;
import com.ebeijia.util.LoggerUtil;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zc on 2016/9/22.
 */
@Controller
@RequestMapping("weixin")
public class WeixinMsgController {

    private static Logger log = LoggerFactory.getLogger(WeixinMsgController.class);

    @Resource
    private WxMpService wxMpService;

    @Resource
    private SubscribeHandler subscribeHandler;

    @RequestMapping(value = "msg", method = RequestMethod.GET)
    public void check(String signature, String timestamp, String nonce, String echostr, PrintWriter out) {
        try {
            if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
                out.print("参数异常");
            } else {
                out.print(echostr);
            }
        } catch (Exception e) {
            log.error("微信验证异常" , e);
        } finally {
            out.close();
        }
    }

    @RequestMapping(value = "msg", method = RequestMethod.POST)
    public void receiveMsg(HttpServletRequest request, PrintWriter out) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            WxMpXmlMessage wxMpXmlMessage = WxMpXmlMessage.fromXml(inputStream);
            String event = wxMpXmlMessage.getEvent();
            Map<String, Object> map = new HashMap<String, Object>();
            if (WxConsts.EVT_SUBSCRIBE.equals(event)) {
                //调用关注方法
                map.put("code", "0");
                subscribeHandler.handle(wxMpXmlMessage, map, wxMpService, null);
            }
            if (WxConsts.EVT_UNSUBSCRIBE.equals(event)) {
                //调用取消关注方法
                map.put("code", "1");
                subscribeHandler.handle(wxMpXmlMessage, map, wxMpService, null);
            }
        } catch (Exception e) {
            log.error("微信处理事件异常" , e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

}
