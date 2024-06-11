package com.ERP.controllers;

import com.ERP.dtos.TaskDto;
import com.ERP.entities.Task;
import com.ERP.entities.TaskHistory;
import com.ERP.services.TaskHistoryService;
import com.ERP.utils.MyResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taskHistory")
public class TaskHistoryController {

    @Autowired
    private TaskHistoryService taskHistoryService;

    @GetMapping("/find/{projectId}")
    public List<TaskHistory> findTaskByProjectId(@PathVariable Long projectId) {
        return taskHistoryService.getAllTaskHistoryByProjectId(projectId);
    }

    @GetMapping("/findAll")
    public List<TaskHistory> findAllTaskHistory() {
        return taskHistoryService.getAllTaskHistory();
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long projectId) {
        List<TaskHistory> taskHistoryList = taskHistoryService.getAllTaskHistoryByProjectId(projectId);
        taskHistoryService.deleteTasksByProjectId(projectId);
        if (!taskHistoryList.isEmpty()) {
            return MyResponseGenerator.generateResponse(HttpStatus.OK, true, "Task deleted successfully", taskHistoryList);
        } else {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Failed to delete task", null);
        }
    }
}
