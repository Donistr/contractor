package org.example.contractor.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "org_form")
@Data
public class OrgForm {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

}
