package com.ERP.servicesTest;


import com.ERP.dtos.EmployeeDto;
import com.ERP.entities.Department;
import com.ERP.entities.Employee;
import com.ERP.repositories.EmployeeRepository;
import com.ERP.services.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;
    AutoCloseable autoCloseable;
    Employee employee;

    EmployeeDto employeeDto;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeService(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .name("daksh")
                .email("dakshmalik437@gmail.com")
                .role("Manager")
                .department(Department.builder().name("Your Department Name").build())
                .task(new ArrayList<>()) // Initialize tasks list
                .salaryPayment(null) // Set salaryPayment, assuming it's nullable
                .leaves(new ArrayList<>()) // Initialize leaves list
                .build();

//       employeeDto = new EmployeeDto("dash", "dakshmalik437@gmail.com", "Manager", "IT");

    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void createEmployee() {
        mock(EmployeeRepository.class);
//        mock(EmployeeDto.class);
        mock(Employee.class);

        // Mocking behavior of employeeRepository.save()
        when(employeeRepository.save(employee)).thenReturn(employee);

        // Assert that the returned EmployeeDto matches the input EmployeeDto

        assertThat(employeeService.createEmployee(employee)).isEqualTo(employee);
    }

    @Test
    void deleteEmployee() {
        mock(EmployeeRepository.class, Mockito.CALLS_REAL_METHODS);
//        mock(EmployeeDto.class);
        mock(Employee.class);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        doAnswer(Answers.CALLS_REAL_METHODS).when(employeeRepository).delete(employee);
        assertThat(employeeService.deleteEmployee(1L)).isEqualTo(true);
    }

    @Test
    void deleteEmployeeByName() {
        mock(EmployeeRepository.class, Mockito.CALLS_REAL_METHODS);
//        mock(EmployeeDto.class);
        mock(Employee.class);
        when(employeeRepository.findByName("daksh")).thenReturn(new ArrayList<Employee>(Collections.singleton(employee)));
        doAnswer(Answers.CALLS_REAL_METHODS).when(employeeRepository).deleteAll(new ArrayList<Employee>(Collections.singleton(employee)));
        assertThat(employeeService.deleteEmployeeByName("daksh")).isEqualTo(true);
    }

    @Test
    void fetchEmployees() {
        mock(EmployeeRepository.class);
//        mock(EmployeeDto.class);
        mock(Employee.class);

        when(employeeRepository.findAll()).thenReturn(new ArrayList<Employee>(Collections.singleton(employee)));

        assertThat(employeeService.fetchEmployees().get(0)).isEqualTo(employee);
    }

    @Test
    void fetchEmployeeById() {
        mock(EmployeeRepository.class);
//        mock(EmployeeDto.class);
        mock(Employee.class);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        assertThat(employeeService.fetchEmployeeById(1L).getName()).isEqualTo(employee.getName());
    }

    @Test
    void updateEmployee() {
        mock(EmployeeRepository.class);
//        mock(EmployeeDto.class);
        mock(Employee.class);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        // Mocking behavior of employeeRepository.save()
        when(employeeRepository.save(employee)).thenReturn(employee);

        // Assert that the returned EmployeeDto matches the input EmployeeDto

        assertThat(employeeService.updateEmployee(1L,employee)).isEqualTo(employee);
    }
}