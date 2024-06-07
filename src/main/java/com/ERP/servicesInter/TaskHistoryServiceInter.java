package com.ERP.servicesInter;

import com.ERP.dtos.TaskHistoryDto;
import com.ERP.entities.TaskHistory;

import java.util.List;

public interface TaskHistoryServiceInter {

    public void createTaskHistory(TaskHistoryDto taskHistory);
    //    public TaskHistory deleteTasksByProjectId(long projectId);
    public List<TaskHistory> getAllTaskHistory();
//    public List<TaskHistory> getAllTaskHistoryByProjectId(long projectId);
}
