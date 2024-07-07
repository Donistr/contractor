package org.example.contractor.service;

import org.example.contractor.dto.OrgFormDTO;

import java.util.List;

public interface OrgFormService {

    List<OrgFormDTO> getAll();

    OrgFormDTO getById(Integer id);

    OrgFormDTO save(OrgFormDTO countryDTO);

    void deleteById(Integer id);

}
