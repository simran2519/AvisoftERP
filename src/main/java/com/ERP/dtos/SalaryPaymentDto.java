package com.ERP.dtos;

import com.ERP.entities.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalaryPaymentDto {
    private long paymentId;

    private long employeeId;

    private double amount;

    @NotNull(message = "Payment date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date paymentDate;
}
