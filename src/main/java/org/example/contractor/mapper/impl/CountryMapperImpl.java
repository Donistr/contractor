package org.example.contractor.mapper.impl;

import org.example.contractor.dto.CountryDTO;
import org.example.contractor.entity.Country;
import org.example.contractor.mapper.CountryMapper;
import org.springframework.stereotype.Component;

@Component
public class CountryMapperImpl implements CountryMapper {

    @Override
    public Country map(CountryDTO countryDTO) {
        Country result = new Country();
        result.setId(countryDTO.getId());
        result.setName(countryDTO.getName());

        return result;
    }

    @Override
    public CountryDTO map(Country country) {
        CountryDTO result = new CountryDTO();
        result.setId(country.getId());
        result.setName(country.getName());

        return result;
    }

}
