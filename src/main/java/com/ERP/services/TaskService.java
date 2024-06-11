package com.ERP.services;

import com.ERP.dtos.TaskDto;
import com.ERP.entities.Employee;
import com.ERP.entities.Project;
import com.ERP.entities.Task;
import com.ERP.exceptions.IdNotFoundException;
import com.ERP.repositories.EmployeeRepository;
import com.ERP.repositories.ProjectRepository;
import com.ERP.repositories.TaskRepository;
import com.ERP.servicesInter.TaskServiceInter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService implements TaskServiceInter {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public TaskDto createTask(TaskDto taskDTO) {
        try {
            Task newTask = Task.builder().name(taskDTO.getName())
                    .startDate(taskDTO.getStartDate()).endDate(taskDTO.getEndDate())
                    .description(taskDTO.getDescription()).status(taskDTO.getStatus())
                    .build();

            Project project = projectRepository.findById(taskDTO.getProjectId())
                    .orElseThrow(() -> new IdNotFoundException("Project not found with ID: " + taskDTO.getProjectId()));

            Employee employee = employeeRepository.findById(taskDTO.getEmployeeId())
                    .orElseThrow(() -> new IdNotFoundException("Employee not found with ID: " + taskDTO.getEmployeeId()));

            newTask.setAssignTo(project);
            newTask.setEmployee(employee);
            taskDTO.setTaskId(newTask.getTaskId());
            Task savedTask = taskRepository.save(newTask);
            taskDTO.setTaskId(savedTask.getTaskId());
            return taskDTO;
        } catch (Exception e) {
            throw new IdNotFoundException("Error adding task: " + e.getMessage());
        }
    }


    public TaskDto updateTask(long taskId, TaskDto taskDTO) {
        try {
            Task taskToUpdate = taskRepository.findById(taskId)
                    .orElseThrow(() -> new IdNotFoundException("Task not found with id: " + taskId));

            if (Objects.nonNull(taskDTO.getName()) && !taskDTO.getName().isEmpty()) {
                taskToUpdate.setName(taskDTO.getName());
            }
            if (Objects.nonNull(taskDTO.getDescription()) && !taskDTO.getDescription().isEmpty()) {
                taskToUpdate.setDescription(taskDTO.getDescription());
            }
            if (Objects.nonNull(taskDTO.getStartDate())) {
                taskToUpdate.setStartDate(taskDTO.getStartDate());
            }
            if (Objects.nonNull(taskDTO.getEndDate())) {
                taskToUpdate.setEndDate(taskDTO.getEndDate());
            }
            if (Objects.nonNull(taskDTO.getStatus()) && !taskDTO.getStatus().isEmpty()) {
                taskToUpdate.setStatus(taskDTO.getName());
            }

            Project project = projectRepository.findById(taskToUpdate.getAssignTo().getProjectId())
                    .orElseThrow(() -> new IdNotFoundException("Project not found with ID: " + taskDTO.getProjectId()));
            taskToUpdate.setAssignTo(project);
            Employee employee = employeeRepository.findById(taskToUpdate.getEmployee().getId())
                    .orElseThrow(() -> new IdNotFoundException("Employee not found with ID: " + taskDTO.getEmployeeId()));

            taskToUpdate.setAssignTo(project);
            taskToUpdate.setEmployee(employee);
            taskRepository.save(taskToUpdate);

            return taskDTO;
        } catch (Exception e) {
            throw new IdNotFoundException("Error updating task: " + e.getMessage());
        }
    }


    @Override
    public List<TaskDto> getAllTask() {
        try {
            List<Task> taskList = taskRepository.findAll();
            List<TaskDto> taskDtoList = new ArrayList<>();
            for (Task task : taskList) {
                TaskDto taskDto = TaskDto.builder().taskId(task.getTaskId()).name(task.getName())
                        .startDate(task.getStartDate()).endDate(task.getEndDate())
                        .description(task.getDescription()).status(task.getStatus())
                        .employeeId(task.getEmployee().getId()).projectId(task.getAssignTo().getProjectId())
                        .build();
                taskDtoList.add(taskDto);
            }
            return taskDtoList;
        } catch (Exception e) {
            throw new IdNotFoundException("Error finding all tasks: " + e.getMessage());
        }
    }

    public TaskDto getTaskById(long taskId) {
        try {
            Task foundTask = taskRepository.findById(taskId)
                    .orElseThrow(() -> new IdNotFoundException("Task not found with id: " + taskId));
            TaskDto taskDto = TaskDto.builder().taskId(foundTask.getTaskId()).name(foundTask.getName())
                    .startDate(foundTask.getStartDate()).endDate(foundTask.getEndDate())
                    .description(foundTask.getDescription()).status(foundTask.getStatus())
                    .employeeId(foundTask.getEmployee().getId()).projectId(foundTask.getAssignTo().getProjectId())
                    .build();

            return taskDto;
        } catch (Exception e) {
            throw new IdNotFoundException("Error finding task: " + e.getMessage());
        }
    }

    public TaskDto deleteTask(long taskId) {
        try {
            Task taskToDelete = taskRepository.findById(taskId)
                    .orElseThrow(() -> new IdNotFoundException("Task not found with id: " + taskId));
            taskRepository.deleteById(taskId);

            TaskDto taskDto = TaskDto.builder().taskId(taskToDelete.getTaskId()).name(taskToDelete.getName())
                    .startDate(taskToDelete.getStartDate()).endDate(taskToDelete.getEndDate())
                    .description(taskToDelete.getDescription()).status(taskToDelete.getStatus())
                    .employeeId(taskToDelete.getEmployee().getId()).projectId(taskToDelete.getAssignTo().getProjectId())
                    .build();

            return taskDto;
        } catch (Exception e) {
            throw new IdNotFoundException("Error deleting task: " + e.getMessage());
        }
    }
}
