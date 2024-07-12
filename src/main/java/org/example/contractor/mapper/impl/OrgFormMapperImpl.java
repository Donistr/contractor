package org.example.contractor.mapper.impl;

import org.example.contractor.dto.OrgFormDTO;
import org.example.contractor.entity.OrgForm;
import org.example.contractor.mapper.OrgFormMapper;
import org.springframework.stereotype.Component;

/**
 * Реализация интерфейса {@link OrgFormMapper}
 */
@Component
public class OrgFormMapperImpl implements OrgFormMapper {

    /**
     * {@inheritDoc}
     */
    @Override
    public OrgForm map(OrgFormDTO orgFormDTO) {
        return OrgForm.builder()
                .id(orgFormDTO.getId())
                .name(orgFormDTO.getName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrgFormDTO map(OrgForm orgForm) {
        return OrgFormDTO.builder()
                .id(orgForm.getId())
                .name(orgForm.getName())
                .build();
    }

}
