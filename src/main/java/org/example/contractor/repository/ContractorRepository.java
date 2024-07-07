package org.example.contractor.repository;

import org.example.contractor.entity.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContractorRepository extends JpaRepository<Contractor, String>,
        JpaSpecificationExecutor<Contractor> {
}
