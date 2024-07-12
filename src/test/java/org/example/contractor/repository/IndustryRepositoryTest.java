package org.example.contractor.repository;

import jakarta.transaction.Transactional;
import org.example.contractor.entity.Industry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Testcontainers
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class IndustryRepositoryTest {

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
    private IndustryRepository repository;

    @Test
    public void getAllIndustriesTest() {
        List<Industry> industries = repository.findAll();
        Assertions.assertEquals(industries.size(), 3);
        Assertions.assertEquals(industries.get(0).getId(), 1);
        Assertions.assertEquals(industries.get(0).getName(), "Авиастроение");
        Assertions.assertEquals(industries.get(1).getId(), 2);
        Assertions.assertEquals(industries.get(1).getName(), "Автомобилестроение");
    }

    @Test
    public void getIndustryByIdTest() {
        Optional<Industry> industry = repository.findById(1);
        Assertions.assertTrue(industry.isPresent());
        Assertions.assertEquals(industry.get().getId(), 1);
        Assertions.assertEquals(industry.get().getName(), "Авиастроение");
    }

    @Test
    @Transactional
    @Rollback
    public void createIndustryTest() {
        Industry industry = Industry.builder()
                .name("aaa")
                .build();
        Industry saved = repository.saveAndFlush(industry);
        Assertions.assertEquals(industry.getId(), saved.getId());
        Assertions.assertEquals(industry.getName(), saved.getName());
    }

    @Test
    @Transactional
    @Rollback
    public void updateIndustryTest() {
        Industry industry = repository.findById(1).orElseThrow();
        industry.setName("123");
        Industry saved = repository.saveAndFlush(industry);
        Assertions.assertEquals(industry.getId(), saved.getId());
        Assertions.assertEquals(industry.getName(), saved.getName());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteIndustryTest() {
        Industry industry = Industry.builder()
                .name("aaa")
                .build();
        repository.saveAndFlush(industry);
        List<Industry> industries = repository.findAll();
        Assertions.assertEquals(industries.size(), 4);
        repository.deleteById(3);
        industries = repository.findAll();
        Assertions.assertEquals(industries.size(), 3);
    }

}
