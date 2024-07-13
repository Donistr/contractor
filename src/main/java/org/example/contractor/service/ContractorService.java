package org.example.contractor.service;

import org.example.contractor.dto.ContractorDTO;
import org.example.contractor.messages.SearchContractorRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Класс предоставляющий методы работы с контрагентами
 */
public interface ContractorService {

    /**
     * Сохранить контрагента, если передан существующий, добавить нового, если не существует
     * @param contractorDTO контрагент
     * @return контрагент
     */
    ContractorDTO save(ContractorDTO contractorDTO);

    /**
     * Получает контрагента по id
     * @param id id контрагента
     * @return контрагент
     */
    ContractorDTO getById(String id);

    /**
     * Удаляет контрагента по id
     * @param id id контрагента
     */
    void deleteById(String id);

    /**
     * Получает контрагентов в соответствии с запросом и пагинацией
     * @param request запрос
     * @param pageable пагинация
     * @return список контрагентов
     */
    List<ContractorDTO> getContractors(SearchContractorRequest request, Pageable pageable);

    /**
     * Получает контрагентов в соответствии с запросом и пагинацией (используя sql)
     * @param request запрос
     * @param pageable пагинация
     * @return список контрагентов
     */
    List<ContractorDTO> getContractorsSql(SearchContractorRequest request, Pageable pageable);

}
