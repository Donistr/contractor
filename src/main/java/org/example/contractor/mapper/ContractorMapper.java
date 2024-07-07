package org.example.contractor.mapper;

import org.example.contractor.dto.ContractorDTO;
import org.example.contractor.entity.Contractor;

public interface ContractorMapper {

    Contractor map(ContractorDTO contractorDTO);

    ContractorDTO map(Contractor contractor);

}
