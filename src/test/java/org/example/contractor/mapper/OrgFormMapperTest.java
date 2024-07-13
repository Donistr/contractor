package org.example.contractor.mapper;

import org.example.contractor.dto.OrgFormDTO;
import org.example.contractor.entity.OrgForm;
import org.example.contractor.mapper.impl.OrgFormMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class OrgFormMapperTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public OrgFormMapper orgFormMapper() {
            return new OrgFormMapperImpl();
        }
    }

    @Autowired
    private OrgFormMapper orgFormMapper;

    @Test
    public void mapIndustryToDTOTest() {
        OrgFormDTO orgFormDTO = OrgFormDTO.builder()
                .id(1)
                .name("name")
                .build();

        OrgForm orgForm = orgFormMapper.map(orgFormDTO);

        Assertions.assertEquals(orgFormDTO.getId(), orgForm.getId());
        Assertions.assertEquals(orgFormDTO.getName(), orgForm.getName());
    }

    @Test
    public void mapDTOToIndustryTest() {
        OrgForm orgForm = OrgForm.builder()
                .id(1)
                .name("name")
                .build();

        OrgFormDTO orgFormDTO = orgFormMapper.map(orgForm);

        Assertions.assertEquals(orgForm.getId(), orgFormDTO.getId());
        Assertions.assertEquals(orgForm.getName(), orgFormDTO.getName());
    }

}
