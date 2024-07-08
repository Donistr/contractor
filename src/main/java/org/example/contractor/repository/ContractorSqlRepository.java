package org.example.contractor.repository;

import org.example.contractor.dto.ContractorDTO;
import org.example.contractor.messages.SearchContractorRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContractorSqlRepository {

    List<ContractorDTO> findAll(SearchContractorRequest request, Pageable pageable);

}
