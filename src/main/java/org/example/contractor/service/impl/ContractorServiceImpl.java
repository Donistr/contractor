package org.example.contractor.service.impl;

import org.example.contractor.dto.ContractorDTO;
import org.example.contractor.entity.Contractor;
import org.example.contractor.exception.ContractorNotFoundException;
import org.example.contractor.mapper.ContractorMapper;
import org.example.contractor.repository.ContractorRepository;
import org.example.contractor.service.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

}
