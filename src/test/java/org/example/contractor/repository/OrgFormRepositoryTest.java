package org.example.contractor.repository;

import jakarta.transaction.Transactional;
import org.example.contractor.entity.OrgForm;
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
public class OrgFormRepositoryTest {

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
    private OrgFormRepository repository;

    @Test
    public void getAllOrgFormsTest() {
        List<OrgForm> orgForms = repository.findAll();
        Assertions.assertEquals(orgForms.size(), 3);
        Assertions.assertEquals(orgForms.get(0).getId(), 1);
        Assertions.assertEquals(orgForms.get(0).getName(), "-");
        Assertions.assertEquals(orgForms.get(1).getId(), 2);
        Assertions.assertEquals(orgForms.get(1).getName(), "Автономная некоммерческая организация");
    }

    @Test
    public void getOrgFormByIdTest() {
        Optional<OrgForm> orgForm = repository.findById(1);
        Assertions.assertTrue(orgForm.isPresent());
        Assertions.assertEquals(orgForm.get().getId(), 1);
        Assertions.assertEquals(orgForm.get().getName(), "-");
    }

    @Test
    @Transactional
    @Rollback
    public void createOrgFormTest() {
        OrgForm orgForm = OrgForm.builder()
                .name("aaa")
                .build();
        OrgForm saved = repository.saveAndFlush(orgForm);
        Assertions.assertEquals(orgForm.getId(), saved.getId());
        Assertions.assertEquals(orgForm.getName(), saved.getName());
    }

    @Test
    @Transactional
    @Rollback
    public void updateOrgFormTest() {
        OrgForm orgForm = repository.findById(1).orElseThrow();
        orgForm.setName("123");
        OrgForm saved = repository.saveAndFlush(orgForm);
        Assertions.assertEquals(orgForm.getId(), saved.getId());
        Assertions.assertEquals(orgForm.getName(), saved.getName());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteOrgFormTest() {
        OrgForm orgForm = OrgForm.builder()
                .name("aaa")
                .build();
        repository.saveAndFlush(orgForm);
        List<OrgForm> orgForms = repository.findAll();
        Assertions.assertEquals(orgForms.size(), 4);
        repository.deleteById(1);
        orgForms = repository.findAll();
        Assertions.assertEquals(orgForms.size(), 3);
    }

}
