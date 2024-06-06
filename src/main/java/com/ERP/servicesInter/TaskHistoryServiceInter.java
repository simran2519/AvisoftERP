package com.ERP.servicesInter;

import com.ERP.entities.TaskHistory;

import java.util.List;

public interface TaskHistoryServiceInter {

    public void createTaskHistory(TaskHistory createdTaskHistory);
//    public TaskHistory deleteTasksByProjectId(long projectId);
    public List<TaskHistory> getAllTaskHistory();
//    public List<TaskHistory> getAllTaskHistoryByProjectId(long projectId);
}
