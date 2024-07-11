package org.example.contractor.mapper.impl;

import org.example.contractor.dto.IndustryDTO;
import org.example.contractor.entity.Industry;
import org.example.contractor.mapper.IndustryMapper;
import org.springframework.stereotype.Component;

@Component
public class IndustryMapperImpl implements IndustryMapper {

    @Override
    public Industry map(IndustryDTO industryDTO) {
        return Industry.builder()
                .id(industryDTO.getId())
                .name(industryDTO.getName())
                .build();
    }

    @Override
    public IndustryDTO map(Industry industry) {
        return IndustryDTO.builder()
                .id(industry.getId())
                .name(industry.getName())
                .build();
    }

}
