package com.ebeijia.controller;

import com.ebeijia.service.WxUserService;
import com.ebeijia.web.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by zc on 2016/9/23.
 */
@Controller
@RequestMapping("/wxUser")
public class WxUserController {

    @Autowired
    private WxUserService wxUserService;

    //@RequestMapping(value = "/queryWxUserByPage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage queryWxUser(String pageOffset, String pageSize, String name){
        Map<String, Object> map = wxUserService.queryWxUserByPage(pageOffset, pageSize, name);
        String code = (String)map.get("code");
        if("-3".equals(code)){
            //return new ResponseMessage("-4","参数输入错误",null);
            return ResponseMessage.error("参数输入错误");
        }
        if("-2".equals(code)){
            return ResponseMessage.error("参数类型错误");
        }
        if("-1".equals(code)){
            return ResponseMessage.error("参数缺失");
        }
        if("0".equals(code)){
            map.remove("code");
            return ResponseMessage.success(map);
        }
        return ResponseMessage.error("服务器内部错误");
    }
}
