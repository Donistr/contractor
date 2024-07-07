package org.example.contractor.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.ZonedDateTime;

@Entity
@Table(name = "contractor")
@Data
public class Contractor {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "name_full")
    private String fullName;

    @Column(name = "inn")
    private String inn;

    @Column(name = "ogrn")
    private String ogrn;

    @ManyToOne
    @JoinColumn(name = "country")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "industry")
    private Industry industry;

    @ManyToOne
    @JoinColumn(name = "org_form")
    private OrgForm orgForm;

    @Column(name = "create_date", nullable = false, updatable = false)
    private ZonedDateTime createDate;

    @Column(name = "modify_date")
    private ZonedDateTime modifyDate;

    @CreatedBy
    @Column(name = "create_user_id", nullable = false, updatable = false)
    private String createUserId;

    @LastModifiedBy
    @Column(name = "modify_user_id")
    private String modifyUserId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

}
