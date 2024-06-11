package com.ERP.controllers;

import com.ERP.dtos.ProjectDto;
import com.ERP.entities.Employee;
import com.ERP.services.EmployeeService;
import com.ERP.utils.MyResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {


    @Autowired
    public EmployeeService employeeService;

     @PreAuthorize("hasRole('ADMIN')")
     @PostMapping("/add/{id}")
     public ResponseEntity<Employee>createEmployee(@RequestBody Employee employee, @PathVariable Long id)
     {
         return ResponseEntity.ok(employeeService.createEmployee(employee,id));
     }

     @PreAuthorize("hasRole('ADMIN')")
     @DeleteMapping("/deleteById/{id}")
     public ResponseEntity<Map<String,Boolean>>deleteEmployee(@PathVariable Long id )
     {

         boolean deleted = false;
         deleted = employeeService.deleteEmployee(id);
         Map<String,Boolean>response = new HashMap<>();
         response.put("deleted",deleted);
          return ResponseEntity.ok(response);
     }

//    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/delete/{name}")
//    public ResponseEntity<Map<String,Boolean>>deleteEmployeeByName(@PathVariable String name )
//    {
//
//        boolean deleted = false;
//        deleted = employeeService.deleteEmployeeByName(name);
//        Map<String,Boolean>response = new HashMap<>();
//        response.put("deleted",deleted);
//        return ResponseEntity.ok(response);
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find")
    public ResponseEntity<List<Employee>> fetchEmployees()
    {
      return  ResponseEntity.ok(employeeService.fetchEmployees());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/findById/{id}")
    public ResponseEntity<Employee>fetchEmployeeById(@PathVariable Long id)
    {
        return ResponseEntity.ok(employeeService.fetchEmployeeById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable ("id") Long id, @RequestBody Employee employeeDto)
    {
        employeeDto = employeeService.updateEmployee(id,employeeDto);
        return ResponseEntity.ok(employeeDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping("/findAll")
    public ResponseEntity<Object> findAll() {
        try {
            List<Employee> allEmployees = employeeService.fetchEmployees();
            return MyResponseGenerator.generateResponse(HttpStatus.OK, true, "Employees are found", allEmployees);
        } catch (Exception e) {
            return MyResponseGenerator.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "Something went wrong", null);
        }
    }
}
