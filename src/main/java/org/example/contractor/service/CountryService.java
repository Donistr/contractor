package org.example.contractor.service;

import org.example.contractor.dto.CountryDTO;

import java.util.List;

public interface CountryService {

    List<CountryDTO> getAll();

    CountryDTO getById(String id);

    CountryDTO save(CountryDTO countryDTO);

    void deleteById(String id);

}
