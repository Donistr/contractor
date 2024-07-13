package org.example.contractor.mapper;

import org.example.contractor.dto.ContractorDTO;
import org.example.contractor.entity.Contractor;

/**
 * Класс предназначенный для преобразования Contractor в ContractorDTO и наоборот
 */
public interface ContractorMapper {

    /**
     * Преобразует ContractorDTO в Contractor
     * @param contractorDTO ContractorDTO
     * @return Contractor
     */
    Contractor map(ContractorDTO contractorDTO);

    /**
     * Преобразует Contractor в ContractorDTO
     * @param contractor Contractor
     * @return ContractorDTO
     */
    ContractorDTO map(Contractor contractor);

}
