package org.example.contractor.service;

import org.example.contractor.dto.ContractorDTO;

public interface ContractorService {

    ContractorDTO save(ContractorDTO contractorDTO);

    ContractorDTO getById(String id);

    void deleteById(String id);

}
