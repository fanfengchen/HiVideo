package com.ebeijia.service.impl;

import com.ebeijia.entity.WxUser;
import com.ebeijia.mapper.WxUserMapper;
import com.ebeijia.service.WxUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zc on 2016/9/23.
 */
@Service
public class WxUserServiceImpl implements WxUserService {

    private static Logger log = LoggerFactory.getLogger(WxUserServiceImpl.class);

    @Autowired
    private WxUserMapper wxUserMapper;

    /**
     * 关注
     */
    public void subscribe(WxUser wx){
        //查询已经有原先的关注记录
        WxUser wxUser = wxUserMapper.queryWxUserByOpenId(wx.getOpenid());
        if(wxUser == null){
            //没有关注过，直接保存
            wxUserMapper.insertSelective(wx);
        }else{
            //关注过，修改关注时间和关注状态
            WxUser wxTemp = new WxUser();
            wxTemp.setId(wxUser.getId());
            wxTemp.setAttentionTime(wx.getAttentionTime());
            wxTemp.setAttentionStatus(wx.getAttentionStatus());
            wxUserMapper.updateByPrimaryKeySelective(wxTemp);
        }
    }

    /**
     * 取消关注
     */
    public void unsubscribe(String openid){

        //查询已经有原先的关注记录
        WxUser wxUser = wxUserMapper.queryWxUserByOpenId(openid);

        if(wxUser != null){
            WxUser wxTemp = new WxUser();
            wxTemp.setId(wxUser.getId());
            wxTemp.setAttentionStatus("1");
            wxTemp.setCancelAttentionTime(new Date());
            wxUserMapper.updateByPrimaryKeySelective(wxTemp);
        }
    }

    /**
     * 查询分页数据
     */
    public Map<String, Object> queryWxUserByPage(String pageOffset, String pageSize, String name){
        Map<String, Object> resMap = new HashMap<String, Object>();
        if(StringUtils.isEmpty(pageOffset) || StringUtils.isEmpty(pageSize)){
            resMap.put("code", "-1");
            return resMap;
        }
        Integer pageOffsetTemp = 0;
        Integer pageSizeTemp = 10;

        boolean temp = false;

        try{
            pageOffsetTemp = Integer.parseInt(pageOffset);
            pageSizeTemp = Integer.parseInt(pageSize);
        }catch(Exception e){
            temp = true;
            log.error("参数转换失败，参数类型输入错误" , e);
        }

        if(temp){
            resMap.put("code", "-2");
            return resMap;
        }

        if(pageOffsetTemp < 1 || pageSizeTemp < 1){
            resMap.put("code", "-3");
            return resMap;
        }

        pageOffsetTemp = pageOffsetTemp - 1;

        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("nickName", name);
        int count = wxUserMapper.selectCountByParams(condition);
        List<WxUser> list = wxUserMapper.selectListByParams(condition, "id", pageOffsetTemp, pageSizeTemp);
        resMap.put("count", count);
        resMap.put("data", list);
        resMap.put("code", "0");
        return resMap;
    }

}
