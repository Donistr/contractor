package org.example.contractor.service;

import org.example.contractor.dto.CountryDTO;

import java.util.List;

/**
 * Класс предоставляющий методы работы со странами
 */
public interface CountryService {

    /**
     * Получает все страны
     * @return список всех стран
     */
    List<CountryDTO> getAll();

    /**
     * Получает страну по id
     * @param id id страны
     * @return страна
     */
    CountryDTO getById(String id);

    /**
     * Сохраняет/создаёт страну
     * @param countryDTO страна
     * @return страна
     */
    CountryDTO save(CountryDTO countryDTO);

    /**
     * Удаляет страну по id
     * @param id id страны
     */
    void deleteById(String id);

}
