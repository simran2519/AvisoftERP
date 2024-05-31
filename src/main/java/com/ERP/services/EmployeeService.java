package com.ERP.services;


import com.ERP.dtos.EmployeeDto;
import com.ERP.entities.Department;
import com.ERP.entities.Employee;
import com.ERP.entities.Authentication;
import com.ERP.repositories.DepartmentRepository;
import com.ERP.repositories.EmployeeRepository;
import com.ERP.repositories.UserEntityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
      this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee,Long id) {


      Optional <Department> department = departmentRepository.findById(id);
//        Employee employee = new Employee();
//        BeanUtils.copyProperties(employeeDto,employee);
        Authentication authentication = new Authentication();
        authentication.setUsername(employee.getName());
        authentication.setPassword(employee.getPassword());
        authentication.setRole("EMPLOYEE");
        employee.setDepartment(department.get());
        employeeRepository.save(employee);

        return employee;
    }

    public boolean deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).get();
        employeeRepository.delete(employee);
        return true;
    }


    public boolean deleteEmployeeByName(String name) {
        List<Employee> employee = employeeRepository.findByName(name);

        employeeRepository.deleteAll(employee);
        return true;
    }

    public List<Employee> fetchEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees;
    }


    public Employee fetchEmployeeById(Long id) {
         Optional<Employee> employee = employeeRepository.findById(id);
         if(employee.isPresent()) {
             EmployeeDto employeeDto = new EmployeeDto();
             BeanUtils.copyProperties(employee.get(), employeeDto);
             return employee.get();
         }
         else throw new NoSuchElementException();
    }


    public Employee updateEmployee(Long id, Employee employeeDto) {
      Employee employee = employeeRepository.findById(id).get();

      if(Objects.nonNull(employeeDto.getName()) && !"".equalsIgnoreCase(employeeDto.getName()))
      {
          employee.setName(employeeDto.getName());
      }

      if(Objects.nonNull(employeeDto.getRole()) && !"".equalsIgnoreCase(employeeDto.getRole()))
      {
          employee.setRole(employeeDto.getRole());
      }

      if(Objects.nonNull(employeeDto.getEmail()) && !"".equalsIgnoreCase(employeeDto.getEmail()))
      {
          employee.setEmail(employeeDto.getEmail());
      }

      employeeRepository.save(employee);

      return employeeDto;
    }
}
