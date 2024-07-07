package org.example.contractor.mapper;

import org.example.contractor.dto.OrgFormDTO;
import org.example.contractor.entity.OrgForm;

public interface OrgFormMapper {

    OrgForm map(OrgFormDTO orgFormDTO);

    OrgFormDTO map(OrgForm orgForm);

}
