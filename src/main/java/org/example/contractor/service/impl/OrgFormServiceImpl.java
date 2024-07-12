package org.example.contractor.service.impl;

import org.example.contractor.dto.OrgFormDTO;
import org.example.contractor.entity.OrgForm;
import org.example.contractor.exception.OrgFormNotFoundException;
import org.example.contractor.mapper.OrgFormMapper;
import org.example.contractor.repository.OrgFormRepository;
import org.example.contractor.service.OrgFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс реализует интерфейс {@link OrgFormService}
 */
@Service
public class OrgFormServiceImpl implements OrgFormService {

    private final OrgFormRepository repository;

    private final OrgFormMapper mapper;

    @Autowired
    public OrgFormServiceImpl(OrgFormRepository repository, OrgFormMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrgFormDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrgFormDTO getById(Integer id) {
        return mapper.map(repository.findById(id)
                .orElseThrow(() -> new OrgFormNotFoundException("не найдена организационная форма с id = " + id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrgFormDTO save(OrgFormDTO countryDTO) {
        OrgForm orgForm = mapper.map(countryDTO);

        Optional<OrgForm> fromDatabaseOptional = repository.findById(orgForm.getId());
        if (fromDatabaseOptional.isEmpty()) {
            return mapper.map(repository.saveAndFlush(orgForm));
        }

        OrgForm fromDatabase = fromDatabaseOptional.get();
        fromDatabase.setName(orgForm.getName());

        return mapper.map(repository.saveAndFlush(fromDatabase));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

}
