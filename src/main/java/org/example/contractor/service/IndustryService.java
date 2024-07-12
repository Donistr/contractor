package org.example.contractor.service;

import org.example.contractor.dto.IndustryDTO;

import java.util.List;

/**
 * Класс предоставляющий методы работы с индустриальными кодами
 */
public interface IndustryService {

    /**
     * Получает все индустриальные коды
     * @return список всех индустриальных кодов
     */
    List<IndustryDTO> getAll();

    /**
     * Получает индустриальный код по id
     * @param id id индустриального кода
     * @return индустриальный код
     */
    IndustryDTO getById(Integer id);

    /**
     * Сохраняет/создаёт индустриальный код
     * @param countryDTO индустриальный код
     * @return индустриальный код
     */
    IndustryDTO save(IndustryDTO countryDTO);

    /**
     * Удаляет индустриальный код по id
     * @param id id индустриального кода
     */
    void deleteById(Integer id);

}
