package com.ERP.entitiesTest;


import com.ERP.entities.Employee;
import com.ERP.entities.Project;
import com.ERP.entities.Task;
import com.ERP.entities.TaskHistory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TaskTest {

    @Test
    void testConstructorAndGetters() {

        // Arrange
        long taskId = 1L;
        String name = "E-commerce Authentication";
        String description = "Add Spring security along side with google authentication and not to forget jwt.";
        Date startDate = Date.valueOf("2024-05-13");
        Date endDate = Date.valueOf("2025-05-13");
        String status = "Assigned";

        Project project = new Project();
        project.setProjectId(1L);
        Employee employee = new Employee();
        employee.setId(1L);

        // Act
        Task task = new Task(taskId, name, description, startDate, endDate, status, project, employee);

        // Assert
        assertEquals(taskId, task.getTaskId());
        assertEquals(name, task.getName());
        assertEquals(description, task.getDescription());
        assertEquals(startDate, task.getStartDate());
        assertEquals(endDate, task.getEndDate());
        assertEquals(status, task.getStatus());
        assertEquals(project.getProjectId(), task.getAssignTo().getProjectId());
        assertEquals(employee.getId(), task.getEmployee().getId());

    }

    @Test
    void testSetters() {

        // Arrange
        long taskId = 1L;
        String name = "E-commerce Authentication";
        String description = "Add Spring security along side with google authentication and not to forget jwt.";
        Date startDate = Date.valueOf("2024-05-13");
        Date endDate = Date.valueOf("2025-05-13");
        String status = "Assigned";

        Project project = new Project();
        project.setProjectId(1L);
        Employee employee = new Employee();
        employee.setId(1L);

        // Act
        Task task = new Task();
        task.setTaskId(taskId);
        task.setName(name);
        task.setDescription(description);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setAssignTo(project);
        task.setEmployee(employee);
        task.setStatus(status);

        // Assert
        assertEquals(taskId, task.getTaskId());
        assertEquals(name, task.getName());
        assertEquals(description, task.getDescription());
        assertEquals(startDate, task.getStartDate());
        assertEquals(endDate, task.getEndDate());
        assertEquals(status, task.getStatus());
        assertEquals(project.getProjectId(), task.getAssignTo().getProjectId());
        assertEquals(employee.getId(), task.getEmployee().getId());

    }
}
