package org.example.contractor.mapper;

import org.example.contractor.dto.CountryDTO;
import org.example.contractor.entity.Country;

public interface CountryMapper {

    Country map(CountryDTO countryDTO);

    CountryDTO map(Country country);

}
