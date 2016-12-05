package com.ebeijia.service;

import com.ebeijia.entity.UserBilAccount;

import java.util.List;
import java.util.Map;

/**
 * Created by LiYan on 2016/9/23.
 */
public interface UserBillAccountService {
    int addUserBillAccount(UserBilAccount userBilAccount);

    UserBilAccount findByUserNoAndOpenid(Map<String,Object> params);

    UserBilAccount findByUserId(Map<String,Object> params);

    Map<String, Object> addUserAccount(String openId, String userNo, String type,
                        String tipsArrears, String isRecord);

    List<UserBilAccount> selectByTips(String tips);

    List<UserBilAccount> selectByUserNo(String userNo);
}
