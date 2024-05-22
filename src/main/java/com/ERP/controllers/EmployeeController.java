package com.ERP.controllers;


import com.ERP.entities.Employee;
import com.ERP.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {


    @Autowired
    public EmployeeService employeeService;

     @PostMapping("/add")
     public ResponseEntity<Employee>createEmployee(@RequestBody Employee employee)
     {
         return ResponseEntity.ok(employeeService.createEmployee(employee));
     }

     @DeleteMapping("/deleteById/{id}")
     public ResponseEntity<Map<String,Boolean>>deleteEmployee(@PathVariable Long id )
     {

         boolean deleted = false;
         deleted = employeeService.deleteEmployee(id);
         Map<String,Boolean>response = new HashMap<>();
         response.put("deleted",deleted);
          return ResponseEntity.ok(response);
     }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Map<String,Boolean>>deleteEmployeeByName(@PathVariable String name )
    {

        boolean deleted = false;
        deleted = employeeService.deleteEmployeeByName(name);
        Map<String,Boolean>response = new HashMap<>();
        response.put("deleted",deleted);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Employee>> fetchEmployees()
    {
      return  ResponseEntity.ok(employeeService.fetchEmployees());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Employee>fetchEmployeeById(@PathVariable Long id)
    {
        return ResponseEntity.ok(employeeService.fetchEmployeeById(id));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable ("id") Long id, @RequestBody Employee employeeDto)
    {
        employeeDto = employeeService.updateEmployee(id,employeeDto);
        return ResponseEntity.ok(employeeDto);
    }


}
