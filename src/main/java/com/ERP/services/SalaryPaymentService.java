package com.ERP.services;

import com.ERP.dtos.SalaryPaymentDto;
import com.ERP.entities.Employee;
import com.ERP.entities.SalaryPayment;
import com.ERP.exceptions.IdNotFoundException;
import com.ERP.repositories.EmployeeRepository;
import com.ERP.repositories.SalaryPaymentRepository;
import com.ERP.servicesInter.SalaryPaymentServiceInter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SalaryPaymentService implements SalaryPaymentServiceInter {
    @Autowired
    private SalaryPaymentRepository salaryPaymentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public SalaryPaymentDto createSalaryPayment(SalaryPaymentDto salaryPaymentDto) {
        try {
            SalaryPayment newSalaryPayment = SalaryPayment.builder()
                    .amount(salaryPaymentDto.getAmount()).paymentDate(salaryPaymentDto.getPaymentDate()).build();
            SalaryPayment savedSalaryPayment = salaryPaymentRepository.save(newSalaryPayment);

            Employee employee = employeeRepository.findById(salaryPaymentDto.getEmployeeId())
                    .orElseThrow(() -> new IdNotFoundException("Employee not found with ID: " + salaryPaymentDto.getEmployeeId()));

            newSalaryPayment.setEmployee(employee);
            SalaryPayment salaryPayment = salaryPaymentRepository.save(newSalaryPayment);
            salaryPaymentDto.setPaymentId(salaryPayment.getPaymentId());

            return salaryPaymentDto;

        } catch (Exception e) {
            throw new IdNotFoundException("Error adding SalaryPayment: " + e.getMessage());
        }
    }

    @Override
    public SalaryPaymentDto updateSalaryPayment(long paymentId, SalaryPaymentDto salaryPaymentDto) {
        try {
            SalaryPayment salaryPaymentToUpdate = salaryPaymentRepository.findById(paymentId)
                    .orElseThrow(() -> new IdNotFoundException("Salary Payment not found with id: " + paymentId));

            if (Objects.nonNull(salaryPaymentDto.getAmount())) {
                salaryPaymentToUpdate.setAmount(salaryPaymentDto.getAmount());
            }
            if (Objects.nonNull(salaryPaymentDto.getPaymentDate())) {
                salaryPaymentToUpdate.setPaymentDate(salaryPaymentDto.getPaymentDate());
            }

            Employee employee = employeeRepository.findById(salaryPaymentDto.getEmployeeId())
                    .orElseThrow(() -> new IdNotFoundException("Employee not found with ID: " + salaryPaymentDto.getEmployeeId()));

            salaryPaymentToUpdate.setEmployee(employee);
            SalaryPayment updatedSalaryPayment = salaryPaymentRepository.save(salaryPaymentToUpdate);
            salaryPaymentDto.setPaymentId(updatedSalaryPayment.getPaymentId());
            return salaryPaymentDto;
        } catch (Exception e) {
            throw new IdNotFoundException("Error updating Salary Payment: " + e.getMessage());
        }
    }

    @Override
    public List<SalaryPaymentDto> getAllSalaryPayment() {
        try {
            List<SalaryPayment> salaryPaymentList = salaryPaymentRepository.findAll();
            List<SalaryPaymentDto> salaryPaymentDtoList = new ArrayList<>();
            for (SalaryPayment salaryPayment : salaryPaymentList) {
                SalaryPaymentDto salaryPaymentDto = SalaryPaymentDto.builder()
                        .paymentId(salaryPayment.getPaymentId()).employeeId(salaryPayment.getEmployee().getId())
                        .amount(salaryPayment.getAmount()).paymentDate(salaryPayment.getPaymentDate()).build();

                salaryPaymentDtoList.add(salaryPaymentDto);
            }
            return salaryPaymentDtoList;
        } catch (Exception e) {
            throw new IdNotFoundException("Error finding all tasks: " + e.getMessage());
        }
    }

    @Override
    public SalaryPaymentDto getSalaryPaymentById(long paymentId) {
        try {
            SalaryPayment foundSalaryPayment = salaryPaymentRepository.findById(paymentId)
                    .orElseThrow(() -> new IdNotFoundException("Salary Payment not found with id: " + paymentId));
            SalaryPaymentDto salaryPaymentDto = SalaryPaymentDto.builder()
                    .paymentId(foundSalaryPayment.getPaymentId()).employeeId(foundSalaryPayment.getEmployee().getId())
                    .amount(foundSalaryPayment.getAmount()).paymentDate(foundSalaryPayment.getPaymentDate()).build();

            return salaryPaymentDto;
        } catch (Exception e) {
            throw new IdNotFoundException("Error finding Salary Payment: " + e.getMessage());
        }
    }

    @Override
    public SalaryPaymentDto deleteSalaryPayment(long paymentId) {
        try {
            SalaryPayment salaryPaymentToDelete = salaryPaymentRepository.findById(paymentId)
                    .orElseThrow(() -> new IdNotFoundException("Salary Payment not found with id: " + paymentId));
            salaryPaymentRepository.deleteById(paymentId);

            SalaryPaymentDto salaryPaymentDto = SalaryPaymentDto.builder()
                    .paymentId(salaryPaymentToDelete.getPaymentId()).employeeId(salaryPaymentToDelete.getEmployee().getId())
                    .amount(salaryPaymentToDelete.getAmount()).paymentDate(salaryPaymentToDelete.getPaymentDate()).build();

            return salaryPaymentDto;
        } catch (Exception e) {
            throw new IdNotFoundException("Error deleting Salary Payment: " + e.getMessage());
        }
    }
}
