package com.ebeijia.util.common;

import net.sf.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with com.ebeijia.util.common
 * User : zc
 * Date : 2016/10/27
 */
public class PageParamUtils {

    public static Map<String, Integer> getPageParam(String aoData) throws Exception{
        //获取分页数据
        int page = 0;
        int size = 10;
        JSONArray json = JSONArray.fromObject(aoData);
        for(int i = 0; i < json.size(); i++){
            String name = (String)json.getJSONObject(i).get("name");
            if("iDisplayLength".equals(name)){
                size = (Integer)json.getJSONObject(i).get("value");
            }
            if("iDisplayStart".equals(name)){
                page = (Integer)json.getJSONObject(i).get("value");
            }
        }
        if(page < 1){
            //如果传入的是负数和0，，则赋值为0
            page = 0;
        }else{
            //如果是正数，则-1
            page = page - 1;
        }
        Map<String, Integer> resMap = new HashMap<String, Integer>();
        resMap.put("page", page * size);
        resMap.put("size", size);
        return resMap;
    }
}
