package org.example.contractor.mapper.impl;

import org.example.contractor.dto.ContractorDTO;
import org.example.contractor.entity.Contractor;
import org.example.contractor.mapper.ContractorMapper;
import org.example.contractor.mapper.CountryMapper;
import org.example.contractor.mapper.IndustryMapper;
import org.example.contractor.mapper.OrgFormMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractorMapperImpl implements ContractorMapper {

    private final CountryMapper countryMapper;

    private final IndustryMapper industryMapper;

    private final OrgFormMapper orgFormMapper;

    @Autowired
    public ContractorMapperImpl(CountryMapper countryMapper, IndustryMapper industryMapper, OrgFormMapper orgFormMapper) {
        this.countryMapper = countryMapper;
        this.industryMapper = industryMapper;
        this.orgFormMapper = orgFormMapper;
    }

    @Override
    public Contractor map(ContractorDTO contractorDTO) {
        return Contractor.builder()
                .id(contractorDTO.getId())
                .parentId(contractorDTO.getParentId())
                .name(contractorDTO.getName())
                .fullName(contractorDTO.getFullName())
                .inn(contractorDTO.getInn())
                .ogrn(contractorDTO.getOgrn())
                .country(countryMapper.map(contractorDTO.getCountry()))
                .industry(industryMapper.map(contractorDTO.getIndustry()))
                .orgForm(orgFormMapper.map(contractorDTO.getOrgForm()))
                .build();
    }

    @Override
    public ContractorDTO map(Contractor contractor) {
        return ContractorDTO.builder()
                .id(contractor.getId())
                .parentId(contractor.getParentId())
                .name(contractor.getName())
                .fullName(contractor.getFullName())
                .inn(contractor.getInn())
                .ogrn(contractor.getOgrn())
                .country(countryMapper.map(contractor.getCountry()))
                .industry(industryMapper.map(contractor.getIndustry()))
                .orgForm(orgFormMapper.map(contractor.getOrgForm()))
                .build();
    }

}
