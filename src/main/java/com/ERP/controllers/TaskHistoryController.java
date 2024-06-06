package com.ERP.controllers;

import com.ERP.dtos.TaskDto;
import com.ERP.entities.TaskHistory;
import com.ERP.services.TaskHistoryService;
import com.ERP.services.TaskService;
import com.ERP.utils.MyResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taskHistory")
public class TaskHistoryController {

    private final TaskHistoryService taskHistoryService;

    @Autowired
    public TaskHistoryController(TaskHistoryService taskHistoryService) {
        this.taskHistoryService = taskHistoryService;
    }

//    @GetMapping("/find/{projectId}")
//    public List<TaskHistory> findTaskByProjectId(@PathVariable Long projectId) {
//        List<TaskHistory> taskHistoryList = taskHistoryService.getAllTaskHistoryByProjectId(projectId);
//        if (taskHistoryList.size() != 0) {
//            return (List<TaskHistory>) MyResponseGenerator.generateResponse(HttpStatus.OK, true, "Tasks found", taskHistoryList);
//        } else {
//            return (List<TaskHistory>) MyResponseGenerator.generateResponse(HttpStatus.NOT_FOUND, false, "Task not found", taskHistoryList);
//        }
//    }

    @GetMapping("/findAll")
    public List<TaskHistory> findAllTaskHistory() {
        return taskHistoryService.getAllTaskHistory();
    }

//    @DeleteMapping("/delete/{projectId}")
//    public ResponseEntity<Object> deleteTask(@PathVariable Long projectId) {
//        List<TaskHistory> taskHistoryList = (List<TaskHistory>) taskHistoryService.deleteTasksByProjectId(projectId);
//        if ( taskHistoryList.size() != 0) {
//            return MyResponseGenerator.generateResponse(HttpStatus.OK, true, "Tasks deleted successfully", taskHistoryList);
//        } else {
//            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Failed to delete task", taskHistoryList);
//        }
//    }
}
