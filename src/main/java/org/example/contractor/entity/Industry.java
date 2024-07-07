package org.example.contractor.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "industry")
@Data
public class Industry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

}
