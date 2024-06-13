package com.ERP.servicesTest;

import com.ERP.dtos.SalaryPaymentDto;
import com.ERP.entities.Employee;
import com.ERP.entities.SalaryPayment;
import com.ERP.exceptions.IdNotFoundException;
import com.ERP.repositories.EmployeeRepository;
import com.ERP.repositories.SalaryPaymentRepository;
import com.ERP.services.SalaryPaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class

SalaryPaymentTest {
    @Mock
    private SalaryPaymentRepository salaryPaymentRepository;
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private SalaryPaymentService salaryPaymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCreateSalaryPayment_Success() {
        // Arrange
        SalaryPaymentDto salaryPaymentDto = new SalaryPaymentDto();
        salaryPaymentDto.setAmount(1000.0);
        salaryPaymentDto.setPaymentDate(Date.valueOf("2024-05-13"));
        salaryPaymentDto.setEmployeeId(1L);

        Employee employee = new Employee();
        employee.setId(1L);

        SalaryPayment savedSalaryPayment = new SalaryPayment();
        savedSalaryPayment.setPaymentId(1L);
        savedSalaryPayment.setAmount(salaryPaymentDto.getAmount());
        savedSalaryPayment.setPaymentDate(salaryPaymentDto.getPaymentDate());
        savedSalaryPayment.setEmployee(employee);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(salaryPaymentRepository.save(any(SalaryPayment.class))).thenReturn(savedSalaryPayment);

        // Act
        SalaryPaymentDto result = salaryPaymentService.createSalaryPayment(salaryPaymentDto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getPaymentId());
        assertEquals(salaryPaymentDto.getAmount(), result.getAmount());
        assertEquals(salaryPaymentDto.getPaymentDate(), result.getPaymentDate());
        assertEquals(salaryPaymentDto.getEmployeeId(), result.getEmployeeId());
    }

    @Test
    public void testCreateSalaryPayment_EmployeeNotFound() {
        // Arrange
        SalaryPaymentDto salaryPaymentDto = new SalaryPaymentDto();
        salaryPaymentDto.setAmount(1000.0);
        salaryPaymentDto.setPaymentDate(Date.valueOf("2024-05-13"));
        salaryPaymentDto.setEmployeeId(1L);

        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act, Assert
        assertThrows(IdNotFoundException.class, () -> {
            salaryPaymentService.createSalaryPayment(salaryPaymentDto);
        });
    }

    @Test
    void updateSalaryPayment_ExistingSalaryPaymentIdAndValidSalaryPaymentDto_ReturnsUpdatedSalaryPaymentDto() {

        // Arrange
        long paymentId = 1L;

        Employee employee = new Employee(1L,"raman","raman.kumar@avisoft.io", "password", "Employee", null,null,null,null);
        SalaryPaymentDto salaryPaymentDto = new SalaryPaymentDto(1L, employee.getId(), 5000, Date.valueOf("2024-05-13"));
        SalaryPayment existingSalaryPayment = new SalaryPayment(1L, null, 5000, Date.valueOf("2024-05-13"));

        // Act
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        when(salaryPaymentRepository.findById(paymentId)).thenReturn(Optional.of(existingSalaryPayment));
        when(salaryPaymentRepository.save(existingSalaryPayment)).thenReturn(existingSalaryPayment);
        when(objectMapper.convertValue(existingSalaryPayment, SalaryPaymentDto.class)).thenReturn(salaryPaymentDto);

        SalaryPaymentDto updatedSalaryPaymentDto = salaryPaymentService.updateSalaryPayment(paymentId, salaryPaymentDto);

        // Assert
        assertNotNull(updatedSalaryPaymentDto);
        assertEquals(salaryPaymentDto, updatedSalaryPaymentDto);

        verify(salaryPaymentRepository, times(1)).findById(paymentId);
        verify(salaryPaymentRepository, times(1)).save(existingSalaryPayment);
    }

    @Test
    void updateSalaryPayment_NonExistingSalaryPaymentId_ThrowsIdNotFoundException() {

        // Arrange
        long paymentId = 1L;

        Employee employee = new Employee(1L,"raman","raman.kumar@avisoft.io", "password", "Employee", null,null,null,null);
        SalaryPaymentDto salaryPaymentDto = new SalaryPaymentDto(1L, employee.getId(), 5000, Date.valueOf("2024-05-13"));

        // Act
        when(salaryPaymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        // Assert
        assertThrows(IdNotFoundException.class, () -> salaryPaymentService.updateSalaryPayment(paymentId, salaryPaymentDto));

        verify(salaryPaymentRepository, times(1)).findById(paymentId);
        verify(salaryPaymentRepository, never()).save(any());
    }

    @Test
    void getAllSalaryPayment_ReturnsListOfSalaryPaymentDto() {

        // Arrange
        Employee employee = new Employee(1L,"raman","raman.kumar@avisoft.io", "password", "Employee", null,null,null,null);

        List<SalaryPayment> salaryPaymentList = Arrays.asList(
                new SalaryPayment(1L, employee, 5000, Date.valueOf("2024-05-13")),
                new SalaryPayment(2L, employee, 5000, Date.valueOf("2025-05-13"))
        );

        SalaryPaymentDto salaryPaymentDto1 = new SalaryPaymentDto(1L, employee.getId(), 5000, Date.valueOf("2024-05-13"));
        SalaryPaymentDto salaryPaymentDto2 = new SalaryPaymentDto(2L, employee.getId(), 5000, Date.valueOf("2025-05-13"));

        // Act
        when(salaryPaymentRepository.findAll()).thenReturn(salaryPaymentList);
        when(objectMapper.convertValue(any(SalaryPayment.class), eq(SalaryPaymentDto.class)))
                .thenReturn(salaryPaymentDto1)
                .thenReturn(salaryPaymentDto2);

        List<SalaryPaymentDto> allSalaryPaymentDto = salaryPaymentService.getAllSalaryPayment();

        // Assert
        assertNotNull(allSalaryPaymentDto);
        assertEquals(2, allSalaryPaymentDto.size());

        assertEquals(salaryPaymentDto1, allSalaryPaymentDto.get(0));
        assertEquals(salaryPaymentDto2, allSalaryPaymentDto.get(1));
        verify(salaryPaymentRepository, times(1)).findAll();
    }

    @Test
    void getSalaryPaymentById_ExistingSalaryPaymentId_ReturnsSalaryPaymentDto() {

        // Arrange
        long paymentId = 1L;
        Employee employee = new Employee(1L,"raman","raman.kumar@avisoft.io", "password", "Employee", null,null,null,null);
        SalaryPayment salaryPayment = new SalaryPayment(1L, employee, 5000, Date.valueOf("2024-05-13"));

        // Act
        when(salaryPaymentRepository.findById(paymentId)).thenReturn(Optional.of(salaryPayment));

        when(objectMapper.convertValue(salaryPayment, SalaryPaymentDto.class)).thenReturn(new SalaryPaymentDto(paymentId, employee.getId(), 5000, Date.valueOf("2024-05-13")));

        SalaryPaymentDto salaryPaymentDto = salaryPaymentService.getSalaryPaymentById(paymentId);

        // Assert
        assertNotNull(salaryPaymentDto);
        assertEquals(paymentId, salaryPaymentDto.getPaymentId());

        verify(salaryPaymentRepository, times(1)).findById(paymentId);
    }

    @Test
    void getSalaryPaymentById_NonExistingSalaryPaymentId_ThrowsIdNotFoundException() {
        // Arrange
        long paymentId = 1L;

        // Act
        when(salaryPaymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        // Assert
        assertThrows(IdNotFoundException.class, () -> salaryPaymentService.getSalaryPaymentById(paymentId));

        verify(salaryPaymentRepository, times(1)).findById(paymentId);
    }

    @Test
    void deleteSalaryPayment_ExistingSalaryPaymentId_ReturnsDeletedPaymentDto() {

        // Arrange
        long paymentId = 1L;
        Employee employee = new Employee(1L,"raman","raman.kumar@avisoft.io", "password", "Employee", null,null,null,null);

        SalaryPayment salaryPayment = new SalaryPayment(1L, employee, 5000, Date.valueOf("2024-05-13"));

        // Act
        when(salaryPaymentRepository.findById(paymentId)).thenReturn(Optional.of(salaryPayment));
        doNothing().when(salaryPaymentRepository).deleteById(paymentId);
        employee = new Employee(1L,"raman","raman.kumar@avisoft.io", "password", "Employee", null,null,null,null);

        when(objectMapper.convertValue(salaryPayment, SalaryPaymentDto.class)).thenReturn(new SalaryPaymentDto(paymentId, employee.getId(), 5000, Date.valueOf("2024-05-13")));

        SalaryPaymentDto deletedSalaryPaymentDto = salaryPaymentService.deleteSalaryPayment(paymentId);

        // Assert
        assertNotNull(deletedSalaryPaymentDto);
        assertEquals(paymentId, deletedSalaryPaymentDto.getPaymentId());

        verify(salaryPaymentRepository, times(1)).findById(paymentId);
        verify(salaryPaymentRepository, times(1)).deleteById(paymentId);
    }

    @Test
    void deleteSalaryPayment_NonExistingSalaryPaymentId_ThrowsIdNotFoundException() {

        // Arrange
        long paymentId = 1L;

        // Act
        when(salaryPaymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        // Assert
        assertThrows(IdNotFoundException.class, () -> salaryPaymentService.deleteSalaryPayment(paymentId));

        verify(salaryPaymentRepository, times(1)).findById(paymentId);
        verify(salaryPaymentRepository, never()).deleteById(anyLong());
    }
}
