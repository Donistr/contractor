package org.example.contractor.mapper.impl;

import org.example.contractor.dto.OrgFormDTO;
import org.example.contractor.entity.OrgForm;
import org.example.contractor.mapper.OrgFormMapper;
import org.springframework.stereotype.Component;

@Component
public class OrgFormMapperImpl implements OrgFormMapper {

    @Override
    public OrgForm map(OrgFormDTO orgFormDTO) {
        OrgForm result = new OrgForm();
        result.setId(orgFormDTO.getId());
        result.setName(orgFormDTO.getName());

        return result;
    }

    @Override
    public OrgFormDTO map(OrgForm orgForm) {
        OrgFormDTO result = new OrgFormDTO();
        result.setId(orgForm.getId());
        result.setName(orgForm.getName());

        return result;
    }

}
