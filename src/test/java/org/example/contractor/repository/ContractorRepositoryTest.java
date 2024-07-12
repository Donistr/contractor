package org.example.contractor.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.example.contractor.dto.CountryDTO;
import org.example.contractor.dto.IndustryDTO;
import org.example.contractor.dto.OrgFormDTO;
import org.example.contractor.entity.Contractor;
import org.example.contractor.entity.Country;
import org.example.contractor.messages.SearchContractorRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Testcontainers
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ContractorRepositoryTest {

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
    private ContractorRepository repository;

    @Test
    public void getContractorByIdTest() {
        Optional<Contractor> contractor = repository.findById("id_1");
        Assertions.assertTrue(contractor.isPresent());
        Assertions.assertEquals(contractor.get().getId(), "id_1");
        Assertions.assertEquals(contractor.get().getParentId(), "parent_id_1");
        Assertions.assertEquals(contractor.get().getName(), "name_1");
        Assertions.assertEquals(contractor.get().getFullName(), "name_full_1");
        Assertions.assertEquals(contractor.get().getInn(), "inn_1");
        Assertions.assertEquals(contractor.get().getOgrn(), "ogrn_1");
        Assertions.assertEquals(contractor.get().getCountry().getId(), "ABH");
        Assertions.assertEquals(contractor.get().getCountry().getName(), "Абхазия");
        Assertions.assertEquals(contractor.get().getIndustry().getId(), 1);
        Assertions.assertEquals(contractor.get().getIndustry().getName(), "Авиастроение");
        Assertions.assertEquals(contractor.get().getOrgForm().getId(), 2);
        Assertions.assertEquals(contractor.get().getOrgForm().getName(), "Автономная некоммерческая организация");
    }

    @Test
    @Transactional
    @Rollback
    public void updateContractorTest() {
        Contractor contractor = repository.findById("id_1").orElseThrow();
        contractor.setName("123");
        Contractor saved = repository.saveAndFlush(contractor);
        Assertions.assertEquals(contractor.getId(), saved.getId());
        Assertions.assertEquals(contractor.getName(), saved.getName());
    }

    @Test
    @Transactional
    @Rollback
    public void deleteContractorTest() {
        List<Contractor> contractors = repository.findAll();
        Assertions.assertEquals(contractors.size(), 2);
        repository.deleteById("id_1");
        contractors = repository.findAll();
        Assertions.assertEquals(contractors.size(), 1);
    }

    @Test
    @Transactional
    @Rollback
    public void searchContractorsTest() {
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

        Assertions.assertEquals(repository.findAll(createSpecification(request)).size(), 1);
    }

    private Specification<Contractor> createSpecification(SearchContractorRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isTrue(root.get("isActive")));
            addEqualPredicate(predicates, root, criteriaBuilder, "id", request.getId());
            addEqualPredicate(predicates, root, criteriaBuilder, "parentId", request.getParentId());
            addLikePredicate(predicates, root, criteriaBuilder, "name", request.getName());
            addLikePredicate(predicates, root, criteriaBuilder, "fullName", request.getFullName());
            addLikePredicate(predicates, root, criteriaBuilder, "inn", request.getInn());
            addLikePredicate(predicates, root, criteriaBuilder, "ogrn", request.getOgrn());

            CountryDTO country = request.getCountry();
            if (country != null) {
                Join<Contractor, Country> join = root.join("country");
                predicates.add(criteriaBuilder.like(join.get("name"), "%" + country.getName() + "%"));
            }

            IndustryDTO industry = request.getIndustry();
            if (industry != null) {
                Join<Contractor, Country> join = root.join("industry");
                predicates.add(criteriaBuilder.equal(join.get("id"), industry.getId()));
                predicates.add(criteriaBuilder.equal(join.get("name"), industry.getName()));
            }

            OrgFormDTO orgForm = request.getOrgForm();
            if (orgForm != null) {
                Join<Contractor, Country> join = root.join("orgForm");
                predicates.add(criteriaBuilder.like(join.get("name"), "%" + orgForm.getName() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addEqualPredicate(List<Predicate> predicates, Root<Contractor> root,
                                          CriteriaBuilder criteriaBuilder, String field, String value) {
        if (value == null) {
            return;
        }

        predicates.add(criteriaBuilder.equal(root.get(field), value));
    }

    private static void addLikePredicate(List<Predicate> predicates, Root<Contractor> root,
                                         CriteriaBuilder criteriaBuilder, String field, String value) {
        if (value == null) {
            return;
        }

        predicates.add(criteriaBuilder.like(root.get(field), "%" + value + "%"));
    }

}
