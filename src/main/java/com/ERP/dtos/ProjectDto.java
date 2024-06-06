package com.ERP.dtos;

import com.ERP.entities.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDto
{
    private long projectId;
    @Size(min=1,max = 50,message = "min characters are 1 and maximum characters can be upto 50")
    @Pattern(regexp = "^[^\\s].*$", message = "Name of project cannot be empty")
    private String name;
    @Size(min=1,max = 200,message = "min characters are 3 and maximum characters can be upto 50")
    @Pattern(regexp = "^[^\\s].*$", message = "Description of project cannot be empty")
    private String description;
    private Date startDate;
    private Date endDate;
    @Size(min=1,max = 50,message = "min characters are 3 and maximum characters can be upto 50")
    @Pattern(regexp = "^[^\\s].*$", message = "Status of project cannot be empty")
    private String status;
//    private Set<TaskDto> taskSet = new HashSet<>();
//    private DepartmentDto department;
//    private Employee employee;
}
