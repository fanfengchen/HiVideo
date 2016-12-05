package com.ebeijia.service;

import com.ebeijia.entity.WxUser;

import java.util.Map;

/**
 * Created by zc on 2016/9/23.
 */
public interface WxUserService {

    public void subscribe(WxUser wx);

    public void unsubscribe(String openid);

    public Map<String, Object> queryWxUserByPage(String pageOffset, String pageSize, String name);
}
