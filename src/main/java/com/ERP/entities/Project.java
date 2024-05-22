package com.ERP.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="project")
public class Project
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long projectId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Date startDate;
    @Column(nullable = false)
    private Date endDate;
    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "assignTo", cascade = CascadeType.ALL)
    private Set<Task> taskSet= new HashSet<>();

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL)
    private Set<Invoice> invoiceSet= new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;

    @ManyToOne
    @JoinColumn(name ="departmentId")
    private Department department;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="project_assets",joinColumns = @JoinColumn(name="projectId", referencedColumnName = "projectId"),
               inverseJoinColumns = @JoinColumn(name="assetId", referencedColumnName = "assetId"))
    private Set<Asset> assetSet = new HashSet<>();
}
