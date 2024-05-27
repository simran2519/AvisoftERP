package com.ERP.controllersTest;

import com.ERP.controllers.SalaryStructureController;
import com.ERP.dtos.SalaryStructureDto;
import com.ERP.services.SalaryStructureService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SalaryStructureController.class)
class SalaryStructureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalaryStructureService salaryStructureService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getSalaryStructure() throws Exception {
        // Mock data
        SalaryStructureDto salaryStructureDto1 = new SalaryStructureDto();
        SalaryStructureDto salaryStructureDto2 = new SalaryStructureDto();
        List<SalaryStructureDto> salaryStructureDtoList = Arrays.asList(salaryStructureDto1, salaryStructureDto2);

        // Mock service method
        when(salaryStructureService.getSalaryStructureList()).thenReturn(salaryStructureDtoList);

        // Perform GET request
        mockMvc.perform(get("/salary-structure/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void addSalaryStructure() throws Exception {
        SalaryStructureDto salaryStructureDto = SalaryStructureDto.builder()
                .level("intern")
                .baseSalary(3000.00)
                .role("developer")
                .structureId(1)
                .build();

        when(salaryStructureService.createSalaryStructure(salaryStructureDto)).thenReturn(salaryStructureDto);

        mockMvc.perform(post("/salary-structure/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(salaryStructureDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void getSalaryStructureById() throws Exception {
        SalaryStructureDto salaryStructureDto = SalaryStructureDto.builder()
                .level("intern")
                .baseSalary(3000.00)
                .role("developer")
                .structureId(1)
                .build();

        when(salaryStructureService.getSalaryStructureById(1L)).thenReturn(salaryStructureDto);

        mockMvc.perform(get("/salary-structure/find/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.level").value("intern"));

    }

    @Test
    void fetchSalaryStructureByRole() throws Exception {
        SalaryStructureDto salaryStructureDto1 = SalaryStructureDto.builder()
                .level("intern")
                .baseSalary(3000.00)
                .role("developer")
                .structureId(1)
                .build();
        SalaryStructureDto salaryStructureDto2 = SalaryStructureDto.builder()
                .level("senior")
                .baseSalary(3000.00)
                .role("batter")
                .structureId(2)
                .build();

        List<SalaryStructureDto> salaryStructureDtoList = Arrays.asList(salaryStructureDto1, salaryStructureDto2);

        when(salaryStructureService.getSalaryStructureByRole("developer")).thenReturn(salaryStructureDtoList);
        mockMvc.perform(get("/salary-structure/findAllByRole/{role}", "developer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].level").value("intern"));

    }

    @Test
    void removeSalaryStructure() throws Exception {
        SalaryStructureDto salaryStructureDto = SalaryStructureDto.builder()
                .level("intern")
                .baseSalary(3000.00)
                .role("developer")
                .structureId(1)
                .build();

        when(salaryStructureService.deleteSalaryStructure(1L)).thenReturn(salaryStructureDto);
        mockMvc.perform(delete("/salary-structure/delete/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(salaryStructureDto)))
                .andExpect(status().isOk());
    }

    @Test
    void updateSalaryStructure() throws Exception {
        // Prepare the salary structure object with updated values
        SalaryStructureDto existingSalaryStructureDto = SalaryStructureDto.builder()
                .level("intern")
                .baseSalary(3000.00)
                .role("developer")
                .structureId(1)
                .build();

        SalaryStructureDto updatedSalaryStructureDto = SalaryStructureDto.builder()
                .level("senior")
                .baseSalary(5000.00)
                .role("senior developer")
                .structureId(1)
                .build();

        // Mock the service behavior to update the salary structure
        when(salaryStructureService.updateSalaryStructure(eq(1L), any(SalaryStructureDto.class)))
                .thenReturn(updatedSalaryStructureDto);

        // Perform PUT request to update the salary structure
        mockMvc.perform(put("/salary-structure/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedSalaryStructureDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.role").value("senior developer"));
    }
}