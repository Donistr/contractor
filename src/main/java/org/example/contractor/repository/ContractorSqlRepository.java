package org.example.contractor.repository;

import org.example.contractor.dto.ContractorDTO;
import org.example.contractor.messages.SearchContractorRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Класс представляет репозиторий для контрагентов, методы которого реализованы с использованием нативного sql
 */
public interface ContractorSqlRepository {

    /**
     * Находит всех контрагентов в соответствии с запросом пользователя и пагинацией
     * @param request запрос пользователя
     * @param pageable пагинация
     * @return список найденных контрагентов
     */
    List<ContractorDTO> findAll(SearchContractorRequest request, Pageable pageable);

}
