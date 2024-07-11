package org.example.contractor.mapper.impl;

import org.example.contractor.dto.OrgFormDTO;
import org.example.contractor.entity.OrgForm;
import org.example.contractor.mapper.OrgFormMapper;
import org.springframework.stereotype.Component;

@Component
public class OrgFormMapperImpl implements OrgFormMapper {

    @Override
    public OrgForm map(OrgFormDTO orgFormDTO) {
        return OrgForm.builder()
                .id(orgFormDTO.getId())
                .name(orgFormDTO.getName())
                .build();
    }

    @Override
    public OrgFormDTO map(OrgForm orgForm) {
        return OrgFormDTO.builder()
                .id(orgForm.getId())
                .name(orgForm.getName())
                .build();
    }

}
