package com.ERP.entitiesTest;

import com.ERP.entities.Employee;
import com.ERP.entities.SalaryPayment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SalaryPaymentTest {
    @Test
    void testGetPaymentId() {
        // Arrange
        long paymentId = 1L;
        SalaryPayment salaryPayment = new SalaryPayment();
        salaryPayment.setPaymentId(paymentId);

        // Act
        long retrievedPaymentId = salaryPayment.getPaymentId();

        // Assert
        assertEquals(paymentId, retrievedPaymentId);
    }

    @Test
    void testGetEmployee() {
        // Arrange
        Employee employee = new Employee();
        SalaryPayment salaryPayment = new SalaryPayment();
        salaryPayment.setEmployee(employee);

        // Act
        Employee retrievedEmployee = salaryPayment.getEmployee();

        // Assert
        assertEquals(employee, retrievedEmployee);
    }

    @Test
    void testGetAmount() {
        // Arrange
        double amount = 5000;
        SalaryPayment salaryPayment = new SalaryPayment();
        salaryPayment.setAmount(amount);

        // Act
        double retrievedAmount = salaryPayment.getAmount();

        // Assert
        assertEquals(amount, retrievedAmount);
    }

    @Test
    void testGetPaymentDate() {
        // Arrange
        Date paymentDate = new Date();
        SalaryPayment salaryPayment = new SalaryPayment();
        salaryPayment.setPaymentDate(paymentDate);

        // Act
        Date retrievedPaymentDate = salaryPayment.getPaymentDate();

        // Assert
        assertEquals(paymentDate, retrievedPaymentDate);
    }

    @Test
    void testGetters() {
        // Arrange
        long paymentId = 1L;
        Employee employee = new Employee();
        double amount = 5000;
        Date paymentDate = new Date();
        SalaryPayment salaryPayment = new SalaryPayment(paymentId, employee, amount, paymentDate);

        // Act and Assert
        assertEquals(paymentId, salaryPayment.getPaymentId());
        assertEquals(employee, salaryPayment.getEmployee());
        assertEquals(amount, salaryPayment.getAmount());
        assertEquals(paymentDate, salaryPayment.getPaymentDate());
    }
}
