package org.example.contractor.repository;

import jakarta.transaction.Transactional;
import org.example.contractor.entity.Country;
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
public class CountryRepositoryTest {

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
    private CountryRepository repository;

    @Test
    public void getAllCountriesTest() {
        List<Country> countries = repository.findAll();
        Assertions.assertEquals(countries.size(), 3);
        Assertions.assertEquals(countries.get(0).getId(), "ABH");
        Assertions.assertEquals(countries.get(0).getName(), "Абхазия");
        Assertions.assertEquals(countries.get(1).getId(), "ABW");
        Assertions.assertEquals(countries.get(1).getName(), "Аруба");
    }

    @Test
    public void getCountryByIdTest() {
        Optional<Country> country = repository.findById("ABH");
        Assertions.assertTrue(country.isPresent());
        Assertions.assertEquals(country.get().getId(), "ABH");
        Assertions.assertEquals(country.get().getName(), "Абхазия");
    }

    @Test
    @Transactional
    @Rollback
    public void createCountryTest() {
        Country country = Country.builder()
                .id("AAA")
                .name("aaa")
                .build();
        Country saved = repository.saveAndFlush(country);
        Assertions.assertEquals(country.getId(), saved.getId());
        Assertions.assertEquals(country.getName(), saved.getName());
    }

    @Test
    @Transactional
    @Rollback
    public void updateCountryTest() {
        Country country = repository.findById("ABH").orElseThrow();
        country.setName("123");
        Country saved = repository.saveAndFlush(country);
        Assertions.assertEquals(country.getId(), saved.getId());
        Assertions.assertEquals(country.getName(), saved.getName());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteCountryTest() {
        Country country = Country.builder()
                .id("AAA")
                .name("aaa")
                .build();
        repository.saveAndFlush(country);
        List<Country> countries = repository.findAll();
        Assertions.assertEquals(countries.size(), 4);
        repository.deleteById("AAA");
        countries = repository.findAll();
        Assertions.assertEquals(countries.size(), 3);
    }

}
