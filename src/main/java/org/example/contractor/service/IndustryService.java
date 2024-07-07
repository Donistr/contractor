package org.example.contractor.service;

import org.example.contractor.dto.IndustryDTO;

import java.util.List;

public interface IndustryService {

    List<IndustryDTO> getAll();

    IndustryDTO getById(Integer id);

    IndustryDTO save(IndustryDTO countryDTO);

    void deleteById(Integer id);

}
