package org.example.contractor.repository;

import org.example.contractor.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndustryRepository extends JpaRepository<Industry, Integer> {
}
