package com.ERP.controllersTest;

import com.ERP.controllers.TaskHistoryController;
import com.ERP.entities.TaskHistory;
import com.ERP.services.TaskHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskHistoryControllerTest {
    @InjectMocks
    private TaskHistoryController taskHistoryController;
    @Mock
    private TaskHistoryService taskHistoryService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindTaskByProjectId() {
        // Arrange
        long projectId = 1L;
        List<TaskHistory> expectedTaskHistoryList = new ArrayList<>();
        when(taskHistoryService.getAllTaskHistoryByProjectId(projectId)).thenReturn(expectedTaskHistoryList);

        // Act
        List<TaskHistory> result = taskHistoryController.findTaskByProjectId(projectId);

        // Assert
        assertEquals(expectedTaskHistoryList, result);
        verify(taskHistoryService, times(1)).getAllTaskHistoryByProjectId(projectId);
    }

    @Test
    public void testFindAllTaskHistory() {
        // Arrange
        List<TaskHistory> expectedTaskHistoryList = new ArrayList<>();
        when(taskHistoryService.getAllTaskHistory()).thenReturn(expectedTaskHistoryList);

        // Act
        List<TaskHistory> result = taskHistoryController.findAllTaskHistory();

        // Assert
        assertEquals(expectedTaskHistoryList, result);
        verify(taskHistoryService, times(1)).getAllTaskHistory();
    }

    @Test
    public void testDeleteTask_Success() {
        // Arrange
        long projectId = 1L;
        List<TaskHistory> taskHistoryList = new ArrayList<>();
        taskHistoryList.add(new TaskHistory()); // Adding a dummy task history to simulate failure scenario
        when(taskHistoryService.getAllTaskHistoryByProjectId(projectId)).thenReturn(taskHistoryList);

        // Act
        ResponseEntity<Object> response = taskHistoryController.deleteTask(projectId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(taskHistoryService, times(1)).deleteTasksByProjectId(projectId);

    }

    @Test
    public void testDeleteTask_Failure() {
        // Arrange
        long projectId = 1L;
        List<TaskHistory> taskHistoryList = new ArrayList<>();
        when(taskHistoryService.getAllTaskHistoryByProjectId(projectId)).thenReturn(taskHistoryList);

        // Act
        ResponseEntity<Object> response = taskHistoryController.deleteTask(projectId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(taskHistoryService, times(1)).deleteTasksByProjectId(projectId);
    }

}
