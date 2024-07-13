package org.example.contractor.mapper;

import org.example.contractor.dto.CountryDTO;
import org.example.contractor.entity.Country;

/**
 * Класс предназначенный для преобразования Country в CountryDTO и наоборот
 */
public interface CountryMapper {

    /**
     * Преобразует CountryDTO в Country
     * @param countryDTO CountryDTO
     * @return Country
     */
    Country map(CountryDTO countryDTO);

    /**
     * Преобразует Country в CountryDTO
     * @param country Country
     * @return CountryDTO
     */
    CountryDTO map(Country country);

}
