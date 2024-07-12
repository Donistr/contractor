package org.example.contractor.repository;

import jakarta.transaction.Transactional;
import org.example.contractor.dto.CountryDTO;
import org.example.contractor.dto.IndustryDTO;
import org.example.contractor.messages.SearchContractorRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ContractorRepositorySqlTest {

    @Container
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("DB_URL", postgreSQLContainer::getJdbcUrl);
        registry.add("DB_USERNAME", postgreSQLContainer::getUsername);
        registry.add("DB_PASSWORD", postgreSQLContainer::getPassword);
    }

    @Autowired
    private ContractorSqlRepository repository;

    @Test
    @Transactional
    @Rollback
    public void searchContractorsSqlTest() {
        SearchContractorRequest request = new SearchContractorRequest();
        request.setFullName("name_full_2");
        request.setInn("inn_2");
        CountryDTO countryDTO = CountryDTO.builder()
                .name("руб")
                .build();
        request.setCountry(countryDTO);
        IndustryDTO industryDTO = IndustryDTO.builder()
                .id(2)
                .name("Автомобилестроение")
                .build();
        request.setIndustry(industryDTO);

        Assertions.assertEquals(repository.findAll(request, Pageable.ofSize(10)).size(), 1);
    }

}
