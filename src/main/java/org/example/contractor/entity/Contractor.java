package org.example.contractor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.ZonedDateTime;

/**
 * Entity представляющее контрагента
 */
@Entity
@Table(name = "contractor")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "active_main_borrower", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean activeMainBorrower = false;

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
