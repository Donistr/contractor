package org.example.contractor.mapper.impl;

import org.example.contractor.dto.IndustryDTO;
import org.example.contractor.entity.Industry;
import org.example.contractor.mapper.IndustryMapper;
import org.springframework.stereotype.Component;

@Component
public class IndustryMapperImpl implements IndustryMapper {

    @Override
    public Industry map(IndustryDTO industryDTO) {
        Industry result = new Industry();
        result.setId(industryDTO.getId());
        result.setName(industryDTO.getName());

        return result;
    }

    @Override
    public IndustryDTO map(Industry industry) {
        IndustryDTO result = new IndustryDTO();
        result.setId(industry.getId());
        result.setName(industry.getName());

        return result;
    }

}
