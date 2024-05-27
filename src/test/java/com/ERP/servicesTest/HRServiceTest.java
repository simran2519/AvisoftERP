package com.ERP.servicesTest;

import com.ERP.controllers.HRController;
import com.ERP.dtos.HRDto;
import com.ERP.entities.HR;
import com.ERP.exceptions.IdNotFoundException;
import com.ERP.repositories.HRRepository;
import com.ERP.services.HRService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

class HRServiceTest {

    private HRRepository hrRepository;
    private ObjectMapper objectMapper;
    private HRService hrService;
    @InjectMocks
    private HRController hrController;

    @BeforeEach
    public void setUp() {
        hrRepository = Mockito.mock(HRRepository.class);
        objectMapper = new ObjectMapper(); // Initialize objectMapper
        hrService = new HRService(hrRepository, objectMapper);
    }

    @Test
    void createHR() {
        // Given
        HRDto hrDto = new HRDto(1L, "John Doe", "password", "Admin");
        HR newHR = new HR(1L, "John Doe", "password", "Admin");

        given(hrRepository.save(any(HR.class))).willReturn(newHR);

        // When
        HRDto savedHR = hrService.createHR(hrDto);

        // Then
        assertThat(savedHR).isNotNull();
        assertThat(savedHR.getName()).isEqualTo(hrDto.getName());
    }

    @Test
    void updateHR() {
        // Given
        long hrId = 1L;
        HRDto hrDto = new HRDto(1L,"Updated Name", "updatedPassword", "Updated Role");
        HR existingHR = new HR(hrId, "Existing Name", "existingPassword", "Existing Role");

        given(hrRepository.findById(hrId)).willReturn(Optional.of(existingHR));
        given(hrRepository.save(any(HR.class))).willReturn(existingHR);

        // When
        HRDto updatedHR = hrService.updateHR(hrId, hrDto);

        // Then
        assertThat(updatedHR).isNotNull();
        assertThat(updatedHR.getName()).isEqualTo(hrDto.getName());
    }

    @Test
    void getAllHR() {
        // Given
        List<HR> hrList = Arrays.asList(
                new HR(1L, "John Doe", "password", "Admin"),
                new HR(2L, "Jane Doe", "password", "Manager")
        );

        given(hrRepository.findAll()).willReturn(hrList);

        // When
        List<HRDto> allHR = hrService.getAllHR();

        // Then
        assertThat(allHR).isNotNull();
        assertThat(allHR.size()).isEqualTo(hrList.size());
    }

    @Test
    void getHRById() {
        // Given
        long hrId = 1L;
        HR existingHR = new HR(hrId, "John Doe", "password", "Admin");

        given(hrRepository.findById(hrId)).willReturn(Optional.of(existingHR));

        // When
        HRDto foundHR = hrService.getHRById(hrId);

        // Then
        assertThat(foundHR).isNotNull();
        assertThat(foundHR.getName()).isEqualTo(existingHR.getName());
    }

    @Test
    void deleteHR() {
        // Given
        long hrId = 1L;
        HR existingHR = new HR(hrId, "John Doe", "password", "Admin");

        given(hrRepository.findById(hrId)).willReturn(Optional.of(existingHR));

        // When
        HRDto deletedHR = hrService.deleteHR(hrId);

        // Then
        assertThat(deletedHR).isNotNull();
        assertThat(deletedHR.getHrId()).isEqualTo(existingHR.getHrId());
    }

    @Test
    void createHR_WithException() {
        // Given
        HRDto hrDto = new HRDto(1L,"John Doe", "password", "Admin");

        given(hrRepository.save(any(HR.class))).willThrow(new RuntimeException("Failed to save"));

        // When
        // Then
        assertThrows(IdNotFoundException.class, () -> hrService.createHR(hrDto));
    }

    @Test
    void updateHR_WithException() {
        // Given
        long hrId = 1L;
        HRDto hrDto = new HRDto(1L,"Updated Name", "updatedPassword", "Updated Role");

        given(hrRepository.findById(hrId)).willReturn(Optional.empty());

        // When
        // Then
        assertThrows(IdNotFoundException.class, () -> hrService.updateHR(hrId, hrDto));
    }

    @Test
    void getHRById_WithException() {
        // Given
        long hrId = 1L;

        given(hrRepository.findById(hrId)).willReturn(Optional.empty());

        // When
        // Then
        assertThrows(IdNotFoundException.class, () -> hrService.getHRById(hrId));
    }

    @Test
    void deleteHR_WithException() {
        // Given
        long hrId = 1L;

        given(hrRepository.findById(hrId)).willReturn(Optional.empty());

        // When
        // Then
        assertThrows(IdNotFoundException.class, () -> hrService.deleteHR(hrId));
    }

    @Test
    void testFindAllHR() {
        // Prepare test data
        List<HRDto> hrList = Arrays.asList(
                new HRDto(1, "John", "password", "Manager"),
                new HRDto(2, "Alice", "password", "Employee")
        );

        // Mock HRService behavior
        when(hrService.getAllHR()).thenReturn(hrList);

        // Call controller method
        List<HRDto> response = hrController.findAllHR();

        // Verify response
        assertEquals(hrList.size(), response.size());
        assertEquals(hrList.get(0).getName(), response.get(0).getName());
        assertEquals(hrList.get(1).getName(), response.get(1).getName());
        // Add more assertions as needed
    }

//    @Test
//    void testDeleteHR_Success() {
//        // Prepare test data
//        long hrId = 1;
//
//        // Mock HRService behavior
//        when(hrService.deleteHR(hrId)).thenReturn(new HRDto(1L, "Raman", "password", "role")); // Assuming deleteHR always returns a non-null HRDto
//
//        // Call controller method
//        ResponseEntity<Object> responseEntity = hrController.deleteHR(hrId);
//
//        // Verify response
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Assuming the response body is a String
//    }

    @Test
    void testDeleteHR_Failure() {
        // Prepare test data
        int hrId = 1;

        // Mock HRService behavior to return null
        when(hrService.deleteHR(hrId)).thenReturn(null);

        // Call controller method
        ResponseEntity<Object> responseEntity = hrController.deleteHR(hrId);

        // Verify response
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Failed to delete HR", responseEntity.getBody()); // Assuming the response body is a String
    }
}