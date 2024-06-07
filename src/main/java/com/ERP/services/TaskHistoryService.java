package com.ERP.services;

import com.ERP.dtos.SalaryStructureDto;
import com.ERP.dtos.TaskHistoryDto;
import com.ERP.entities.SalaryStructure;
import com.ERP.entities.Task;
import com.ERP.entities.TaskHistory;
import com.ERP.exceptions.IdNotFoundException;
import com.ERP.repositories.TaskHistoryRepository;
import com.ERP.repositories.TaskRepository;
import com.ERP.servicesInter.TaskHistoryServiceInter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TaskHistoryService implements TaskHistoryServiceInter {

    @Autowired
    private TaskHistoryRepository taskHistoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public void createTaskHistory(TaskHistoryDto taskHistoryDto){
        TaskHistory newTaskHistory = objectMapper.convertValue(taskHistoryDto, TaskHistory.class);
        System.out.println(taskHistoryDto.getStartDate() + " and " + newTaskHistory.getStartDate());
        taskHistoryRepository.save(newTaskHistory);
        System.out.println("Task Added to History !!");
    }

//    public TaskHistory deleteTasksByProjectId(long projectId){
//        try {
//            TaskHistory taskHistory = taskHistoryRepository.findById(projectId)
//                    .orElseThrow(() -> new IdNotFoundException("Task History not found with id: " + projectId));
//            taskHistoryRepository.deleteByProjectId(projectId);
//            return taskHistory;
//        } catch (Exception e) {
//            throw new IdNotFoundException("Error deleting task History: " + e.getMessage());
//        }
//    }


    public List<TaskHistory> getAllTaskHistory() {
        try {
            List<TaskHistory> taskHistoryList = taskHistoryRepository.findAll();
            return taskHistoryList;
        } catch (Exception e) {
            throw new IdNotFoundException("Error finding all task History: " + e.getMessage());
        }
    }

//    public List<TaskHistory> getAllTaskHistoryByProjectId(long projectId) {
//        try {
//            List<TaskHistory> taskHistoryList = taskHistoryRepository.findAllByProjectId(projectId);
//            return taskHistoryList;
//        } catch (Exception e) {
//            throw new IdNotFoundException("Error finding all task History: " + e.getMessage());
//        }
//    }
}
