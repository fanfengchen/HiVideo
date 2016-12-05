package com.ebeijia.service.impl;

import com.ebeijia.entity.TaskInfo;
import com.ebeijia.mapper.TaskInfoMapper;
import com.ebeijia.service.TaskInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by LiYan on 2016/9/27.
 */
@Service
public class TaskInfoServiceImpl implements TaskInfoService {
    @Autowired
    private TaskInfoMapper taskInfoMapper;

    @Override
    public List<TaskInfo> selectByResult(String taskResult) {
        return taskInfoMapper.selectByResult(taskResult);
    }

    @Override
    @Transactional
    public int modifyTaskInfo(TaskInfo taskInfo) {
        return taskInfoMapper.updateByPrimaryKeySelective(taskInfo);
    }
}
