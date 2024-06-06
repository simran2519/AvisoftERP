package com.ERP.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryStructureDto {

    private long structureId;

    @NotNull(message = "Role Cannot be Null")
    private String role;

    private String level;

    @NotNull(message = "BaseSalary Cannot be Null")
    private double baseSalary;
}
