package com.ERP.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(uniqueConstraints = @UniqueConstraint(
        name = "username_unique",
        columnNames = "username"))
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String username;
    private String password;
    private String role;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
    private List<Task>task = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="department_id")
    @JsonBackReference
    private Department department;

    @OneToOne(mappedBy = "employee",cascade = CascadeType.ALL)
    private SalaryPayment salaryPayment;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
    private List<Leaves> leaves;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL, orphanRemoval = true)
    List<Project> projectSet = new ArrayList<>();
}
