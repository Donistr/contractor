package org.example.contractor.service.impl;

import org.example.contractor.dto.CountryDTO;
import org.example.contractor.entity.Country;
import org.example.contractor.exception.CountryNotFoundException;
import org.example.contractor.mapper.CountryMapper;
import org.example.contractor.repository.CountryRepository;
import org.example.contractor.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс реализует интерфейс {@link CountryService}
 */
@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository repository;

    private final CountryMapper mapper;

    @Autowired
    public CountryServiceImpl(CountryRepository repository, CountryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CountryDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryDTO getById(String id) {
        return mapper.map(repository.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("не найдена страна с id = " + id)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountryDTO save(CountryDTO countryDTO) {
        Country country = mapper.map(countryDTO);

        Optional<Country> fromDatabaseOptional = repository.findById(country.getId());
        if (fromDatabaseOptional.isEmpty()) {
            return mapper.map(repository.saveAndFlush(country));
        }

        Country fromDatabase = fromDatabaseOptional.get();
        fromDatabase.setName(country.getName());

        return mapper.map(repository.saveAndFlush(fromDatabase));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

}
