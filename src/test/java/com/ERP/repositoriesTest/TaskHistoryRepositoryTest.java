package com.ERP.repositoriesTest;

import com.ERP.entities.*;
import com.ERP.repositories.TaskHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Use a separate test database
public class TaskHistoryRepositoryTest {

    private TaskHistoryRepository taskHistoryRepository;

    TaskHistory taskHistory1 = new TaskHistory();
    TaskHistory taskHistory2 = new TaskHistory();
    TaskHistory taskHistory3 = new TaskHistory();

    Employee employee = new Employee();
    Project project = new Project();

    @BeforeEach
    void setUp() {
        // Create a mock instance of the repository interface
        taskHistoryRepository = mock(TaskHistoryRepository.class);

        employee.setId(1L);
        project.setProjectId(1L);

        taskHistory1.setTaskHistoryId(1L);
        taskHistory1.setTaskId(1L);
        taskHistory1.setName("E-commerce project T-46");
        taskHistory1.setDescription("Complete this ASAP");
        taskHistory1.setStartDate(Date.valueOf("2024-05-13"));
        taskHistory1.setEndDate(Date.valueOf("2024-05-15"));
        taskHistory1.setEmployee(employee.getId());
        taskHistory1.setAssignTo(project.getProjectId());

        taskHistory2.setTaskHistoryId(2L);
        taskHistory2.setTaskId(2L);
        taskHistory2.setName("E-commerce project T-46");
        taskHistory2.setDescription("Complete this ASAP");
        taskHistory2.setStartDate(Date.valueOf("2024-05-13"));
        taskHistory2.setEndDate(Date.valueOf("2024-05-15"));
        taskHistory2.setEmployee(employee.getId());
        taskHistory2.setAssignTo(project.getProjectId());

        taskHistory3.setTaskHistoryId(3L);
        taskHistory3.setTaskId(3L);
        taskHistory3.setName("E-commerce project T-46");
        taskHistory3.setDescription("Complete this ASAP");
        taskHistory3.setStartDate(Date.valueOf("2024-05-13"));
        taskHistory3.setEndDate(Date.valueOf("2024-05-15"));
        employee.setId(2L);
        project.setProjectId(2L);
        taskHistory3.setEmployee(employee.getId());
        taskHistory3.setAssignTo(project.getProjectId());
    }

    @Test
    void testFindAll() {
        // Arrange
        List<TaskHistory> taskHistoryList = new ArrayList<>();

        taskHistoryList.add(taskHistory1);
        taskHistoryList.add(taskHistory2);

        // Mock the behavior of the repository method
        when(taskHistoryRepository.findAll()).thenReturn(taskHistoryList);

        // Act
        List<TaskHistory> result = taskHistoryRepository.findAll();

        // Assert
        assertEquals(taskHistoryList, result);
    }

    @Test
    void testFindByProjectId() {
        // Arrange
        long projectId = 1L;
        TaskHistory taskHistory = new TaskHistory();

        // Set up mock behavior
        when(taskHistoryRepository.findAllByAssignTo(projectId)).thenReturn(List.of(taskHistory1, taskHistory2));

        // Act
        List<TaskHistory> result = taskHistoryRepository.findAllByAssignTo(projectId);

        // Assert
        assertEquals(List.of(taskHistory1,taskHistory2), result);

        // Verify that the method was called with the correct argument
        verify(taskHistoryRepository, times(1)).findAllByAssignTo(projectId);
    }

    @Test
    void testSave() {
        // Arrange
        TaskHistory taskHistory = taskHistory1;
        TaskHistory createdTaskHistory = taskHistory1;

        // Mock the behavior of the repository method
        when(taskHistoryRepository.save(taskHistory)).thenReturn(createdTaskHistory);

        // Act
        TaskHistory result = taskHistoryRepository.save(taskHistory);

        // Assert
        assertEquals(taskHistory, result);
    }

    @Test
    void testDeleteByProjectId() {
        // Arrange
        long projectId = 1L;

        // Mock the behavior of the repository method
        doNothing().when(taskHistoryRepository).deleteByAssignTo(projectId);

        // Act
        taskHistoryRepository.deleteByAssignTo(projectId);

        // Assert
        // No explicit assertion needed for void methods, verify() can be used instead
        verify(taskHistoryRepository, times(1)).deleteByAssignTo(projectId);
    }

}
