package com.ERP.entitiesTest;

import com.ERP.entities.SalaryStructure;
import com.ERP.entities.TaskHistory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TaskHistoryTest {

    @Test
    void testConstructorAndGetters() {

        // Arrange
        long taskHistoryId = 1L;
        long taskId = 1L;
        String name = "E-commerce Authentication";
        String description = "Add Spring security along side with google authentication and not to forget jwt.";
        Date startDate = Date.valueOf("2024-05-13");
        Date endDate = Date.valueOf("2025-05-13");
        String status = "Assigned";

        long assignTo = 1L;
        long employee = 1L;

        // Act
        TaskHistory taskHistory = new TaskHistory(taskHistoryId,taskId, name, description, startDate, endDate, status, assignTo, employee);

        // Assert
        assertEquals(taskHistoryId, taskHistory.getTaskHistoryId());
        assertEquals(taskId, taskHistory.getTaskId());
        assertEquals(name, taskHistory.getName());
        assertEquals(description, taskHistory.getDescription());
        assertEquals(startDate, taskHistory.getStartDate());
        assertEquals(endDate, taskHistory.getEndDate());
        assertEquals(status, taskHistory.getStatus());
        assertEquals(assignTo, taskHistory.getAssignTo());
        assertEquals(employee, taskHistory.getEmployee());

    }

    @Test
    void testSetters() {

        // Arrange
        long taskHistoryId = 1L;
        long taskId = 1L;
        String name = "E-commerce Authentication";
        String description = "Add Spring security along side with google authentication and not to forget jwt.";
        Date startDate = Date.valueOf("2024-05-13");
        Date endDate = Date.valueOf("2025-05-13");
        String status = "Assigned";

        long assignTo = 1L;
        long employee = 1L;

        // Act
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTaskHistoryId(taskHistoryId);
        taskHistory.setTaskId(taskId);
        taskHistory.setName(name);
        taskHistory.setDescription(description);
        taskHistory.setStartDate(startDate);
        taskHistory.setEndDate(endDate);
        taskHistory.setStatus(status);
        taskHistory.setAssignTo(assignTo);
        taskHistory.setEmployee(employee);

        // Assert
        assertEquals(taskHistoryId, taskHistory.getTaskHistoryId());
        assertEquals(taskId, taskHistory.getTaskId());
        assertEquals(name, taskHistory.getName());
        assertEquals(description, taskHistory.getDescription());
        assertEquals(startDate, taskHistory.getStartDate());
        assertEquals(endDate, taskHistory.getEndDate());
        assertEquals(status, taskHistory.getStatus());
        assertEquals(assignTo, taskHistory.getAssignTo());
        assertEquals(employee, taskHistory.getEmployee());
    }
}
