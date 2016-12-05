package com.ebeijia.service.impl;

import com.ebeijia.common.CommonConstant;
import com.ebeijia.entity.UserBilAccount;
import com.ebeijia.mapper.UserBilAccountMapper;
import com.ebeijia.service.UserBillAccountQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created with com.ebeijia.service.impl
 * User : zc
 * Date : 2016/9/28
 * 查询账户数据
 */
@Service
public class UserBillAccountQueryServiceImpl implements UserBillAccountQueryService {

    private static Logger log = LoggerFactory.getLogger(UserBillAccountQueryServiceImpl.class);

    @Autowired
    private UserBilAccountMapper userBilAccountMapper;

    /**
     * 根据accountNo户号已经保存过
     * 如果已经添加过就不再添加，只是调接口查询数据
     */
    private UserBilAccount queryUserBillAccountByAccountNo(String accountNo){
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userNo", accountNo);
        return userBilAccountMapper.selectByParams(condition);
    }

    /**
     * 删除用户账号数据，修改用户账号数据
     */
    public Map<String, Object> deleteUserBillAccount(String userNo, String openId){
        Map<String, Object> resMap = new HashMap<String, Object>();

        if(StringUtils.isEmpty(userNo) || StringUtils.isEmpty(openId)){
            //输入的参数缺失
            resMap.put("code", CommonConstant.userBillAccConst_par_err);
            return resMap;
        }

        int count = userBilAccountMapper.updateAccountStatus("3", openId, userNo);
        if(count == 0){
            //输入的信息不存在
            resMap.put("code", CommonConstant.userBillAccConst_res_null);
            return resMap;
        }
        resMap.put("code", CommonConstant.userBillAccConst_succ);
        return resMap;
    }

    /**
     * 查询用户账户数据，分页查询数据
     */
    public Map<String, Object> queryUserBillAccount(String userId){

        Map<String, Object> resMap = new HashMap<String, Object>();

        if(StringUtils.isEmpty(userId)){
            resMap.put("code", CommonConstant.userBillAccConst_par_err);
            return resMap;
        }

        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userId", userId);
        condition.put("accountStatus", "1");

        List<UserBilAccount> list = userBilAccountMapper.selectListByParams(condition, "id", null, null);

        resMap.put("code", CommonConstant.userBillAccConst_succ);
        resMap.put("data", switchData(list));

        return resMap;
    }

    private List<Map<String, Object>> switchData(List<UserBilAccount> data){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(UserBilAccount bill : data){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", bill.getId());
            map.put("userNo", bill.getUserNo());
            map.put("userName", StringUtils.isEmpty(bill.getUserName()) ? "" : bill.getUserName());
            map.put("accountType", bill.getAccountType());
            map.put("tipsArrears", bill.getTipsArrears());
            map.put("remark", bill.getRemark());
            list.add(map);
        }
        return list;
    }

    /**
     * 修改数据
     */
    public Map<String, Object> updateUserBillAccount(String openId, String userNo, String id,
                    String tipsArrears){

        Map<String, Object> resMap = new HashMap<String, Object>();

        if(StringUtils.isEmpty(userNo)
                || StringUtils.isEmpty(openId)
                || StringUtils.isEmpty(tipsArrears)){
            resMap.put("code", CommonConstant.userBillAccConst_par_err);
            return resMap;
        }

        //用于防止输入别的参数，只能输入0和1
        tipsArrears = "1".equals(tipsArrears) ? tipsArrears : "0";
        //只修改没有被删除的账号，并且这个账户只存在一个
        String accountStatus = "1";

        int count = userBilAccountMapper.updateAccountTipsArrears
                        (tipsArrears, openId, userNo, Long.parseLong(id), accountStatus);
        resMap.put("code", count == 0 ? CommonConstant.userBillAccConst_res_null
                : CommonConstant.userBillAccConst_succ);
        return resMap;
    }

    /**
     * 根据id修改备注
     */
    public Map<String, Object> updateRemarkById(String remark, String id){
        Map<String, Object> resMap = new HashMap<String, Object>();
        if(StringUtils.isEmpty(id)){
            resMap.put("code", CommonConstant.userBillAccount_par_err);
            return resMap;
        }
        if(remark == null){
            remark = "";
        }
        int count = userBilAccountMapper.updateRemarkById(remark, Long.parseLong(id));
        resMap.put("code", CommonConstant.userBillAccount_succ);
        return resMap;
    }

}
