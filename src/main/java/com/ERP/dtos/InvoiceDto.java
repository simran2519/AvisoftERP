package com.ERP.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceDto
{
    private long invoiceId;
    private double amount;
    private Date invoiceDate;
    @Size(min=1,max = 50,message = "min characters are 3 and maximum characters can be upto 50")
    @Pattern(regexp = "^[^\\s].*$", message = "payment status cannot be empty")
    private String paymentStatus;
}
