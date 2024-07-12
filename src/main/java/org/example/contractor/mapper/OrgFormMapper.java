package org.example.contractor.mapper;

import org.example.contractor.dto.OrgFormDTO;
import org.example.contractor.entity.OrgForm;

/**
 * Класс предназначенный для преобразования OrgForm в OrgFormDTO и наоборот
 */
public interface OrgFormMapper {

    /**
     * Преобразует OrgFormDTO в OrgForm
     * @param orgFormDTO OrgFormDTO
     * @return OrgForm
     */
    OrgForm map(OrgFormDTO orgFormDTO);

    /**
     * Преобразует OrgForm в OrgFormDTO
     * @param orgForm OrgForm
     * @return OrgFormDTO
     */
    OrgFormDTO map(OrgForm orgForm);

}
