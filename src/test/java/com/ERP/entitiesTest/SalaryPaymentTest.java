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
    void testConstructorAndGetters() {
        // Arrange
        long paymentId = 1L;
        Employee employee = new Employee();
        double amount = 5000;
        Date paymentDate = new Date();

        // Act
        SalaryPayment salaryPayment = new SalaryPayment(paymentId, employee, amount, paymentDate);

        // Assert
        assertEquals(paymentId, salaryPayment.getPaymentId());
        assertEquals(employee, salaryPayment.getEmployee());
        assertEquals(amount, salaryPayment.getAmount());
        assertEquals(paymentDate, salaryPayment.getPaymentDate());
    }

    @Test
    void testSetters() {
        // Arrange
        SalaryPayment salaryPayment = new SalaryPayment();
        long paymentId = 1L;
        Employee employee = new Employee();
        double amount = 5000;
        Date paymentDate = new Date();

        // Act
        salaryPayment.setPaymentId(paymentId);
        salaryPayment.setEmployee(employee);
        salaryPayment.setAmount(amount);
        salaryPayment.setPaymentDate(paymentDate);

        // Assert
        assertEquals(paymentId, salaryPayment.getPaymentId());
        assertEquals(employee, salaryPayment.getEmployee());
        assertEquals(amount, salaryPayment.getAmount());
        assertEquals(paymentDate, salaryPayment.getPaymentDate());
    }
}
