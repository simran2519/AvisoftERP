package com.ERP.repositoriesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.ERP.entities.Employee;
import com.ERP.entities.SalaryPayment;
import com.ERP.repositories.SalaryPaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Use a separate test database
public class SalaryPaymentTestRepository {
    private SalaryPaymentRepository salaryPaymentRepository;

    SalaryPayment salaryPayment1 = new SalaryPayment();
    SalaryPayment salaryPayment2 = new SalaryPayment();
    Employee employee = new Employee();

    @BeforeEach
    void setUp() {
        // Create a mock instance of the repository interface
        salaryPaymentRepository = mock(SalaryPaymentRepository.class);

        employee.setId(1L);

        salaryPayment1.setPaymentId(1L);
        salaryPayment1.setAmount(5000);
        salaryPayment1.setPaymentDate(Date.valueOf("2024-05-13"));
        salaryPayment1.setEmployee(employee);

        salaryPayment2.setPaymentId(1L);
        salaryPayment2.setAmount(5000);
        salaryPayment2.setPaymentDate(Date.valueOf("2024-05-13"));
        salaryPayment2.setEmployee(employee);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<SalaryPayment> salaryPaymentList = new ArrayList<>();
        Employee employee = new Employee();
        employee.setId(1L);

        salaryPaymentList.add(salaryPayment1);
        salaryPaymentList.add(salaryPayment2);

        // Mock the behavior of the repository method
        when(salaryPaymentRepository.findAll()).thenReturn(salaryPaymentList);

        // Act
        List<SalaryPayment> result = salaryPaymentRepository.findAll();

        // Assert
        assertEquals(salaryPaymentList, result);
    }

    @Test
    void testFindById() {
        // Arrange
        long paymentId = 1L;
        SalaryPayment salaryPayment = new SalaryPayment();

        // Set up mock behavior
        when(salaryPaymentRepository.findById(paymentId)).thenReturn(Optional.of(salaryPayment));

        // Act
        Optional<SalaryPayment> result = salaryPaymentRepository.findById(paymentId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(salaryPayment, result.get());
    }

    @Test
    void testSave() {
        // Arrange
        SalaryPayment salaryPaymentToSave = salaryPayment1;
        SalaryPayment savedSalaryPayment = salaryPayment1;

        // Mock the behavior of the repository method
        when(salaryPaymentRepository.save(salaryPaymentToSave)).thenReturn(savedSalaryPayment);

        // Act
        SalaryPayment result = salaryPaymentRepository.save(salaryPaymentToSave);

        // Assert
        assertEquals(savedSalaryPayment, result);
    }

    @Test
    void testDelete() {
        // Arrange
        long paymentIdToDelete = 1L;

        // Mock the behavior of the repository method
        doNothing().when(salaryPaymentRepository).deleteById(paymentIdToDelete);

        // Act
        salaryPaymentRepository.deleteById(paymentIdToDelete);

        // Assert
        // No explicit assertion needed for void methods, verify() can be used instead
        verify(salaryPaymentRepository, times(1)).deleteById(paymentIdToDelete);
    }

}
