package com.ERP.repositoriesTest;


import com.ERP.entities.Employee;
import com.ERP.repositories.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;


    Employee stored;
    Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1L)
                .email("dakshmalik437@gmail.com")
                .username("daksh")
                .role("Manager")

                .task(new ArrayList<>()) // Initialize tasks list
                .salaryPayment(null) // Set salaryPayment, assuming it's nullable
                .leaves(new ArrayList<>()) // Initialize leaves list
                .build();

          stored = employeeRepository.save(employee);
//
//        employee = Employee.builder()
//                .name("daksh")
//                .email("dakshmalik437@gmail.com")
//                .role("Manager")
//
//                .build();
    }

    @AfterEach
    void tearDown() {
        employee=null;
        employeeRepository.deleteAll();
    }

//    @Test
//    void findByName_Found() {
//        List<Employee>employees = employeeRepository.findByName("daksh");
//        assertThat(employees.get(0).getName()).isEqualTo(stored.getName());
//        assertThat(employees.get(0).getUsername()).isEqualTo(stored.getUsername());
//        assertThat(employees.get(0).getRole()).isEqualTo(stored.getRole());
//
//    }

//    @Test
//    void testfindByName_NotFound(){
//        List<Employee>employees = employeeRepository.findByName("vasu");
//        assertThat(employees.isEmpty()).isTrue();
//    }

    @Test
    void findAllEmployees()
    {
        List<Employee>employees = employeeRepository.findAll();
        assertThat(employees.get(employees.size()-1).getUsername()).isEqualTo(stored.getUsername());
    }

    @Test
    void findById()
    {
        Optional<Employee> employee1 = employeeRepository.findById(1L);
       if( employee1.isPresent())
           assertThat(employee1.get().getUsername()).isEqualTo(stored.getUsername());

    }

}