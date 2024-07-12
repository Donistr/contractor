package org.example.contractor.mapper;

import org.example.contractor.dto.IndustryDTO;
import org.example.contractor.entity.Industry;
import org.example.contractor.mapper.impl.IndustryMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class IndustryMapperTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public IndustryMapper industryMapper() {
            return new IndustryMapperImpl();
        }
    }

    @Autowired
    private IndustryMapper industryMapper;

    @Test
    public void mapIndustryToDTOTest() {
        IndustryDTO industryDTO = IndustryDTO.builder()
                .id(1)
                .name("name")
                .build();

        Industry industry = industryMapper.map(industryDTO);

        Assertions.assertEquals(industryDTO.getId(), industry.getId());
        Assertions.assertEquals(industryDTO.getName(), industry.getName());
    }

    @Test
    public void mapDTOToIndustryTest() {
        Industry industry = Industry.builder()
                .id(1)
                .name("name")
                .build();

        IndustryDTO industryDTO = industryMapper.map(industry);

        Assertions.assertEquals(industry.getId(), industryDTO.getId());
        Assertions.assertEquals(industry.getName(), industryDTO.getName());
    }

}
