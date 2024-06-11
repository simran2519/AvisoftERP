package com.ERP.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SalaryPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private double amount;

    @Column(name = "payment_date")
    private Date paymentDate;
}
