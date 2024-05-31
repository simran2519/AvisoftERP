package com.ERP.controllersTest;
import com.ERP.controllers.EmployeeController;
import com.ERP.entities.Department;
import com.ERP.entities.Employee;
import com.ERP.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;

    Employee employee1;
    Employee employee2;

    List<Employee> employeeList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        employee1 = Employee.builder()
                .id(1L)
                .name("daksh")
                .email("dakshmalik437@gmail.com")
                .role("Manager")
                .department(Department.builder().name("Your Department Name").build())
                .task(new ArrayList<>()) // Initialize tasks list
                .salaryPayment(null) // Set salaryPayment, assuming it's nullable
                .leaves(new ArrayList<>()) // Initialize leaves list
                .build();

        employeeList.add(employee1);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createEmployee() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(employee1);

        when(employeeService.createEmployee(employee1,1L)).thenReturn(employee1);
        this.mockMvc.perform(post("/employee/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print()).andExpect(status().isOk());

    }

    @Test
    void deleteEmployee() throws Exception {
        when(employeeService.deleteEmployee(1L)).thenReturn(true);

        this.mockMvc.perform(delete("/employee/deleteById/" + "1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deleteEmployeeByName() throws Exception {
        when(employeeService.deleteEmployeeByName("daksh")).thenReturn(true);

        this.mockMvc.perform(delete("/employee/delete/" + "daksh")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void fetchEmployees() throws Exception {
        when(employeeService.fetchEmployees()).thenReturn(employeeList);
        this.mockMvc.perform(get("/employee/find")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void fetchEmployeeById() throws Exception {
        when(employeeService.fetchEmployeeById(1L)).thenReturn(employee1);
        this.mockMvc.perform(get("/employee/findById/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void updateEmployee() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(employee1);

        when(employeeService.createEmployee(employee1,1L)).thenReturn(employee1);
        this.mockMvc.perform(post("/employee/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print()).andExpect(status().isOk());

    }
}

