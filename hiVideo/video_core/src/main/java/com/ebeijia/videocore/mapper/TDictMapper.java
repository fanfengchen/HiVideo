package com.ebeijia.videocore.mapper;

import com.ebeijia.videocore.pojo.TDict;

public interface TDictMapper {
    int insert(TDict record);

    int insertSelective(TDict record);
}