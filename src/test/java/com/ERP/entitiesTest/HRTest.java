package com.ERP.entitiesTest;

import com.ERP.entities.HR;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class HRTest {
    @Test
    void testConstructorAndGetters() {

        // Arrange
        long hrId = 1L;
        String name = "Raman";
        String password = "raman|123|";
        String role = "senior HR";

        // Act
        HR hr = new HR(hrId, name, password, role);

        // Assert
        assertEquals(hrId, hr.getHrId());
        assertEquals(name, hr.getName());
        assertEquals(password, hr.getPassword());
        assertEquals(role, hr.getRole());

    }

    @Test
    void testSetters() {

        // Arrange
        long hrId = 1L;
        String name = "Raman";
        String password = "raman|123|";
        String role = "senior HR";

        // Act
        HR hr = new HR();
        hr.setHrId(hrId);
        hr.setName(name);
        hr.setPassword(password);
        hr.setRole(role);

        // Assert
        assertEquals(hrId, hr.getHrId());
        assertEquals(name, hr.getName());
        assertEquals(password, hr.getPassword());
        assertEquals(role, hr.getRole());

    }
}
