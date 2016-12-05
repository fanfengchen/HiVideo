package com.ebeijia.controller;

import com.ebeijia.common.CommonConstant;
import com.ebeijia.service.ArrearageBillService;
import com.ebeijia.web.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with com.ebeijia.controller
 * User : zc
 * Date : 2016/9/28
 */
@Controller
@RequestMapping("/wechat")
public class ArrearageBillController {

    @Autowired
    private ArrearageBillService arrearageBillService;

    /**
     * 查询自来水表数据
     * accountNo 表示户号
     * openid
     * tipsArrears 表示是否提醒
     * isRecord 表示是否记录
     */
    @RequestMapping(value = "/queryWaterBill",method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage queryWaterBillInfo(String userNo, String openId,
                                              HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map resMap = this.arrearageBillService
                .queryWaterBill(userNo, openId);
        String resCode = (String)resMap.get("code");
        if(CommonConstant.arreaImplResConst_succ.equals(resCode)){
            Map<String,Object> map = new HashMap<>();
            map.put("bill",resMap.get("data"));
            map.put("userInfo",resMap.get("userInfo"));
            return ResponseMessage.success(map);
        }
        if(CommonConstant.arreaImplResConst_par_err.equals(resCode)){
            return ResponseMessage.error("输入的参数缺失");
        }
        if(CommonConstant.arreaImplResConst_res_null.equals(resCode)){
            return ResponseMessage.error("输入的户号不存在");
        }
        if(CommonConstant.arreaImplResConst_fail.equals(resCode)){
            return ResponseMessage.error("查询数据失败");
        }
        return ResponseMessage.error("服务器内部错误");
    }

    /**
     * 查询燃气数据
     * accountNo 表示户号
     * openid
     * tipsArrears 表示是否提醒
     * isRecord 表示是否记录
     */
    @RequestMapping(value = "/queryGasBill",method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage queryGasBillInfo(String userNo, String openId,
                                           HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map resMap = this.arrearageBillService
                .queryGasBill(userNo, openId);
        String resCode = (String)resMap.get("code");
        if(CommonConstant.arreaImplResConst_succ.equals(resCode)){
            Map<String,Object> map = new HashMap<>();
            map.put("bill",resMap.get("data"));
            map.put("userInfo",resMap.get("userInfo"));
            return ResponseMessage.success(map);
        }
        if(CommonConstant.arreaImplResConst_par_err.equals(resCode)){
            return ResponseMessage.error("输入的参数缺失");
        }
        if(CommonConstant.arreaImplResConst_res_null.equals(resCode)){
            return ResponseMessage.error("输入的户号不存在");
        }
        if(CommonConstant.arreaImplResConst_fail.equals(resCode)){
            return ResponseMessage.error("查询数据失败");
        }
        return ResponseMessage.error("服务器内部错误");
    }

    /**
     * 查询远传水表数据
     * * accountNo 表示户号
     * openid
     * tipsArrears 表示是否提醒
     * isRecord 表示是否记录
     */
    @RequestMapping(value = "/queryRechargeBill",method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage queryRechargeBillInfo(String userNo, String openId,
                                                 HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map resMap = this.arrearageBillService
                .querRechargeBill(userNo, openId);
        String resCode = (String)resMap.get("code");
        if(CommonConstant.arreaImplResConst_succ.equals(resCode)){
            Map<String,Object> map = new HashMap<>();
            map.put("bill",resMap.get("data"));
            map.put("userInfo",resMap.get("userInfo"));
            return ResponseMessage.success(map);
        }
        if(CommonConstant.arreaImplResConst_par_err.equals(resCode)){
            return ResponseMessage.error("输入的参数缺失");
        }
        if(CommonConstant.arreaImplResConst_res_null.equals(resCode)){
            return ResponseMessage.error("输入的户号不存在");
        }
        if(CommonConstant.arreaImplResConst_fail.equals(resCode)){
            return ResponseMessage.error("查询数据失败");
        }
        if(CommonConstant.arreaImplResConst_balance.equals(resCode)){
            return ResponseMessage.error("-1","用户不欠费不需要缴费");
        }
        return ResponseMessage.error("服务器内部错误");
    }

}
