package com.ERP.entitiesTest;


import com.ERP.entities.HR;
import com.ERP.entities.SalaryStructure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SalaryStructureTest {

    @Test
    void testConstructorAndGetters() {

        // Arrange
        long structureId = 1L;
        String role = "Raman";
        String level = "senior Backend Developer";
        double baseSalary = 15500.00;

        // Act
        SalaryStructure salaryStructure = new SalaryStructure(structureId, role, level, baseSalary);

        // Assert
        assertEquals(structureId, salaryStructure.getStructureId());
        assertEquals(role, salaryStructure.getRole());
        assertEquals(level, salaryStructure.getLevel());
        assertEquals(baseSalary, salaryStructure.getBaseSalary());

    }

    @Test
    void testSetters() {

        // Arrange
        long structureId = 1L;
        String role = "Raman";
        String level = "senior Backend Developer";
        double baseSalary = 15500.00;

        // Act
        SalaryStructure salaryStructure = new SalaryStructure();
        salaryStructure.setStructureId(structureId);
        salaryStructure.setLevel(level);
        salaryStructure.setRole(role);
        salaryStructure.setBaseSalary(baseSalary);

        // Assert
        assertEquals(structureId, salaryStructure.getStructureId());
        assertEquals(role, salaryStructure.getRole());
        assertEquals(level, salaryStructure.getLevel());
        assertEquals(baseSalary, salaryStructure.getBaseSalary());

    }
}
