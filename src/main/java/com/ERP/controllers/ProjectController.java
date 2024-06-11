package com.ERP.controllers;

import com.ERP.dtos.ProjectDto;
import com.ERP.entities.Project;
import com.ERP.exceptions.IdNotFoundException;
import com.ERP.services.ProjectService;
import com.ERP.utils.MyResponseGenerator;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@Validated
public class ProjectController
{
    ProjectService projectService;
    private final Validator validator;

    public ProjectController(ProjectService projectService, LocalValidatorFactoryBean validatorFactory)
    {
        this.projectService=projectService;
        this.validator = validatorFactory.getValidator();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Object> addProject(@Valid @RequestBody ProjectDto projectDto) {
        try {
            ProjectDto newProject = projectService.addProject(projectDto);
            return MyResponseGenerator.generateResponse(HttpStatus.CREATED, true, "Project is added", newProject);
        } catch (NullPointerException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Null Pointer Exception", null);
        } catch (Exception e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Something went wrong", null);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @PutMapping("/update/{projectId}")
    public ResponseEntity<Object> updateProject(@Valid @RequestBody ProjectDto projectDto, @PathVariable Long projectId) {
        try {
            ProjectDto projectDto1 = projectService.updateProject(projectDto, projectId);
            return MyResponseGenerator.generateResponse(HttpStatus.OK, true, "Project is updated successfully", projectDto1);
        } catch (IdNotFoundException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Id is not found", null);
        } catch (NullPointerException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Null Pointer Exception", null);
        } catch (Exception e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Something went wrong and Project is not updated successfully", null);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE','CLIENT')")
    @GetMapping("/find/{projectId}")
    public ResponseEntity<Object> findProject(@PathVariable long projectId) {
        try {
            ProjectDto projectTofind = projectService.findProject(projectId);
            return MyResponseGenerator.generateResponse(HttpStatus.OK, true, "Project is found", projectTofind);
        } catch (IdNotFoundException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Id is not found", null);
        } catch (NullPointerException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Null Pointer Exception", null);
        } catch (Exception e) {
            return MyResponseGenerator.generateResponse(HttpStatus.NOT_FOUND, false, "Project is not found", null);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addAll")
    public ResponseEntity<Object> addAll(@Valid @RequestBody List<ProjectDto> projectDtos) {
        try {
            List<ProjectDto> newProjects = projectService.addAllProject(projectDtos);
            return MyResponseGenerator.generateResponse(HttpStatus.OK, true, "Projects are added", newProjects);
        } catch (IdNotFoundException e)
        {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Id is not found", null);
        } catch (NullPointerException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Null Pointer Exception", null);
        } catch (Exception e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Something went wrong", null);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<Object> deleteProject(@PathVariable long projectId) {
        try {
            ProjectDto projectDto = projectService.deleteProject(projectId);
            return MyResponseGenerator.generateResponse(HttpStatus.OK, true, "Project is Deleted Successfully", projectDto);
        } catch (IdNotFoundException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Id is not found", null);
        } catch (NullPointerException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Null Pointer Exception", null);
        } catch (Exception e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Project is not Deleted Successfully", null);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE','CLIENT')")
    @GetMapping("/findAll")
    public List<ProjectDto> findAll()
    {
        return projectService.findAllProject();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{projectId}/assignToEmployee/{employeeId}")
    public ResponseEntity<Object> assignProjectToEmployee(@PathVariable long projectId, @PathVariable long employeeId) {
        try {
            Project assignedProject = projectService.assignProjectToEmployee(projectId, employeeId);
            if (assignedProject != null) {
                return MyResponseGenerator.generateResponse(HttpStatus.OK, true, "Project assigned to employee successfully", assignedProject);
            } else {
                return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Something went wrong and project is not assigned", assignedProject);
            }
        } catch (IdNotFoundException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Id not found", null);
        } catch (NullPointerException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Null Pointer Exception", null);
        } catch (Exception e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "There is an Exception", null);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{projectId}/assignToDepartment/{departmentId}")
    public ResponseEntity<Object> assignProjectToDepartment(@PathVariable long projectId, @PathVariable long departmentId) {
        try {
            Project assignedProject = projectService.assignProjectToDepartment(projectId, departmentId);
            if (assignedProject != null) {
                return MyResponseGenerator.generateResponse(HttpStatus.OK, true, "Project assigned to department successfully", assignedProject);
            } else {
                return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Something went wrong and project is not assigned", assignedProject);
            }
        } catch (IdNotFoundException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Id not found", null);
        } catch (NullPointerException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Null Pointer Exception", null);
        } catch (Exception e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "There is an Exception", null);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{projectId}/assignClientToProject/{clientId}")
    public ResponseEntity<Object> addClientToProject(@PathVariable long projectId, @PathVariable long clientId) {
        try {
            Project assignedProject = projectService.addClientToProject(projectId, clientId);
            if (assignedProject != null) {
                return MyResponseGenerator.generateResponse(HttpStatus.OK, true, "Project assigned to department successfully", assignedProject);
            } else {
                return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Something went wrong and project is not assigned", assignedProject);
            }
        } catch (IdNotFoundException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Id not found", null);
        } catch (NullPointerException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Null Pointer Exception", null);
        } catch (Exception e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "There is an Exception", null);
        }
    }
}
