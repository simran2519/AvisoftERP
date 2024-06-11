package com.ERP.controllers;

import com.ERP.dtos.DepartmentDto;
import com.ERP.dtos.DepartmentDto;
import com.ERP.exceptions.IdNotFoundException;
import com.ERP.services.DepartmentService;
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
@RequestMapping("/department")
@Validated
public class DepartmentController
{
    DepartmentService departmentService;
    private final Validator validator;


    public DepartmentController(DepartmentService departmentService, LocalValidatorFactoryBean validatorFactory)
    {
        this.departmentService=departmentService;
        this.validator=validatorFactory.getValidator();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Object> addDepartment(@Valid @RequestBody DepartmentDto departmentDto) {
        try {
            DepartmentDto departmentDto1 = departmentService.addDepartment(departmentDto);
            return MyResponseGenerator.generateResponse(HttpStatus.CREATED, true, "Department is added", departmentDto1);
        } catch (NullPointerException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Null Pointer Exception", null);
        } catch (Exception e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Something went wrong", null);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{departmentId}")
    public ResponseEntity<Object> updateDepartment(@Valid @RequestBody DepartmentDto departmentDto, @PathVariable Long departmentId) {
        try {
            DepartmentDto departmentDto1 = departmentService.updateDepartment(departmentDto, departmentId);
            return MyResponseGenerator.generateResponse(HttpStatus.OK, true, "Department is updated successfully", departmentDto1);
        } catch (IdNotFoundException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Id is not found", null);
        } catch (NullPointerException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Null Pointer Exception", null);
        }  catch (Exception e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Something went wrong and Department is not updated successfully", null);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/{departmentId}")
    public ResponseEntity<Object> findDepartment(@PathVariable long departmentId) {
        try {
            DepartmentDto departmentToFind = departmentService.findDepartment(departmentId);
            return MyResponseGenerator.generateResponse(HttpStatus.OK, true, "Department is found", departmentToFind);
        } catch (IdNotFoundException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Id is not found", null);
        } catch (NullPointerException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Null Pointer Exception", null);
        } catch (Exception e) {
            return MyResponseGenerator.generateResponse(HttpStatus.NOT_FOUND, false, "Department is not found", null);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addAll")
    public ResponseEntity<Object> addAll(@Valid @RequestBody List<DepartmentDto> departmentDtos) {
        try {
            List<DepartmentDto> newDepartments = departmentService.addAllDepartment(departmentDtos);
            return MyResponseGenerator.generateResponse(HttpStatus.OK, true, "Departments are added", newDepartments);
        } catch (IdNotFoundException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Id is not found", null);
        } catch (NullPointerException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Null Pointer Exception", null);
        }  catch (Exception e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Something went wrong", null);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{departmentId}")
    public ResponseEntity<Object> deleteDepartment(@PathVariable long departmentId) {
        try {
            DepartmentDto departmentDto = departmentService.deleteDepartment(departmentId);
            return MyResponseGenerator.generateResponse(HttpStatus.OK, true, "Department is Deleted Successfully", departmentDto);
        } catch (IdNotFoundException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Id is not found", null);
        } catch (NullPointerException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Null Pointer Exception", null);
        } catch (Exception e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Department is not Deleted Successfully", null);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/findAll")
    public List<DepartmentDto> findAll()
    {
        return departmentService.findAllDepartment();
    }

}
