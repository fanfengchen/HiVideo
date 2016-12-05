package com.ebeijia.mapper;

import com.ebeijia.entity.TaskInfo;

import java.util.List;

public interface TaskInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TaskInfo record);

    int insertSelective(TaskInfo record);

    TaskInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskInfo record);

    int updateByPrimaryKey(TaskInfo record);

    List<TaskInfo> selectByResult(String taskResult);
}