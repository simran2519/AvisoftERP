package com.ERP.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String role;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
    private List<Task>task = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="department_id")
    private Department department;

    @OneToOne(mappedBy = "employee",cascade = CascadeType.ALL)
    private SalaryPayment salaryPayment;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
    private List<Leaves> leaves;

}
