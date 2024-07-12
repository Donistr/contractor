package org.example.contractor.service;

import org.example.contractor.dto.OrgFormDTO;

import java.util.List;

/**
 * Класс предоставляющий методы работы с организационными формами
 */
public interface OrgFormService {

    /**
     * Получает все организационные формы
     * @return список всех организационных форм
     */
    List<OrgFormDTO> getAll();

    /**
     * Получает организационную форму по id
     * @param id id организационной формы
     * @return
     */
    OrgFormDTO getById(Integer id);

    /**
     * Сохраняет/создаёт организационную форму
     * @param countryDTO организационная форма
     * @return организационная форма
     */
    OrgFormDTO save(OrgFormDTO countryDTO);

    /**
     * Удаляет организационную форму по id
     * @param id id организационной формы
     */
    void deleteById(Integer id);

}
