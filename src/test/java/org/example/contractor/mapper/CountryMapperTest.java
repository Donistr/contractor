package org.example.contractor.mapper;

import org.example.contractor.dto.CountryDTO;
import org.example.contractor.entity.Country;
import org.example.contractor.mapper.impl.CountryMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class CountryMapperTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public CountryMapper countryMapper() {
            return new CountryMapperImpl();
        }
    }

    @Autowired
    private CountryMapper countryMapper;

    @Test
    public void mapCountryToDTOTest() {
        CountryDTO countryDTO = CountryDTO.builder()
                .id("RUS")
                .name("name")
                .build();

        Country country = countryMapper.map(countryDTO);

        Assertions.assertEquals(countryDTO.getId(), country.getId());
        Assertions.assertEquals(countryDTO.getName(), country.getName());
    }

    @Test
    public void mapDTOToCountryTest() {
        Country country = Country.builder()
                .id("RUS")
                .name("name")
                .build();

        CountryDTO countryDTO = countryMapper.map(country);

        Assertions.assertEquals(country.getId(), countryDTO.getId());
        Assertions.assertEquals(country.getName(), countryDTO.getName());
    }

}
