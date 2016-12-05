package com.ebeijia.controller;

import com.ebeijia.common.CommonConstant;
import com.ebeijia.entity.UserBilAccount;
import com.ebeijia.service.UserBillAccountQueryService;
import com.ebeijia.web.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created with com.ebeijia.controller
 * User : zc
 * Date : 2016/9/28
 */
@Controller
@RequestMapping("/wechat")
public class UserBillAccountController {

    @Autowired
    private UserBillAccountQueryService userBillAccountQueryService;

    /**
     * 取消绑定，根据id修改用户账单户号的状态，改为删除
     */
    @RequestMapping(value = "/cancelBingding" , method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage deleteUserBillAccount(String userNo, String openId,
                                                 HttpServletResponse rsp){
        rsp.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> resMap = userBillAccountQueryService.deleteUserBillAccount(userNo, openId);
        String resCode = (String)resMap.get("code");

        if(CommonConstant.userBillAccConst_succ.equals(resCode)){
            return ResponseMessage.success();
        }
        if(CommonConstant.userBillAccConst_par_err.equals(resCode)){
            return ResponseMessage.error("输入的参数缺失");
        }
        if(CommonConstant.userBillAccConst_res_null.equals(resCode)){
            return ResponseMessage.error("需要删除的数据不存在");
        }
        return ResponseMessage.error("服务器内部错误");
    }

    /**
     * 查询用户账单户号
     * userId为openid
     */
    @RequestMapping(value = "/queryBingdingList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage queryUserBillAccount(String openId,
                                                HttpServletResponse rsp){
        rsp.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> resMap = userBillAccountQueryService
                .queryUserBillAccount(openId);
        String resCode = (String)resMap.get("code");
        if(CommonConstant.userBillAccConst_succ.equals(resCode)){
            return ResponseMessage.success(resMap.get("data"));
        }
        if(CommonConstant.userBillAccConst_par_err.equals(resCode)){
            return ResponseMessage.error("输入的参数缺失");
        }
        return ResponseMessage.error("服务器内部错误");
    }

    /**
     * 修改用户账户管理，用于修改是否提醒
     */
    @RequestMapping(value = "/updateBingding", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateUserBillAccount(String openId, String userNo,String id,
                                                 String tipsArrears, HttpServletResponse rsp){
        rsp.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> resMap = userBillAccountQueryService
                .updateUserBillAccount(openId, userNo, id, tipsArrears);

        String resCode = (String)resMap.get("code");
        if(CommonConstant.userBillAccConst_succ.equals(resCode)){
            return ResponseMessage.success();
        }
        if(CommonConstant.userBillAccConst_par_err.equals(resCode)){
            return ResponseMessage.error("输入的参数缺失");
        }
        if(CommonConstant.userBillAccConst_data_exist.equals(resCode)){
            return ResponseMessage.error("输入的户号已经存在");
        }
        if(CommonConstant.userBillAccConst_res_null.equals(resCode)){
            return ResponseMessage.error("需要修改的数据不存在");
        }
        return ResponseMessage.error("服务器内部错误");
    }

    @RequestMapping(value = "/updateRemark", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateRemarkById(String remark, String id, HttpServletResponse rsp){
        rsp.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> resMap = userBillAccountQueryService
                .updateRemarkById(remark, id);

        String resCode = (String)resMap.get("code");
        if(CommonConstant.userBillAccount_succ.equals(resCode)){
            return ResponseMessage.success();
        }
        if(CommonConstant.userBillAccount_par_err.equals(resCode)){
            return ResponseMessage.error("输入的参数缺失");
        }
        return ResponseMessage.error("服务器内部错误");
    }

}

