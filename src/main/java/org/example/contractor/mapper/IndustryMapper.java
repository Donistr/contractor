package org.example.contractor.mapper;

import org.example.contractor.dto.IndustryDTO;
import org.example.contractor.entity.Industry;

/**
 * Класс предназначенный для преобразования Industry в IndustryDTO и наоборот
 */
public interface IndustryMapper {

    /**
     * Преобразует IndustryDTO в Industry
     * @param industryDTO IndustryDTO
     * @return Industry
     */
    Industry map(IndustryDTO industryDTO);

    /**
     * Преобразует Industry в IndustryDTO
     * @param industry Industry
     * @return IndustryDTO
     */
    IndustryDTO map(Industry industry);

}
