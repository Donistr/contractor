package org.example.contractor.service.impl;

import org.example.contractor.dto.IndustryDTO;
import org.example.contractor.entity.Industry;
import org.example.contractor.exception.IndustryNotFoundException;
import org.example.contractor.mapper.IndustryMapper;
import org.example.contractor.repository.IndustryRepository;
import org.example.contractor.service.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс реализует интерфейс {@link IndustryService}
 */
@Service
public class IndustryServiceImpl implements IndustryService {

    private final IndustryRepository repository;

    private final IndustryMapper mapper;

    @Autowired
    public IndustryServiceImpl(IndustryRepository repository, IndustryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IndustryDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndustryDTO getById(Integer id) {
        return mapper.map(repository.findById(id)
                .orElseThrow(() -> new IndustryNotFoundException("не найден индустриальный код с id " + id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndustryDTO save(IndustryDTO countryDTO) {
        Industry industry = mapper.map(countryDTO);

        Optional<Industry> fromDatabaseOptional = repository.findById(industry.getId());
        if (fromDatabaseOptional.isEmpty()) {
            return mapper.map(repository.saveAndFlush(industry));
        }

        Industry fromDatabase = fromDatabaseOptional.get();
        fromDatabase.setName(industry.getName());

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
