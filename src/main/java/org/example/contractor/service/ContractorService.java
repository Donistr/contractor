package org.example.contractor.service;

import org.example.contractor.dto.ContractorDTO;
import org.example.contractor.messages.SearchContractorRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContractorService {

    ContractorDTO save(ContractorDTO contractorDTO);

    ContractorDTO getById(String id);

    void deleteById(String id);

    List<ContractorDTO> getContractors(SearchContractorRequest request, Pageable pageable);

}
