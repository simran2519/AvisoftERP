package com.ERP.servicesTest;

import com.ERP.entities.TaskHistory;
import com.ERP.exceptions.IdNotFoundException;
import com.ERP.repositories.TaskHistoryRepository;
import com.ERP.services.TaskHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class TaskHistoryServiceTest {

    @Mock
    private TaskHistoryRepository taskHistoryRepository;

    @InjectMocks
    private TaskHistoryService taskHistoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTaskHistory() {
        // Arrange
        TaskHistory taskHistory = new TaskHistory();

        // Act
        taskHistoryService.createTaskHistory(taskHistory);

        // Assert
        verify(taskHistoryRepository, times(1)).save(taskHistory);
    }

    @Test
    public void testDeleteTasksByProjectId() {

        // Arrange
        doNothing().when(taskHistoryRepository).deleteByAssignTo(anyLong());

        // Act
        assertDoesNotThrow(() -> taskHistoryService.deleteTasksByProjectId(1L));

        // Assert
        verify(taskHistoryRepository, times(1)).deleteByAssignTo(1L);
    }

    @Test
    public void testDeleteTasksByProjectIdException() {

        // Arrange
        doThrow(new RuntimeException("Database connection error")).when(taskHistoryRepository).deleteByAssignTo(anyLong());

        // Act and Assert
        IdNotFoundException exception = assertThrows(IdNotFoundException.class, () -> {
            taskHistoryService.deleteTasksByProjectId(1L);
        });

        verify(taskHistoryRepository, times(1)).deleteByAssignTo(1L);
        assertTrue(exception.getMessage().contains("Error deleting task History"));

    }

    @Test
    public void testGetAllTaskHistory() {
        // Arrange
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTaskHistoryId(2L);
        List<TaskHistory> taskHistoryList = new ArrayList<>();
        taskHistoryList.add(taskHistory);

        when(taskHistoryRepository.findAll()).thenReturn(taskHistoryList);

        // Act
        List<TaskHistory> result = taskHistoryService.getAllTaskHistory();

        // Assert
        assertEquals(taskHistoryList, result);
        assertEquals(result.get(0).getTaskHistoryId(), taskHistory.getTaskHistoryId());
        verify(taskHistoryRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllTaskHistoryException() {
        // Arrange
        when(taskHistoryRepository.findAll()).thenThrow(new RuntimeException("Database connection error"));

        // Act and Assert
        IdNotFoundException exception = assertThrows(IdNotFoundException.class, () -> {
            taskHistoryService.getAllTaskHistory();
        });

        assertTrue(exception.getMessage().contains("Error finding all task History"));
    }

    @Test
    public void testGetAllTaskHistoryByProjectId() {
        // Arrange
        long projectId = 1L;
        List<TaskHistory> taskHistoryList = new ArrayList<>();
        when(taskHistoryRepository.findAllByAssignTo(projectId)).thenReturn(taskHistoryList);

        // Act
        List<TaskHistory> result = taskHistoryService.getAllTaskHistoryByProjectId(projectId);

        // Assert
        assertEquals(taskHistoryList, result);
        verify(taskHistoryRepository, times(1)).findAllByAssignTo(projectId);
    }

    @Test
    public void testGetAllTaskHistoryByProjectIdException() {
        // Arrange
        long projectId = 1L;
        when(taskHistoryRepository.findAllByAssignTo(projectId)).thenThrow(new RuntimeException("Database connection error")); // Mock repository to throw exception when findAllByAssignTo() is called with projectId

        // Act and Assert
        IdNotFoundException exception = assertThrows(IdNotFoundException.class, () -> {
            taskHistoryService.getAllTaskHistoryByProjectId(projectId); // Perform the action being tested (should throw IdNotFoundException)
        });

        assertTrue(exception.getMessage().contains("Error finding all task History")); // Verify that exception message contains expected error message
    }
}
