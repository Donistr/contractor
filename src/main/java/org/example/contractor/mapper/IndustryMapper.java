package org.example.contractor.mapper;

import org.example.contractor.dto.IndustryDTO;
import org.example.contractor.entity.Industry;

public interface IndustryMapper {

    Industry map(IndustryDTO industryDTO);

    IndustryDTO map(Industry industry);

}
