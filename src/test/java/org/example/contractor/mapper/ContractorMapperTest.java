package org.example.contractor.mapper;

import org.example.contractor.dto.ContractorDTO;
import org.example.contractor.dto.CountryDTO;
import org.example.contractor.dto.IndustryDTO;
import org.example.contractor.dto.OrgFormDTO;
import org.example.contractor.entity.Contractor;
import org.example.contractor.entity.Country;
import org.example.contractor.entity.Industry;
import org.example.contractor.entity.OrgForm;
import org.example.contractor.mapper.impl.ContractorMapperImpl;
import org.example.contractor.mapper.impl.CountryMapperImpl;
import org.example.contractor.mapper.impl.IndustryMapperImpl;
import org.example.contractor.mapper.impl.OrgFormMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class ContractorMapperTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public CountryMapper countryMapper() {
            return new CountryMapperImpl();
        }

        @Bean
        public IndustryMapper industryMapper() {
            return new IndustryMapperImpl();
        }

        @Bean
        public OrgFormMapper orgFormMapper() {
            return new OrgFormMapperImpl();
        }

        @Bean
        public ContractorMapper contractorMapper() {
            return new ContractorMapperImpl(countryMapper(), industryMapper(), orgFormMapper());
        }
    }

    @Autowired
    private ContractorMapper contractorMapper;

    @Test
    public void mapContractorToDTOTest() {
        CountryDTO countryDTO = CountryDTO.builder()
                .id("RUS")
                .name("name")
                .build();

        IndustryDTO industryDTO = IndustryDTO.builder()
                .id(1)
                .name("name")
                .build();

        OrgFormDTO orgFormDTO = OrgFormDTO.builder()
                .id(1)
                .name("name")
                .build();

        ContractorDTO contractorDTO = ContractorDTO.builder()
                .id("id")
                .parentId("parentId")
                .name("name")
                .fullName("fullName")
                .inn("inn")
                .ogrn("ogrn")
                .country(countryDTO)
                .industry(industryDTO)
                .orgForm(orgFormDTO)
                .build();

        Contractor contractor = contractorMapper.map(contractorDTO);

        Assertions.assertEquals(contractorDTO.getId(), contractor.getId());
        Assertions.assertEquals(contractorDTO.getParentId(), contractor.getParentId());
        Assertions.assertEquals(contractorDTO.getName(), contractor.getName());
        Assertions.assertEquals(contractorDTO.getFullName(), contractor.getFullName());
        Assertions.assertEquals(contractorDTO.getInn(), contractor.getInn());
        Assertions.assertEquals(contractorDTO.getOgrn(), contractor.getOgrn());
        Assertions.assertEquals(contractorDTO.getCountry().getId(), contractor.getCountry().getId());
        Assertions.assertEquals(contractorDTO.getCountry().getName(), contractor.getCountry().getName());
        Assertions.assertEquals(contractorDTO.getIndustry().getId(), contractor.getIndustry().getId());
        Assertions.assertEquals(contractorDTO.getIndustry().getName(), contractor.getIndustry().getName());
        Assertions.assertEquals(contractorDTO.getOrgForm().getId(), contractor.getOrgForm().getId());
        Assertions.assertEquals(contractorDTO.getOrgForm().getName(), contractor.getOrgForm().getName());
    }

    @Test
    public void mapDTOToContractorTest() {
        Country country = Country.builder()
                .id("RUS")
                .name("name")
                .build();

        Industry industry = Industry.builder()
                .id(1)
                .name("name")
                .build();

        OrgForm orgForm = OrgForm.builder()
                .id(1)
                .name("name")
                .build();

        Contractor contractor = Contractor.builder()
                .id("id")
                .parentId("parentId")
                .name("name")
                .fullName("fullName")
                .inn("inn")
                .ogrn("ogrn")
                .country(country)
                .industry(industry)
                .orgForm(orgForm)
                .build();

        ContractorDTO contractorDTO = contractorMapper.map(contractor);

        Assertions.assertEquals(contractorDTO.getId(), contractor.getId());
        Assertions.assertEquals(contractorDTO.getParentId(), contractor.getParentId());
        Assertions.assertEquals(contractorDTO.getName(), contractor.getName());
        Assertions.assertEquals(contractorDTO.getFullName(), contractor.getFullName());
        Assertions.assertEquals(contractorDTO.getInn(), contractor.getInn());
        Assertions.assertEquals(contractorDTO.getOgrn(), contractor.getOgrn());
        Assertions.assertEquals(contractorDTO.getCountry().getId(), contractor.getCountry().getId());
        Assertions.assertEquals(contractorDTO.getCountry().getName(), contractor.getCountry().getName());
        Assertions.assertEquals(contractorDTO.getIndustry().getId(), contractor.getIndustry().getId());
        Assertions.assertEquals(contractorDTO.getIndustry().getName(), contractor.getIndustry().getName());
        Assertions.assertEquals(contractorDTO.getOrgForm().getId(), contractor.getOrgForm().getId());
        Assertions.assertEquals(contractorDTO.getOrgForm().getName(), contractor.getOrgForm().getName());
    }

}
