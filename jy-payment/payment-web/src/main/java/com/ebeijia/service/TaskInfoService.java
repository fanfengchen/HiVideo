package com.ebeijia.service;

import com.ebeijia.entity.TaskInfo;

import java.util.List;

/**
 * Created by LiYan on 2016/9/27.
 */
public interface TaskInfoService {
    List<TaskInfo> selectByResult(String taskResult);

    int modifyTaskInfo(TaskInfo taskInfo);
}
