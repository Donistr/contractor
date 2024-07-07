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
        Contractor result = new Contractor();
        result.setId(contractorDTO.getId());
        result.setParentId(contractorDTO.getParentId());
        result.setName(contractorDTO.getName());
        result.setFullName(contractorDTO.getFullName());
        result.setInn(contractorDTO.getInn());
        result.setOgrn(contractorDTO.getOgrn());
        result.setCountry(countryMapper.map(contractorDTO.getCountry()));
        result.setIndustry(industryMapper.map(contractorDTO.getIndustry()));
        result.setOrgForm(orgFormMapper.map(contractorDTO.getOrgForm()));

        return result;
    }

    @Override
    public ContractorDTO map(Contractor contractor) {
        ContractorDTO result = new ContractorDTO();
        result.setId(contractor.getId());
        result.setParentId(contractor.getParentId());
        result.setName(contractor.getName());
        result.setFullName(contractor.getFullName());
        result.setInn(contractor.getInn());
        result.setOgrn(contractor.getOgrn());
        result.setCountry(countryMapper.map(contractor.getCountry()));
        result.setIndustry(industryMapper.map(contractor.getIndustry()));
        result.setOrgForm(orgFormMapper.map(contractor.getOrgForm()));

        return result;
    }

}
