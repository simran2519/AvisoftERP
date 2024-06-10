package com.ERP.services;

import com.ERP.entities.TaskHistory;
import com.ERP.exceptions.IdNotFoundException;
import com.ERP.repositories.TaskHistoryRepository;
import com.ERP.servicesInter.TaskHistoryServiceInter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskHistoryService implements TaskHistoryServiceInter {
    @Autowired
    private TaskHistoryRepository taskHistoryRepository;

    public void createTaskHistory(TaskHistory taskHistory){
        taskHistoryRepository.save(taskHistory);
    }

    @Transactional
    public void deleteTasksByProjectId(long projectId){
        try {
            taskHistoryRepository.deleteByAssignTo(projectId);
        } catch (Exception e) {
            throw new IdNotFoundException("Error deleting task History: " + e.getMessage());
        }
    }


    public List<TaskHistory> getAllTaskHistory() {
        try {
            List<TaskHistory> taskHistoryList = taskHistoryRepository.findAll();
            return taskHistoryList;
        } catch (Exception e) {
            throw new IdNotFoundException("Error finding all task History: " + e.getMessage());
        }
    }

    public List<TaskHistory> getAllTaskHistoryByProjectId(long projectId) {
        try {
            List<TaskHistory> taskHistoryList = taskHistoryRepository.findAllByAssignTo(projectId);
            return taskHistoryList;
        } catch (Exception e) {
            throw new IdNotFoundException("Error finding all task History: " + e.getMessage());
        }
    }
}
