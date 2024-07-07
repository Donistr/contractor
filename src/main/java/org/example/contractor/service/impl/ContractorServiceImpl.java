package org.example.contractor.service.impl;

import jakarta.persistence.criteria.*;
import org.example.contractor.dto.ContractorDTO;
import org.example.contractor.dto.CountryDTO;
import org.example.contractor.dto.IndustryDTO;
import org.example.contractor.dto.OrgFormDTO;
import org.example.contractor.entity.Contractor;
import org.example.contractor.entity.Country;
import org.example.contractor.exception.ContractorNotFoundException;
import org.example.contractor.mapper.ContractorMapper;
import org.example.contractor.messages.SearchContractorRequest;
import org.example.contractor.repository.ContractorRepository;
import org.example.contractor.service.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractorServiceImpl implements ContractorService {

    private final ContractorRepository repository;

    private final ContractorMapper mapper;

    @Autowired
    public ContractorServiceImpl(ContractorRepository repository, ContractorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ContractorDTO save(ContractorDTO contractorDTO) {
        Contractor contractor = mapper.map(contractorDTO);

        Optional<Contractor> fromDatabaseOptional = repository.findById(contractor.getId());
        if (fromDatabaseOptional.isEmpty()) {
            return mapper.map(repository.saveAndFlush(contractor));
        }

        Contractor fromDatabase = fromDatabaseOptional.get();
        fromDatabase.setParentId(contractor.getParentId());
        fromDatabase.setName(contractor.getName());
        fromDatabase.setFullName(contractor.getFullName());
        fromDatabase.setInn(contractor.getInn());
        fromDatabase.setOgrn(contractor.getOgrn());
        fromDatabase.setCountry(contractor.getCountry());
        fromDatabase.setIndustry(contractor.getIndustry());
        fromDatabase.setOrgForm(contractor.getOrgForm());

        return mapper.map(repository.saveAndFlush(fromDatabase));
    }

    @Override
    public ContractorDTO getById(String id) {
        return mapper.map(repository.findById(id)
                .orElseThrow(() -> new ContractorNotFoundException("not found contractor with id = " + id)));
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<ContractorDTO> getContractors(SearchContractorRequest request, Pageable pageable) {
        return repository.findAll(createSpecification(request), pageable)
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
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
