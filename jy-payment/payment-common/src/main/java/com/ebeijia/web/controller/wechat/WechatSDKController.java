package com.ebeijia.web.controller.wechat;

import com.ebeijia.common.CommonCodeDefine;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class WechatSDKController {

    private Logger              logger          = LoggerFactory.getLogger(this.getClass());
    private static final String RE_SUCCESS_CODE = "0000";

    @Autowired
    private WxMpService         wxMpService;

    /**
     * 获取 jssdk 签名信息
     * 
     * @param request
     * @param url
     * @return
     * @throws WxErrorException
     */
    @RequestMapping(value = "getsdk", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getJsSdkInfo(HttpServletRequest request, @RequestParam("url") String url) throws WxErrorException {
        WxJsapiSignature signnature = wxMpService.createJsapiSignature(url);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resCode", RE_SUCCESS_CODE);
        resultMap.put("signnature", signnature);
        return resultMap;
    }

    /**
     * 根据授权返回的code 获取 openid
     * 
     * @param code
     * @return
     */
    @RequestMapping(value = "getOpenid/{code}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getOpenIDByCode(@PathVariable("code") String code) {
        logger.info("=================code=" + code);
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String openid = "";
        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            openid = wxMpOAuth2AccessToken.getOpenId();
            logger.info("=================openId=" + openid);
            resultMap.put("resCode", RE_SUCCESS_CODE);
            resultMap.put("openId", openid);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultMap.put("resCode", CommonCodeDefine.E1002.getCode());
        }
        return resultMap;
    }
}
