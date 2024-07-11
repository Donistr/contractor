package org.example.contractor.mapper.impl;

import org.example.contractor.dto.CountryDTO;
import org.example.contractor.entity.Country;
import org.example.contractor.mapper.CountryMapper;
import org.springframework.stereotype.Component;

@Component
public class CountryMapperImpl implements CountryMapper {

    @Override
    public Country map(CountryDTO countryDTO) {
        return Country.builder()
                .id(countryDTO.getId())
                .name(countryDTO.getName())
                .build();
    }

    @Override
    public CountryDTO map(Country country) {
        return CountryDTO.builder()
                .id(country.getId())
                .name(country.getName())
                .build();
    }

}
