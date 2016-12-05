package com.ebeijia.service;

import com.ebeijia.entity.UserBilAccount;

import java.util.Map;

/**
 * Created with com.ebeijia.service
 * User : zc
 * Date : 2016/9/28
 */
public interface UserBillAccountQueryService {

    public Map<String, Object> deleteUserBillAccount(String userNo, String openId);

    public Map<String, Object> queryUserBillAccount(String openid);

    public Map<String, Object> updateUserBillAccount(String opneId, String userNo, String id, String tipsArrears);

    public Map<String, Object> updateRemarkById(String remark, String id);

}
